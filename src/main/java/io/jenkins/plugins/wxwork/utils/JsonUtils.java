package io.jenkins.plugins.wxwork.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import java.io.IOException;

/**
 * <p>JsonUtils</p>
 *
 * @author nekoimi 2022/07/16
 */
public class JsonUtils {
    private static final ObjectMapper instance = new ObjectMapper();
    static {
        instance.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    private JsonUtils() {
    }

    public static String toJson(Object object) {
        try {
            return instance.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            return instance.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T toBean(byte[] jsonBytes, Class<T> clazz) {
        try {
            return instance.readValue(jsonBytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
