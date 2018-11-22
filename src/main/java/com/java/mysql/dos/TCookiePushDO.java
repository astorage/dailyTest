package com.java.mysql.dos;

import lombok.Data;

/**
 * Author: Boris
 * Date: 2018/9/5 16:25
 * Copyright (C), 2017-2018,
 * Description:
 */
@Data
public class TCookiePushDO {
    /**
     * 分群id
     */
    private Integer categoryId;
    /**
     * user
     */
    private String user;
    /**
     * 日期
     */
    private String dataDate;
    /**
     * 序号
     */
    private Integer sequNum;
}
