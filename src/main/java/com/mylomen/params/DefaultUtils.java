package com.mylomen.params;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author: Shaoyongjun
 * @date: 2020/12/23
 * @time: 8:34 下午
 * @copyright
 */
class DefaultUtils {


    static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }


    static boolean isNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }


    static long toLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException var4) {
                return defaultValue;
            }
        }
    }

    static long toLong(String str) {
        return toLong(str, 0L);
    }


    static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        } else if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        } else {
            return object instanceof Map ? ((Map) object).isEmpty() : false;
        }
    }

    static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }


     static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
