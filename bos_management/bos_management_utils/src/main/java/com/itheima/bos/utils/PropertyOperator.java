package com.itheima.bos.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 属性操作工具
 *
 * @author tengchao
 * @create 2018-01-05-16:14.
 */
public class PropertyOperator {

    public <T> T replacePropertyFromEmptyToNull(T t) {
        try {
            if (t == null) {
                throw new Exception("传入的数据值为null！");
            }
            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass(), Object.class);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method readMethod = propertyDescriptor.getReadMethod();
                Class<?> propertyType = propertyDescriptor.getPropertyType();
                Object invoke = readMethod.invoke(t);
                if(invoke instanceof String && ((String)invoke).trim().length()==0){
                    List list = new ArrayList();
                    list.add(null);
                    propertyDescriptor.getWriteMethod().invoke(t,list.toArray());
                }else if (propertyType.getClassLoader() != null && invoke !=null) {
                    if (objectIsEmpty(invoke)){
                        List list = new ArrayList();
                        list.add(null);
                        propertyDescriptor.getWriteMethod().invoke(t,list.toArray());
                    }else {
                        Object o = this.replacePropertyFromEmptyToNull(invoke);
                    }
                }else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public boolean objectIsEmpty(Object o) {
        if (o == null) {
            return true;
        }
        Class<?> clazz = o.getClass();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method readMethod = propertyDescriptor.getReadMethod();
                Class<?> propertyType = propertyDescriptor.getPropertyType();
                Object invoke = readMethod.invoke(o);
                if (invoke instanceof String && ((String)invoke).trim().length()==0) {
                    continue;
                }
                if (null == invoke) {
                    continue;
                }
                if (invoke instanceof Map && ((Map) invoke).isEmpty()) {
                    continue;
                }
                if (invoke instanceof List && ((List) invoke).isEmpty()) {
                    continue;
                }
                if (invoke instanceof Set && ((Set) invoke).isEmpty()) {
                    continue;
                }
                if (propertyType.getClassLoader() != null && objectIsEmpty(invoke)) {
                    continue;
                }
                return false;
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
    }
}
