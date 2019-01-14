package com.java.aviatortest.usercategory;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Boris
 * Date: 2018/12/24 16:44
 * Description:
 */
public class Test {
    private static Map<String, Expression> expressionMap = new HashMap<>();
    public static void main(String[] args) {
        String rule1 = "A111H008_0_001||A111H008_0_002";
        Expression expression1 = AviatorEvaluator.compile(rule1);
        expressionMap.put("rule1", expression1);
        String rule2 = "A121H030_0_002 == nil ? true : false";
        Expression expression2 = AviatorEvaluator.compile(rule2);
        expressionMap.put("rule2", expression2);
        String rule3 = "B220H011_0_001 >= 1";
        Expression expression3 = AviatorEvaluator.compile(rule3);
        expressionMap.put("rule3", expression3);
        String rule4 = "B220H026_0_001 >= 5";
        Expression expression4 = AviatorEvaluator.compile(rule4);
        expressionMap.put("rule4", expression4);

        Map<String, Object> param = new HashMap<>();
        param.put("A111H008_0_001", false);
        param.put("A111H008_0_002", true);
        //param.put("A121H030_0_002", false);
        param.put("B220H011_0_001", 3.3);
        param.put("B220H026_0_001", 6);

        List<String> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (Map.Entry<String, Expression> entry : expressionMap.entrySet()) {
            Boolean flag = (Boolean)entry.getValue().execute(param);
            if (flag) {
                list.add(entry.getKey());
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
        System.out.println(list);

    }


}
