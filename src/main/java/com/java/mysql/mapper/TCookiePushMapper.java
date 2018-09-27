package com.java.mysql.mapper;

import com.java.mysql.dos.TCookiePushDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author: Boris
 * Date: 2018/9/5 16:15
 * Copyright (C), 2017-2018,
 * Description:
 */
public interface TCookiePushMapper {


    List<TCookiePushDO> pageQueryByCategoryId(Map<String, Object> param);

    int insertData(@Param("dataList") List<TCookiePushDO> dataList);
}
