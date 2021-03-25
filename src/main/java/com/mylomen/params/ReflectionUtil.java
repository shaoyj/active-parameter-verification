package com.mylomen.params;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public ReflectionUtil() {
    }

    public static Object getFieldValue(Object entity, Field field) {
        if (field != null) {
            field.setAccessible(true);
        }

        Object value = null;

        try {
            value = field.get(entity);
        } catch (IllegalAccessException var4) {
            var4.printStackTrace();
        }

        return value;
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        Field field = getField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);

            try {
                result = field.get(obj);
            } catch (Exception var5) {
                return "";
            }
        }

        return result;
    }

    public static Field getField(Object obj, String fieldName) {
        Field field = null;
        Class clazz = obj.getClass();

        while(clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException var5) {
                clazz = clazz.getSuperclass();
            }
        }

        return field;
    }

    public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
        Field field = getField(obj, fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            } catch (Exception var5) {
            }
        }

    }
}
