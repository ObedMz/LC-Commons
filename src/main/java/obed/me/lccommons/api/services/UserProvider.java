package obed.me.lccommons.api.services;

import lombok.Getter;
import obed.me.lccommons.api.entities.PlayerData;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class UserProvider {
    private static volatile UserProvider instance;
    private final WebClient apiClient = WebClient.getInstance();
    private ConcurrentHashMap<String, PlayerData> cache = new ConcurrentHashMap<>();
    private final String ENDPOINT = EndPointType.USER.getEndPoint();
    private UserProvider() {
    }

    public static UserProvider getInstance(){
        if(instance == null){
            synchronized (UserProvider.class){
                if(instance == null)
                    instance = new UserProvider();
            }
        }
        return instance;
    }

    public PlayerData createUser(PlayerData user) {
        user = apiClient.create(ENDPOINT, user, PlayerData.class);
        cache.put(user.getUsername(), user);
        return user;
    }

    public PlayerData getUserByName(String name) {
        PlayerData playerData = apiClient.get(ENDPOINT.concat("/" + name), PlayerData.class);
        cache.put(name, playerData);
        return playerData;
    }
    public void deleteUser(String name){
        apiClient.delete(ENDPOINT.concat(name));
        cache.remove(name);
    }
    public boolean isExpiredRank(PlayerData jug) {
        Instant currentInstant = Instant.now();
        Instant expirationInstant = jug.getRankInfo().getExpiresInstant();
        return currentInstant.isAfter(expirationInstant);
    }
}
