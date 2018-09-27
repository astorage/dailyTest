package com.java.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: Boris
 * Date: 2018/7/11 9:05
 * Copyright (C), 2017-2018,
 * Description: 返回数据结构
 */
@Data
public class DataResult<T> implements Serializable {
    /**
     * 返回码
     */
    private String code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    public DataResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DataResult() {
        this.code = ReturnConstant.ReturnMsgEnum.SUCCESS.getCode();
        this.msg = ReturnConstant.ReturnMsgEnum.SUCCESS.getMsg();
    }

    /**
     * 结果返回是否成功（也可以自己根据返回码判断）
     * @return
     */
    public Boolean isSuccess(){
        if (ReturnConstant.ReturnMsgEnum.SUCCESS.getCode().equals(code)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 设置枚举已经定义好的返回码和返回信息，在非成功返回时使用
     * @param returnMsg
     */
    public void setReturnMsg(ReturnConstant.ReturnMsgEnum returnMsg) {
        this.code = returnMsg.getCode();
        this.msg = returnMsg.getMsg();
    }

    /**
     * 非成功的返回，设置返回码返回信息
     * 应用场景：在非成功返回的情况下，前端不需要关心返回码，只关心返回的信息
     * @param code
     * @param msg
     */
    public void setReturnMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
