package bibliotheque.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static DBConnection instance;
    private final Connection connection;

    private DBConnection() throws SQLException, IOException {
        Properties props = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("main/resources/application.properties")) {
            if (input == null) {
                throw new IOException("Fichier de configuration non trouvé.");
            }
            props.load(input);
        }

        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        try {
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connexion a la base de données etablie.");
        } catch (SQLException ex) {
            throw new SQLException("Echec de la création de la connexion a la base de données : " + ex.getMessage(), ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection getInstance() {
        DBConnection result = instance;
        if (result == null) {
            synchronized (DBConnection.class) {
                result = instance;
                if (result == null) {
                    try {
                        instance = result = new DBConnection();
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException("Echec de l'initialisation de la connexion a la base de donnees : " + ex.getMessage(), ex);
                    }
                }
            }
        }
        return result;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Echec de la fermeture de la connexion : " + ex.getMessage());
            }
        }
    }
}
