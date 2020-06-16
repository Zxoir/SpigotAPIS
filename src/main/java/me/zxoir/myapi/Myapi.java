package me.zxoir.myapi;

import me.zxoir.myapi.gui.GUIManager;
import me.zxoir.myapi.profileapi.ProfileManager;
import me.zxoir.myapi.utils.Util;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Zxoir
 * @since 15/06/2020
 */

public final class Myapi extends JavaPlugin {

    @Override
    public void onEnable() {
        Util.Debug = true; // This enables debug messages on my Util class

        ProfileManager profileManager = new ProfileManager();
        new GUIManager(this); // You must include this to register your gui listeners
    }
}
