package com.bjpowernode.dao;

import com.bjpowernode.bean.User;

import java.util.List;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-22 23:36
 */
public interface UserDao {
    List<User> select(); //从文件中读取数据
    List<User> select(User user); //查询，重载的
    void add(User user); //往文件中加入用户
    void update(User user); //修改用户信息
    void delete(int id); //删除用户
    void frozen(int id); //冻结用户
    List<User> selectUserToLend(); //查找可以借书的用户
}
