package com.clarence.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Util {
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
    private static String setColor(String message) {
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
}
