package com.clarence.test;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class reportCommand implements CommandExecutor {
    private Test test = null;
    public reportCommand(Test test) {
        this.test=test;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player player)) {
            Util.setConsoleMessage("CONSOLE cannot execute this command");
            return true;
        }
        if (args.length == 0) {
            String message = "Please do " + Util.getGreenColor() + "/report help" + Util.getBlueColor() + " for more info\nDeveloped by: "+Util.getGreenColor()+Test.getPlugin(Test.class).getDescription().getAuthors().getFirst();
            Util.setPlayerMessage(player, message);
            return true;
        }

        switch (args[0]) {
            case "check":
                check(player, args);
                break;
            case "clear":
                clear(player, args);
                break;
            case "help":
                help(player);
                break;
            case "version":
                version(player);
                break;
            case "reload":
                reload(player);
                break;
            default:
                report(player, args);
                break;
        }
        return false;
    }

    private void check(Player player, String[] args) {
        String message = "Command usage: " + Util.getGreenColor() + "/report check <player>";

        if (args.length == 1) {
            Util.setPlayerMessage(player, message);
            return;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

        if (!offlinePlayer.hasPlayedBefore()) { Util.setPlayerMessage(player, "The specified player doesn't exist or is not online"); return; }

        if (!test.getReportConfiguration().hasSectionPath(offlinePlayer.getUniqueId().toString())) { Util.setPlayerMessage(player, "The specified player does not have any report"); return; }

        ConfigurationSection configurationSection = test.getReportConfiguration().getSection(offlinePlayer.getUniqueId().toString());

        List<String> configKeys = new ArrayList<>(configurationSection.getKeys(true));

        for (int i = 0; i < configKeys.size(); i++) {
            String dataName = configKeys.get(i);

            switch (dataName) {
                case "name":
                    Util.setPlayerMessage(player, "Name: " + Util.getGreenColor() + configurationSection.get("name"));
                    break;
                case "reason":
                    Util.setPlayerMessage(player, "Reason: " +Util.getGreenColor() + configurationSection.get("reason"));
                    break;
                case "reported by":
                    Util.setPlayerMessage(player, "Reported by: " + Util.getGreenColor()+ configurationSection.get("reported by"));
                    break;
                case "date":
                    Util.setPlayerMessage(player, "Date: " + Util.getGreenColor() + configurationSection.get("date"));
                    break;
            }
        }
    }

    private void help(Player player){
        String message = "Command usage: " + Util.getGreenColor() + "/report version\n/report reload\n/report clear <player>\n/report check <player>";
        Util.setPlayerMessage(player, message);
    }
    private void version(Player player) {
        String version = "Current version: " + Util.getGreenColor() + Test.getPlugin(Test.class).getDescription().getVersion();

        if (test.getUpdateChecker() == null || !test.getUpdateChecker().isUpdateAvailable()) {
            Util.setPlayerMessage(player, version);
            return;
        }
        String updatedVersion = version + "\n"+Util.getBlueColor()+"Download the latest version here "+Util.getGreenColor() + test.getUpdateChecker().getUpdateUrl();

        if (test.getUpdateChecker().isUpdateAvailable()) {
            Util.setPlayerMessage(player, updatedVersion);
            return;
        }
    }
    private void reload(Player player) {
        String reloadMessage = "Files have been reloaded";

        test.getDefaultConfiguration().reloadConfiguration();
        test.getReportConfiguration().reloadConfiguration();

        Util.setPlayerMessage(player, reloadMessage);
    }
    private void clear(Player player, String[] args) {
        if (args.length == 1) {
            Util.setPlayerMessage(player, "Please specify a player");
            return;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

        if (!offlinePlayer.hasPlayedBefore()) { Util.setPlayerMessage(player, "The specified player doesn't exist or is not online"); return; }

        if (!test.getReportConfiguration().hasSectionPath(offlinePlayer.getUniqueId().toString())) { Util.setPlayerMessage(player, "The specified player does not have any report"); return; }

        Util.setPlayerMessage(player, "Cleared " + Util.getGreenColor() + offlinePlayer.getName() + Util.getBlueColor() + " report");

        test.getReportConfiguration().set(offlinePlayer.getUniqueId().toString(), null);

        test.getReportConfiguration().saveConfiguration();
    }
    private void report(Player player, String[] args){
        Player target = player.getServer().getPlayerExact(args[0]);

        if (target == null) {
            String exist = "The specified player doesn't exist or is not online";
            Util.setPlayerMessage(player, exist);
            return;
        }

        if (args.length == 1) {
            String invalid = "Enter a reason on why you report this player";
            Util.setPlayerMessage(player, invalid);
            return;
        }

        if (target == player) {
            Util.setPlayerMessage(player, "You can't report yourself");
            return;
        }

        UUID playerUUID = getUUID(player);
        UUID targetUUID = getUUID(target);

        inventory inventory = Util.createNewInventory(null, "Report");

        StringBuilder stringBuilder = Util.createNewStringBuilder(args, 1);

        String getReason = stringBuilder.toString().strip();

       inventory.createHead(target, 1, 11);
       inventory.createItemStack(Material.BARRIER, 1, Util.format("Report reason", Util.getGreenColor() + "&l"), Util.format(getReason, Util.getBlueColor() + "&l"), 13);
       inventory.createItemStackWithDataContainer(Material.DIAMOND_AXE, 1, Util.format(itemDataNames.CONFIRM.getName(), Util.getGreenColor() + "&l"), Util.format("Submit Report", Util.getBlueColor() + "&l"), itemDataNames.CONFIRM.getName(), 15);

        Util.getUniqueTarget().put(playerUUID, new targetData(targetUUID, target.getDisplayName(), getReason));
        Util.getUniqueInventory().put(playerUUID, inventory);

        player.openInventory(inventory.getInventory());
    }
    private UUID getUUID(Player player) {
        return player.getUniqueId();
    }
}
