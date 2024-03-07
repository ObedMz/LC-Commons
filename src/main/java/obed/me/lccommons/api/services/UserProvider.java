package obed.me.lccommons.api.services;

import obed.me.lccommons.api.entities.PlayerData;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class UserProvider {
    private static volatile UserProvider instance;
    private final APIClient apiClient = APIClient.getInstance();
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
    public PlayerData getUserCache(String name){
        PlayerData playerData = cache.getOrDefault(name, null);
        if(playerData == null){
            playerData = getUserByName(name);
            cache.put(name, playerData);
        }
        return playerData;
    }
    public PlayerData createUser(PlayerData user) {
        user = apiClient.create(ENDPOINT, user, PlayerData.class);
        cache.put(user.getUsername(), user);
        return user;
    }

    public PlayerData getUserByName(String name) {
        PlayerData playerData = apiClient.get(ENDPOINT.concat("/" + name), PlayerData.class);
        if(playerData != null){
            cache.put(name, playerData);
            return playerData;
        }
        return null;
    }

    public void savePlayer(PlayerData playerData){
        playerData = apiClient.create(ENDPOINT, playerData, PlayerData.class);
        cache.put(playerData.getUsername(), playerData);
    }
    public void deleteUser(String name){
        apiClient.delete(ENDPOINT.concat(name));
        cache.remove(name);
    }
    public boolean isExpiredRank(PlayerData jug) {
        if(jug.getRankInfo().isPermanent()||
        jug.getRankInfo().getRank().equals(RankProvider.getInstance().getStoredDefaultRank())){
            return false;
        }
        Instant currentInstant = Instant.now();
        Instant expirationInstant = jug.getRankInfo().getExpiresInstant();

        if(expirationInstant == null)
            return false;

        return currentInstant.isAfter(expirationInstant);
    }

    public PlayerData checkActivePunishment(PlayerData jug) {
        Instant currentInstant = Instant.now();
        jug.getActivePunishment().getPunishmentList().forEach(p -> {
            Instant expirationInstant = p.getExpiresInstant();
            if(currentInstant.isAfter(expirationInstant))
                jug.getActivePunishment().getPunishmentList().remove(p);
        });
        return jug;
    }

}
