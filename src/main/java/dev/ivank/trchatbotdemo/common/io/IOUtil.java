package dev.ivank.trchatbotdemo.common.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import dev.ivank.trchatbotdemo.common.MessageLogger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class IOUtil {
    public static <T> List<T> readResourcesIntoCollection(ObjectMapper mapper, String path, Class<T> tClass) {
        URL resource = IOUtil.class.getClassLoader().getResource(path);
        if (resource != null) {
            File file = new File(resource.getFile());
            try {
                TypeFactory typeFactory = mapper.getTypeFactory();
                return mapper.readValue(file, typeFactory.constructCollectionType(List.class, tClass));
            } catch (IOException e) {
                MessageLogger.error(e.getMessage(), e);
            }
        } else {
            MessageLogger.error("Can't get the resource file from FS: %s".formatted(path));
        }
        return new ArrayList<>();
    }
}
