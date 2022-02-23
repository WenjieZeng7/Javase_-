package com.bjpowernode.service;

import com.bjpowernode.bean.Lend;

import java.util.List;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 16:52
 */
public interface LendService {
    List<Lend> select(Lend lend); //查询借阅记录
    void add(int bookId, int userId); //添加借阅记录
    List<Lend> returnBook(Lend lend); //还书，在Dao层是删除。这里的返回值用于删除之后的界面展示。
}
