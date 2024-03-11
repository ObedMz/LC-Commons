package obed.me.lccommons.api.entities;

import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

@Data
public class SvInfo {
    private String serverId;
    private ArrayList<String> players = new ArrayList<>();
    private int max_players;
    private String map;
    private long duration;
    private ServerState serverState;
    private String ip;
    private Integer port;

}
