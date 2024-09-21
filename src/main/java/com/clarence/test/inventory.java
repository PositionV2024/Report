package com.clarence.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class inventory {
    private static Inventory inventory;
    private static int[] slots = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static String reportReason;

    public static void createInventory(Player player, Player targetProfile, InventoryHolder owner, int size, String title) {
        inventory = Bukkit.createInventory(owner, size, title);
        generateDefaultItems(Material.BLUE_STAINED_GLASS_PANE, 1, Enchantment.UNBREAKING, 5);
        createHead(targetProfile, 1, 11);
        createItemStack(Material.BARRIER, 1, format("Reason", Util.getGreenColor() + "&l"), format(reportReason, Util.getBlueColor() + "&l"), 13);
        createItemStack(Material.DIAMOND_AXE, 1, format("Confirm", Util.getGreenColor() + "&l"), format("Report", Util.getBlueColor() + "&l"), 15);
        player.openInventory(inventory);
    }
    public static void setReason(String reason) {reportReason = reason; }
    private static void generateDefaultItems(Material MaterialType, int amount, Enchantment enchantmentType, int enchantmentLevel) {
        for (int i : slots) {
            ItemStack itemStack = new ItemStack(MaterialType, amount);
            itemStack.addUnsafeEnchantment(enchantmentType, enchantmentLevel);
            inventory.setItem(i, itemStack);
        }
    }
    private static void createHead(Player playerProfile, int amount, int player_Head_Slot) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, amount);

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        PlayerProfile profile = playerProfile.getPlayerProfile();

        if (!skullMeta.hasOwner()) {
            skullMeta.setOwnerProfile(profile);
            skullMeta.setDisplayName(playerProfile.getDisplayName().toUpperCase());
        }

        itemStack.setItemMeta(skullMeta);
        inventory.setItem(player_Head_Slot, itemStack);
    }
    private static void createItemStack(Material MaterialType, int amount, String displayName, String lores, int reason_Slot) {
        ItemStack itemStack = new ItemStack(MaterialType, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Collections.singletonList(lores));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(reason_Slot, itemStack);
    }
    private static String format(String name, String color) {
        return ChatColor.translateAlternateColorCodes('&',  color + "[" + name.substring(0, name.length()).toUpperCase() + "]");
    }
}
