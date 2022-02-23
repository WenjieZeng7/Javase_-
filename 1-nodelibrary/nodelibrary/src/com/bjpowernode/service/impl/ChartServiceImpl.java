package com.bjpowernode.service.impl;

import com.bjpowernode.dao.ChartDao;
import com.bjpowernode.dao.impl.ChartDaoImpl;
import com.bjpowernode.service.ChartService;

import java.util.Map;

/**
 * @author Jesse 1094798816@qq.com
 * @create 2022-02-23 16:23
 */
public class ChartServiceImpl implements ChartService {
    private ChartDao chartDao = new ChartDaoImpl();
    @Override
    public Map<String, Integer> bookTypeCount() {
        return chartDao.bookTypeCount();
    }
}
