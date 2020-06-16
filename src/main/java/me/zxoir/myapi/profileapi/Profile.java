package me.zxoir.myapi.profileapi;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Profile {

    // Keys
    private UUID uuid;
    private String name;

    // Statistics
    private int kills;
    private int deaths;
    private int points;
    private int totalLogins;
    private double kdr;
    private long playtime;
    private LocalDateTime lastSeen;

    // Punishes and cooldons

    private boolean loaded;

    public Profile(OfflinePlayer player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }
}
