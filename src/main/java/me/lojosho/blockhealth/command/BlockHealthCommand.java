package me.lojosho.blockhealth.command;

import me.lojosho.blockhealth.BlockHealthPlugin;
import me.lojosho.blockhealth.manager.HealthBlocks;
import me.lojosho.blockhealth.manager.ToolsDamage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BlockHealthCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args[0]) {
            case "reload":
                BlockHealthPlugin.getInstance().reloadConfig();
                ToolsDamage.setupTools();
                HealthBlocks.setupBlocks();
                sender.sendMessage("Successfully Reloaded " + HealthBlocks.getBlocks().size() + " blocks and " + ToolsDamage.getTools().size() + " tools!");
        }
        return false;
    }
}
