package com.java.aviatortest;

import lombok.Data;

import java.util.Date;

/**
 * Author: Boris
 * Date: 2018/11/29 20:20
 * Description:
 */
@Data
public class TestAviator {
    int i;
    float f;
    Date date;

    public TestAviator(int i, float f, Date date) {
        this.i = i;
        this.f = f;
        this.date = date;
    }
}
