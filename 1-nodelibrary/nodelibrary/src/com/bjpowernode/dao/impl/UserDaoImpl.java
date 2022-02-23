package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.bean.User;
import com.bjpowernode.dao.UserDao;
import com.bjpowernode.util.BeanUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-22 23:39
 */
public class UserDaoImpl implements UserDao {
    @Override
    public List<User> select() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>)ois.readObject();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //如果以上出现了异常，则返回一个空list
        return new ArrayList<>();
    }

    /**
     * 条件查询，用于借阅功能
     * @param user
     * @return
     */
    @Override
    public List<User> select(User user) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>)ois.readObject();
            return list.stream().filter(u -> u.getId() == user.getId()).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //如果以上出现了异常，则返回一个空list
        return new ArrayList<>();
    }

    //需要先把文件中的读出来，再给list<User>最后加入新用户，最后重新持久化到文件中
    @Override
    public void add(User user) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>)ois.readObject();
            if (list != null){
                //实现自动生成编号
                User lastUser = list.get(list.size()-1);
                user.setId(lastUser.getId()+1);
                //将需要加入的user对象加入到list中
                list.add(user);
            }else{
                //当一开始就没有用户时
                list = new ArrayList<>();
                user.setId(1001);
                list.add(user);
            }
            //输出到文件
            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
            oos.writeObject(list);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加失败");
        } finally {
            try{
                if (ois != null){
                    ois.close();
                }
                if (oos != null){
                    oos.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 同样的，需要先取出来，修改后再持久化
    @Override
    public void update(User user) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>)ois.readObject();
            if(list != null){
                //从list中查找要修改的数据，用到了stream流和lamda表达式
                User originUser = list.stream().filter(u -> u.getId() == user.getId()).findFirst().get();
                //修改数据
                BeanUtil.populate(originUser,user);
            }

            //输出到文件
            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
            oos.writeObject(list);

        } catch (Exception e) {
            e.printStackTrace();
            throw new  RuntimeException("修改失败");
        } finally {
            try{
                if (ois != null){
                    ois.close();
                }
                if (oos != null){
                    oos.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //通过编号删除
    @Override
    public void delete(int id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>)ois.readObject();
            //使用steam流查找
            User user = list.stream().filter(u -> u.getId() == id).findFirst().get();
            //删除
            list.remove(user);

            //输出到文件
            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
            oos.writeObject(list);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除用户失败"); //给service或controller层用
        } finally {
            try{
                if (ois != null){
                    ois.close();
                }
                if (oos != null){
                    oos.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void frozen(int id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>)ois.readObject();
            //使用steam流查找
            User user = list.stream().filter(u -> u.getId() == id).findFirst().get();
            //冻结
            user.setStatus(Constant.USER_FROZEN);

            //输出到文件
            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
            oos.writeObject(list);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("冻结失败"); //给service或controller层用
        } finally {
            try{
                if (ois != null){
                    ois.close();
                }
                if (oos != null){
                    oos.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> selectUserToLend() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>)ois.readObject();
            if(list != null){
                //使用Stream流查询没有被冻结的用户且没有借过书的用户
                List<User> collect = list.stream().filter(u -> Constant.USER_OK.equals(u.getStatus()) && false == u.isLend()).collect(Collectors.toList());
                return collect;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //如果以上出现了异常，则返回一个空list
        return new ArrayList<>();
    }
}
