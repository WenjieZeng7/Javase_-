package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.dao.BookDao;
import com.bjpowernode.util.BeanUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 14:17
 */
public class BookDaoImpl implements BookDao{

    @Override
    public List<Book> select(Book book) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> list = (List<Book>)ois.readObject();
            if(list != null){
                if(book == null || ("".equals(book.getBookName()) && "".equals(book.getIsbn()))){
                    //查询所有图书
                    return list;
                }else{
                    List<Book> conditionList = new ArrayList<>();
                    //根据编号查询，用在借阅那
                    if(book.getId()!= 0){
                        conditionList = list.stream().filter(b -> b.getId() == book.getId()).collect(Collectors.toList());
                        return conditionList;
                    }


                    //按条件查询
                    if(!"".equals(book.getBookName()) && !"".equals(book.getIsbn())){
                        conditionList = list.stream().filter(b -> b.getBookName().equals(book.getBookName())).collect(Collectors.toList());
                        //基于第一个按书名查询的基础之后再查询
                        conditionList = conditionList.stream().filter(b -> b.getIsbn().equals(book.getIsbn())).collect(Collectors.toList());
                    }else{
                        if(!"".equals(book.getBookName())){
                            //按图书名进行查询,lamda表达式
                            conditionList = list.stream().filter(b -> b.getBookName().equals(book.getBookName())).collect(Collectors.toList());
                        }
                        if(!"".equals(book.getIsbn())){
                            //按isbn查询
                            conditionList = list.stream().filter(b -> b.getIsbn().equals(book.getIsbn())).collect(Collectors.toList());
                        }
                    }

                    return conditionList;
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
    public void add(Book book) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> list = (List<Book>)ois.readObject();
            if(list != null){
                //生成图书id
                Book lastBook = list.get(list.size() - 1);
                book.setId(lastBook.getId()+1);

                //添加
                list.add(book);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.BOOK_PATH));
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
    public void delete(int id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> list = (List<Book>)ois.readObject();
            if(list != null){
               //使用steam查找
                Book book = list.stream().filter(b -> b.getId() == id).findFirst().get();
                list.remove(book);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.BOOK_PATH));
                oos.writeObject(list);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
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
    public void update(Book book) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> list = (List<Book>)ois.readObject();
            if(list != null){
                //使用steam查找
                Book originBook = list.stream().filter(b -> b.getId() == book.getId()).findFirst().get();
                //进行修改,使用了工具类
                BeanUtil.populate(originBook,book);


                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.BOOK_PATH));
                oos.writeObject(list);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
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
