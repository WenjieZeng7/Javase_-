package com.bjpowernode.util;

import com.bjpowernode.bean.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.rmi.server.UID;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 13:52
 */
public class initDataUtil {
    public static void main(String[] args) {
        //初始化用户数据
        List<User> userList = new ArrayList<>();
        userList.add(new User(1001,false,"abc", Constant.USER_OK, BigDecimal.TEN));
        initData(PathConstant.USER_PATH,userList);

        //初始化图书数据
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1,"java", "add",Constant.TYPE_COMPUTER,"123-1","pub",Constant.STATUS_LEND));
        initData(PathConstant.BOOK_PATH,bookList);

        //初始化借阅数据
        List<Lend> lendList = new ArrayList<>();
        User user = new User(1001, true,"abc", Constant.USER_OK, BigDecimal.TEN);
        Book book = new Book(1, "java", "add", Constant.TYPE_COMPUTER, "123-1", "pub", Constant.STATUS_STORAGE);
        Lend lend = new Lend();
        lend.setUser(user);
        lend.setBook(book);
        lend.setStatus(Constant.LEND_LEND);
        LocalDate begin = LocalDate.now();
        lend.setLendDate(begin);
        lend.setReturnDate(begin.plusDays(30));
        lend.setId(UUID.randomUUID().toString()); //使用UUID生成编号（UUID就不重复，借阅记录的编号只要不重复即可）
        lendList.add(lend);
        initData(PathConstant.LEND_PATH,lendList);

    }


    /**
     * 初始化数据
     * @param path
     * @param list
     */
    public static void initData(String path,List<?> list) //泛型的通配符<?>
    {
        File directory = new File(path.split("/")[0]+"/");
        File file = new File(path);
        ObjectOutputStream oos = null;

        try {
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();

                //利用对象输出流将list数据写到文件中
                oos = new ObjectOutputStream(new FileOutputStream(path));
                oos.writeObject(list);
            }
        }catch (IOException e){
        }finally {
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
