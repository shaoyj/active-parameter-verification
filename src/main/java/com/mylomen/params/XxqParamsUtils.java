package com.mylomen.params;


import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Shaoyongjun
 * @date: 2020/9/3
 * @time: 8:22 PM
 * @copyright
 */
public class XxqParamsUtils {

    private static final Map<Class<?>, List<Field>> xxqParamsCheckCacheFieldListMap = new ConcurrentHashMap<>(128);

    /**
     * 获取clazz成员变量中 拥有@Column注解的 Field 列表
     *
     * @param clazz
     * @return
     */
    private static List<Field> getAnnotationFieldList(Class<?> clazz) {
        List<Field> cacheList = xxqParamsCheckCacheFieldListMap.get(clazz);
        if (cacheList != null) {
            return cacheList;
        }


        List<Field> list = new ArrayList<>();

        Field[] entityFields = clazz.getDeclaredFields();
        if (DefaultUtils.isNotEmpty(entityFields)) {
            Arrays.stream(entityFields).forEach(field -> {
                if (field.isAnnotationPresent(XxqParams.class)) {
                    list.add(field);
                }
            });
        }

        //父类
        if (clazz != Object.class && clazz.getSuperclass() != Object.class) {
            Field[] parentFileds = clazz.getSuperclass().getDeclaredFields();
            if (DefaultUtils.isNotEmpty(parentFileds)) {
                //遍历
                Arrays.stream(parentFileds).forEach(objFile -> {
                    if (objFile.isAnnotationPresent(XxqParams.class)) {
                        list.add(objFile);
                    }
                });
            }
        }


        //放入缓存
        xxqParamsCheckCacheFieldListMap.put(clazz, list);
        return list;
    }


    public static String activeVerify(Object obj) {
        if (obj == null) {
            return "参数为null";
        }

        //校验是否是 Map 参数
        if (obj instanceof Map) {
            return XxqMapParamsUtils.activeVerify((Map) obj);
        }

        List<Field> annotationFieldList = getAnnotationFieldList(obj.getClass());
        if (DefaultUtils.isEmpty(annotationFieldList)) {
            return null;
        }

        return annotationFieldList.stream()
                .map(field -> getErrMsg(field.getName(), ReflectionUtil.getFieldValue(obj, field), field.getAnnotation(XxqParams.class)))
                .filter(DefaultUtils::isNotEmpty)
                .findFirst()
                .orElse(null);
    }


    public static String activeVerify(Object obj, XxqParamsGroup group) {
        if (obj == null) {
            return "参数为null";
        }

        //校验是否是 Map 参数
        if (obj instanceof Map) {
            return XxqMapParamsUtils.activeVerify((Map) obj, group);
        }


        List<Field> annotationFieldList = getAnnotationFieldList(obj.getClass());
        if (DefaultUtils.isEmpty(annotationFieldList)) {
            return null;
        }

        return annotationFieldList.stream().map(field -> {
            // 执行get方法返回一个Object
            Object value = ReflectionUtil.getFieldValue(obj, field);


            //获取注解
            XxqParams annotation = field.getAnnotation(XxqParams.class);


            //如果 groups 存在，则不包含的 group 不进行验证
            List<XxqParamsGroup> groupList = Arrays.asList(annotation.groups());
            if (DefaultUtils.isNotEmpty(groupList)) {
                if (!groupList.contains(group)) {
                    return null;
                }
            }


            return getErrMsg(field.getName(), value, annotation);

        }).filter(DefaultUtils::isNotEmpty).findFirst().orElse(null);
    }


    /**
     * 获取错误信息
     *
     * @param fieldName  字段名称
     * @param value
     * @param annotation
     * @return
     */
    static String getErrMsg(String fieldName, Object value, XxqParams annotation) {
        if (DefaultUtils.isNotEmpty(annotation.noNull())) {
            if (Objects.isNull(value)) {
                return getWarnMsg(fieldName, annotation);
            }
        }


        if (DefaultUtils.isNotEmpty(annotation.noEmpty())) {
            if (DefaultUtils.isEmpty(value)) {
                return getWarnMsg(fieldName, annotation);
            }
        }


        if (DefaultUtils.isNotEmpty(annotation.noBlank())) {
            if (Objects.isNull(value)) {
                return getWarnMsg(fieldName, annotation);
            }

            if (DefaultUtils.isBlank(value.toString())) {
                return getWarnMsg(fieldName, annotation);
            }

        }


        if (DefaultUtils.isNotEmpty(annotation.noNullAndZero())) {
            if (NumUtils.isNullOrZero(value)) {
                return getWarnMsg(fieldName, annotation);
            }
        }

        if (DefaultUtils.isNotEmpty(annotation.minLength())) {
            long min = DefaultUtils.toLong(annotation.minLength());
            if (min > 0) {
                if (Objects.nonNull(value) && value.toString().length() < min) {
                    return getWarnMsg(fieldName, annotation);
                }
            }
        }

        if (DefaultUtils.isNotEmpty(annotation.maxLength())) {
            long max = DefaultUtils.toLong(annotation.maxLength());
            if (max > 0) {
                if (Objects.nonNull(value) && value.toString().length() > max) {
                    return getWarnMsg(fieldName, annotation);
                }
            }
        }

        return null;
    }

    private static String getWarnMsg(String fieldName, XxqParams annotation) {
        if (DefaultUtils.isNotEmpty(annotation.noNull())) {
            return annotation.noNull();
        } else {
            return fieldName + "字段非法";
        }
    }


}
