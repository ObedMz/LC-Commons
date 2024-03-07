package obed.me.lccommons.api.entities.punishments;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PunishmentHistory {
    private UUID player;
    private List<Punishment> punishmentList = new ArrayList<>();
}
