package me.zxoir.myapi.profileapi;

import lombok.AllArgsConstructor;
import me.zxoir.myapi.Myapi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.util.concurrent.CompletableFuture.runAsync;

@AllArgsConstructor
public class ProfileSaver {
    private final Profile profile;
    private final Myapi plugin;

    private static final String SAVE = "UPDATE stats SET kills=?, deaths=?, points=? WHERE uuid=?";

    public void save() {
        runAsync(() -> {

            Connection connection = null;

            try {
                connection = plugin.getHikari().getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
                preparedStatement.setInt(1, profile.getKills());
                preparedStatement.setInt(2, profile.getDeaths());
                preparedStatement.setInt(3, profile.getPoints());
                preparedStatement.setString(4, profile.getUuid().toString());
                preparedStatement.execute();
                preparedStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }
}
