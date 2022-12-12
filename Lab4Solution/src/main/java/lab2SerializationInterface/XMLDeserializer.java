package lab2SerializationInterface;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class XMLDeserializer<T> extends JacksonDeserializer<T> {
    @Override
    public ObjectMapper mapperValue() {
        return XmlMapper.builder().addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();
    }
}