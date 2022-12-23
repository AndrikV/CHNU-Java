package lab1ClothesShop;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Class that described different clothes of shop
 */
public class Clothing {
    public enum FOR_WHOM {
        MALE, FEMALE, BOY, GIRL
    }

    static private int objectsCount = 0;

    protected String name;
    protected String type;
    protected FOR_WHOM forWhom;
    protected Manufacturer manufacturer;
    protected Timestamp manufactureDate;
    protected int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturerInfo(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public FOR_WHOM getForWhom() {
        return forWhom;
    }

    public void setForWhom(FOR_WHOM forWhom) {
        this.forWhom = forWhom;
    }

    public Timestamp getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Timestamp manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public Clothing() {
        forWhom = FOR_WHOM.MALE;
        manufacturer = new Manufacturer(null, null);
        manufactureDate = new Timestamp(System.currentTimeMillis());   // current date
        price = 0;
    }

    private Clothing(ClothingBuilder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.forWhom = builder.for_whom;
        this.manufacturer = builder.manufacturer;
        this.manufactureDate = builder.manufactureDate;   // current date
        this.price = builder.price;
    }

    public static class ClothingBuilder{
        private String name;
        private String type;
        private FOR_WHOM for_whom;
        private Manufacturer manufacturer;
        private Timestamp manufactureDate;
        private int price;

        public ClothingBuilder(String name, String type, FOR_WHOM for_whom) {
            this.name = name;
            this.type = type;
            this.for_whom = for_whom;
        }

        public ClothingBuilder setManufacturerInfo(Manufacturer manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public ClothingBuilder setManufactureDate(Timestamp manufactureDate) {
            this.manufactureDate = manufactureDate;
            return this;
        }

        public ClothingBuilder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Clothing build() {
            return new Clothing(this);
        }
    }

    @Override
    public String toString() {
        String string = manufacturer.toString();
        String manufacturerStr = string.substring(0, string.length() - 1);
        manufacturerStr = manufacturerStr.replaceAll("\n", "\n    ");
        return "Name: " + name + "\n"
            + "Type: " + type + "\n"
            + "For whom: " + forWhom + "\n"
            + "Manufacturer:\n    " + manufacturerStr + "\n"
            + "Manufacture date: " + manufactureDate + "\n"
            + "Price: " + price + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothing clothing = (Clothing) o;
        return name.equals(clothing.name) && type.equals(clothing.type)
                && forWhom == clothing.forWhom && manufacturer.equals(clothing.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, forWhom, manufacturer);
    }

    public Clothing(LinkedHashMap<String, Object> clothing) {
        try {
            name = (String) clothing.get("name");
            type = (String) clothing.get("type");
            forWhom = (FOR_WHOM) clothing.get("forWhom");
            manufacturer = (Manufacturer) clothing.get("manufacturer");
            manufactureDate = (Timestamp) clothing.get("manufactureDate");
            price = (int) clothing.get("price");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
