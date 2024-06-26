package obed.me.lccommons.api.entities;

import lombok.Data;
import obed.me.lccommons.api.entities.auth.Auth;
import obed.me.lccommons.api.entities.groups.RankInfo;
import obed.me.lccommons.api.entities.punishments.Punishment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PlayerData {
    private UUID uuid;
    private boolean premium;
    private String username;
    private String password;
    private int coins;
    private int vipPoints;
    private Auth authInfo = new Auth();
    private RankInfo rankInfo = new RankInfo();
    private List<Punishment> punishmentList = new ArrayList<>();
}
