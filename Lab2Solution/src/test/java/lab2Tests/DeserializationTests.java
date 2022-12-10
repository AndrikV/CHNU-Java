package lab2Tests;

import com.fasterxml.jackson.core.type.TypeReference;
import lab1ClothesShop.Clothing;
import lab1ClothesShop.Manufacturer;
import lab1ClothesShop.Shop;
import lab2SerializationInterface.ClothingTextDeserializer;
import lab2SerializationInterface.JSONDeserializer;
import lab2SerializationInterface.XMLDeserializer;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;


public class DeserializationTests {
    final private LocalDate date = LocalDate.now();
    final private Manufacturer manufacturer = new Manufacturer("Louis Vuitton", "Telegram: @example");
    final private Clothing clothing = new Clothing.ClothingBuilder(1, "Rainbow", "T-shirt", Clothing.FOR_WHOM.MALE)
            .setManufactureDate(date)
            .setManufacturerInfo(manufacturer)
            .setPrice(300)
            .build();
    final private Shop shop = new Shop("Some name", new ArrayList<>(Arrays.asList(clothing, clothing)));

    @Test
    public void jsonToEntity() {
        SoftAssert softAssert = new SoftAssert();

        JSONDeserializer<Clothing> jsonClothingDeserializer = new JSONDeserializer<>();
        Clothing deserializedClothing = jsonClothingDeserializer.entityFromFile(
                Clothing.class, "clothing.json");
        softAssert.assertEquals(deserializedClothing, clothing);

        JSONDeserializer<Manufacturer> jsonManufacturerDeserializer = new JSONDeserializer<>();
        Manufacturer deserializedManufacturer = jsonManufacturerDeserializer.entityFromFile(
                Manufacturer.class, "manufacturer.json");
        softAssert.assertEquals(deserializedManufacturer, manufacturer);

        JSONDeserializer<Shop> jsonShopDeserializer = new JSONDeserializer<>();
        Shop deserializedShop = jsonShopDeserializer.entityFromFile(
                Shop.class, "shop.json");
        softAssert.assertEquals(deserializedShop, shop);

        softAssert.assertAll();
    }

    @Test
    public void jsonToListOfEntities() {
        SoftAssert softAssert = new SoftAssert();

        JSONDeserializer<Clothing> jsonClothingDeserializer = new JSONDeserializer<>();
        List<Clothing> deserializedListOfClothes = jsonClothingDeserializer.listFromFile(
                new TypeReference<>() {}, "clothesList.json");
        softAssert.assertEquals(deserializedListOfClothes, Collections.nCopies(5, clothing));

        JSONDeserializer<Manufacturer> jsonManufacturerDeserializer = new JSONDeserializer<>();
        List<Manufacturer> deserializedListOfManufacturers = jsonManufacturerDeserializer.listFromFile(
                new TypeReference<>() {},"manufacturersList.json");
        softAssert.assertEquals(deserializedListOfManufacturers, Collections.nCopies(5, manufacturer));

        JSONDeserializer<Shop> jsonShopDeserializer = new JSONDeserializer<>();
        List<Shop> deserializedListOfShops = jsonShopDeserializer.listFromFile(
                new TypeReference<>() {},"shopsList.json");
        softAssert.assertEquals(deserializedListOfShops, Collections.nCopies(5, shop));

        softAssert.assertAll();
    }

    @Test
    public void xmlToEntity() {
        SoftAssert softAssert = new SoftAssert();

        XMLDeserializer<Clothing> xmlClothingDeserializer = new XMLDeserializer<>();
        Clothing deserializedClothing = xmlClothingDeserializer.entityFromFile(
                Clothing.class, "clothing.xml");
        softAssert.assertEquals(deserializedClothing, clothing);

        XMLDeserializer<Manufacturer> xmlManufacturerDeserializer = new XMLDeserializer<>();
        Manufacturer deserializedManufacturer = xmlManufacturerDeserializer.entityFromFile(
                Manufacturer.class, "manufacturer.xml");
        softAssert.assertEquals(deserializedManufacturer, manufacturer);

        XMLDeserializer<Shop> xmlShopDeserializer = new XMLDeserializer<>();
        Shop deserializedShop = xmlShopDeserializer.entityFromFile(
                Shop.class, "shop.xml");
        softAssert.assertEquals(deserializedShop, shop);

        softAssert.assertAll();
    }

    @Test
    public void xmlToListOfEntities() {
        SoftAssert softAssert = new SoftAssert();

        XMLDeserializer<Clothing> xmlClothingDeserializer = new XMLDeserializer<>();
        List<Clothing> deserializedListOfClothes = xmlClothingDeserializer.listFromFile(
                new TypeReference<>() {}, "clothesList.xml");
        softAssert.assertEquals(deserializedListOfClothes, Collections.nCopies(5, clothing));

        XMLDeserializer<Manufacturer> xmlManufacturerDeserializer = new XMLDeserializer<>();
        List<Manufacturer> deserializedListOfManufacturers = xmlManufacturerDeserializer.listFromFile(
                new TypeReference<>() {}, "manufacturersList.xml");
        softAssert.assertEquals(deserializedListOfManufacturers, Collections.nCopies(5, manufacturer));

        XMLDeserializer<Shop> xmlShopDeserializer = new XMLDeserializer<>();
        List<Shop> deserializedListOfShops = xmlShopDeserializer.listFromFile(
                new TypeReference<>() {}, "shopsList.xml");
        softAssert.assertEquals(deserializedListOfShops, Collections.nCopies(5, shop));

        softAssert.assertAll();
    }

    @Test
    public void validTextToEntity() {
        ClothingTextDeserializer clothingTextDeserializer = new ClothingTextDeserializer();
        Clothing localClothing = clothingTextDeserializer.entityFromFile("clothing.txt");
        assertEquals(localClothing, clothing);
    }

    @Test
    public void invalidTextToEntities() {
        ClothingTextDeserializer clothingTextDeserializer = new ClothingTextDeserializer();
        try {
            clothingTextDeserializer.entityFromFile("invalidClothing.txt");
        }
        catch (RuntimeException e)
        {
            assert true;
            return;
        }
        assert false;
    }

    @Test
    public void validTextToEntities() {
        List<Clothing> clothingList = Collections.nCopies(5, clothing);;
        ClothingTextDeserializer clothingTextDeserializer = new ClothingTextDeserializer();
        List<Clothing> deserializedList = clothingTextDeserializer.listFromFile("clothesList.txt");
        assertEquals(deserializedList, clothingList);
    }

    @Test
    public void invalidTextToEntity() {
        ClothingTextDeserializer clothingTextDeserializer = new ClothingTextDeserializer();
        try {
            clothingTextDeserializer.entityFromFile("invalidClothes.txt");
        }
        catch (RuntimeException e)
        {
            assert true;
            return;
        }
        assert false;
    }
}
