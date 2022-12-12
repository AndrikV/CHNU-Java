package lab1Tests;

import lab1ClothesShop.Shop;
import lab1ClothesShop.Manufacturer;
import lab1ClothesShop.Clothing;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

public class ShopTests {
    final private Shop shop = new Shop("Some name");

    @Test
    public void creatingTest() {
        assertEquals(shop.getName(), "Some name");
    }

    @Test
    public void addingGoodsTest() {
        final LocalDate date = LocalDate.now();
        final Manufacturer manufacturer = new Manufacturer("Louis Vuitton", "Telegram: @example");
        final Clothing clothing = new Clothing.ClothingBuilder(1, "Rainbow", "T-shirt", Clothing.FOR_WHOM.MALE)
                .setManufactureDate(date)
                .setManufacturerInfo(manufacturer)
                .setPrice(300)
                .build();
        shop.addGood(clothing);
        assertEquals(shop.getGoods().get(0), clothing);
    }

    @Test
    public void equalsTest() {
        Shop shop1 = new Shop("Some name");
        shop1.addGood(new Clothing());
        shop1.addGood(new Clothing());
        assertEquals(shop, shop1);
    }
}
