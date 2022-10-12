package lab1Tests;

import lab1ClothesShop.Manufacturer;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.*;

public class ManufacturerTests {
    final private Manufacturer manufacturer = new Manufacturer("Louis Vuitton", "Telegram: @example");

    @Test
    public void constructorTest() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(manufacturer.getName(), "Louis Vuitton");
        softAssert.assertEquals(manufacturer.getContactInfo(), "Telegram: @example");
        softAssert.assertAll();
    };

    @Test
    public void equalsTest() {
        assertEquals(manufacturer, new Manufacturer("Louis Vuitton", "Phone: +380663647294"));
    }

    @Test
    public void toStringTest() {
        assertEquals(manufacturer.toString(), "Name: Louis Vuitton\n"
                + "Contact info: Telegram: @example\n");
    }
}
