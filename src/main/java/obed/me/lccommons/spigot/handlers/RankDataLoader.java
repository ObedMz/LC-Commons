package obed.me.lccommons.spigot.handlers;

import obed.me.lccommons.api.entities.PlayerData;
import obed.me.lccommons.api.entities.groups.Rank;
import obed.me.lccommons.api.services.RankProvider;
import obed.me.lccommons.api.services.UserProvider;
import obed.me.lccommons.spigot.SpigotCommons;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class RankDataLoader implements Listener {

    @EventHandler
    public void PlayerLoginEvent(PlayerLoginEvent e){
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
                if(UserProvider.getInstance().isExpiredRank(playerData)){
                    playerData.getRankInfo().setRank(RankProvider.getInstance().getDefaultRank());
                    playerData = UserProvider.getInstance().createUser(playerData);
                }
                Rank rank = playerData.getRankInfo().getRank();
                rank.getPermissions().forEach(str -> p.addAttachment(SpigotCommons.getInstance(), str, true));
                SpigotCommons.getInstance().getConfig().getStringList(
                        "permissions." + rank.getName().toUpperCase()).forEach(str ->{
                            System.out.println(str);
                            p.addAttachment(SpigotCommons.getInstance(), str.startsWith("-") ?
                                    str.replaceFirst("-", "") :
                                    str, !str.startsWith("-"));
                        }
                );
                System.out.println("Data loaded for user: " + playerData.getUsername());

            }
        }.runTaskAsynchronously(SpigotCommons.getInstance());
    }


}
