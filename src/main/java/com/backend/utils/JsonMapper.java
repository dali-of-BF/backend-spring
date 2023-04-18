package com.backend.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 对jackson 的objectMapper进一步封装
 *
 * @author chenjp
 */
public class JsonMapper {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        OBJECT_MAPPER.findAndRegisterModules();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }


    public static <T> T covertValue(Object src, Class<T> valueType) {
        return null != src ? OBJECT_MAPPER.convertValue(src, valueType) : null;
    }

    public static <T> List<T> covertList(Collection<?> src, Class<T> valueType) {
        return CollectionUtils.isNotEmpty(src) ?
                src.stream().map(item -> covertValue(item, valueType)).filter(Objects::nonNull).collect(Collectors.toList()) : Collections.emptyList();
    }

    public static <T> T readValue(byte[] src, Class<T> valueType) throws IOException {
        return null != src ? OBJECT_MAPPER.readValue(src, valueType) : null;
    }

    public static <T> T readValue(InputStream src, Class<T> valueType) throws IOException {
        return null != src ? OBJECT_MAPPER.readValue(src, valueType) : null;
    }

    public static <T> T readValue(String src, Class<T> valueType) throws JsonProcessingException {
        return StringUtils.isNotBlank(src) ? OBJECT_MAPPER.readValue(src, valueType) : null;
    }

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return null != value ? OBJECT_MAPPER.writeValueAsString(value) : null;
    }

    public static byte[] writeValueAsBytes(Object value) throws JsonProcessingException {
        return null != value ? OBJECT_MAPPER.writeValueAsBytes(value) : null;
    }

    public static ObjectReader readerForUpdating(Object valueToUpdate) {
        return null != valueToUpdate ? OBJECT_MAPPER.readerForUpdating(valueToUpdate) : null;
    }

}
