package lab2SerializationInterface;


import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public interface Deserializer<T> {
    T entityFromFile(Class<T> tClass, String fileName);
    List<T> listFromFile(TypeReference<List<T>> listTypeReference, String fileName);
}
