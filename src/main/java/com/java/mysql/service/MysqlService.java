package com.java.mysql.service;

import com.java.mysql.dos.TCookiePushDO;

import java.util.List;
import java.util.Map;

/**
 * Author: Boris
 * Date: 2018/9/5 16:34
 * Copyright (C), 2017-2018,
 * Description:
 */
public interface MysqlService {

    List<TCookiePushDO> pageQueryByCategoryId(Map<String, Object> param);

    int insertData(List<TCookiePushDO> dataList);

}
