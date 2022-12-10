package lab2SerializationInterface;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

abstract class JacksonSerializer<T> implements Serializer<T> {
    abstract public ObjectMapper mapperValue();
    final ObjectMapper mapper = mapperValue();

    boolean _toFile(Object object, String fileName) {
        try {
            mapper.writeValue(new File(fileName), object);
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean toFile(T entities, String fileName) {
        return _toFile(entities, fileName);
    }

    @Override
    public boolean toFile(List<T> entities, String fileName) {
        return _toFile(entities, fileName);
    }
}
