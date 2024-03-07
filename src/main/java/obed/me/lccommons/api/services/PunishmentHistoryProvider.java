package obed.me.lccommons.api.services;

import obed.me.lccommons.api.entities.punishments.Punishment;
import obed.me.lccommons.api.entities.punishments.PunishmentHistory;
import obed.me.lccommons.api.utils.CommonsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PunishmentHistoryProvider {

    private static volatile PunishmentHistoryProvider instance;
    private final APIClient apiClient = APIClient.getInstance();
    private ConcurrentHashMap<String, PunishmentHistory> cache = new ConcurrentHashMap<>();
    private final String ENDPOINT = EndPointType.PUNISHMENT.getEndPoint();

    public static PunishmentHistoryProvider getInstance(){
        if(instance == null){
            synchronized (PunishmentHistory.class){
                if(instance == null){
                    instance = new PunishmentHistoryProvider();
                }

            }
        }
        return instance;
    }

    public List<PunishmentHistory> getActivePunishmentIP(String ip) {
        List<PunishmentHistory> list = new ArrayList<>();

        for (PunishmentHistory punishmentHistory : List.of(apiClient.get(ENDPOINT.concat("/ip/history?ip=" + ip), PunishmentHistory[].class))) {
            for(Punishment punishment : punishmentHistory.getPunishmentList()){
                  if(punishment.getIp().equals(ip)){
                      list.add(punishmentHistory);
                      break;
                  }
              }

        }

        return list;
    }

    public void savePunishment(UUID player, Punishment punishment) {
        apiClient.create(ENDPOINT.concat("/" + player), punishment, Punishment.class);

    }

    public void savePunishments(UUID player, List<Punishment> punishments) {
        apiClient.create(("v1/punishments/" + player), punishments, Punishment[].class);
    }
}
