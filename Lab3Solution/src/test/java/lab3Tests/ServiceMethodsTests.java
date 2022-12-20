package lab3Tests;

import com.fasterxml.jackson.core.type.TypeReference;
import lab1ClothesShop.Clothing;
import lab1ClothesShop.Manufacturer;
import lab1ClothesShop.Shop;
import lab2SerializationInterface.JSONDeserializer;
import lab3Collections.ServiceMethods;
import lab3Collections.ServiceMethodsUsingStreamAPI;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

public class ServiceMethodsTests {
    private ServiceMethodsUsingStreamAPI serviceMethodsUsingStreamAPI = new ServiceMethodsUsingStreamAPI();
    private ServiceMethods serviceMethods = new ServiceMethods();

    @Test
    public void clothesCountByForWhomTest() {
        JSONDeserializer<Clothing> jsonDeserializer = new JSONDeserializer<>();
        List<Clothing> testList = jsonDeserializer.listFromFile(new TypeReference<>() {}, "clothesList.json");

        HashMap<Clothing.FOR_WHOM, Integer> resultForTesting = serviceMethods.clothesCountByForWhom(testList);
        HashMap<Clothing.FOR_WHOM, Integer> resultForTestingStreamAPI = serviceMethodsUsingStreamAPI.clothesCountByForWhom(testList);
        HashMap<Clothing.FOR_WHOM, Integer> expectedResult = new HashMap<>();
        expectedResult.put(Clothing.FOR_WHOM.MALE, 3);
        expectedResult.put(Clothing.FOR_WHOM.FEMALE, 3);
        expectedResult.put(Clothing.FOR_WHOM.BOY, 2);
        expectedResult.put(Clothing.FOR_WHOM.GIRL, 2);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(resultForTesting, expectedResult);
        softAssert.assertEquals(resultForTestingStreamAPI, expectedResult);
        softAssert.assertAll();
    }

    @Test
    public void clothesCountByManufacturersTest() {
        JSONDeserializer<Clothing> jsonDeserializer = new JSONDeserializer<>();
        List<Clothing> testList = jsonDeserializer.listFromFile(new TypeReference<>() {}, "clothesList.json");
        List<Manufacturer> manufacturerList = new ArrayList<>();
        manufacturerList.add(new Manufacturer("manufacturer1", "Some contact info"));
        manufacturerList.add(new Manufacturer("manufacturer2", "Some contact info"));
        manufacturerList.add(new Manufacturer("manufacturer3", "Some contact info"));
        manufacturerList.add(new Manufacturer("manufacturer4", "Some contact info"));

        List<Map.Entry<Manufacturer, Integer>> resultForTesting = serviceMethods
                .clothesCountByManufacturers(testList, manufacturerList);
        List<Map.Entry<Manufacturer, Integer>> resultForTestingStreamAPI = serviceMethodsUsingStreamAPI
                .clothesCountByManufacturers(testList, manufacturerList);
        List<Map.Entry<Manufacturer, Integer>> expectedResult = new ArrayList<>();
        expectedResult.add(new AbstractMap.SimpleEntry<>(manufacturerList.get(1), 5));
        expectedResult.add(new AbstractMap.SimpleEntry<>(manufacturerList.get(0), 2));
        expectedResult.add(new AbstractMap.SimpleEntry<>(manufacturerList.get(2), 2));
        expectedResult.add(new AbstractMap.SimpleEntry<>(manufacturerList.get(3), 1));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(resultForTesting, expectedResult);
        softAssert.assertEquals(resultForTestingStreamAPI, expectedResult);
        softAssert.assertAll();
    }

    @Test
    public void clothesCountByShopsTest() {
        JSONDeserializer<Shop> jsonDeserializer = new JSONDeserializer<>();
        List<Shop> shops = jsonDeserializer.listFromFile(new TypeReference<>() {}, "shopsList.json");
        List<AbstractMap.SimpleEntry<String, Integer>> sortedShops = serviceMethods.clothesCountByShops(shops);
        List<AbstractMap.SimpleEntry<String, Integer>> sortedShopsWithStreamAPI = serviceMethodsUsingStreamAPI.clothesCountByShops(shops);
        List<AbstractMap.SimpleEntry<String, Integer>> expectedResult = new ArrayList<>();
        expectedResult.add(new AbstractMap.SimpleEntry<>(shops.get(3).getName(), 10));
        expectedResult.add(new AbstractMap.SimpleEntry<>(shops.get(0).getName(), 6));
        expectedResult.add(new AbstractMap.SimpleEntry<>(shops.get(2).getName(), 5));
        expectedResult.add(new AbstractMap.SimpleEntry<>(shops.get(1).getName(), 4));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(sortedShops, expectedResult);
        softAssert.assertEquals(sortedShopsWithStreamAPI, expectedResult);
        softAssert.assertAll();
    }
}
