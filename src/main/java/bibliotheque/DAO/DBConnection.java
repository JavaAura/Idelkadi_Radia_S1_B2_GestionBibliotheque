package main.java.bibliotheque.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private DBConnection() throws SQLException, IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("main/resources/application.properties")){
            Properties props = new Properties();
            if (input == null) {
                throw new IOException("Fichier de configuration non trouvé.");
            }
            props.load(input);

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            this.connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException ex) {
            throw  new SQLException("Échec de la création de la connexion à la base de données : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new DBConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Échec de la fermeture de la connexion : " + ex.getMessage());
            }
        }
    }
}
