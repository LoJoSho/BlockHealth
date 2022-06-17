package me.lojosho.blockhealth.listener;

import de.jeff_media.customblockdata.CustomBlockData;
import me.lojosho.blockhealth.BlockHealthPlugin;
import me.lojosho.blockhealth.manager.HealthBlocks;
import me.lojosho.blockhealth.manager.ToolsDamage;
import me.lojosho.blockhealth.util.SendMessageUtil;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Iterator;

public class BlockListener implements Listener {

    NamespacedKey key = new NamespacedKey(BlockHealthPlugin.getInstance(), "BlockHealth");

    // Variables
    Player player;
    Material mat;
    int health;

    // Placeholders
    TagResolver placeholders;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {

        if (event.isCancelled()) return;

        player = event.getPlayer();
        mat = event.getBlock().getType();


        if (BlockHealthPlugin.restrictBlockPlace()) {
            if (!HealthBlocks.getBlocks().contains(mat)) {
                SendMessageUtil.sendMessage("Messages.CantPlace", player);
                event.setCancelled(true);
                return;
            }
        }
        PersistentDataContainer customBlockData = new CustomBlockData(event.getBlock(), BlockHealthPlugin.getInstance());
        // Encountered some problems without checking? Don't know, but this works!
        if (!customBlockData.has(key, PersistentDataType.INTEGER)) {
            customBlockData.set(key, PersistentDataType.INTEGER, HealthBlocks.getBlockDefaultHealth(mat));
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        player = event.getPlayer();
        PersistentDataContainer customBlockData = new CustomBlockData(event.getBlock(), BlockHealthPlugin.getInstance());
        mat = event.getBlock().getType();

        if ((!HealthBlocks.getBlocks().contains(mat) && BlockHealthPlugin.restrictBlockBreak()) && (BlockHealthPlugin.restrictToolUsage() && !ToolsDamage.getTools().contains(player.getInventory().getItemInMainHand().getType()))) {
            SendMessageUtil.sendMessage("Messages.CantBreak", player);
            event.setCancelled(true);
            return;
        }

        if (!customBlockData.has(key, PersistentDataType.INTEGER)) return;

        health = customBlockData.get(key, PersistentDataType.INTEGER);

        if (HealthBlocks.getBlockDefaultHealth(mat) > health) {
            health = HealthBlocks.getBlockDefaultHealth(mat);
        }
        health = health - ToolsDamage.getToolDamage(player.getInventory().getItemInMainHand().getType());
        if (health > 0) {
            customBlockData.set(key, PersistentDataType.INTEGER, health);
            placeholders =
                    TagResolver.resolver(Placeholder.parsed("health", String.valueOf(health)));
            SendMessageUtil.sendMessage("Messages.FinalHealth", player, placeholders);
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled()) return;

        Iterator<Block> iter = event.blockList().iterator();
        while (iter.hasNext()) {
            if (HealthBlocks.getBlocks().contains(iter.next().getType())) {
                PersistentDataContainer customBlockData = new CustomBlockData(iter.next(), BlockHealthPlugin.getInstance());
                if (customBlockData.has(key, PersistentDataType.INTEGER)) {
                    int health = customBlockData.get(key, PersistentDataType.INTEGER);
                    int finalhealth = health - BlockHealthPlugin.getInstance().getConfig().getInt("OtherDamage.Explosion");
                    if (finalhealth > 0) {
                        customBlockData.set(key, PersistentDataType.INTEGER, finalhealth);
                        iter.remove();
                    }
                }
            } else {
                iter.remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerInteraction(PlayerInteractEvent event) {

        player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && player.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD)) {
            if (event.getClickedBlock() == null) return;

            PersistentDataContainer customBlockData = new CustomBlockData(event.getClickedBlock(), BlockHealthPlugin.getInstance());

            if (!customBlockData.has(key, PersistentDataType.INTEGER)) return;

            if (event.getPlayer().isOp()) {
                placeholders =
                        TagResolver.resolver(Placeholder.parsed("blockstring", event.getClickedBlock().toString()),
                        TagResolver.resolver(Placeholder.parsed("health", String.valueOf(customBlockData.get(key, PersistentDataType.INTEGER)))));

                SendMessageUtil.sendMessage("Messages.debugHealth", event.getPlayer(), placeholders);
            } else {
                placeholders =
                        TagResolver.resolver(Placeholder.parsed("health", String.valueOf(customBlockData.get(key, PersistentDataType.INTEGER))));
                SendMessageUtil.sendMessage("Messages.ReportHealth", event.getPlayer(), placeholders);
            }
        }
    }
}
