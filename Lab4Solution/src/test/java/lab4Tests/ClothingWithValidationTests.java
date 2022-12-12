package lab4Tests;

import lab1ClothesShop.Manufacturer;
import lab4Validation.ClothingWithValidation;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.LocalDate;

public class ClothingWithValidationTests {
    final private LocalDate date = LocalDate.now();
    final private Manufacturer manufacturer = new Manufacturer("Louis Vuitton", "Telegram: @example");

    @Test
    public void buildUsingValidData() {
        try {
            ClothingWithValidation clothing = new ClothingWithValidation();
            clothing.setId(1);
            clothing.setName("Rainbow");
            clothing.setType("T-shirt");
            clothing.setForWhom(ClothingWithValidation.FOR_WHOM.MALE);
            clothing.setManufactureDate(date);
            clothing.setManufacturerInfo(manufacturer);
            clothing.setPrice(300);

            clothing.validate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test(dataProvider = "invalidClothesDataProvider")
    public void buildUsingInvalidData(ClothingWithValidation.ClothingWithValidationBuilder invalidClothing) {
        try {
            invalidClothing.build();
        }
        catch (ClothingWithValidation.ValidationError e) {
            assert true;
            return;
        }
        assert false;
    }

    @DataProvider
    public Object[][] invalidClothesDataProvider() {
        return new Object[][] {
            {new ClothingWithValidation.ClothingWithValidationBuilder(-1, null, null, null)},
            {new ClothingWithValidation.ClothingWithValidationBuilder(100, null, null, null)},
            {new ClothingWithValidation.ClothingWithValidationBuilder(100, "Valid", null, null)},
            {new ClothingWithValidation.ClothingWithValidationBuilder(100, "Valid", "Valid", null)},
            {new ClothingWithValidation.ClothingWithValidationBuilder(100, "Valid", "Valid", ClothingWithValidation.FOR_WHOM.MALE)}
        };
    }
}
