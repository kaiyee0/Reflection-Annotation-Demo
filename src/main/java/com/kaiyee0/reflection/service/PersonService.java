package com.kaiyee0.reflection.service;

import com.kaiyee0.reflection.annotation.JsonObject;
import com.kaiyee0.reflection.annotation.PositiveNumber;
import com.kaiyee0.reflection.exception.PersonException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @SneakyThrows
    public String getValidatedPersonAsJson(Object object) {
        if (Objects.isNull(object)) {
            throw new PersonException("object can not be null");
        }
        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(JsonObject.class)) {
            throw new PersonException(String.format("%s is not annotated with @JsonObject", clazz.getSimpleName()));
        }
        Map<String, Object> jsonObjectMaps = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(PositiveNumber.class)) {
                String numStr = field.get(object).toString();
                if (Integer.parseInt(numStr) <= 0) {
                    throw new PersonException(String.format("Field %s value %s is not positive", field.getName(), numStr));
                }
            }

            jsonObjectMaps.put(field.getName(), field.get(object));
        }

        String jsonString = jsonObjectMaps.entrySet().stream()
                .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
                .collect(Collectors.joining(","));
        return "{" + jsonString + "}";
    }
}
