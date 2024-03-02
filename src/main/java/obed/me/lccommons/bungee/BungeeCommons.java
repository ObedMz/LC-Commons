package obed.me.lccommons.bungee;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
@Setter
public final class BungeeCommons extends Plugin {
    private static BungeeCommons instance;
    @Override
    public void onLoad(){
        instance = this;
    }
    @Override
    public void onEnable(){
    }

    public static BungeeCommons getInstance(){
        return instance;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
