package com.java.model;

/**
 * Author: Boris
 * Date: 2018/7/11 9:06
 * Copyright (C), 2017-2018,
 * msgription:
 */
public class ReturnConstant {

    /**
     * 返回码枚举
     */
    public enum ReturnMsgEnum{
        SUCCESS("00000000", "成功！"),
        SYSTEM_EXCEPTION("00000001", "系统异常"),
        EXCEPTION("00000002", "异常"),
        DB_EXCEPTION("00000002", "数据库异常"),
        FAIL("00000010", "失败"),
        ;
        private String code;

        private String msg;

        ReturnMsgEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
