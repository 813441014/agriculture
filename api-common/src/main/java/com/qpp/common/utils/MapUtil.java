package com.qpp.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @ClassName MapUtil
 * @Description TODO Map 工具类
 * @Author qipengpai
 * @Date 2018/11/9 14:56
 * @Version 1.0.1
 */
@Slf4j
public class MapUtil {

    private MapUtil(){}

    public static Map<String, Object> convertDataMap(HttpServletRequest request) {
        Map<String, String[]> properties = request.getParameterMap();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Iterator<?> entries = properties.entrySet().iterator();
        Map.Entry<?, ?> entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry<?, ?>) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            }
            else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++)
                {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            }
            else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    /**
     * @Author qipengpai
     * @Description //TODO map 是否不为空 并且存在键 并且该键对应值不为空
     * @Date 2018/11/9 15:03
     * @Param [objectMap, key] 
     * @return boolean
     * @throws 
     **/
    public static boolean containsKeyAndNotEmpty(Map<String, Object> objectMap,String key){
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
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass){
        if (map == null)
            return null;

        Object obj = null;
        try {
            obj = beanClass.newInstance();
            org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static Map<Object, Object> objectToMap(Object obj) {
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
    public static Object mapToObjectReflect(Map<String, String> map, Class<?> beanClass){
        if (map == null)
            return null;

        Object obj = null;
        try {
            obj = beanClass.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                    continue;
                }

                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Map<String, String> objectToMapReflect(Object obj) {
        if(obj == null){
            return null;
        }

        Map<String, String> map = new HashMap<>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj)+"");
            } catch (IllegalAccessException e) {
                log.error("[MapUtil]{objectToMapReflect} -> error !",e);
                e.printStackTrace();
            }
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
    public static Object mapToObjectIntrospector(Map<String, Object> map, Class<?> beanClass){
        if (map == null)
            return null;

        Object obj = null;
        try {
            obj = beanClass.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            //返回bean的所有属性的描述符
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    setter.invoke(obj, map.get(property.getName()));
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return obj;
    }

    public static Map<String, Object> objectToMapIntrospector(Object obj){
        if(obj == null)
            return null;

        Map<String, Object> map = new HashMap<>();

        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(obj.getClass());

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

        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
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
