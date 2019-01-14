package com.java.aviatortest.usercategory;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

/**
 * Author: Boris
 * Date: 2018/12/25 16:09
 * Copyright (C), 2017-2018, 浙江执御信息技术有限公司
 * Description:
 */
public class AwiatorUtil {

    public static Expression getExpression(String exp) {
       return AviatorEvaluator.compile(exp);
    }

    public static Expression getExpressionFromRule(String rule){
        String exp = AnalyzeRuleUtil.analyze(rule);
        return AviatorEvaluator.compile(exp);
    }
}
