package obed.me.lccommons.test;

import obed.me.lccommons.api.entities.PlayerData;
import obed.me.lccommons.api.entities.punishments.Punishment;
import obed.me.lccommons.api.entities.punishments.PunishmentType;
import obed.me.lccommons.api.services.PunishmentHistoryProvider;
import obed.me.lccommons.api.services.UserProvider;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        PlayerData playerData = UserProvider.getInstance().getUserByName("obedmz");

        Punishment punishment = new Punishment();
        punishment.setActive(true);
        punishment.setPermanent(true);
        punishment.setIp("192.168.0.1");
        punishment.setIsIP(false);
        punishment.setType(PunishmentType.BAN);
        punishment.setIssuedInstant(Instant.now());
        punishment.setExpiresInstant(Instant.now().plus(Duration.ofMinutes(2)));
        punishment.setPlayer(playerData.getUuid());
        punishment.setReason("por peruano");
        punishment.setPunisher(UUID.randomUUID());
        PunishmentHistoryProvider.getInstance().savePunishment(punishment);
        System.out.println(playerData);
    }
    //La sanción expira correctamente y se cambia su estado a inactivo.
    //Agregar validación de si es permanente antes de hacer la validación de expiración.
    //agregar el filtro de TypeBan, agregar la query isIP para devolver los baneos por ip activo.


}
