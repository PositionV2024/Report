package com.clarence.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.UUID;

public class Util {
    private static final HashMap<UUID, inventory> uniqueInventory = new HashMap<>();
    private static final HashMap<UUID, String> uniqueReportReason = new HashMap<>();
    private static final HashMap<UUID, UUID> uniqueTarget = new HashMap<>();

    public static void setConsoleMessage(String message) {
        setLogger("[" + getPluginName() + "] " + message);
    }
    public static void setUpdateMessage(String message) {
        setLogger("[" + getPluginName() + " UPDATE] " + message.toUpperCase());
    }
    public static void setPlayerMessage(Player player, String message) {
        if (player == null) {
            return;
        }
        player.sendMessage(setColor(getBlueColor() + "[" + getPluginName() + "] » " + message));
    }
    public static StringBuilder createNewStringBuilder(String[] totalLength, int buildFrom) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = buildFrom; i < totalLength.length; i++) {
            stringBuilder.append(totalLength[i]).append(" ");
        }
        return stringBuilder;
    }
    public static String setColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    private static String getPluginName(){
        return Test.getPlugin(Test.class).getName();
    }
    public static String getBlueColor(){
        return "&b";
    }
    public static String getGreenColor(){
        return "&a";
    }
    private static void setLogger(String message) {
        Bukkit.getLogger().info(message);
    }
    public static inventory createNewInventory(InventoryHolder owner, String title) {
        return new inventory(owner, InventoryType.CHEST.getDefaultSize(), title);
    }
    public static String format(String name, String color) {
        return inventory.format(name, color);
    }
    public static NamespacedKey getItemData(){
        return inventory.getItemData();
    }
    public static int[] getEmptySlots() {
        return inventory.getEmptySlots();
    }
    public static HashMap<UUID, inventory> getUniqueInventory() {return uniqueInventory;}
    public static HashMap<UUID, String> getUniqueReportReason() {return uniqueReportReason;}
    public static HashMap<UUID, UUID> getUniqueTarget() {return uniqueTarget;}
}
