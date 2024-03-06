package obed.me.lccommons.test;

import obed.me.lccommons.api.entities.PlayerData;
import obed.me.lccommons.api.entities.groups.Rank;
import obed.me.lccommons.api.services.RankProvider;
import obed.me.lccommons.api.services.UserProvider;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        PlayerJoin("ObedMz");
    }


    public static void PlayerJoin(String str){
        PlayerData playerData = UserProvider.getInstance().getUserByName(str);
        if(playerData == null){
            System.out.println("data not found");
            return;
        }
        if(UserProvider.getInstance().isExpiredRank(playerData)){
            System.out.println("rank is expired.");
            playerData.getRankInfo().setRank(RankProvider.getInstance().getDefaultRank());
            playerData = UserProvider.getInstance().createUser(playerData);
        } else {
            System.out.println("rank is not expired.");

        }
        System.out.println("Data loaded for user: " + playerData);
    }
}
