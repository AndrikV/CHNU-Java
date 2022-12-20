package lab3Collections;

import lab1ClothesShop.Clothing;
import lab1ClothesShop.Manufacturer;
import lab1ClothesShop.Shop;

import java.util.*;

public class ServiceMethods {
    /**
     * Calculate count of clothes by FOR_WHOM categories
     *
     * @param clothes: list of clothes
     * @return hash map, where key is all 4 FOR_WHOM and values are count of clothes
     */
    public HashMap<Clothing.FOR_WHOM, Integer> clothesCountByForWhom(List<Clothing> clothes) {
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


    static private class ManufacturerClothesCountComparator implements Comparator<Map.Entry<Manufacturer, Integer>> {
        @Override
        public int compare(Map.Entry<Manufacturer, Integer> ob1, Map.Entry<Manufacturer, Integer> ob2) {
            int comparingByCounts = Integer.compare(ob2.getValue(), ob1.getValue());
            if (comparingByCounts != 0) {
                return comparingByCounts;
            }
            else {
                return ob1.getKey().getName().compareTo(ob2.getKey().getName());
            }
        }
    }

    /**
     * Calculate count of clothes for each manufacturer
     *
     * @param clothes: list of clothes
     * @param manufacturers: list of manufacturers, for which need to be calculated count of clothes
     * @return List<Map.Entry<Manufacturer, Integer>>, where Integer is a count of clothes
     */
    public List<Map.Entry<Manufacturer, Integer>> clothesCountByManufacturers(
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
        resultList.sort(new ManufacturerClothesCountComparator());
        return resultList;
    }

    static public class ComparableByClothesCountShop implements Comparable<ComparableByClothesCountShop> {
        Shop shop;

        Shop getShop() {
            return shop;
        }

        Integer getClothesCount() {
            return shop.getGoods().size();
        }

        public ComparableByClothesCountShop(Shop shop)
        {
            this.shop = shop;
        }

        @Override
        public int compareTo(ComparableByClothesCountShop shop) {
            return Integer.compare(getClothesCount(), shop.getClothesCount());
        }
    }

    /**
     * For each shop calculate count of clothes
     *
     * @param shops, for which need to be calculated count of clothes
     * @return List<AbstractMap.SimpleEntry<String, Integer>>, where String is shop name and Integer is a count of clothes
     */
    public List<AbstractMap.SimpleEntry<String, Integer>> clothesCountByShops(List<Shop> shops) {
        List<ComparableByClothesCountShop> sortedShops = new ArrayList<>();
        for (Shop shop : shops) {
            sortedShops.add(new ComparableByClothesCountShop(shop));
        }
        sortedShops.sort((a, b) -> b.compareTo(a));
        List<AbstractMap.SimpleEntry<String, Integer>> shopsClothesCount = new ArrayList<>();
        for (ComparableByClothesCountShop ob : sortedShops) {
            shopsClothesCount.add(new AbstractMap.SimpleEntry<>(ob.getShop().getName(), ob.getClothesCount()));
        }
        return shopsClothesCount;
    }
}
