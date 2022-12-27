package lab4Tests;

import lab1ClothesShop.Manufacturer;
import lab4Validation.Clothing;
import lab4Validation.ValidationError;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;

public class ClothingValidationTests {
    static final private LocalDate date = LocalDate.now();
    static final private Manufacturer manufacturer = new Manufacturer("Louis Vuitton", "Telegram: @example");
    static private Clothing validClothing;
    @BeforeClass
    public static void beforeClass() {
        validClothing = new Clothing.ClothingWithValidationBuilder(1, "Valid", "Valid",  Clothing.FOR_WHOM.MALE)
                .setManufacturerInfo(manufacturer)
                .setManufactureDate(date)
                .setPrice(100)
                .build();
    }

    @Test(dataProvider = "invalidClothesDataProvider", expectedExceptions = ValidationError.class)
    public void buildUsingInvalidData(Clothing.ClothingWithValidationBuilder invalidClothing) {
        invalidClothing.build();
    }

    @DataProvider
    public Object[][] invalidClothesDataProvider() {
        return new Object[][] {
            {new Clothing.ClothingWithValidationBuilder(-1, validClothing.getName(), validClothing.getType(), validClothing.getForWhom())},
            {new Clothing.ClothingWithValidationBuilder(ID, null, "Valid", Clothing.FOR_WHOM.MALE)},    //TODO
            {new Clothing.ClothingWithValidationBuilder(ID, "Valid", null, Clothing.FOR_WHOM.MALE)},
            {new Clothing.ClothingWithValidationBuilder(ID, "Valid", "Valid", null)},
            {new Clothing.ClothingWithValidationBuilder(ID, "Valid", "Valid", Clothing.FOR_WHOM.MALE)}
        };
    }
}
