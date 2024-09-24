package com.clarence.test;

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
        UUID playerUUID = player.getUniqueId();

        if (dataContainer.has(key)) {
            switch (dataContainer.get(key, PersistentDataType.STRING)) {
                case "Confirm":
                    Date date = Calendar.getInstance().getTime();

                    SimpleDateFormat generalDate = new SimpleDateFormat(com.clarence.test.date.getGeneralDateFormat());
                    SimpleDateFormat detailedDate = new SimpleDateFormat(com.clarence.test.date.getDetailedDateFormat());

                    String formattedGeneralDate = generalDate.format(date);
                    String formmattedDetailedDate = detailedDate.format(date);

                    Set<String> configKeys = test.getReportConfiguration().getConfigkKeys(false);

                    if (configKeys.contains(Util.getUniqueTarget().get(playerUUID).getPlayerUuid().toString())) {
                        ConfigurationSection configurationSection = test.getReportConfiguration().getSection(Util.getUniqueTarget().get(playerUUID).getPlayerUuid().toString());

                        List<String> reasonList = configurationSection.getStringList("reason");
                        List<String> reportedByList = configurationSection.getStringList("reported by");
                        List<String> dateList = configurationSection.getStringList("date");

                        reasonList.add(Util.getUniqueTarget().get(playerUUID).getReason());
                        dateList.add(formattedGeneralDate + " : " + formmattedDetailedDate);
                        reportedByList.add(player.getName() + " : " + playerUUID);

                        configurationSection.set("reason", reasonList);
                        configurationSection.set("date", dateList);
                        configurationSection.set("reported by", reportedByList);

                        test.getReportConfiguration().saveConfiguration();

                        player.closeInventory();
                        return;
                    }

                    Util.setPlayerMessage(player, "You have reported " + Util.getGreenColor() + Util.getUniqueTarget().get(playerUUID).getPlayerName() + Util.getBlueColor() + " for " + Util.getGreenColor() + Util.getUniqueTarget().get(playerUUID).getReason() + Util.getBlueColor() + " at " + Util.getGreenColor() + formattedGeneralDate + " " + Util.getBlueColor() +  " : " + Util.getGreenColor() + formmattedDetailedDate);

                    ConfigurationSection configurationSection = configurationSection = test.getReportConfiguration().createSection(Util.getUniqueTarget().get(playerUUID).getPlayerUuid().toString());
                    configurationSection.set("name", Arrays.asList(Util.getUniqueTarget().get(playerUUID).getPlayerName()));
                    configurationSection.set("reason", Arrays.asList(Util.getUniqueTarget().get(playerUUID).getReason()));
                    configurationSection.set("reported by", Arrays.asList(player.getName() + " : " + playerUUID));
                    configurationSection.set("date", Arrays.asList(formattedGeneralDate + " : " + formmattedDetailedDate));

                    test.getReportConfiguration().saveConfiguration();
                    break;
                case "Back":
                    player.openInventory(Util.getUniqueInventory().get(player.getUniqueId()).getInventory());
                    break;
                default:
                    inventory inventory = Util.createNewInventory(null, "Player information");

                    inventory.createItemStackWithDataContainer(Material.BARRIER, 1, Util.format("Go back", Util.getGreenColor() + "&l"), Util.format("Go back", Util.getBlueColor() + "&l"), itemDataNames.BACK.getName(), 11);
                    player.openInventory(inventory.getInventory());
                    break;
            }
        }

        e.setCancelled(true);
    }
}
