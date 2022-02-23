package com.bjpowernode.service.impl;

import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.dao.LendDao;
import com.bjpowernode.dao.UserDao;
import com.bjpowernode.dao.impl.LendDaoImpl;
import com.bjpowernode.dao.impl.UserDaoImpl;
import com.bjpowernode.service.UserService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-22 23:47
 */
public class UserServiceImpl implements UserService{
    //调用DAO层的方法
    private UserDao userDao = new UserDaoImpl();

    private LendDao lendDao = new LendDaoImpl();

    @Override
    public List<User> select() {
        return userDao.select();
    }

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public void frozen(int id) {
        userDao.frozen(id);
    }

    @Override
    public List<User> selectUserToLend() {
        return userDao.selectUserToLend();
    }

    /**
     * 用户充值
     * @param user
     * @param money
     * @return
     */
    @Override
    public User charge(User user, BigDecimal money) {
        BigDecimal sum = money.add(user.getMoney());
        if(BigDecimal.ZERO.compareTo(sum)<0){ //余额大于0
            //修改用户状态
            user.setStatus(Constant.USER_OK);
        }
        user.setMoney(sum);

        //更新用户数据
        userDao.update(user);

        //还要更新借阅中的数据
        List<Lend> lendList = lendDao.select(null);
        for (int i = 0; i < lendList.size(); i++) {
            Lend lend = lendList.get(i);
            if (lend.getUser().getId() == user.getId()){
                lend.setUser(user);
                lendDao.update(lend);
                break;
            }
        }
        return user;
    }
}
