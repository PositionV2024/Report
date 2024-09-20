package com.clarence.test;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.List;

public class inventory {

    private Inventory inventory;
    private int[] slots = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

    public inventory (InventoryHolder owner, int size, String title) {
        createInventory(owner, size, title);
    }
    private void createInventory(InventoryHolder owner, int size, String title) {
        inventory = Bukkit.createInventory(owner, size, title);
    }
    public Inventory getInventory() {return inventory; }
    public void generateDefaultItems(Material MaterialType, int amount) {
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25}) {
            ItemStack itemStack = new ItemStack(MaterialType, amount);
            itemStack.addUnsafeEnchantment(Enchantment.UNBREAKING, 10);
            inventory.setItem(i, itemStack);
        }
    }
    public ItemStack createItemStack(Material MaterialType, int amount) {
        ItemStack itemStack = new ItemStack(MaterialType.PLAYER_HEAD, amount);
        return itemStack;
    }
}
