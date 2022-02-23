package com.bjpowernode.service;

import com.bjpowernode.bean.Book;

import java.util.List;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 14:30
 */
public interface BookService {
    List<Book> select(Book book);
    void add(Book book);
    void delete(int id);
    void update(Book book);
}
