package com.qpp.common.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName MapUtil
 * @Description TODO Map 工具类
 * @Author qipengpai
 * @Date 2018/11/9 14:56
 * @Version 1.0.1
 */
public class MapUtil {

    /**
     * @Author qipengpai
     * @Description //TODO map 是否不为空 并且存在键 并且该键对应值不为空
     * @Date 2018/11/9 15:03
     * @Param [objectMap, key] 
     * @return boolean
     * @throws 
     **/
    public static boolean ContainsKeyAndNotEmpty(Map<String, Object> objectMap,String key){
        if (MapUtils.isNotEmpty(objectMap) && objectMap.containsKey(key) && StringUtils.isNotEmpty(objectMap.get(key)+"")) {
            return true;
        }
        return false;
    }

    /**
     * @Author qipengpai
     * @Description //TODO apache beanutils方式
     * @Date 2018/11/29 11:29
     * @Param [map, beanClass]
     * @return java.lang.Object
     * @throws
     **/
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }

    public static Map<?, ?> objectToMap(Object obj) {
        if (obj == null)
            return null;

        return new org.apache.commons.beanutils.BeanMap(obj);
    }

    /**
     * @Author qipengpai
     * @Description //TODO 反射方式
     * @Date 2018/11/29 11:31
     * @Param [map, beanClass]
     * @return java.lang.Object
     * @throws
     **/
    public static Object mapToObjectReflect(Map<String, String> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    public static Map<String, String> objectToMapReflect(Object obj) throws Exception {
        if(obj == null){
            return null;
        }

        Map<String, String> map = new HashMap<String, String>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj)+"");
        }

        return map;
    }

    /**
     * @Author qipengpai
     * @Description //TODO 内省机制
     * @Date 2018/11/30 11:44
     * @Param [map, beanClass]
     * @return java.lang.Object
     * @throws
     **/
    public static Object mapToObjectIntrospector(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        //返回bean的所有属性的描述符
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
    }

    public static Map<String, Object> objectToMapIntrospector(Object obj) throws Exception {
        if(obj == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter!=null ? getter.invoke(obj) : null;
            map.put(key, value);
        }

        return map;
    }

    /**
     * @Author qipengpai
     * @Description //TODO map 排序转字符串
     * @Date 2018/12/13 10:44
     * @Param [request]
     * @return java.lang.String
     * @throws
     **/
    public static String sort(Map<String, String> request) {
        String[] keys = request.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder raw = new StringBuilder();
        for (String key : keys) {
            raw.append(key).append("=").append(request.get(key)).append("&");
        }
        return raw.deleteCharAt(raw.length() - 1).toString();
    }
}
