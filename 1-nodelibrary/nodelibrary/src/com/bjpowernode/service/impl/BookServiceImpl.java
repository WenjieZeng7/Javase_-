package com.bjpowernode.service.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.dao.BookDao;
import com.bjpowernode.dao.impl.BookDaoImpl;
import com.bjpowernode.service.BookService;

import java.util.List;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 14:31
 */
public class BookServiceImpl implements BookService{
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public List<Book> select(Book book) {
        return bookDao.select(book);
    }

    @Override
    public void add(Book book) {
        bookDao.add(book);
    }

    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }
}
