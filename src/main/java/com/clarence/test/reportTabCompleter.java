package com.clarence.test;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class reportTabCompleter implements TabCompleter {
    private final Test test;

    public reportTabCompleter(Test test) {
        this.test = test;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 1 && args[0].isEmpty()) {
            ArrayList<String> list = new ArrayList<>();
            list.add("help");
            list.add("version");
            list.add("check");
            list.add("reload");

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player != commandSender) {
                    list.add(player.getDisplayName());
                }
            }
            return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());
        } else if (args.length == 2 && args[0].equals("check")) {

            ArrayList<String> list = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player != commandSender) {
                    list.add(player.getDisplayName());
                }
            }
            return StringUtil.copyPartialMatches(args[1], list, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
