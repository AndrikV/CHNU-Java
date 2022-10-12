package lab1ClothesShop;

import java.util.ArrayList;
import java.util.List;


/**
 * Class that described clothes shop
 */
public class Shop {
    private String name;
    private List<Clothing> goods;

    public Shop(String name) {
        this.name = name;
        this.goods = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Clothing> getGoods() {
        return goods;
    }

    public void setGoods(List<Clothing> goods) {
        this.goods = goods;
    }

    public void addGood(Clothing good) {
        this.goods.add(good);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Shop)
            return name.equals( ((Shop) obj).name );
        return false;
    }
}
