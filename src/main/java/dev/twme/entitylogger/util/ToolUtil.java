package dev.twme.entitylogger.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

/**
 * Utility class for tools.
 */
public class ToolUtil {

    /**
     * Check if an item stack is a tool.
     * @param itemStack The item stack to check.
     * @return True if the item stack is a tool, otherwise false.
     */
    public static boolean isTool(ItemStack itemStack) {
        return itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(PersistentKeys.CheckTool);
    }

    /**
     * Get the tool itemstack.
     * @return The tool itemstack.
     */
    public static ItemStack getTool() {
        var itemStack = new ItemStack(Material.BREEZE_ROD);
        var itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(PersistentKeys.CheckTool, PersistentDataType.STRING, "CheckTool");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
