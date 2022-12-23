package lab4Tests;

import lab1ClothesShop.Manufacturer;
import lab4Validation.Clothing;
import lab4Validation.ValidationError;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;

public class ClothingValidationTests {
    final private LocalDate date = LocalDate.now();
    final private Manufacturer manufacturer = new Manufacturer("Louis Vuitton", "Telegram: @example");

    @Test
    public void buildUsingValidData() {
        SoftAssert softAssert = new SoftAssert();
        try {
            Clothing clothing = new Clothing();
            clothing.setId(1);
            softAssert.assertEquals(clothing.getId(), 1);

            clothing.setName("Rainbow");
            softAssert.assertEquals(clothing.getName(), "Rainbow");

            clothing.setType("T-shirt");
            softAssert.assertEquals(clothing.getType(), "T-shirt");

            clothing.setForWhom(Clothing.FOR_WHOM.MALE);
            softAssert.assertEquals(clothing.getForWhom(), Clothing.FOR_WHOM.MALE);

            clothing.setManufactureDate(date);
            softAssert.assertEquals(clothing.getManufactureDate(), date);

            clothing.setManufacturerInfo(manufacturer);
            softAssert.assertEquals(clothing.getManufacturer(), manufacturer);

            clothing.setPrice(300);
            softAssert.assertEquals(clothing.getPrice(), 300);

            clothing.validate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "invalidClothesDataProvider", expectedExceptions = ValidationError.class)
    public void buildUsingInvalidData(Clothing.ClothingWithValidationBuilder invalidClothing) {
        invalidClothing.build();
    }

    @DataProvider
    public Object[][] invalidClothesDataProvider() {
        return new Object[][] {
            {new Clothing.ClothingWithValidationBuilder(-1, null, null, null)},
            {new Clothing.ClothingWithValidationBuilder(100, null, null, null)},
            {new Clothing.ClothingWithValidationBuilder(100, "Valid", null, null)},
            {new Clothing.ClothingWithValidationBuilder(100, "Valid", "Valid", null)},
            {new Clothing.ClothingWithValidationBuilder(100, "Valid", "Valid", Clothing.FOR_WHOM.MALE)}
        };
    }
}
