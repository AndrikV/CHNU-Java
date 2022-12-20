package lab3Collections;

import lab1ClothesShop.Clothing;
import lab1ClothesShop.Manufacturer;
import lab1ClothesShop.Shop;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ServiceMethodsUsingStreamAPI {
    /**
     * Calculate count of clothes by FOR_WHOM categories
     *
     * @param clothes: list of clothes
     * @return hash map, where key is all 4 FOR_WHOM and values are count of clothes
     */
    public HashMap<Clothing.FOR_WHOM, Integer> clothesCountByForWhom(List<Clothing> clothes) {
        // Change: used collect(Collectors.groupingBy(...))
        return new HashMap<> (
            clothes.stream()
                .collect(
                    Collectors.groupingBy(
                        Clothing::getForWhom,
                        Collectors.reducing(0, e -> 1, Integer::sum)
                    )
                )
        );
    }

    private static class ManufacturerClothesCountComparator implements Comparator<Map.Entry<Manufacturer, Integer>> {
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
            AtomicReference<Manufacturer> atomicManufacturer = new AtomicReference<>(manufacturer);
            List<Clothing> tmp = clothes.stream()
                    .filter((clothing) -> clothing.getManufacturer().equals(atomicManufacturer.get()))
                    .collect(Collectors.toList());
            hashMap.put(manufacturer, tmp.size());
        }

        List<AbstractMap.Entry<Manufacturer, Integer>> resultList = new ArrayList<>(hashMap.entrySet());
        resultList = resultList.stream()
                .sorted(new ManufacturerClothesCountComparator())
                .collect(Collectors.toList());
        return resultList;
    }

    public static class ComparableByClothesCountShop implements Comparable<ComparableByClothesCountShop> {
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
        sortedShops = sortedShops.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        List<AbstractMap.SimpleEntry<String, Integer>> shopsClothesCount = new ArrayList<>();
        for (ComparableByClothesCountShop ob : sortedShops) {
            shopsClothesCount.add(new AbstractMap.SimpleEntry<>(ob.getShop().getName(), ob.getClothesCount()));
        }
        return shopsClothesCount;
    }
}
