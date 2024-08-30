package me.doruk.launchvector3;

import me.doruk.launchvector3.Command.LaunchCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class LaunchVector3 extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("launch").setExecutor(new LaunchCommand());
        getLogger().info("LaunchVector3 enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("LaunchVector3 disabled ");
    }
}