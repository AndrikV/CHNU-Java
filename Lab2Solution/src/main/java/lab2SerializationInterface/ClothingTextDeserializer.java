package lab2SerializationInterface;

import com.fasterxml.jackson.core.type.TypeReference;
import lab1ClothesShop.Clothing;
import lab1ClothesShop.Manufacturer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClothingTextDeserializer {
    private Clothing entityFromReader(BufferedReader reader) {
        try {
            String line;
            String toCheck;
            int l;

            int id;
            String name;
            String type;
            Clothing.FOR_WHOM forWhom = null;
            String manufacturerName;
            String manufacturerContactInfo;
            LocalDate manufactureDate;
            int price;

            line = reader.readLine();
            toCheck = "Id: ";
            l = toCheck.length();
            if (line.startsWith(toCheck) && line.length() > l) {
                id = Integer.parseUnsignedInt(line.substring(l));
            }
            else {
                throw new RuntimeException("Failed to deserialize clothing from text: id not found");
            }

            line = reader.readLine();
            toCheck = "Name: ";
            l = toCheck.length();
            if (line.startsWith(toCheck) && line.length() > l) {
                name = line.substring(l);
            }
            else {
                throw new RuntimeException("Failed to deserialize clothing from text: name not found");
            }

            line = reader.readLine();
            toCheck = "Type: ";
            l = toCheck.length();
            if (line.startsWith(toCheck) && line.length() > l) {
                type = line.substring(l);
            }
            else {
                throw new RuntimeException("Failed to deserialize clothing from text: type not found");
            }

            line = reader.readLine();
            toCheck = "For whom: ";
            l = toCheck.length();
            if (line.startsWith(toCheck) && line.length() > l) {
                String str_for_whom = line.substring(l).trim();
                boolean notFound = true;
                for (Clothing.FOR_WHOM forWhomVal : Clothing.FOR_WHOM.values()) {
                    if (str_for_whom.equals(forWhomVal.toString())) {
                        forWhom = forWhomVal;
                        notFound = false;
                    }
                }
                if (notFound) {
                    throw new RuntimeException("Failed to deserialize clothing from text: "
                            + "\"for whom\" value does not match any possible values");
                }
            }
            else {
                throw new RuntimeException("Failed to deserialize clothing from text: \"for whom\" not found");
            }

            line = reader.readLine();
            toCheck = "Manufacturer:";
            l = toCheck.length();
            if (line.startsWith(toCheck) && line.length() > l) {
                throw new RuntimeException("Failed to deserialize clothing from text: manufacturer data found");
            }

            line = reader.readLine();
            toCheck = "    Name: ";
            l = toCheck.length();
            if (line.startsWith(toCheck) && line.length() > l) {
                manufacturerName = line.substring(l);
            }
            else {
                throw new RuntimeException("Failed to deserialize clothing from text: manufacturer name not found");
            }

            line = reader.readLine();
            toCheck = "    Contact info: ";
            l = toCheck.length();
            if (line.startsWith(toCheck) && line.length() > l) {
                manufacturerContactInfo = line.substring(l);
            }
            else {
                throw new RuntimeException("Failed to deserialize clothing from text: manufacturer contact info not found");
            }

            line = reader.readLine();
            toCheck = "Manufacture date: ";
            l = toCheck.length();
            if (line.startsWith(toCheck) && line.length() > l) {
                String strManufactureDate = line.substring(l);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                try {
                    manufactureDate = LocalDate.parse(strManufactureDate, formatter);
                }
                catch (Exception e) {
                    throw new RuntimeException("Failed to parse manufacture date\nDetails: " + e.getMessage());
                }
            }
            else {
                throw new RuntimeException("Failed to deserialize clothing from text: manufacture date not found");
            }

            line = reader.readLine();
            toCheck = "Price: ";
            l = toCheck.length();
            if (line.startsWith(toCheck) && line.length() > l) {
                price = Integer.parseInt(line.substring(l));
            }
            else {
                throw new RuntimeException("Failed to deserialize clothing from text: price not found");
            }

            return new Clothing.ClothingBuilder(id, name, type, forWhom)
                    .setManufactureDate(manufactureDate)
                    .setManufacturerInfo(new Manufacturer(manufacturerName, manufacturerContactInfo))
                    .setPrice(price)
                    .build();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Transform text to clothing object.
     * Text format:
     * Id: int
     * Name: string
     * Type: string
     * For whom: ForWhom.toString()
     * Manufacturer:
     *     Name: string
     *     Contact info: string
     * Manufacture date: YYYY-MM-DD
     * Price: int
     *
     * @param fileName: file with text that need to be deserialized
     * @return List<Clothing>
     */
    public Clothing entityFromFile(String fileName) throws RuntimeException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            Clothing clothing = entityFromReader(reader);
            reader.close();

            return clothing;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Transform text to list of clothes.
     * Text format:
     * Clothing.toString()
     * ==========
     * Clothing.toString()
     * ==========
     * Clothing.toString()
     *
     * @param fileName: file with text that need to be deserialized
     * @return List<Clothing>
     */
    public List<Clothing> listFromFile(String fileName) throws RuntimeException  {
        List<Clothing> resultList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            do {
                resultList.add(entityFromReader(reader));
                line = reader.readLine();
                if (!(line.equals("=========="))) {
                    throw new RuntimeException("Failed to deserialize clothes from text");
                }
            } while(true);
        }
        catch (NullPointerException e) {
            return resultList;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
