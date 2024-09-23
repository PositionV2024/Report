package com.clarence.test;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class listener implements Listener {
    private String targetName;
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
                    Util.setPlayerMessage(player, "You have reported " + Util.getGreenColor() + "x" + Util.getBlueColor() + " for " + Util.getGreenColor() + reportCommand.getUuidStringHashMap().get(player.getUniqueId()));
                    break;
                case "Back":
                    player.openInventory(reportCommand.getUuiDinventoryHashMap().get(player.getUniqueId()).getInventory());
                    break;
                default:
                    targetName = dataContainer.get(key, PersistentDataType.STRING);
                    inventory inventory = Util.createNewInventory(null, "Player information");

                    for (int i = 0; i < getEmptySlots.length; i++) {
                        if (i == 0) {
                            inventory.createItemStackWithDataContainer(Material.BOOK, 1, Util.format("UNKNOWN", Util.getGreenColor() + "&l"), Util.format("UNKNOWN", Util.getBlueColor() + "&l"), itemDataNames.BACK.getName(), getEmptySlots[i]);
                        } else {
                            inventory.createItemStack(Material.BOOK, 1, Util.format("UNKNOWN " + i, Util.getGreenColor() + "&l"), Util.format("UNKNOWN " + i, Util.getBlueColor() + "&l"), getEmptySlots[i]);
                        }
                    }
                    player.openInventory(inventory.getInventory());
                    break;
            }
        }

        e.setCancelled(true);
    }
}
