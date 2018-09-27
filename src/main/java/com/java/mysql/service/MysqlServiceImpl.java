package com.java.mysql.service;

import com.java.mysql.dos.TCookiePushDO;
import com.java.mysql.mapper.TCookiePushMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author: Boris
 * Date: 2018/9/5 16:35
 * Copyright (C), 2017-2018,
 * Description:
 */
@Service
public class MysqlServiceImpl implements MysqlService {
    @Autowired
    private TCookiePushMapper tCookiePushMapper;



    @Override
    public List<TCookiePushDO> pageQueryByCategoryId(Map<String, Object> param) {
        List<TCookiePushDO> tCookiePushDOList = tCookiePushMapper.pageQueryByCategoryId(param);
        return tCookiePushDOList;
    }

    @Override
    public int insertData(List<TCookiePushDO> dataList) {
        return tCookiePushMapper.insertData(dataList);
    }
}
