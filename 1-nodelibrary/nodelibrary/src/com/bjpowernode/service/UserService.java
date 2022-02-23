package com.bjpowernode.service;

import com.bjpowernode.bean.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-22 23:46
 */
public interface UserService {
    List<User> select();
    void add(User user);
    void update(User user);
    void delete(int id);
    void frozen(int id);
    List<User> selectUserToLend(); //查找可以借书的用户
    User charge(User user, BigDecimal money); //充值
}
