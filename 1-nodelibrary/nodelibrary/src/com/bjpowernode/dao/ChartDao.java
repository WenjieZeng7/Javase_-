package com.bjpowernode.dao;

import java.util.Map;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 16:01
 */
public interface ChartDao {
    Map<String,Integer> bookTypeCount(); //统计不同类别图书的数量
}
