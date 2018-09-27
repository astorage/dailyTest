package com.java.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Author: Boris
 * Date: 2018/9/14 10:19
 * Copyright (C), 2017-2018,
 * Description:
 */
@Data
public class StudentDTO {

    @NotNull(message = "id不能为空")
    private Integer id;
    @NotBlank(message = "name不能为空")
    private String name;

    private Integer age;

    private String calss;

    private List<StudentDTO> classmateList;

    @NotEmpty(message = "nums不能为空")
    private List<String> hobbies;

    private Integer sex;
    /**
     * 各科成绩
     */
    private Map<String, Integer> scores;
}
