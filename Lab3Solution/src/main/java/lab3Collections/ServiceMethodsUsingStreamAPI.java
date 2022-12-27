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
    public HashMap<Clothing.FOR_WHOM, Long> clothesCountByForWhom(List<Clothing> clothes) {
        // Change: used collect(Collectors.groupingBy(...))
        return new HashMap<> (
            clothes.stream()
                .collect(
                    Collectors.groupingBy(
                        Clothing::getForWhom,
                        Collectors.counting()
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
            return ob1.getKey().getName().compareTo(ob2.getKey().getName());
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

    public static class ShopClothesCountComparator implements Comparator<AbstractMap.SimpleEntry<String, Integer>> {
        @Override
        public int compare(AbstractMap.SimpleEntry<String, Integer> ob1, AbstractMap.SimpleEntry<String, Integer> ob2) {
            int comparingByCounts = Integer.compare(ob2.getValue(), ob1.getValue());
            if (comparingByCounts != 0) {
                return comparingByCounts;
            }
            return ob1.getKey().compareTo(ob2.getKey());
        }
    }

    /**
     * For each shop calculate count of clothes
     *
     * @param shops, for which need to be calculated count of clothes
     * @return List<AbstractMap.SimpleEntry<String, Integer>>, where String is shop name and Integer is a count of clothes
     */
    public List<AbstractMap.SimpleEntry<String, Integer>> clothesCountByShops(List<Shop> shops) {
        List<AbstractMap.SimpleEntry<String, Integer>> result = new ArrayList<>();
        for (Shop shop : shops) {
            result.add(new AbstractMap.SimpleEntry<>(shop.getName(), shop.getGoods().size()));
        }
        return result.stream().sorted(new ShopClothesCountComparator()).collect(Collectors.toList());
    }
}
