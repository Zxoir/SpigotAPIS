package me.zxoir.myapi.gui;

import me.zxoir.myapi.utils.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to
 * register inventory events
 */

public class GUIManager implements Listener {

    private Map<Object, GUI> guis = new HashMap<>();

    public GUIManager(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void addMenu(Object key, GUI gui) {
        guis.put(key, gui);
    }

    public Collection<GUI> getGUIs() {
        return guis.values();
    }

    public GUI getGUI(Object key) {
        return guis.get(key);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getWhoClicked().getOpenInventory().getTopInventory();
        Inventory bottomInventory = event.getWhoClicked().getOpenInventory().getBottomInventory();

        // If the inventory is not null
        if (event.getClickedInventory() == null || event.getInventory() == null)
            return;

        // If the clicked item is not null
        if (!event.getClick().equals(ClickType.NUMBER_KEY)) {
            if (event.getCursor() == null || event.getCursor().getType().equals(Material.AIR)) {
                if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
                    return;
            }
        }

        if (!guis.isEmpty()) {
            // If there has been at least 1 gui created

            for (GUI gui : guis.values()) {
                Inventory guiInventory = gui.inventory;

                if (inventory.equals(guiInventory)) {
                    // If the inventory is a custom GUI

                    if (event.getClick().isShiftClick() || event.getClick().isKeyboardClick()) {

                        if (!gui.inventoryClick || !gui.playerInventoryClick) {
                            // If inventory click or player inventory click has been disabled

                            //event.setCancelled(true);
                            event.setResult(Event.Result.DENY);
                            gui.click((Player) event.getWhoClicked(), event.getCurrentItem(), event.getClickedInventory().getType());
                            ((Player) event.getWhoClicked()).updateInventory();

                            Util.debug("Inventory click by " + event.getWhoClicked().getName() + " has been cancelled." +
                                    "\nGUI Name: " + guiInventory.getTitle() +
                                    "\nGUI Type: " + event.getClickedInventory().getType().name());
                        }

                    } else {

                        if (event.getClickedInventory().equals(inventory) && !gui.inventoryClick) {
                            // If inventory click is disabled

                            Util.debug("Inventory click by " + event.getWhoClicked().getName() + " has been cancelled." +
                                    "\nGUI Name: " + guiInventory.getTitle() +
                                    "\nGUI Type: " + event.getClickedInventory().getType().name());

                            event.setCancelled(true);
                        }

                        if (event.getClickedInventory().equals(bottomInventory) && !gui.playerInventoryClick) {
                            // If player inventory click is disabled

                            Util.debug("Inventory click by " + event.getWhoClicked().getName() + " has been cancelled." +
                                    "\nGUI Name: " + guiInventory.getTitle() +
                                    "\nGUI Type: " + event.getClickedInventory().getType().name());

                            event.setCancelled(true);
                        }

                        ((Player) event.getWhoClicked()).updateInventory();
                        gui.click((Player) event.getWhoClicked(), event.getCurrentItem(), event.getClickedInventory().getType());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryDragEvent event) {
        Inventory inventory = event.getWhoClicked().getOpenInventory().getTopInventory();

        // If the inventory is not null
        if (inventory == null)
            return;

        if (!guis.isEmpty()) {
            // If there has been at least 1 gui created

            for (GUI gui : guis.values()) {

                if (inventory.equals(gui.inventory)) {
                    // If the inventory is a custom GUI

                    if (!gui.inventoryDrag) {
                        // If inventory drag is disabled

                        Util.debug("Inventory drag by " + event.getWhoClicked().getName() + " has been cancelled." +
                                "\nGUI Name: " + inventory.getTitle() +
                                "\nGUI Type: " + inventory.getType().name());

                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
