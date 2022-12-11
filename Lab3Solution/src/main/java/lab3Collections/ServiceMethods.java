package lab3Collections;

import lab1ClothesShop.Clothing;
import lab1ClothesShop.Manufacturer;
import lab1ClothesShop.Shop;

import java.util.*;


public class ServiceMethods {
    /**
     * Calculate number of clothes by FOR_WHOM categories
     *
     * @param clothes: list of clothes
     * @return hash map, where key is all 4 FOR_WHOM and values are number of clothes
     */
    static public HashMap<Clothing.FOR_WHOM, Integer> clothesNumberByForWhom(List<Clothing> clothes) {
        HashMap<Clothing.FOR_WHOM, Integer> result = new HashMap<>();
        for (Clothing.FOR_WHOM forWhom : Clothing.FOR_WHOM.values()) {
            result.put(forWhom, 0);
        }

        for (Clothing clothing : clothes) {
            Clothing.FOR_WHOM category = clothing.getForWhom();
            int currentCount = result.get(category);
            result.replace(category, currentCount + 1);
        }
        return result;
    }


    static private class ManufacturerClothesNumberComparator implements Comparator<Map.Entry<Manufacturer, Integer>> {
        @Override
        public int compare(Map.Entry<Manufacturer, Integer> ob1, Map.Entry<Manufacturer, Integer> ob2) {
            int comparingByNumbers = Integer.compare(ob2.getValue(), ob1.getValue());
            if (comparingByNumbers != 0) {
                return comparingByNumbers;
            }
            else {
                return ob1.getKey().getName().compareTo(ob2.getKey().getName());
            }
        }
    }

    /**
     * Calculate number of clothes for each manufacturer
     *
     * @param clothes: list of clothes
     * @param manufacturers: list of manufacturers, for which need to be calculated number of clothes
     * @return List<Map.Entry<Manufacturer, Integer>>, where Integer is a number of clothes
     */
    static public List<Map.Entry<Manufacturer, Integer>> clothesNumberByManufacturers(
            List<Clothing> clothes, List<Manufacturer> manufacturers) {
        HashMap<Manufacturer, Integer> hashMap = new HashMap<>();
        for (Manufacturer manufacturer : manufacturers) {
            hashMap.put(manufacturer, 0);
        }

        for (Clothing clothing : clothes) {
            if (hashMap.containsKey(clothing.getManufacturer())) {
                Manufacturer manufacturer = clothing.getManufacturer();
                int currentCount = hashMap.get(manufacturer);
                hashMap.replace(manufacturer, currentCount + 1);
            }
        }

        List<AbstractMap.Entry<Manufacturer, Integer>> resultList = new ArrayList<>(hashMap.entrySet());
        resultList.sort(new ManufacturerClothesNumberComparator());
        return resultList;
    }

    static public class ComparableByClothesNumberShop implements Comparable<ComparableByClothesNumberShop> {
        Shop shop;

        Shop getShop() {
            return shop;
        }

        Integer getClothesNumber() {
            return shop.getGoods().size();
        }

        public ComparableByClothesNumberShop(Shop shop)
        {
            this.shop = shop;
        }

        @Override
        public int compareTo(ComparableByClothesNumberShop shop) {
            return Integer.compare(getClothesNumber(), shop.getClothesNumber());
        }
    }

    /**
     * For each shop calculate number of clothes
     *
     * @param shops, for which need to be calculated number of clothes
     * @return List<AbstractMap.SimpleEntry<String, Integer>>, where String is shop name and Integer is a number of clothes
     */
    static public List<AbstractMap.SimpleEntry<String, Integer>> clothesNumberByShops(List<Shop> shops) {
        List<ComparableByClothesNumberShop> sortedShops = new ArrayList<>();
        for (Shop shop : shops) {
            sortedShops.add(new ComparableByClothesNumberShop(shop));
        }
        sortedShops.sort((a, b) -> b.compareTo(a));
        List<AbstractMap.SimpleEntry<String, Integer>> shopsClothesNumber = new ArrayList<>();
        for (ComparableByClothesNumberShop ob : sortedShops) {
            shopsClothesNumber.add(new AbstractMap.SimpleEntry<>(ob.getShop().getName(), ob.getClothesNumber()));
        }
        return shopsClothesNumber;
    }
}
