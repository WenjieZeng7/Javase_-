package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.dao.LendDao;
import com.bjpowernode.util.BeanUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 16:47
 */
public class LendDaoImpl implements LendDao {
    @Override
    public List<Lend> select(Lend lend) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> list = (List<Lend>)ois.readObject();
            if(list != null){
                if(lend == null || "".equals(lend.getId())){
                    return list;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void add(Lend lend) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> list = (List<Lend>)ois.readObject();
            if(list != null){
                list.add(lend);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if (ois != null){
                    ois.close();
                }
                if (oos != null){
                    oos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(String id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> list = (List<Lend>)ois.readObject();
            if(list != null){
                Lend originLend = list.stream().filter(r -> Objects.equals(id, r.getId())).findFirst().get();
                list.remove(originLend);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if (ois != null){
                    ois.close();
                }
                if (oos != null){
                    oos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Lend lend) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> list = (List<Lend>)ois.readObject();
            if(list != null){
                Lend originLend = list.stream().filter(u -> u.getId().equals(lend.getId())).findFirst().get();
                //修改数据
                BeanUtil.populate(originLend,lend);

                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if (ois != null){
                    ois.close();
                }
                if (oos != null){
                    oos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
