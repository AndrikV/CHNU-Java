package lab5Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;


/**
 * Class allow work with database using JDBC tools
 */
public class DatabaseJDBSHandler {
    final String databaseURL;
    final String user;
    final String password;
    final Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public DatabaseJDBSHandler(String userPassword) throws SQLException {
        this.databaseURL = "jdbc:postgresql://localhost:5432/JavaClothesShop";
        this.user = "postgres";
        this.password = userPassword;
        this.connection = DriverManager.getConnection(databaseURL, user, password);
    }

    public DatabaseJDBSHandler(String databaseURL, String user, String password) throws SQLException {
        this.databaseURL = databaseURL;
        this.user = user;
        this.password = password;
        this.connection = DriverManager.getConnection(databaseURL, user, password);
    }

    public void clearDatabase() throws SQLException {
        final String[] deleteStatements = {
        "DROP TABLE IF EXISTS \"Shops_Clothes\";",
        "DROP TABLE IF EXISTS \"Shops\";",
        "DROP TABLE IF EXISTS \"Clothes\";",
        "DROP TABLE IF EXISTS \"Manufacturers\";"
        };

        try(Statement statement = connection.createStatement()) {
            for (String sql : deleteStatements)
                statement.execute(sql);
        }
    }

    public void createManufacturersTable() throws SQLException {
        final String tableStatement = """
        CREATE TABLE "Manufacturers" (
            "id" SERIAL PRIMARY KEY,
            "name" VARCHAR(256) NOT NULL,
            "contactInfo" TEXT NOT NULL
        );
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }

    public void createShopsTable() throws SQLException {
        final String tableStatement = """
        CREATE TABLE "Shops" (
            "id" SERIAL PRIMARY KEY,
            "name" VARCHAR(256) NOT NULL
        );
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }

    public void createClothesTable() throws SQLException {
        final String tableStatement = """
        CREATE TABLE "Clothes" (
            "id" SERIAL PRIMARY KEY,
            "name" VARCHAR(256) NOT NULL,
            "type" VARCHAR(256) NOT NULL,
            "forWhom" VARCHAR(256) NOT NULL,
            "manufacturer" INT NOT NULL
                REFERENCES "Manufacturers" ("id")
                ON DELETE CASCADE ON UPDATE CASCADE,
            "manufactureDate" TIMESTAMP NOT NULL
                DEFAULT CURRENT_TIMESTAMP,
            "price" INT NOT NULL
        );
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }

    public void createShopsClothesTable() throws SQLException {
        final String tableStatement = """
        CREATE TABLE "Shops_Clothes" (
            "shopId" INT NOT NULL
                REFERENCES "Shops" ("id")
                ON DELETE CASCADE ON UPDATE CASCADE,
            "clothingId" INT NOT NULL
                REFERENCES "Clothes" ("id")
                ON DELETE CASCADE ON UPDATE CASCADE
        );
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }

    public void insertDataToManufacturersTable() throws SQLException {
        final String tableStatement = """
        INSERT INTO "Manufacturers" ("name", "contactInfo") VALUES
          	('manufacturer1', 'Some contact info'),
          	('manufacturer2', 'Some contact info'),
          	('manufacturer3', 'Some contact info');
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }

    public void insertDataToShopsTable() throws SQLException {
        final String tableStatement = """
        INSERT INTO "Shops" ("name") VALUES
            ('shopName1'),
            ('shopName2'),
            ('shopName3');
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }

    public void insertDataToClothesTable() throws SQLException {
        final String tableStatement = """
        INSERT INTO "Clothes" ("name", "type", "forWhom", "manufacturer", "manufactureDate", "price") VALUES
            ('clothingName1', 'type1', 'MALE', 1, '2022-12-10', 100),
            ('clothingName2', 'type1', 'MALE', 1, '2022-12-11', 110),
            ('clothingName3', 'type2', 'FEMALE', 1, '2022-12-10', 200),
            ('clothingName4', 'type2', 'FEMALE', 1, '2022-12-11', 225),
            ('clothingName5', 'type3', 'BOY', 2, '2022-12-12', 75),
            ('clothingName6', 'type3', 'BOY', 2, '2022-12-12', 60),
            ('clothingName7', 'type3', 'BOY', 2, '2022-12-12', 70),
            ('clothingName8', 'type4', 'GIRL', 3, '2022-12-13', 75),
            ('clothingName9', 'type4', 'GIRL', 3, '2022-12-13', 60),
            ('clothingName10', 'type5', 'GIRL', 3, '2022-12-14', 70),
            ('clothingName11', 'type5', 'GIRL', 3, '2022-12-14', 70);
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }

    public void insertDataToShopsClothesTable() throws SQLException {
        final String tableStatement = """
        INSERT INTO "Shops_Clothes" ("shopId", "clothingId") VALUES
          	(1, 1),
          	(1, 2),
          	(1, 3),
          	(1, 4),
          	(1, 5),
          	(1, 6),
          	(1, 7),
          	(1, 8),
          	(1, 9),
          	(1, 10),
          	(1, 11),
          	(2, 2),
          	(2, 4),
          	(2, 5),
          	(2, 6),
          	(2, 8),
          	(2, 9),
          	(2, 11),
          	(3, 1),
          	(3, 3),
          	(3, 4),
          	(3, 5),
          	(3, 10);
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }
}
