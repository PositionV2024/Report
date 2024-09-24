package com.clarence.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;

import java.util.Collections;

public class inventory {
    private Inventory inventory;
    private int[] generateDefaultItemSlots = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

    private static Test test = Test.getPlugin(Test.class);
    private static NamespacedKey itemData = new NamespacedKey(test, "itemData");
    private static int[] emptySlots = new int[]{11, 13, 15};
    private int NUMBER_OF_ITEMS = 0;

    public inventory(InventoryHolder owner, int size, String title) {
        inventory = Bukkit.createInventory(owner, size, title);
        generateDefaultItems(Material.BLUE_STAINED_GLASS_PANE, 1, Enchantment.UNBREAKING, 5);
    }
    private void generateDefaultItems(Material MaterialType, int amount, Enchantment enchantmentType, int enchantmentLevel) {
        for (int i : generateDefaultItemSlots) {
            ItemStack itemStack = new ItemStack(MaterialType, amount);
            itemStack.addUnsafeEnchantment(enchantmentType, enchantmentLevel);
            inventory.setItem(i, itemStack);
            increaseNumberOfItems();
        }
    }
    public void createHead(Player playerProfile, int amount, int slot) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, amount);

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        PlayerProfile profile = playerProfile.getPlayerProfile();

        if (!skullMeta.hasOwner()) {
            skullMeta.setOwnerProfile(profile);
            skullMeta.setDisplayName(playerProfile.getDisplayName().toUpperCase());
        }

        itemStack.setItemMeta(skullMeta);
        inventory.setItem(slot, itemStack);
        increaseNumberOfItems();
    }
    public void createItemStackWithDataContainer(Material MaterialType, int amount, String displayName, String lores, String item_Data, int slot) {
        ItemStack itemStack = new ItemStack(MaterialType, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        dataContainer.set(itemData, PersistentDataType.STRING, item_Data);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Collections.singletonList(lores));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
        increaseNumberOfItems();
    }
    public void createItemStack(Material MaterialType, int amount, String displayName, String lores, int slot) {
        ItemStack itemStack = new ItemStack(MaterialType, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Collections.singletonList(lores));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
        increaseNumberOfItems();
    }
    public static String format(String name, String color) {
        return ChatColor.translateAlternateColorCodes('&',  color + "[" + name.substring(0, name.length()).toUpperCase() + "]");
    }
    public static NamespacedKey getItemData() {return itemData; }
    public static int[] getEmptySlots() {return emptySlots; }
    public Inventory getInventory() {return inventory; }
    private void increaseNumberOfItems() { NUMBER_OF_ITEMS++;}
    public int getNumberOfItems() {return NUMBER_OF_ITEMS; }
}
