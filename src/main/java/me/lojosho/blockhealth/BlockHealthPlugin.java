package me.lojosho.blockhealth;

import me.lojosho.blockhealth.command.BlockHealthCommand;
import me.lojosho.blockhealth.command.BlockHealthTabComplete;
import me.lojosho.blockhealth.listener.BlockListener;
import me.lojosho.blockhealth.manager.HealthBlocks;
import me.lojosho.blockhealth.manager.ToolsDamage;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockHealthPlugin extends JavaPlugin {

    private static BlockHealthPlugin instance;
    private static boolean restrictBlockPlace;
    private static boolean restrictBlockBreak;
    private static boolean restrictToolUsage;

    @Override
    public void onEnable() {
        // Plugin startup logic
        BlockHealthPlugin.instance = this;
        saveDefaultConfig();
        HealthBlocks.setupBlocks();
        ToolsDamage.setupTools();
        getServer().getPluginManager().registerEvents(new BlockListener(), this);

        setupUsages();
        getServer().getPluginCommand("blockhealth").setExecutor(new BlockHealthCommand());
        getServer().getPluginCommand("blockhealth").setTabCompleter(new BlockHealthTabComplete());
    }

    @Override
    public void onDisable() {
        ToolsDamage.clearTools();
        HealthBlocks.clearBlocks();
    }
    public static BlockHealthPlugin getInstance() {
        return instance;
    }
    public static boolean restrictBlockPlace() {
        return restrictBlockPlace;
    }
    public static boolean restrictBlockBreak() {
        return restrictBlockBreak;
    }
    public static boolean restrictToolUsage() {
        return restrictToolUsage;
    }


    public static void setupUsages() {
        restrictBlockPlace = getInstance().getConfig().getBoolean("Config.RestrictBlockPlace");
        restrictToolUsage = getInstance().getConfig().getBoolean("Config.RestrictToolUsage");
        restrictBlockBreak = getInstance().getConfig().getBoolean("Config.restrictBlockBreak");
    }
}
