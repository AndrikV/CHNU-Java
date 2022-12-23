package lab5Database;

import lab1ClothesShop.Clothing;
import lab1ClothesShop.Manufacturer;
import lab1ClothesShop.Shop;

import java.sql.*;
import java.util.List;


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
        "DROP TABLE IF EXISTS Shops_Clothes;",
        "DROP TABLE IF EXISTS Shops;",
        "DROP TABLE IF EXISTS Clothes;",
        "DROP TABLE IF EXISTS Manufacturers;"
        };

        try(Statement statement = connection.createStatement()) {
            for (String sql : deleteStatements)
                statement.execute(sql);
        }
    }

    public void createManufacturersTable() throws SQLException {
        final String tableStatement = """
        CREATE TABLE Manufacturers (
            id SERIAL PRIMARY KEY,
            name VARCHAR(256) NOT NULL,
            contactInfo TEXT NOT NULL
        );
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }

    public void createShopsTable() throws SQLException {
        final String tableStatement = """
        CREATE TABLE Shops (
            id SERIAL PRIMARY KEY,
            name VARCHAR(256) NOT NULL
        );
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }

    public void createClothesTable() throws SQLException {
        final String tableStatement = """
        CREATE TABLE Clothes (
            id SERIAL PRIMARY KEY,
            name VARCHAR(256) NOT NULL,
            type VARCHAR(256) NOT NULL,
            forWhom VARCHAR(256) NOT NULL,
            manufacturer INT NOT NULL
                REFERENCES Manufacturers (id)
                ON DELETE CASCADE ON UPDATE CASCADE,
            manufactureDate TIMESTAMP NOT NULL
                DEFAULT CURRENT_TIMESTAMP,
            price INT NOT NULL
        );
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }

    public void createShopsClothesTable() throws SQLException {
        final String tableStatement = """
        CREATE TABLE Shops_Clothes (
            shopId INT NOT NULL
                REFERENCES Shops (id)
                ON DELETE CASCADE ON UPDATE CASCADE,
            clothingId INT NOT NULL
                REFERENCES Clothes (id)
                ON DELETE CASCADE ON UPDATE CASCADE
        );
        """;
        try(Statement statement = connection.createStatement()) {
            statement.execute(tableStatement);
        }
    }

    public void addRecordToManufacturersTable(Manufacturer manufacturer) throws SQLException {
        final String insertionStatement = "INSERT INTO Manufacturers (name, contactInfo) VALUES (?, ?) RETURNING ID;";
        PreparedStatement preparedStatement = connection.prepareStatement(insertionStatement);
        preparedStatement.setString(1, manufacturer.getName());
        preparedStatement.setString(2, manufacturer.getContactInfo());
        preparedStatement.execute();
    }

    public void addRecordsToManufacturersTable(List<Manufacturer> manufacturers) throws SQLException {
        for (Manufacturer manufacturer : manufacturers) {
            addRecordToManufacturersTable(manufacturer);
        }
    }

    public void addRecordToShopsTable(Shop shop) throws SQLException {
        final String insertionStatement = "INSERT INTO Shops (name) VALUES (?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertionStatement);
        preparedStatement.setString(1, shop.getName());
        preparedStatement.execute();
    }

    public void addRecordsToShopsTable(List<Shop> shops) throws SQLException {
        for (Shop shop : shops) {
            addRecordToShopsTable(shop);
        }
    }

    public void addRecordToClothesTable(Clothing clothing) throws SQLException {
        final String insertionStatement = """
            INSERT INTO Clothes (name, type, forWhom, manufacturer, manufactureDate, price)
            VALUES (?, ?, ?, ?, ?, ?);
        """;
        PreparedStatement preparedStatement = connection.prepareStatement(insertionStatement);
        preparedStatement.setString(1, clothing.getName());
        preparedStatement.setString(2, clothing.getType());
        preparedStatement.setString(3, clothing.getForWhom().toString());
        preparedStatement.setInt(4, 1);         // TODO: ?
        preparedStatement.setTimestamp(5, clothing.getManufactureDate());
        preparedStatement.setInt(6, clothing.getPrice());
        preparedStatement.execute();
    }

    public void addRecordsToClothesTable(List<Clothing> clothes) throws SQLException {
        for (Clothing clothing : clothes) {
            addRecordToClothesTable(clothing);
        }
    }

    public void addRecordToShopsClothesTable(Shop shop) throws SQLException {
        int shopId = 1; // TODO: get id of shop
        for(Clothing clothing : shop.getGoods()) {
            final String insertionStatement = """
                INSERT INTO Shops_Clothes (shopId, clothingId)
                VALUES (?, ?);
            """;
            PreparedStatement preparedStatement = connection.prepareStatement(insertionStatement);
            preparedStatement.setInt(1, shopId);
            int clothingId = 1; // TODO: get id of clothing
            preparedStatement.setInt(2, clothingId);
            preparedStatement.execute();
        }
    }

    public void addRecordsToShopsClothesTable(List<Shop> shops) throws SQLException {
        for (Shop shop : shops) {
            addRecordToShopsClothesTable(shop);
        }
    }
}
