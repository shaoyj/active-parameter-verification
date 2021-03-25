package com.mylomen.params;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: Shaoyongjun
 * @date: 2020/9/3
 * @time: 8:22 PM
 * @copyright by  上海鱼泡泡信息技术有限公司
 */
public class XxqMapParamsUtils {

    /**
     * class-><method,List<XxqParams>>
     */
    private static final Map<String, Map<String, List<XxqParams>>> xxqParamsMapCheckCacheMap = new ConcurrentHashMap<>(128);

    /**
     * 备注: XxqParamsUtils 驱动
     */
    private static int callerMethodOrder = 3;
    /**
     * 备注: XxqParamsUtils 驱动
     */
    private static int expectStackTraceLength = 4;

    /**
     * 获取clazz成员变量中 拥有@Column注解的 Field 列表
     *
     * @param clazzName
     * @return
     */
    private static Map<String, List<XxqParams>> getAnnotationFieldList(String clazzName) {
        Map<String, List<XxqParams>> myCacheMap = xxqParamsMapCheckCacheMap.get(clazzName);
        if (myCacheMap != null) {
            return myCacheMap;
        }


        Map<String, List<XxqParams>> cacheMap = new ConcurrentHashMap<>(16);

        List<Class<?>> list = new ArrayList<>();

        Class<?> clazz = null;
        try {
            clazz = Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        list.add(clazz);

        Class<?>[] interfaces = clazz.getInterfaces();

        if (DefaultUtils.isNotEmpty(interfaces)) {

            List<Class<?>> interfaceList = Arrays.stream(interfaces)
                    .filter(objClazz -> objClazz != Object.class)
                    .collect(Collectors.toList());

            if (DefaultUtils.isNotEmpty(interfaceList)) {
                list.addAll(interfaceList);
            }
        }


        for (Class<?> clazzItem : list) {

            for (Method method : clazzItem.getMethods()) {

                //当前只支持 单个 map 参数的方法 && 单个map 参数+其他对象(但是第一个参数必须是 map)
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length >= 1 && parameterTypes[0] == Map.class) {

                    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                    if (parameterAnnotations.length == 0) {
                        continue;
                    }

                    List<XxqParams> parameterNameList = new ArrayList<>();

                    for (Annotation[] parameterAnnotation : parameterAnnotations) {

                        for (Annotation annotation : parameterAnnotation) {

                            if (annotation instanceof XxqParams.MapList) {

                                XxqParams.MapList paramList = (XxqParams.MapList) annotation;

                                List<XxqParams> collect = Arrays.stream(paramList.value())
                                        .filter(data -> DefaultUtils.isNotEmpty(data.key()))
                                        .collect(Collectors.toList());

                                if (DefaultUtils.isNotEmpty(collect)) {
                                    parameterNameList.addAll(collect);
                                }
                            }
                        }
                    }


                    if (DefaultUtils.isNotEmpty(parameterNameList) && !cacheMap.containsKey(method.getName())) {
                        cacheMap.put(method.getName(), parameterNameList);
                    }
                }
            }

        }


        xxqParamsMapCheckCacheMap.put(clazzName, cacheMap);

        return cacheMap;
    }


    /**
     * 不支持 异步
     *
     * @param map
     * @return
     */
    static String activeVerify(Map map) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length < expectStackTraceLength) {
            return null;
        }

        //获取调用者的类名称
        String clazzName = stackTrace[callerMethodOrder].getClassName();

        Map<String, List<XxqParams>> annotationFieldMap = getAnnotationFieldList(clazzName);
        if (DefaultUtils.isEmpty(annotationFieldMap)) {
            return null;
        }

        //获取调用者的方法
        String method = stackTrace[callerMethodOrder].getMethodName();

        List<XxqParams> xxqMapParamsList = annotationFieldMap.get(method);
        if (DefaultUtils.isEmpty(xxqMapParamsList)) {
            return null;
        }


        return xxqMapParamsList.stream().map(xxqMapParam -> {

            String key = xxqMapParam.key();
            if (DefaultUtils.isEmpty(key)) {
                return null;
            }

            Object value = map.get(key);


            return XxqParamsUtils.getErrMsg(key, value, xxqMapParam);

        }).filter(DefaultUtils::isNotEmpty).findFirst().orElse(null);
    }

    /**
     * 不支持 异步
     *
     * @param map
     * @param group
     * @return
     */
    static String activeVerify(Map map, XxqParamsGroup group) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < expectStackTraceLength) {
            return null;
        }

        //获取调用者的类名称
        String clazzName = stackTrace[callerMethodOrder].getClassName();

        Map<String, List<XxqParams>> annotationFieldMap = getAnnotationFieldList(clazzName);
        if (DefaultUtils.isEmpty(annotationFieldMap)) {
            return null;
        }

        //获取调用者的方法
        String method = stackTrace[callerMethodOrder].getMethodName();

        List<XxqParams> xxqMapParamsList = annotationFieldMap.get(method);
        if (DefaultUtils.isEmpty(xxqMapParamsList)) {
            return null;
        }

        return xxqMapParamsList.stream().map(xxqMapParam -> {

            String key = xxqMapParam.key();
            if (DefaultUtils.isEmpty(key)) {
                return null;
            }


            //如果 groups 存在，则不包含的 group 不进行验证
            List<XxqParamsGroup> groupList = Arrays.asList(xxqMapParam.groups());
            if (DefaultUtils.isNotEmpty(groupList)) {
                if (!groupList.contains(group)) {
                    return null;
                }
            }

            Object value = map.get(key);

            return XxqParamsUtils.getErrMsg(key, value, xxqMapParam);

        }).filter(DefaultUtils::isNotEmpty).findFirst().orElse(null);
    }
}

