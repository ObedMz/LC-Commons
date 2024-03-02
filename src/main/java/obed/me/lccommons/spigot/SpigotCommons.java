package obed.me.lccommons.spigot;

import lombok.Getter;
import lombok.Setter;
import obed.me.lccommons.api.LCCommons;
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
    }

    public static SpigotCommons getInstance(){
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
