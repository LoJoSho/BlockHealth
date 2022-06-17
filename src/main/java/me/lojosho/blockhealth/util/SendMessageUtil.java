package me.lojosho.blockhealth.util;

import me.lojosho.blockhealth.BlockHealthPlugin;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

public class SendMessageUtil {

    /**
     * Sends a message to the player gotten from the config with placeholders.
     * @param translatableMessage
     * @param player
     * @param placeholder
     */
    public static void sendMessage(String translatableMessage, Player player, TagResolver placeholder) {
        // Gets plugin instance
        BlockHealthPlugin plugin = BlockHealthPlugin.getInstance();
        // Gets translatable message
        String rawMessage = plugin.getConfig().getString(translatableMessage);
        // Convert to components for MiniMessage
        Component message = MiniMessage.miniMessage().deserialize(rawMessage, placeholder);
        // Setup audience for action bar
        Audience audience = BukkitAudiences.create(plugin).player(player);
        if (checkActionBar()) {
            audience.sendActionBar(message);
            return;
        }
        audience.sendMessage(message);
    }

    /**
     * Send a message to a player gotten from the config with no placeholders.
     * @param translatableMessage
     * @param player
     */
    public static void sendMessage(String translatableMessage, Player player) {
        sendMessage(translatableMessage, player, TagResolver.empty());
    }

    public static void sendDebugMessage(String message) {
        BlockHealthPlugin plugin = BlockHealthPlugin.getInstance();
        if (!plugin.getConfig().getBoolean("Config.debug")) return;
        plugin.getLogger().info("[DEBUG] " + message);

    }

    /**
     * Should we send the message through an actionbar?
     * @return
     */
    private static boolean checkActionBar() {
        return BlockHealthPlugin.getInstance().getConfig().getBoolean("Messages.UseActionBarForMessages");
    }

}
