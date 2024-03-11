package obed.me.lccommons.spigot;

import obed.me.lccommons.api.entities.SvInfo;
import obed.me.lccommons.api.services.RedisProvider;
import obed.me.lccommons.spigot.handlers.RankDataLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpigotCommons extends JavaPlugin {
    private static SpigotCommons instance;
    @Override
    public void onLoad(){
        instance = this;
    }
    @Override
    public void onEnable() {
        saveDefaultConfig();
        if(getConfig().getBoolean("loadJoin"))
            Bukkit.getPluginManager().registerEvents(new RankDataLoader(), this);
        registerRedisServer();
    }

    private void registerRedisServer(){
        SvInfo svInfo = new SvInfo();
        svInfo.setServerId(Bukkit.getServerName());
        svInfo.setIp(Bukkit.getServer().getIp());
        svInfo.setPort(Bukkit.getServer().getPort());
        RedisProvider.getInstance().init(svInfo, Bukkit.getServerName(), getConfig().getString("redis.ip"),
                getConfig().getInt("redis.port"),
                getConfig().getString("redis.pwd"),
                getConfig().getString("redis.key"),
                getConfig().getInt("redis.max"));
    }
    public static SpigotCommons getInstance(){
        return instance;
    }

}
