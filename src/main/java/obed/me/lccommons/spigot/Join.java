package obed.me.lccommons.spigot;

import obed.me.lccommons.api.entities.PlayerData;
import obed.me.lccommons.api.services.UserProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.concurrent.CompletableFuture;

public class Join implements Listener {

    @EventHandler
    public void login(PlayerLoginEvent e){
        CompletableFuture.runAsync(() -> {
            PlayerData user = UserProvider.getInstance().getUserByName(e.getPlayer().getName());
            if(user != null){
                UserProvider.getInstance().getUsersCache().put(e.getPlayer().getName(), user);
                e.getPlayer().sendMessage("logeate");
            } else {
                e.getPlayer().sendMessage("registrate");
            }
        });
    }
}
