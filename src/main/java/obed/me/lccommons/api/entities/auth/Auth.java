package obed.me.lccommons.api.entities.auth;

import lombok.Data;

import java.time.Instant;

@Data
public class Auth {
    private String ip;
    private Instant lastConnection;
    private boolean Logged;
    private String status;
}
