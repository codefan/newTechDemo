package com.centit.kubernetes.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.kubernetes.client.custom.Quantity;

public class BeanUtils {

    @Test
    public static <T> Map<String, Object> convertToMap(Object obj) throws IllegalArgumentException, IllegalAccessException {
        
        Class<? extends Object> clazz = obj.getClass();
        Map<String, Object> params = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if(field.getType().isAssignableFrom(Quantity.class)){
                Quantity quantity = (Quantity)field.get(obj);
                params.put(field.getName(), quantity.getNumber().toString());
                continue;
            }
            params.put(field.getName(), field.get(obj));
        }
        return params;
    }
}
