package obed.me.lccommons.test;

import obed.me.lccommons.api.entities.PlayerData;
import obed.me.lccommons.api.entities.punishments.Punishment;
import obed.me.lccommons.api.entities.punishments.PunishmentHistory;
import obed.me.lccommons.api.services.PunishmentHistoryProvider;
import obed.me.lccommons.api.services.RankProvider;
import obed.me.lccommons.api.services.UserProvider;
import obed.me.lccommons.api.utils.CommonsUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
    }
    public static void updateUser(){
        String name = "osito";
        PlayerData playerData = UserProvider.getInstance().getUserByName(name);
        if(playerData == null)
            return;

        playerData.setPassword("changepassword");
        playerData.getAuthInfo().setIp("192.168.0.2");
        playerData.getAuthInfo().setLastConnection(Instant.now());
        UserProvider.getInstance().savePlayer(playerData);
        System.out.println("se actualizaron los datos.");
    }
    public static void registerTest(){
        String name = "osito";
        PlayerData playerData = UserProvider.getInstance().getUserByName(name);
        if(playerData == null)
        {
            System.out.println("El jugador no está registrado en el servidor.");

            System.out.println("Registrando al jugador...");
            playerData = new PlayerData();
            playerData.setUsername(name);
            playerData.setPassword("123456");
            playerData.getAuthInfo().setIp("192.168.0.1");
            playerData.getAuthInfo().setLogged(true);
            playerData.getAuthInfo().setLastConnection(Instant.now());
            playerData = UserProvider.getInstance().createUser(playerData);
        }
        System.out.println(playerData);
    }
    public static void IPCheck(String str){
        long start1 = System.currentTimeMillis();
        List<PunishmentHistory> list = PunishmentHistoryProvider.getInstance().getActivePunishmentIP(str);
        long end1 = System.currentTimeMillis();
        list.forEach(p -> p.getPunishmentList().forEach(item -> {
            if(CommonsUtil.isExpiredData(item.getExpiresInstant())){
                System.out.println("Removing expired bans.");
                item.setActive(false);
            }

        }));

        AtomicReference<Punishment> test = new AtomicReference<>(new Punishment());
        for (PunishmentHistory p : list) {
            Stream<Punishment> activePunishment = p.getPunishmentList().stream().filter(Punishment::getActive);
            Optional<Punishment> val = activePunishment.findFirst();
            if(val.isPresent()){
                test.set(val.get());
                break;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Fetching Data time elapse: " + (end1 - start1) + "ms");
        System.out.println(str + "is IP Banned for: " + test.get().getReason() + "took: " + (end - end1) + "ms");
        //se tiene que enviar los datos actualizados de la expiración.
        for (PunishmentHistory punishmentHistory : list) {
            PunishmentHistoryProvider.getInstance().savePunishments(punishmentHistory.getPlayer(), punishmentHistory.getPunishmentList());

        }
    }
    public static void PlayerJoin(String str){
        PlayerData playerData = UserProvider.getInstance().getUserByName(str);
        if(playerData == null){
            System.out.println("data not found");
            return;
        }
        if(UserProvider.getInstance().isExpiredRank(playerData)){
            System.out.println("rank is expired.");
            playerData.getRankInfo().setRank(RankProvider.getInstance().getDefaultRank());
            playerData = UserProvider.getInstance().createUser(playerData);
        }
        System.out.println("Data loaded for user: " + playerData);
    }
}
