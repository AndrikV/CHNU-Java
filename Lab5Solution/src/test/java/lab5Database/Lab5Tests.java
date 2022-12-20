package lab5Database;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// TODO: tests for each insert**
public class Lab5Tests {
    final String databaseURL = "jdbc:postgresql://localhost:5432/JavaClothesShop";
    final String user = "postgres";
    final String password = "PG_GK_Miner_2022";

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
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    public void createTablesFromScratch() {
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
            System.out.println(e.getMessage());
            assert false;
            return;
        }

        // checking that the tables are actually created
        try(Statement statement = databaseJDBSHandler.getConnection().createStatement()) {
            statement.executeQuery("SELECT * FROM \"Manufacturers\"");
            statement.executeQuery("SELECT * FROM \"Clothes\"");
            statement.executeQuery("SELECT * FROM \"Shops\"");
            statement.executeQuery("SELECT * FROM \"Shops_Clothes\"");
        } catch (SQLException e) {
            assert false;
        }
    }

    @Test
    public void insertValues() {
        DatabaseJDBSHandler databaseJDBSHandler;
        try {
            databaseJDBSHandler = new DatabaseJDBSHandler(databaseURL, user, password);
            databaseJDBSHandler.insertDataToManufacturersTable();
            databaseJDBSHandler.insertDataToClothesTable();
            databaseJDBSHandler.insertDataToShopsTable();
            databaseJDBSHandler.insertDataToShopsClothesTable();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
            return;
        }

        // checking that the data are actually inserted
        SoftAssert softAssert = new SoftAssert();
        try(Statement statement = databaseJDBSHandler.getConnection()
                .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            int size;
            ResultSet resultSet;

            resultSet = statement.executeQuery("SELECT * FROM \"Manufacturers\"");
            resultSet.last();
            size = resultSet.getRow();
            softAssert.assertEquals(size, 3);

            resultSet = statement.executeQuery("SELECT * FROM \"Clothes\"");
            resultSet.last();
            size = resultSet.getRow();
            softAssert.assertEquals(size, 11);

            resultSet = statement.executeQuery("SELECT * FROM \"Shops\"");
            resultSet.last();
            size = resultSet.getRow();
            softAssert.assertEquals(size, 3);

            resultSet = statement.executeQuery("SELECT * FROM \"Shops_Clothes\"");
            resultSet.last();
            size = resultSet.getRow();
            softAssert.assertEquals(size, 23);

            softAssert.assertAll();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }
}
