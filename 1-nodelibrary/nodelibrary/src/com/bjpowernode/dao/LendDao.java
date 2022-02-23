package com.bjpowernode.dao;

import com.bjpowernode.bean.Lend;

import java.util.List;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 16:46
 */
public interface LendDao {
    List<Lend> select(Lend lend); //查询借阅记录
    void add(Lend lend); //添加借阅记录
    void delete(String id); //还书时删除借阅记录
    void update(Lend lend); //更新借阅记录
}
