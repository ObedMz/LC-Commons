package obed.me.lccommons.test;

import obed.me.lccommons.api.entities.groups.Rank;
import obed.me.lccommons.api.services.RankProvider;

public class Main {
    public static void main(String[] args) {
        Rank rank = RankProvider.getInstance().getDefaultRank();
        System.out.println("Default rank is:" + rank);
    }
}
