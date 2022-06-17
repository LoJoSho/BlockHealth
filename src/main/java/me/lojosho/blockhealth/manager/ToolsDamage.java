package me.lojosho.blockhealth.manager;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import me.lojosho.blockhealth.BlockHealthPlugin;
import org.bukkit.Material;

import java.util.HashMap;

public class ToolsDamage {

    private static HashMap<Material, Integer> toolDamageMap = new HashMap();

    public static void setupTools() {
        BlockHealthPlugin plugin = BlockHealthPlugin.getInstance();

        toolDamageMap.clear();
        for (String a : plugin.getConfig().getConfigurationSection("Tools").getKeys(false)) {
            Material mat = Material.getMaterial(a);
            toolDamageMap.put(mat, plugin.getConfig().getInt("Tools." + mat + ".DAMAGE"));
        }
    }

    /**
     * Get the damage that a tool gives out.
     *
     * @param material
     * @return
     */
    public static int getToolDamage(Material material) {
        return ImmutableMap.copyOf(toolDamageMap).getOrDefault(material, 1);
    }

    /**
     * Get all available tools that have damage.
     * @return
     */
    public static ImmutableSet<Material> getTools() {
        return ImmutableMap.copyOf(toolDamageMap).keySet();
    }

    public static void clearTools() {
        toolDamageMap.clear();
    }

}
