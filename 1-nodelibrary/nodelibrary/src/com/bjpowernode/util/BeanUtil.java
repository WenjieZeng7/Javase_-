package com.bjpowernode.util;

import java.lang.reflect.Field;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 15:15
 */
public class BeanUtil {
    /**
     * 将对象origin属性值，拷贝到dest中
     * @param origin
     * @param dest
     */
    public static void populate(Object origin, Object dest){
         //使用反射解决通用性问题
        //判断两个对象是否是同一个类型
        try {
            if (origin.getClass() != dest.getClass()) {
                throw new RuntimeException("两个对象必须是同一个类型");
            }
            Class<?> clazz = origin.getClass();
            //获取origin的属性
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                //排除UID
                if("serialVersionUID".equals(f.getName())){
                    continue;
                }
                //打破封装
                f.setAccessible(true);
                //赋值
                f.set(origin, f.get(dest)); //把dest的赋值到origin
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
