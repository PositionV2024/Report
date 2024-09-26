package com.clarence.test;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;


public class listener implements Listener {
    private Test test;
    public listener (Test test) {
        this.test = test;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) { return; }
        if (!e.getCurrentItem().hasItemMeta()) { return; }

        PersistentDataContainer dataContainer = e.getCurrentItem().getItemMeta().getPersistentDataContainer();
        NamespacedKey key = Util.getItemData();
        int[] getEmptySlots = Util.getEmptySlots();

        if (dataContainer.has(key)) {
            switch (dataContainer.get(key, PersistentDataType.STRING)) {
                case "Confirm":
                    Date date = Calendar.getInstance().getTime();

                    SimpleDateFormat generalDate = new SimpleDateFormat(com.clarence.test.date.getGeneralDateFormat());
                    SimpleDateFormat detailedDate = new SimpleDateFormat(com.clarence.test.date.getDetailedDateFormat());

                    String formattedGeneralDate = generalDate.format(date);
                    String formmattedDetailedDate = detailedDate.format(date);

                    Set<String> configKeys = test.getReportConfiguration().getConfigKeys(false);

                    if (configKeys.contains(Util.getUniqueTarget().get(player.getUniqueId()).getPlayerUuid().toString())) {

                        reportPlayer(player, formattedGeneralDate, formmattedDetailedDate, false);

                        player.closeInventory();
                        return;
                    }

                    reportPlayer(player, formattedGeneralDate, formmattedDetailedDate, true);

                    player.closeInventory();
                    break;
                case "Back":
                    player.openInventory(Util.getUniqueInventory().get(player.getUniqueId()).getInventory());
                    break;
            }
        }

        e.setCancelled(true);
    }

    public void reportPlayer(Player player, String formattedGeneralDate, String formmattedDetailedDate, boolean isOnce) {
        Util.setPlayerMessage(player, "You have reported " + Util.getGreenColor() + Util.getUniqueTarget().get(player.getUniqueId()).getPlayerName() + Util.getBlueColor() + " for " + Util.getGreenColor() + Util.getUniqueTarget().get(player.getUniqueId()).getReason());

        if (isOnce) {
            ConfigurationSection configurationSection = configurationSection = test.getReportConfiguration().createSection(Util.getUniqueTarget().get(player.getUniqueId()).getPlayerUuid().toString());

            configurationSection.set("name", Arrays.asList(Util.getUniqueTarget().get(player.getUniqueId()).getPlayerName()));
            configurationSection.set("reason", Arrays.asList(Util.getUniqueTarget().get(player.getUniqueId()).getReason()));
            configurationSection.set("reported by", Arrays.asList(player.getName()));
            configurationSection.set("date", Arrays.asList(formattedGeneralDate + " : " + formmattedDetailedDate));
            configurationSection.set("Player reported amount",1);
        } else {
            ConfigurationSection configurationSection = test.getReportConfiguration().getSection(Util.getUniqueTarget().get(player.getUniqueId()).getPlayerUuid().toString());

            List<String> reasonList = configurationSection.getStringList("reason");
            List<String> reportedByList = configurationSection.getStringList("reported by");
            List<String> dateList = configurationSection.getStringList("date");

            reasonList.add(Util.getUniqueTarget().get(player.getUniqueId()).getReason());
            dateList.add(formattedGeneralDate + " : " + formmattedDetailedDate);
            reportedByList.add(player.getName());

            int amount = configurationSection.getInt("Player reported amount", 0) + 1;

            configurationSection.set("reason", reasonList);
            configurationSection.set("date", dateList);
            configurationSection.set("reported by", reportedByList);
            configurationSection.set("Player reported amount", amount);
        }

        test.getReportConfiguration().saveConfiguration();
    }
}
