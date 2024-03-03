package obed.me.lccommons.test;

import obed.me.lccommons.api.entities.PlayerData;
import obed.me.lccommons.api.entities.groups.Rank;
import obed.me.lccommons.api.services.RankProvider;
import obed.me.lccommons.api.services.UserProvider;

public class Main {
    public static void main(String[] args) {
        Rank rank = new Rank();
        rank.setDefaultRank(false);
        rank.setName("VIP");
        rank = RankProvider.getInstance().createRank(rank);
        System.out.println(rank.getName());
    }
}
