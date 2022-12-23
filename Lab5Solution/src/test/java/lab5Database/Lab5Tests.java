package lab5Database;

import lab1ClothesShop.Clothing;
import lab1ClothesShop.Manufacturer;
import lab1ClothesShop.Shop;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class Lab5Tests {
    final String databaseURL = "jdbc:postgresql://localhost:5432/JavaClothesShop";
    final String user = "postgres";
    final String password = "PG_GK_Miner_2022";

    public class Data {
        final long dayInMillis = 1000 * 60 * 60 * 60;
        List<Manufacturer> manufacturers = new ArrayList<>();
        List<Clothing> clothes = new ArrayList<>();
        List<Shop> shops = new ArrayList<>();

        Data() {
            manufacturers.add(new Manufacturer("manufacturer1", "Some contact info"));
            manufacturers.add(new Manufacturer("manufacturer2", "Some contact info"));
            manufacturers.add(new Manufacturer("manufacturer3", "Some contact info"));

            clothes.add(new Clothing.ClothingBuilder("clothingName1", "type1", Clothing.FOR_WHOM.MALE)
                    .setManufacturerInfo(manufacturers.get(0))
                    .setPrice(100)
                    .setManufactureDate(new Timestamp(System.currentTimeMillis() - 7 * dayInMillis))
                    .build()
            );
            clothes.add(new Clothing.ClothingBuilder("clothingName2", "type1", Clothing.FOR_WHOM.FEMALE)
                            .setManufacturerInfo(manufacturers.get(0))
                            .setPrice(110)
                            .setManufactureDate(new Timestamp(System.currentTimeMillis() - 7 * dayInMillis))
                            .build()
            );
            clothes.add(new Clothing.ClothingBuilder("clothingName3", "type2", Clothing.FOR_WHOM.BOY)
                            .setManufacturerInfo(manufacturers.get(1))
                            .setPrice(200)
                            .setManufactureDate(new Timestamp(System.currentTimeMillis() - 5 * dayInMillis))
                            .build()
            );
            clothes.add(new Clothing.ClothingBuilder("clothingName4", "type2", Clothing.FOR_WHOM.GIRL)
                            .setManufacturerInfo(manufacturers.get(1))
                            .setPrice(225)
                            .setManufactureDate(new Timestamp(System.currentTimeMillis() - 5 * dayInMillis))
                            .build()
            );
            clothes.add(new Clothing.ClothingBuilder("clothingName5", "type3", Clothing.FOR_WHOM.GIRL)
                            .setManufacturerInfo(manufacturers.get(2))
                            .setPrice(250)
                            .setManufactureDate(new Timestamp(System.currentTimeMillis() - 4 * dayInMillis))
                            .build()
            );

            shops.add(new Shop("Shop1", new ArrayList<>(clothes.subList(2, 5))));
            shops.add(new Shop("Shop2", new ArrayList<>(clothes.subList(0, 3))));
            shops.add(new Shop("Shop3", new ArrayList<>(clothes.subList(0, 5))));
        }
    }
    Data data = new Data();

    @Test
    public void construtorWithPasswordOnly() {
        try {
            new DatabaseJDBSHandler(password);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    public void construtorWithPasswordUserAndURL() {
        try {
            new DatabaseJDBSHandler(databaseURL, user, password);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createTablesFromScratch() throws SQLException {
        DatabaseJDBSHandler databaseJDBSHandler;
        try {
            databaseJDBSHandler = new DatabaseJDBSHandler(databaseURL, user, password);
            databaseJDBSHandler.clearDatabase();
            databaseJDBSHandler.createManufacturersTable();
            databaseJDBSHandler.createClothesTable();
            databaseJDBSHandler.createShopsTable();
            databaseJDBSHandler.createShopsClothesTable();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        // checking that the tables are actually created
        SoftAssert softAssert = new SoftAssert();
        try (Statement statement = databaseJDBSHandler.getConnection().createStatement()) {
            softAssert.assertNotNull(statement.executeQuery("SELECT * FROM Manufacturers"));
            softAssert.assertNotNull(statement.executeQuery("SELECT * FROM Clothes"));
            softAssert.assertNotNull(statement.executeQuery("SELECT * FROM Shops"));
            softAssert.assertNotNull(statement.executeQuery("SELECT * FROM Shops_Clothes"));
        }
        softAssert.assertAll();
    }

    private int getCountOfRecords(ResultSet resultSet)
            throws AssertionError, SQLException {
        resultSet.last();
        return resultSet.getRow();
    }


//    @Test(dependsOnMethods = { "createTablesFromScratch" })
    @Test(dependsOnMethods = { "createTablesFromScratch" })
    public void addRecordsToManufacturersTableTest() {
        DatabaseJDBSHandler databaseJDBSHandler;
        try {
            databaseJDBSHandler = new DatabaseJDBSHandler(databaseURL, user, password);
            databaseJDBSHandler.addRecordsToManufacturersTable(data.manufacturers);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        try(Connection connection = databaseJDBSHandler.getConnection()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Manufacturers;");
            int size = getCountOfRecords(resultSet);
            assertEquals(size, data.manufacturers.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

//    @Test(dependsOnMethods = { "createTablesFromScratch" })
    @Test(dependsOnMethods = { "createTablesFromScratch" })
    public void addRecordsToShopsTableTest() {
        DatabaseJDBSHandler databaseJDBSHandler;
        try {
            databaseJDBSHandler = new DatabaseJDBSHandler(databaseURL, user, password);
            databaseJDBSHandler.addRecordsToShopsTable(data.shops);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try(Connection connection = databaseJDBSHandler.getConnection()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Shops;");
            int size = getCountOfRecords(resultSet);
            assertEquals(size, data.shops.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @Test(dependsOnMethods = { "createTablesFromScratch", "addRecordsToManufacturersTableTest" })
    @Test(dependsOnMethods = { "addRecordsToManufacturersTableTest" })
    public void addRecordsToClothesTableTest() {
        DatabaseJDBSHandler databaseJDBSHandler;
        try {
            databaseJDBSHandler = new DatabaseJDBSHandler(databaseURL, user, password);
            databaseJDBSHandler.addRecordsToClothesTable(data.clothes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try(Connection connection = databaseJDBSHandler.getConnection()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Clothes;");
            int size = getCountOfRecords(resultSet);
            assertEquals(size, data.clothes.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test(dependsOnMethods = {"addRecordsToShopsTableTest", "addRecordsToClothesTableTest"})
            public void addRecordsToShopsClothesTableTest() {
        DatabaseJDBSHandler databaseJDBSHandler;
        try {
            databaseJDBSHandler = new DatabaseJDBSHandler(databaseURL, user, password);
            databaseJDBSHandler.addRecordsToShopsClothesTable(data.shops);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try(Connection connection = databaseJDBSHandler.getConnection()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Shops_Clothes;");
            int size = getCountOfRecords(resultSet);
            int expectedSize = 0;
            for (Shop shop : data.shops)
                expectedSize += shop.getGoods().size();
            assertEquals(size, expectedSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
