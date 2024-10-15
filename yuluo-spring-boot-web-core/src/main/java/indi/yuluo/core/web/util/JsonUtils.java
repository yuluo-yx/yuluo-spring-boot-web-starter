package indi.yuluo.core.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public final class JsonUtils {

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    static {
        OBJECT_MAPPER
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .registerModule(new JavaTimeModule());
    }

    public static String toJson(Object source) {
        if (source == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        if (!StringUtils.hasText(jsonStr)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T fromJson(String jsonStr, TypeReference<T> type) {
        if (!StringUtils.hasText(jsonStr)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr, type);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static JsonNode fromJson(String jsonStr) {
        if (!StringUtils.hasText(jsonStr)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readTree(jsonStr);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * check if the string is a json string
     * @param jsonStr json string
     * @return true if the string is a json string
     */


    public static boolean isJsonStr(String jsonStr) {
        if (jsonStr == null || jsonStr.trim().isEmpty()) {
            return false;
        }
        jsonStr = jsonStr.trim();
        if (!(jsonStr.startsWith("{") && jsonStr.endsWith("}"))
                && !(jsonStr.startsWith("[") && jsonStr.endsWith("]"))) {
            return false;
        }
        try {
            OBJECT_MAPPER.readTree(jsonStr);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

}
