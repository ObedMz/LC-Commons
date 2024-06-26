package obed.me.lccommons.api.entities.punishments;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class Punishment {
    private UUID id;
    private String punisher;

    private String reason;

    private Instant issuedInstant;

    private Instant expiresInstant;

    private Boolean active;

    private Boolean permanent;
    private String ip;
    private UUID player;
    private Boolean isIP;
    private PunishmentType type;
}
