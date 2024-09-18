package com.clarence.test;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            String message = "Please do " + Util.getGreenColor() + "/report help" + Util.getBlueColor() + " for more info";
            Util.setPlayerMessage(player, message);
            return true;
        }

        switch (args[0]) {
            case "version":
                String version = "Current version: " + Util.getGreenColor() + Test.getPlugin(Test.class).getDescription().getVersion();
                if (test.getUpdateChecker() == null) {
                    Util.setPlayerMessage(player, version);
                    return true;
                }
                String updatedVersion = version + "\nDownload the latest version here: " + test.getUpdateChecker().getUpdateUrl();
                if (test.getUpdateChecker().isUpdateAvailable()) {
                    if (test.getDefaultConfiguration().hasUpdate()) {
                        Util.setPlayerMessage(player, updatedVersion);
                        return true;
                    }
                } else {
                    Util.setPlayerMessage(player, version);
                }
                break;
        }
        return false;
    }
}
