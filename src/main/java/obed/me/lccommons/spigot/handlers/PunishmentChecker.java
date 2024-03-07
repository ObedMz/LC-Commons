package obed.me.lccommons.spigot.handlers;

import obed.me.lccommons.api.entities.PlayerData;
import obed.me.lccommons.api.entities.groups.Rank;
import obed.me.lccommons.api.entities.punishments.Punishment;
import obed.me.lccommons.api.entities.punishments.PunishmentType;
import obed.me.lccommons.api.services.RankProvider;
import obed.me.lccommons.api.services.UserProvider;
import obed.me.lccommons.spigot.SpigotCommons;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PunishmentChecker implements Listener {

    @EventHandler
    public void CheckByName(PlayerLoginEvent e){
        new BukkitRunnable(){
            @Override
            public void run() {
                Player p = e.getPlayer();
                System.out.println(p.getName());
                PlayerData playerData = UserProvider.getInstance().getUserByName(p.getName());
                if(playerData == null){
                    e.disallow(PlayerLoginEvent.Result.KICK_FULL, ChatColor.RED + "No estás autenticado.");
                    return;
                }
                playerData = UserProvider.getInstance().checkActivePunishment(playerData);
                //revisar si es BAN O BANIP
                for (Punishment punishment : playerData.getActivePunishment().getPunishmentList()) {
                    if(punishment.getType() == PunishmentType.BAN){
                        e.disallow(PlayerLoginEvent.Result.KICK_FULL, ChatColor.RED + punishment.getReason());
                        break;
                    }
                }
                System.out.println("Data loaded for user: " + playerData.getUsername());

            }
        }.runTaskAsynchronously(SpigotCommons.getInstance());
    }

    public void CheckByIP(String ip){
        new BukkitRunnable(){
            @Override
            public void run() {


            }
        }.runTaskAsynchronously(SpigotCommons.getInstance());
    }


}
