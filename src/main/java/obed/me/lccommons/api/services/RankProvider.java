package obed.me.lccommons.api.services;

import obed.me.lccommons.api.entities.groups.Rank;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RankProvider {
    private static volatile RankProvider instance;
    private final WebClient apiClient = WebClient.getInstance();
    private ConcurrentHashMap<String, Rank> rankCache = new ConcurrentHashMap<>();
    private RankProvider() {
    }

    public static RankProvider getInstance(){
        if(instance == null){
            synchronized (RankProvider.class){
                if(instance == null)
                    instance = new RankProvider();
            }
        }
        return instance;
    }

    public Rank createRank(Rank rank) {
        return apiClient.create(EndPointType.RANK.getEndPoint(), rank, Rank.class);
    }

    public Rank getRankByName(String name) {
        return apiClient.get(EndPointType.RANK.getEndPoint().concat(name), Rank.class);
    }
    public List<Rank> getAllRanks() {
        return List.of(apiClient.get(EndPointType.RANKS.getEndPoint(), Rank[].class));
    }


    public void deleteRank(String name){
        apiClient.delete(EndPointType.RANK.getEndPoint().concat(name));
    }
}
