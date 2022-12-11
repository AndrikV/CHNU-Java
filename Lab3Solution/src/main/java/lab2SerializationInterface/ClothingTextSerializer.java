package lab2SerializationInterface;

import lab1ClothesShop.Clothing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class ClothingTextSerializer implements Serializer<Clothing> {
    @Override
    public boolean toFile(List<Clothing> entities, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (int i = 0; i < entities.size(); i++)
            {
                writer.write(entities.get(i).toString());
                if (i < entities.size() - 1) {
                    writer.write("==========\n");
                }
            }
            writer.close();
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean toFile(Clothing entity, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(entity.toString());
            writer.close();
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
