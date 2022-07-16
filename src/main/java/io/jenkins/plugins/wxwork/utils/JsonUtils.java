package io.jenkins.plugins.wxwork.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * <p>JsonMapper</p>
 *
 * @author nekoimi 2022/07/16
 */
public class JsonUtils {
    private static final ObjectMapper instance = new ObjectMapper();
    static {
        instance.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
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
}
