package obed.me.lccommons.api.entities.groups;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class RankInfo {
    private UUID uuid;
    private Rank rank;
    private boolean permanent;
    private boolean hide;
    private String userColor;
    private Instant dateInstant;
    private Instant expiresInstant;
}
