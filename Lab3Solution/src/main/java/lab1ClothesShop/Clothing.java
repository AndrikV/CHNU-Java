package lab1ClothesShop;

import java.time.LocalDate;
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

    private int id;
    private String name;
    private String type;
    private FOR_WHOM forWhom;
    private Manufacturer manufacturer;
    private LocalDate manufactureDate;
    private int price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public Clothing() {
        id = 0;
        forWhom = FOR_WHOM.MALE;
        manufacturer = new Manufacturer(null, null);
        manufactureDate = LocalDate.now();   // current date
        price = 0;
    }

    private Clothing(ClothingBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type;
        this.forWhom = builder.for_whom;
        this.manufacturer = builder.manufacturer;
        this.manufactureDate = builder.manufactureDate;   // current date
        this.price = builder.price;
    }

    public static class ClothingBuilder{
        private int id;
        private String name;
        private String type;
        private FOR_WHOM for_whom;
        private Manufacturer manufacturer;
        private LocalDate manufactureDate;
        private int price;

        public ClothingBuilder(int id, String name, String type, FOR_WHOM for_whom) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.for_whom = for_whom;
        }

        public ClothingBuilder setManufacturerInfo(Manufacturer manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public ClothingBuilder setManufactureDate(LocalDate manufactureDate) {
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
        return "Id: " + id + "\n"
            + "Name: " + name + "\n"
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
            id = (int) clothing.get("id");
            name = (String) clothing.get("name");
            type = (String) clothing.get("type");
            forWhom = (FOR_WHOM) clothing.get("forWhom");
            manufacturer = (Manufacturer) clothing.get("manufacturer");
            manufactureDate = (LocalDate) clothing.get("manufactureDate");
            price = (int) clothing.get("price");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
