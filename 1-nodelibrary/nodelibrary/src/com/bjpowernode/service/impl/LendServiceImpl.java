package com.bjpowernode.service.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.dao.BookDao;
import com.bjpowernode.dao.LendDao;
import com.bjpowernode.dao.UserDao;
import com.bjpowernode.dao.impl.BookDaoImpl;
import com.bjpowernode.dao.impl.LendDaoImpl;
import com.bjpowernode.dao.impl.UserDaoImpl;
import com.bjpowernode.service.LendService;

import java.lang.invoke.LambdaConversionException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 16:53
 */
public class LendServiceImpl implements LendService {
    private LendDao lendDao = new LendDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    private UserDao userDao = new UserDaoImpl();

    @Override
    public List<Lend> select(Lend lend) {
        return lendDao.select(lend);
    }

    @Override
    public void add(int bookId, int userId) {
        //查询图书数据
        Book paramBook = new Book();
        paramBook.setId(bookId);
        List<Book> bookList = bookDao.select(paramBook);

        //根据编号查询用户数据
        User paramUser = new User();
        paramUser.setId(userId);
        List<User> userList = userDao.select(paramUser);

        //创建lend对象
        Lend lend = new Lend();
        lend.setId(UUID.randomUUID().toString());

        //给图书赋值
        Book book = bookList.get(0);
        book.setStatus(Constant.STATUS_LEND);
        lend.setBook(book);

        //给用户赋值
        User user = userList.get(0);
        user.setLend(true);
        lend.setUser(user);

        //设置lend的其他属性
        lend.setStatus(Constant.LEND_LEND);
        LocalDate begin = LocalDate.now();
        lend.setLendDate(begin);
        lend.setReturnDate(begin.plusDays(30));

        //修改数据库的图书和用户数据
        userDao.update(user);
        bookDao.update(book);
        //添加出借记录
        lendDao.add(lend);
    }

    @Override
    public List<Lend> returnBook(Lend lend) {
        //获取还书该lend对象的用户和图书对象
        Book book = lend.getBook();
        User user = lend.getUser();

        //修改用户和图书的状态
        book.setStatus(Constant.STATUS_STORAGE);
        user.setLend(false);

        //更新用户和图书的数据
        userDao.update(user);
        bookDao.update(book);

        //删除lend数据
        lendDao.delete(lend.getId());

        //删除之后返回最新的lend数据库内容
        return lendDao.select(null);
    }
}
