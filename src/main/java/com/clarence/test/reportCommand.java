package com.clarence.test;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

public class reportCommand implements CommandExecutor {
    private Test test = null;
    public reportCommand(Test test) {
        this.test = test;
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
        inventory inventory = new inventory(null, InventoryType.CHEST.getDefaultSize(), "Report");

        ItemStack itemStack = inventory.createItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        PlayerProfile profile = target.getPlayerProfile();
        profile.setTextures(target.getPlayerProfile().getTextures());

        if (!skullMeta.hasOwner()) {
            skullMeta.setOwnerProfile(profile);
            skullMeta.setDisplayName(profile.getName());
        }

        itemStack.setItemMeta(skullMeta);

        inventory.getInventory().setItem(11, itemStack);

        inventory.generateDefaultItems(Material.PURPLE_STAINED_GLASS_PANE, 1);
        player.openInventory(inventory.getInventory());
    }
}
