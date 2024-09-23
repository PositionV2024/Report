package com.clarence.test;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class reportCommand implements CommandExecutor {
    private Test test = null;
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
    private void help(Player player){
        String message = "Command usage: " + Util.getGreenColor() + "/report version\n/report reload";
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

        UUID playerUUID = player.getUniqueId();

        inventory inventory = Util.createNewInventory(null, "Report");

        StringBuilder stringBuilder = Util.createNewStringBuilder(args, 1);

        String getReason = stringBuilder.toString().strip();

       inventory.createHeadWithDataContainer(target, 1, target.getDisplayName(), 11);
       inventory.createItemStack(Material.BARRIER, 1, Util.format("Report reason", Util.getGreenColor() + "&l"), Util.format(getReason, Util.getBlueColor() + "&l"), 13);
       inventory.createItemStackWithDataContainer(Material.DIAMOND_AXE, 1, Util.format(itemDataNames.CONFIRM.getName(), Util.getGreenColor() + "&l"), Util.format("Submit Report", Util.getBlueColor() + "&l"), itemDataNames.CONFIRM.getName(), 15);

       Util.getUniqueTarget().put(playerUUID, target);
        Util.getUniqueReportReason().put(playerUUID, getReason);
        Util.getUniqueInventory().put(playerUUID, inventory);

        player.openInventory(inventory.getInventory());
    }
}
