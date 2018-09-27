package com.java.contorller;

import com.java.model.dto.StudentDTO;
import com.java.mysql.service.MysqlService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


/**
 * Author: Boris
 * Date: 2018/9/14 10:25
 * Copyright (C), 2017-2018,
 * Description:
 */
@Validated
@RestController
public class TestHibernateValidationController {
    @Autowired
    private MysqlService mysqlService;

    @RequestMapping("/hibernateValidate/test")
    public String test(){
        return "this is a web test";
    }

    /**
     * 对单个参数进行必填校验
     * @param name
     * @return
     */
    @RequestMapping("/hibernateValidate/testSingleParam")
    public String testSingleParam( @NotNull(message = "参数name不能为null") String name, BindingResult bindingResult, @NotBlank(message = "性别不合法") String gender) {
        if (bindingResult.hasErrors()) {
            return "no pass validate single param: " + name;
        }
        return "pass validate single param: " + name;
    }

    @RequestMapping("/hibernateValidate/testObjectParam")
    public String testObjectParam( @Validated StudentDTO studentDTO, BindingResult bindingResult) {

        return "object validation pass: " + studentDTO;
    }
}
