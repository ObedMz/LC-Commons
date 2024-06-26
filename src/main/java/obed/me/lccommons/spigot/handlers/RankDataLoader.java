package obed.me.lccommons.spigot.handlers;

import obed.me.lccommons.api.entities.PlayerData;
import obed.me.lccommons.api.entities.groups.Rank;
import obed.me.lccommons.api.entities.groups.RankInfo;
import obed.me.lccommons.api.services.RankProvider;
import obed.me.lccommons.api.services.UserProvider;
import obed.me.lccommons.spigot.SpigotCommons;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RankDataLoader implements Listener {
    private final Pattern INHERITANCE_PATTERN = Pattern.compile("\\{.*?}:\\s*(.*)");

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = UserProvider.getInstance().getUserByName(player.getName());
        new BukkitRunnable() {
            @Override
            public void run() {
                loadPlayerData(player, playerData);
            }
        }.runTaskAsynchronously(SpigotCommons.getInstance());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UserProvider.getInstance().removeUserCache(event.getPlayer().getName().toLowerCase());
    }

    private void loadPlayerData(Player player, PlayerData playerData) {
        if (playerData == null) {
            SpigotCommons.getInstance().getServer().getScheduler().runTask(
                SpigotCommons.getInstance(),
                () -> player.kickPlayer(ChatColor.RED + "No estás autenticado. Entra de vuelta"));
            return;
        }
        RankInfo rankInfo = playerData.getRankInfo();
        if (rankInfo.getRank() == null || UserProvider.getInstance().isExpiredRank(playerData)) {
            Rank defaultRank = RankProvider.getInstance().getStoredDefaultRank();
            rankInfo.setRank(defaultRank);
            UserProvider.getInstance().savePlayer(playerData);
        }

        Rank playerRank = rankInfo.getRank();
        getPermissionList(playerRank).forEach(permission -> player.addPermission(permission));
    }

    private List<String> getPermissionList(Rank rank) {
        List<String> permissions = new ArrayList<>(rank.getPermissions());
        for (String permission : SpigotCommons.getInstance().getConfig().getStringList("permissions." + rank.getName().toUpperCase())) {
            if (permission.startsWith("{")) {
                String inheritance = permission.replaceAll("[{}]", "").split(":")[1].replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
                permissions.addAll(getInheritance(inheritance));
                continue;
            }
            permissions.add(permission);
        }
        return permissions;
    }

    private List<String> getInheritance(String path) {
        List<String> permissions = new ArrayList<>();
        String root = "permissions." + path.toUpperCase(Locale.ROOT);
        for (String permission : SpigotCommons.getInstance().getConfig().getStringList(root)) {
            Matcher matcher = INHERITANCE_PATTERN.matcher(permission);
            if (matcher.matches()) {
                String inheritance = matcher.group(1).replaceAll("[{}]", "").toUpperCase(Locale.ROOT);
                permissions.addAll(getInheritance(inheritance));
                continue;
            }
            permissions.add(permission);
        }
        return permissions;
    }
}
