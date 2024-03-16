package obed.me.lccommons.test;

import obed.me.lccommons.api.entities.PlayerData;
import obed.me.lccommons.api.entities.punishments.Punishment;
import obed.me.lccommons.api.entities.punishments.PunishmentType;
import obed.me.lccommons.api.services.PunishmentHistoryProvider;
import obed.me.lccommons.api.services.UserProvider;
import obed.me.lccommons.api.utils.CommonsUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        PlayerData pp = new PlayerData();
        pp.setUsername("iTzdemonDj");
        pp.setPassword("pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=");
        pp.setPremium(false);

        PlayerData playerData = UserProvider.getInstance().createUser(pp);
        System.out.println(playerData);

    }
    //La sanción expira correctamente y se cambia su estado a inactivo.
    //Agregar validación de si es permanente antes de hacer la validación de expiración.
    //agregar el filtro de TypeBan, agregar la query isIP para devolver los baneos por ip activo.


}
