package obed.me.lccommons.api.entities;

import lombok.Data;
import obed.me.lccommons.api.entities.auth.Auth;
import obed.me.lccommons.api.entities.groups.Rank;
import obed.me.lccommons.api.entities.punishments.Punishment;

import java.util.UUID;

@Data
public class User {
    private UUID uuid;
    private boolean premium;
    private String username;
    private String password;
    private Auth authInfo;
    private Rank rank;
    private Punishment Activepunishment;

    public User(String name, String password){
        this.username = name;
        this.password = password;
    }
}
