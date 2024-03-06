package obed.me.lccommons.api.entities.punishments;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class Punishment {
    private UUID punisher;

    private String reason;

    private Instant issuedInstant;

    private Instant expiresInstant;

    private Boolean active;

    private Boolean permanent;

    private PunishmentType type;
}
