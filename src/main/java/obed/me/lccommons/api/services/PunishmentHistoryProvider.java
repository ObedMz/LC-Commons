package obed.me.lccommons.api.services;

import obed.me.lccommons.api.entities.punishments.Punishment;

import java.util.List;
import java.util.UUID;

public class PunishmentHistoryProvider {

    private static volatile PunishmentHistoryProvider instance;
    private final APIClient apiClient = APIClient.getInstance();
    private final String ENDPOINT = EndPointType.PUNISHMENTS.getEndPoint();

    public static PunishmentHistoryProvider getInstance(){
        if(instance == null){
            synchronized (PunishmentHistoryProvider.class){
                if(instance == null)
                    instance = new PunishmentHistoryProvider();
            }
        }
        return instance;
    }

    public List<Punishment> getByIP(String ip, boolean bol) {
        return List.of(apiClient.get(ENDPOINT.concat("/ip/" + ip +"?active=" + bol), Punishment[].class));
    }
    public List<Punishment> getByPlayer(UUID uuid, boolean bol) {
        return List.of(apiClient.get(ENDPOINT.concat("/player/" + uuid +"?active=" + bol), Punishment[].class));
    }

    public List<Punishment> getAllPunishment(boolean bol) {
        return List.of(apiClient.get(ENDPOINT.concat("?active=" + bol), Punishment[].class));
    }

    public Punishment[] savePunishments(List<Punishment> punishments) {
        return apiClient.create(ENDPOINT, punishments, Punishment[].class);
    }
    public Punishment savePunishment(Punishment punishments) {
        return apiClient.create("v1/punishment", punishments, Punishment.class);
    }
}
