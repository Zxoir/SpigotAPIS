package me.zxoir.myapi.profileapi;

import lombok.AllArgsConstructor;
import me.zxoir.myapi.Myapi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.concurrent.CompletableFuture.runAsync;

@AllArgsConstructor
public class ProfileLoader {
    private final Profile profile;
    private final Myapi plugin;

    private static final String INSERT = "INSERT INTO stats VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name=?, points=points+1";
    private static final String SELECT = "SELECT kills,deaths,points FROM stats WHERE uuid=?";

    public void load() {
        runAsync(() -> {

            Connection connection = null;

            try {
                connection = plugin.getHikari().getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
                preparedStatement.setString(1, profile.getUuid().toString());
                preparedStatement.setString(2, profile.getName());
                preparedStatement.setInt(3, profile.getKills());
                preparedStatement.setInt(4, profile.getDeaths());
                preparedStatement.setInt(5, profile.getPoints());
                preparedStatement.setString(6, profile.getName());
                preparedStatement.execute();

                preparedStatement = connection.prepareStatement(SELECT);
                preparedStatement.setString(1, profile.getUuid().toString());

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    profile.setKills(resultSet.getInt("kills"));
                    profile.setDeaths(resultSet.getInt("deaths"));
                    profile.setPoints(resultSet.getInt("points"));
                    profile.setLoaded(true);
                }

                preparedStatement.close();
                resultSet.close();

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
