package obed.me.lccommons.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import obed.me.lccommons.api.entities.SvInfo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.*;

@Data
public class RedisProvider {
    private static volatile RedisProvider INSTANCE;
    private final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
    private final ExecutorService poolExecutor = Executors.newCachedThreadPool();
    private Boolean Running = false;
    private SvInfo serverInfo;
    private String REDIS_KEY;
    private JedisPool jedisPool;

    private RedisProvider() {}

    public static RedisProvider getInstance() {
        if(INSTANCE ==null) {
            synchronized (RedisProvider.class) {
                if(INSTANCE == null)INSTANCE = new RedisProvider();
            }
        }
        return INSTANCE;
    }
    /**
     * Inicializa la conexión con Redis.
     * @param serverName Nombre del servidor.
     * @param host       Dirección del servidor de Redis.
     * @param port       Puerto del servidor de Redis.
     * @param passwd     Contraseña del servidor de Redis (si es necesario).
     * @param key        Clave para identificar los datos en Redis.
     * @param max        Número máximo de conexiones en el pool.
     */
    public void init(SvInfo svInfo , String serverName, String host, int port, String passwd, String key, Integer max) {
        REDIS_KEY = key;
        try{
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(max);
            jedisPool = new JedisPool(poolConfig, host, port, 2000, passwd);
            System.out.println("Successfully connected tu redis server.");
            setServerInfo(svInfo);
            CompletableFuture.runAsync(this::run);
        }catch (Exception e){
            System.out.println("An error occurred during initialization of Redis CLI.");
            e.printStackTrace();
        }
    }
    public void stop() {
        if (getRunning()) {
            setRunning(false);
            schedule.shutdown();
        }
    }

    private void run() {
        schedule.scheduleAtFixedRate(this::updateData, 5, 1, TimeUnit.SECONDS);
    }


    public Jedis getResource() {
        return jedisPool.getResource();
    }
    public void close() {
        if (jedisPool != null) {
            jedisPool.close();
            schedule.shutdown();
        }
    }
    /**
     * Método encargado de actualizar el objeto SVInformation
     * del servidor y enviarla a REDIS.
     * @see SvInfo
     */
    private void updateData() {
        try (Jedis jedis = getResource()) {
            jedis.hset(REDIS_KEY, serverInfo.getServerId(), serverInfo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<SvInfo> getDataFrom(String KEY, String serverId){
        return CompletableFuture.supplyAsync(() -> {
            try (Jedis jedis = getResource()) {
                String jsonString = jedis.hget(KEY, serverId);
                if (jsonString != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(jsonString, SvInfo.class);
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }, poolExecutor);
    }
    public CompletableFuture<SvInfo> getDataFrom(String serverId){
            return getDataFrom(getREDIS_KEY(), serverId);
    }
}
