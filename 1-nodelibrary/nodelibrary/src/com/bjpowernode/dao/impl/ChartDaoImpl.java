package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.dao.ChartDao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 16:01
 */
public class ChartDaoImpl implements ChartDao {
    /**
     * 统计各个类别图书的数量
     * @return
     */
    @Override
    public Map<String, Integer> bookTypeCount() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> list= (List<Book>)ois.readObject();
            //使用stream流统计
            Map<String, List<Book>> collect = list.stream().collect(Collectors.groupingBy(Book::getType));
            //处理stream返回的结果
            HashMap<String,Integer> map = new HashMap<>();
            Iterator<Map.Entry<String, List<Book>>> iterator = collect.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, List<Book>> next = iterator.next();
                map.put(next.getKey(),next.getValue()== null ? 0 : next.getValue().size() );
            }
            return map;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new HashMap<>();
    }
}
