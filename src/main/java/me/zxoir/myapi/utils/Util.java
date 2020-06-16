package me.zxoir.myapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Util {
    public static boolean Debug = false;

    public static void debug(String message) {
        if (Debug)
            Bukkit.getServer().getConsoleSender().sendMessage(message);
    }

    public static String colorize(String arg) {
        return ChatColor.translateAlternateColorCodes('&', arg);
    }
}
