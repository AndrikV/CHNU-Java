package lab2Tests;

import lab1ClothesShop.Clothing;
import lab1ClothesShop.Manufacturer;
import lab1ClothesShop.Shop;
import lab2SerializationInterface.ClothingTextSerializer;
import lab2SerializationInterface.JSONSerializer;
import lab2SerializationInterface.XMLSerializer;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class SerializationTests {
    final private LocalDate date = LocalDate.now();
    final private Manufacturer manufacturer = new Manufacturer("Louis Vuitton", "Telegram: @example");
    final private Clothing clothing = new Clothing.ClothingBuilder(1, "Rainbow", "T-shirt", Clothing.FOR_WHOM.MALE)
            .setManufactureDate(date)
            .setManufacturerInfo(manufacturer)
            .setPrice(300)
            .build();
    final private Shop shop = new Shop("Some name", new ArrayList<>(Arrays.asList(clothing, clothing)));

    @Test
    public void entityToJSON() {
        SoftAssert softAssert = new SoftAssert();

        JSONSerializer<Clothing> clothingJSONSerializer = new JSONSerializer<Clothing>();
        softAssert.assertTrue(clothingJSONSerializer.toFile(clothing, "clothing.json"));

        JSONSerializer<Manufacturer> manufacturerJSONSerializer = new JSONSerializer<Manufacturer>();
        softAssert.assertTrue(manufacturerJSONSerializer.toFile(manufacturer, "manufacturer.json"));

        JSONSerializer<Shop> shopJSONSerializer = new JSONSerializer<Shop>();
        softAssert.assertTrue(shopJSONSerializer.toFile(shop, "shop.json"));

        softAssert.assertAll();
    }

    @Test
    public void listOfEntitiesToJSON() {
        SoftAssert softAssert = new SoftAssert();

        List<Clothing> listOfClothes = Collections.nCopies(5, clothing);
        JSONSerializer<Clothing> clothingJSONSerializer = new JSONSerializer<>();
        softAssert.assertTrue(clothingJSONSerializer.toFile(listOfClothes, "clothesList.json"));

        List<Manufacturer> listOfManufacturers = Collections.nCopies(5, manufacturer);
        JSONSerializer<Manufacturer> manufacturerJSONSerializer = new JSONSerializer<>();
        softAssert.assertTrue(manufacturerJSONSerializer.toFile(listOfManufacturers, "manufacturersList.json"));

        List<Shop> listOfShops = Collections.nCopies(5, shop);
        JSONSerializer<Shop> shopJSONSerializer = new JSONSerializer<>();
        softAssert.assertTrue(shopJSONSerializer.toFile(listOfShops, "shopsList.json"));

        softAssert.assertAll();
    }

    @Test
    public void entityToXML() {
        SoftAssert softAssert = new SoftAssert();

        XMLSerializer<Clothing> clothingXMLSerializer = new XMLSerializer<>();
        softAssert.assertTrue(clothingXMLSerializer.toFile(clothing, "clothing.xml"));

        XMLSerializer<Manufacturer> manufacturerXMLSerializer = new XMLSerializer<>();
        softAssert.assertTrue(manufacturerXMLSerializer.toFile(manufacturer, "manufacturer.xml"));

        XMLSerializer<Shop> shopXMLSerializer = new XMLSerializer<>();
        softAssert.assertTrue(shopXMLSerializer.toFile(shop, "shop.xml"));

        softAssert.assertAll();
    }

    @Test
    public void listOfEntitiesToXML() {
        SoftAssert softAssert = new SoftAssert();

        List<Clothing> listOfClothes = Collections.nCopies(5, clothing);
        XMLSerializer<Clothing> clothingXMLSerializer = new XMLSerializer<>();
        softAssert.assertTrue(clothingXMLSerializer.toFile(listOfClothes, "clothesList.xml"));

        List<Manufacturer> listOfManufacturers = Collections.nCopies(5, manufacturer);
        XMLSerializer<Manufacturer> manufacturerXMLSerializer = new XMLSerializer<>();
        softAssert.assertTrue(manufacturerXMLSerializer.toFile(listOfManufacturers, "manufacturersList.xml"));

        List<Shop> listOfShops = Collections.nCopies(5, shop);
        XMLSerializer<Shop> shopXMLSerializer = new XMLSerializer<>();
        softAssert.assertTrue(shopXMLSerializer.toFile(listOfShops, "shopsList.xml"));

        softAssert.assertAll();
    }

    @Test
    public void entityToText() {
        ClothingTextSerializer clothingTextSerializer = new ClothingTextSerializer();
        assertTrue(clothingTextSerializer.toFile(clothing, "clothing.txt"));
    }

    @Test
    public void listOfEntitiesToText() {
        List<Clothing> listOfClothes = Collections.nCopies(5, clothing);
        ClothingTextSerializer clothingTextSerializer = new ClothingTextSerializer();
        assertTrue(clothingTextSerializer.toFile(listOfClothes, "clothesList.txt"));
    }
}
