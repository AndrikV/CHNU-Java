package lab2SerializationInterface;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

abstract class JacksonDeserializer<T> implements Deserializer<T> {
    abstract public ObjectMapper mapperValue();
    final ObjectMapper mapper = mapperValue();

    @Override
    public T entityFromFile(Class<T> tClass, String fileName) {
        try {
            return mapper.readValue(new File(fileName), tClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> listFromFile(TypeReference<List<T>> listTypeReference, String fileName) {
        try {
            return mapper.readValue(new File(fileName), listTypeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
