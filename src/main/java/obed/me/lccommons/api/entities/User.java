package obed.me.lccommons.api.entities;

import lombok.Data;
import obed.me.lccommons.api.entities.auth.Auth;
import obed.me.lccommons.api.entities.groups.Rank;
import obed.me.lccommons.api.entities.punishments.Punishment;

import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String name;
    private Auth authInfo;
    private Rank rank;
    private Punishment punishment;

    public User(String name){
        this.name = name;
    }
}
