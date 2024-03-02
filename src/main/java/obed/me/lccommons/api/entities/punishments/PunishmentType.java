package obed.me.lccommons.api.entities.punishments;

public enum PunishmentType {

    BAN,
    MUTE,
    KICK,
    WARN;

    public static PunishmentType fromString(String type) {
        for (PunishmentType punishmentType : values()) {
            if (punishmentType.name().equalsIgnoreCase(type)) {
                return punishmentType;
            }
        }
        return null;
    }
}
