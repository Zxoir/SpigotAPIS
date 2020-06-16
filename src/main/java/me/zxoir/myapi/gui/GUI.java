package me.zxoir.myapi.gui;

import me.zxoir.myapi.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * This class is used to create
 * custom gui's easily and to be
 * registered with <class>GUIManager</class>
 */

public abstract class GUI {
    /**
     * This is the custom gui
     */
    public Inventory inventory;

    /**
     * if this is false, any click
     * in the player's inventory
     * will be disabled
     */
    public boolean playerInventoryClick = true;

    /**
     * if this is false, any click
     * in the gui's inventory
     * will be disabled
     */
    public boolean inventoryClick = true;

    /**
     * if this is false, this
     * will disable the item drag
     */
    public boolean inventoryDrag = true;

    /**
     * @param inventoryName GUI name
     * @param size size of the inventory (Must be a multiple of 9)
     * @description this creates the inventory with a custom size and name
     */
    public GUI(String inventoryName, int size) {
        inventory = Bukkit.createInventory(null, size, Util.colorize(inventoryName));
    }

    /**
     * @param size size of the inventory (Must be a multiple of 9)
     * @description this creates the inventory with a custom size only
     */
    public GUI(int size) {
        inventory = Bukkit.createInventory(null, size);
    }

    /**
     * @param index inventory location where you want to set ita specific item
     * @param itemStack the item that is to be set in a location
     * @return returns this class
     * @description this sets a item to a specific location inside the custom gui
     */
    public GUI setItem(int index, ItemStack itemStack) {
        inventory.setItem(index, itemStack);
        return this;
    }

    /**
     * @param itemStack the item that is to be added in the inventory
     * @return returns this class
     * @description this adds the item to the custom gui (Not a specific location)
     */
    public GUI addItem(ItemStack itemStack) {
        inventory.addItem(itemStack);
        return this;
    }

    /**
     * @param itemStack the item that is to be removed from the inventory
     * @return returns this class
     * @description this removes all instances of your item from the inventory
     */
    public GUI removeItem(ItemStack itemStack) {
        inventory.remove(itemStack);
        return this;
    }

    /**
     * @param itemStack this removes the first instance of the item from the inventory
     * @return returns this class
     * @description this removes the first instance of your item from the inventory
     */
    public GUI removeFirstItem(ItemStack itemStack) {
        inventory.removeItem(itemStack); // not sure if this only removes 1 or not
        return this;
    }

    /**
     * @return returns this class
     * @description this clears all the items in the inventory
     */
    public GUI clearItems() {
        inventory.clear();
        return this;
    }

    /**
     * @param index location of the item that is to be removed
     * @return returns this class
     * @description this removes an item from a specific location
     */
    public GUI removeAt(int index) {
        inventory.setItem(index, new ItemStack(Material.AIR));
        return this;
    }

    /**
     * @param items the array of items inside the inventory
     * @return this class
     * @description this sets all the items in the inventory
     */
    public GUI setItems(ItemStack[] items) {
        inventory.setContents(items);
        return this;
    }

    /**
     * @param size the max stack size of the inventory
     * @return this class
     * @description this sets the max stack size inside the inventory
     */
    public GUI setMaxStackSize(int size) {
        inventory.setMaxStackSize(size);
        return this;
    }

    /**
     * @param player the player that receives the inventory
     * @description this opens the inventory to a player
     */
    public void show(Player player) {
        player.openInventory(inventory);
    }

    /**
     * @param bool disable/enable player inventory click
     */
    public void disablePlayerInventoryClick(Boolean bool) {
        this.playerInventoryClick = bool;
    }

    /**
     * @param bool disable/enable gui inventory click
     */
    public void disableInventoryClick(Boolean bool) {
        this.inventoryClick = bool;
    }


    /**
     * @param bool disable/enable gui inventory drag
     */
    public void disableInventoryDrag(Boolean bool) {
        this.inventoryDrag = bool;
    }

    /**
     * @param player the player with the inventory open
     * @param itemStack the item that the player clicked
     * @param inventoryType the clicked inventory type
     * @description this is to be used when you extend GUI in a new class
     */
    public abstract void click(Player player, ItemStack itemStack, InventoryType inventoryType);

    /**
     * @return the custom gui created
     * @description this returns the gui created
     */
    public Inventory create() {
        return inventory;
    }
}
