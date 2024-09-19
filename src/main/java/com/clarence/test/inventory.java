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
    private int size;
    private String title;
    private InventoryHolder owner;
    private Inventory inventory;

    public inventory (InventoryHolder owner, int size, String title) {
        this.owner = owner;
        this.size = size;
        this.title = title;
    }
    public void createInventory() {
        inventory = Bukkit.createInventory(this.owner, this.size, this.title);
    }
    public Inventory getInventory() {return inventory; }
    public void generateDefaultItems(Material MaterialType, int amount, List<Integer> slots) {
        for (int i : slots) {
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
