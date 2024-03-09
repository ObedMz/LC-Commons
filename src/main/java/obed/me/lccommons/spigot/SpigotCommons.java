package obed.me.lccommons.spigot;

import lombok.Getter;
import lombok.Setter;
import obed.me.lccommons.spigot.handlers.RankDataLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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
