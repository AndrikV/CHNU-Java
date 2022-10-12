package lab1ClothesShop;

/**
 * Class that described clothes manufacturer
 */
public class Manufacturer {
    private String name;
    private String contactInfo;

    public Manufacturer(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n"
            + "Contact info: " + contactInfo + "\n";
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(((Manufacturer) obj).name);
    }
}
