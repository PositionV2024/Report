package com.clarence.test;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class listener implements Listener {
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
                    Util.setPlayerMessage(player, "You have reported " + Util.getGreenColor() + "x" + Util.getBlueColor() + " for " + Util.getGreenColor() + Util.getUniqueReportReason().get(player.getUniqueId()));
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
