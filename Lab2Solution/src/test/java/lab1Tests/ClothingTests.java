package lab1Tests;


import java.time.LocalDate;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import lab1ClothesShop.Clothing;
import lab1ClothesShop.Manufacturer;

import static org.testng.Assert.assertEquals;


/**
 * Class that described clothes
 */
public class ClothingTests {
    final private LocalDate date = LocalDate.now();
    final private Manufacturer manufacturer = new Manufacturer("Louis Vuitton", "Telegram: @example");
    final private Clothing clothing = new Clothing.ClothingBuilder(1, "Rainbow", "T-shirt", Clothing.FOR_WHOM.MALE)
            .setManufactureDate(date)
            .setManufacturerInfo(manufacturer)
            .setPrice(300)
            .build();

    @Test
    public void builder() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(clothing.getId(), 1);
        softAssert.assertEquals(clothing.getName(), "Rainbow");
        softAssert.assertEquals(clothing.getType(), "T-shirt");
        softAssert.assertEquals(clothing.getForWhom(), Clothing.FOR_WHOM.MALE);
        softAssert.assertEquals(clothing.getManufactureDate(), date);
        softAssert.assertEquals(clothing.getManufacturer(), manufacturer);
        softAssert.assertEquals(clothing.getPrice(), 300);
        softAssert.assertAll();
    }

    @Test
    public void toStringTest() {
        assertEquals(clothing.toString(), "Name: Rainbow\n"
                + "Type: T-shirt\n"
                + "Sex: MALE\n"
                + "Manufacturer: " + manufacturer + "\n"
                + "Manufacture date: " + date + "\n"
                + "Price: 300\n");
    }

    @Test
    public void equalsTest() {
        final Clothing clothing1 = new Clothing.ClothingBuilder(2, "Rainbow", "T-shirt", Clothing.FOR_WHOM.MALE)
                .setManufactureDate(LocalDate.of(2022, 6, 12))
                .setManufacturerInfo(new Manufacturer("Louis Vuitton", "email: example@gucci.com"))
                .setPrice(250)
                .build();
        assertEquals(clothing1, clothing);
    }
}
