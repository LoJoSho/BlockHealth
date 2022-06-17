package me.lojosho.blockhealth.manager;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import me.lojosho.blockhealth.BlockHealthPlugin;
import org.bukkit.Material;

import java.util.HashMap;

public class HealthBlocks {

    private static HashMap<Material, Integer> blockHealthMap = new HashMap();

    public static void setupBlocks() {

        BlockHealthPlugin plugin = BlockHealthPlugin.getInstance();

        blockHealthMap.clear();
        for (String a : plugin.getConfig().getConfigurationSection("Blocks").getKeys(false)) {
            Material mat = Material.getMaterial(a);
            blockHealthMap.put(mat, plugin.getConfig().getInt("Blocks." + mat + ".HEALTH"));
        }
    }

    /**
     * Get a blocks default health
     * @param material
     * @return
     */
    public static int getBlockDefaultHealth(Material material) {
        return ImmutableMap.copyOf(blockHealthMap).getOrDefault(material, 1);
    }

    /**
     * Get all blocks that have health attached to them
     * @return
     */
    public static ImmutableSet<Material> getBlocks() {
        return ImmutableMap.copyOf(blockHealthMap).keySet();
    }

    public static void clearBlocks() {
        blockHealthMap.clear();
    }

}
