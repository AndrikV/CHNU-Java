package lab2SerializationInterface;


import java.util.List;

public interface Serializer<T> {
    boolean toFile(List<T> entities, String fileName);
    boolean toFile(T entity, String fileName);
}
