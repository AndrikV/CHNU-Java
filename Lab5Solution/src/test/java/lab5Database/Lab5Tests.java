package lab5Database;

import lab1ClothesShop.Clothing;
import lab1ClothesShop.Manufacturer;
import lab1ClothesShop.Shop;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

// TODO: separated classes
public class Lab5Tests {
    static final String databaseURL = "jdbc:postgresql://localhost:5432/JavaClothesShop";
    static final String user = "postgres";
    static final String password = "PG_GK_Miner_2022";
    static protected DatabaseJDBSHandler databaseJDBSHandler;


    public static class Data {
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

        protected int getClothesShopsRecordsCount() {
            return shops.stream().map(shop -> shop.getGoods().size()).reduce(0, Integer::sum);
        }
    }
    Data data = new Data();

    @BeforeClass
    static public void beforeClass() throws SQLException {
        databaseJDBSHandler = new DatabaseJDBSHandler(databaseURL, user, password);
    }

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

    private int getRecordsCount(String tableName) throws SQLException {
        DatabaseJDBSHandler databaseJDBSHandler = new DatabaseJDBSHandler(databaseURL, user, password);
        try(Statement statement = databaseJDBSHandler.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT Count(*) FROM " + tableName + ";");
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    @Test(dependsOnMethods = { "createTablesFromScratch" })
    public void addRecordsToManufacturersTableTest() {
        try {
            databaseJDBSHandler.addRecordsToManufacturersTable(data.manufacturers);
            assertEquals(getRecordsCount("Manufacturers"), data.manufacturers.size());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(dependsOnMethods = { "createTablesFromScratch" })
    public void addRecordsToShopsTableTest() {
        try {
            databaseJDBSHandler.addRecordsToShopsTable(data.shops);
            assertEquals(getRecordsCount("Shops"), data.shops.size());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(dependsOnMethods = { "addRecordsToManufacturersTableTest" })
    public void addRecordsToClothesTableTest() {
        try {
            databaseJDBSHandler.addRecordsToClothesTable(data.clothes);
            assertEquals(getRecordsCount("Clothes"), data.clothes.size());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(dependsOnMethods = {"addRecordsToShopsTableTest", "addRecordsToClothesTableTest"})
    public void addRecordsToShopsClothesTableTest() {
        try {
            databaseJDBSHandler.addRecordsToShopsClothesTable(data.shops);
            assertEquals(getRecordsCount("Shops_Clothes"), data.getClothesShopsRecordsCount());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
