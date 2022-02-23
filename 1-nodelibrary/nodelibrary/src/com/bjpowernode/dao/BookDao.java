package com.bjpowernode.dao;

import com.bjpowernode.bean.Book;

import java.util.List;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 14:16
 */
public interface BookDao {
    List<Book> select(Book book); //查询图书
    void add(Book book); //添加图书
    void delete(int id); //删除图书
    void update(Book book); //修改图书
}
