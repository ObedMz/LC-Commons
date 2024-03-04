package obed.me.lccommons.spigot;

import lombok.Getter;
import lombok.Setter;
import obed.me.lccommons.api.entities.PlayerData;
import obed.me.lccommons.api.entities.groups.Rank;
import obed.me.lccommons.api.services.RankProvider;
import obed.me.lccommons.api.services.UserProvider;
import obed.me.lccommons.spigot.handlers.RankDataLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public final class SpigotCommons extends JavaPlugin {

    private static SpigotCommons instance;
    @Override
    public void onLoad(){
        instance = this;
    }
    @Override
    public void onEnable() {
        if(getConfig().getBoolean("loadJoin")){
            Bukkit.getPluginManager().registerEvents(new RankDataLoader(), this);
        }

    }

    public static SpigotCommons getInstance(){
        return instance;
    }

}
