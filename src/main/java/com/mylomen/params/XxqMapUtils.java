package com.mylomen.params;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author: Shaoyongjun
 * @date: 2020/8/27
 * @time: 4:59 PM
 * @copyright
 */
public class XxqMapUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    private final static String NULL_STR = "null";

    public static boolean keyNotNull(Map req, String key) {
        if (DefaultUtils.isEmpty(req)) {
            return false;
        }


        if (!req.containsKey(key)) {
            return false;
        }

        Object obj = req.get(key);

        return obj != null && !obj.toString().equalsIgnoreCase(NULL_STR);
    }

    public static boolean keyIsNull(Map req, String key) {
        if (DefaultUtils.isEmpty(req)) {
            return true;
        }

        if (!req.containsKey(key)) {
            return true;
        }

        Object obj = req.get(key);

        return obj == null || obj.toString().equalsIgnoreCase(NULL_STR);
    }

    public static boolean keyIsEmpty(Map req, String key) {
        if (DefaultUtils.isEmpty(req)) {
            return true;
        }

        if (!req.containsKey(key)) {
            return true;
        }

        Object obj = req.get(key);

        return obj == null || DefaultUtils.isEmpty(obj.toString());
    }

    public static boolean keyIsNotEmpty(Map req, String key) {
        if (DefaultUtils.isEmpty(req)) {
            return false;
        }

        if (!req.containsKey(key)) {
            return false;
        }

        Object obj = req.get(key);

        return obj != null && DefaultUtils.isNotEmpty(obj.toString());
    }


    public static Long getLongValue(Map map, String key) {
        if (DefaultUtils.isNotEmpty(map)) {

            if (map.containsKey(key)) {

                Object obj = map.get(key);

                if (obj != null) {

                    if (obj instanceof Number) {

                        return ((Number) obj).longValue();

                    } else if (obj instanceof String) {
                        String str = obj.toString();
                        if (DefaultUtils.isEmpty(str)) {
                            return null;
                        }

                        if (DefaultUtils.isNumeric(str)) {
                            return new Long(str);
                        }

                        return null;
                    }
                }
            }
        }

        return null;
    }


    public static Long getLongValue(Map map, String key, Long defaultValue) {
        Long longValue = getLongValue(map, key);
        return longValue == null ? defaultValue : longValue;
    }


    public static Integer getIntegerValue(Map map, String key) {
        if (DefaultUtils.isNotEmpty(map)) {

            if (map.containsKey(key)) {

                Object obj = map.get(key);

                if (obj != null) {

                    if (obj instanceof Number) {

                        return ((Number) obj).intValue();

                    } else if (obj instanceof String) {

                        return new Integer(obj.toString());
                    }
                }
            }
        }

        return null;
    }


    public static Float getFloatValue(Map map, String key) {
        if (DefaultUtils.isNotEmpty(map)) {

            if (map.containsKey(key)) {

                Object obj = map.get(key);

                if (obj != null) {

                    if (obj instanceof Number) {

                        return ((Number) obj).floatValue();

                    } else if (obj instanceof String) {

                        return new Float(obj.toString());
                    }
                }
            }
        }

        return null;
    }

    public static Float getFloatValue(Map map, String key, Float defaultValue) {
        Float floatValue = getFloatValue(map, key);
        return floatValue == null ? defaultValue : floatValue;
    }


    public static Double getDoubleValue(Map map, String key) {
        if (DefaultUtils.isNotEmpty(map)) {

            if (map.containsKey(key)) {

                Object obj = map.get(key);

                if (obj != null) {

                    if (obj instanceof Number) {

                        return ((Number) obj).doubleValue();

                    } else if (obj instanceof String) {

                        return new Double(obj.toString());
                    }
                }
            }
        }

        return null;
    }

    public static Double getDoubleValue(Map map, String key, Float defaultValue) {
        Double doubleValue = getDoubleValue(map, key);
        return doubleValue == null ? defaultValue : doubleValue;
    }

    public static Integer getIntegerValue(Map map, String key, Integer defaultValue) {
        Integer integerValue = getIntegerValue(map, key);
        return integerValue == null ? defaultValue : integerValue;
    }


    public static String getStringValue(Map map, String key) {
        if (DefaultUtils.isNotEmpty(map)) {

            if (map.containsKey(key)) {

                Object obj = map.get(key);

                if (obj != null) {
                    return obj.toString();
                }
            }
        }

        return null;
    }

    public static String getStringValue(Map map, String key, String defaultValue) {
        String stringValue = getStringValue(map, key);
        return stringValue == null ? defaultValue : stringValue;
    }


    public static Boolean getBooleanValue(Map map, String key) {
        if (DefaultUtils.isNotEmpty(map)) {

            if (map.containsKey(key)) {

                Object obj = map.get(key);

                if (obj != null) {

                    if (obj instanceof Boolean) {

                        return (Boolean) obj;

                    } else if (obj instanceof String) {
                        String objStr = (String) obj;
                        if ("true".equalsIgnoreCase(objStr) || "false".equalsIgnoreCase(objStr)) {
                            return Boolean.valueOf(obj.toString());
                        }
                    }
                }
            }
        }

        return null;
    }

    public static Boolean getBooleanValue(Map map, String key, Boolean defaultValue) {
        Boolean booleanValue = getBooleanValue(map, key);
        return booleanValue == null ? defaultValue : booleanValue;
    }

    public static BigDecimal getBigDecimalValue(Map map, String key) {
        if (DefaultUtils.isNotEmpty(map)) {

            if (map.containsKey(key)) {

                Object obj = map.get(key);

                if (obj != null) {

                    if (obj instanceof BigDecimal) {

                        return (BigDecimal) obj;

                    } else if (obj instanceof String) {

                        return new BigDecimal(obj.toString());

                    }
                }
            }
        }

        return null;
    }

    public static BigDecimal getBigDecimalValue(Map map, String key, BigDecimal defaultValue) {
        BigDecimal value = getBigDecimalValue(map, key);
        return value == null ? defaultValue : value;
    }

    public static List getList(Map map, String key) {
        if (DefaultUtils.isNotEmpty(map)) {

            if (map.containsKey(key)) {

                Object obj = map.get(key);

                if (obj != null) {

                    if (obj instanceof List) {
                        return (List) obj;
                    } else if (obj instanceof String) {

                        List dataList = null;
                        try {
                            dataList = mapper.readValue((String) obj, List.class);
                        } catch (Exception e) {
                        }

                        if (dataList != null) {
                            return dataList;
                        }
                    }
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"name\":\"mkyong\", \"age\":\"37\"}";

        try {

            // convert JSON string to Map
            Map<String, String> map = mapper.readValue(json, Map.class);

            // it works
            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});

            System.out.println(map);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List getList(Map map, String key, List defaultList) {
        List dataList = getList(map, key);
        return dataList == null ? defaultList : dataList;
    }

    public static boolean keyIsList(Map map, String key) {
        List data = getList(map, key);
        return data != null;
    }

    public static Map getMap(Map map, String key) {
        if (DefaultUtils.isNotEmpty(map)) {

            if (map.containsKey(key)) {

                Object obj = map.get(key);

                if (obj != null) {

                    if (obj instanceof Map) {
                        return (Map) obj;
                    } else if (obj instanceof String) {

                        Map dataMap = null;
                        try {
                            dataMap = mapper.readValue((String) obj, Map.class);
                        } catch (Exception e) {
                        }

                        if (dataMap != null) {
                            return dataMap;
                        }
                    }
                }
            }
        }

        return null;
    }

    public static Map getMap(Map map, String key, Map defaultMap) {
        Map dataMap = getMap(map, key);
        return dataMap == null ? defaultMap : dataMap;
    }

    public static boolean keyIsMap(Map map, String key) {
        Map dataMap = getMap(map, key);
        return dataMap != null;
    }


}
