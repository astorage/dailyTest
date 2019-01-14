package com.java.aviatortest.usercategory;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Boris
 * Date: 2018/12/28 20:58
 * Description:
 */
public class UserCategoryRuleTest {

    /**
     * 测试人群规则缓存在map中，然后来判断
     */
    @Test
    public void test() throws Exception{

        Map<String, Expression> expressionMap = getExpressionMap();
        Map<String, Object> user = new HashMap<>();
        user.put("A111H085_0_001", true);
        user.put("E220H088_0_001", true);
        user.put("B220H039_0_001", 200);
        user.put("A111H041_0_001", true);
        user.put("A220H029_0_001", 201);
        user.put("A120H089_0_007", true);
        user.put("A121H013_0_006", true);
        user.put("E220H091_0_001", true);
        user.put("A221H024_0_004", true);
        user.put("E220H089_0_001", true);
        user.put("A121H030_0_002", true);
        user.put("A121H031_0_002", true);
        user.put("B220H066_0_001", 195);
        user.put("A121H002_0_008", true);
        user.put("A220H090_0_001", 200);

        Boolean TRUE = true;
        List<String> categoryList = new ArrayList<>();
        long start = System.currentTimeMillis();
        int i = 0;
        for (Map.Entry<String, Expression> entry : expressionMap.entrySet()) {
            //System.out.println(i++);
            //System.out.println(entry.getKey());
            Object obj = entry.getValue().execute(user);
            //System.out.println(obj);
            if ( TRUE.equals(obj)) {
                categoryList.add(entry.getKey());
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(categoryList);

    }


    public static Map<String, Expression> getExpressionMap() {
        Map<String, Expression> expressionMap = new HashMap<>();
        String rule = "[{\"A121H030_0_001\":\"\"},{\"A220H029_0_001\":\"[1,null]\"}]";
        String rule1 = "[{\"A121H030_0_002\":\"\"},{\"A121H031_0_002\":\"\"}]";
        String rule2 = "[{\"A220H029_0_001\":\"[1,null]\"}]";
        String rule3 = "[{\"B220H038_0_001\":\"[1,null]\"}]";
        String rule4 = "[{\"B220H026_0_001\":\"[30,null]\"}]";
        String rule5 = "[{\"A111H001_0_002\":\"\"}]";
        String rule6 = "[{\"B220H027_0_001\":\"[1,1]\"}]![{\"A121H013_0_001\":\"\",\"A121H013_0_002\":\"\",\"A121H013_0_004\":\"\"}]";
        String rule7 = "[{\"B220H027_0_001\":\"[2,3]\"}]![{\"A121H013_0_001\":\"\",\"A121H013_0_002\":\"\",\"A121H013_0_004\":\"\"}]";
        String rule8 = "[{\"B220H027_0_001\":\"[4,null]\"}]![{\"A121H013_0_001\":\"\",\"A121H013_0_002\":\"\",\"A121H013_0_004\":\"\"}]";
        String rule9 = "[{\"A121U002_0_002\":\"\"},{\"B220U074_0_001\":\"[10,null]\"}]";
        String rule10 = "[{\"A121U002_0_002\":\"\"},{\"A111U008_0_001\":\"\"},{\"B220U071_0_001\":\"[null,0.5]\"}]![{\"B220U073_0_001\":\"[1,null]\"}]![{\"A121U002_0_002\":\"\"},{\"B220U074_0_001\":\"[10,null]\"}]";
        String rule11 = "[{\"A121H030_0_002\":\"\"},{\"A121H031_0_002\":\"\"},{\"A220H090_0_001\":\"[7,null]\"}]";
        String rule12 = "[{\"B220H027_0_001\":\"[1,null]\"}]![{\"B220H009_0_001\":\"[1,null]\"}]";
        String rule13 = "[{\"A121H002_0_001\":\"\",\"A121H002_0_002\":\"\",\"A121H002_0_003\":\"\",\"A121H002_0_008\":\"\",\"A121H002_0_009\":\"\",\"A121H002_0_004\":\"\",\"A121H002_0_010\":\"\",\"A121H002_0_005\":\"\",\"A121H002_0_011\":\"\",\"A121H002_0_012\":\"\",\"A121H002_0_006\":\"\"},{\"B220H102_0_001\":\"[3,3]\"}]";
        String rule14 = "[{\"A121H002_0_001\":\"\",\"A121H002_0_002\":\"\",\"A121H002_0_003\":\"\",\"A121H002_0_004\":\"\",\"A121H002_0_005\":\"\",\"A121H002_0_006\":\"\",\"A121H002_0_012\":\"\",\"A121H002_0_011\":\"\",\"A121H002_0_010\":\"\",\"A121H002_0_008\":\"\"},{\"B220H027_0_001\":\"[1,1]\"},{\"B220H038_0_001\":\"[2,2]\"}]";
        String rule15 = "[{\"A121H013_0_006\":\"\"},{\"A121H002_0_001\":\"\",\"A121H002_0_002\":\"\",\"A121H002_0_003\":\"\",\"A121H002_0_004\":\"\",\"A121H002_0_005\":\"\",\"A121H002_0_006\":\"\"},{\"B220H027_0_001\":\"[1,1]\"},{\"B220H102_0_001\":\"[7,7]\"}]";
        String rule16 = "[{\"C120U042_5_002\":\"[1,null]\"},{\"C120U010_5_001\":\"[1,null]\"},{\"C120U010_5_002\":\"[1,null]\"},{\"C120U010_5_003\":\"[1,null]\"},{\"C120U010_5_005\":\"[1,null]\"},{\"C120U010_5_006\":\"[1,null]\"}]";
        String rule17 = "[{\"A121U032_0_001\":\"\"}]![{\"B220U073_0_001\":\"[1,null]\"}]![{\"A121U002_0_002\":\"\"},{\"B220U074_0_001\":\"[10,null]\"}]";
        String rule18 = "[{\"C120H042_7_002\":\"[1,null]\"},{\"C120H010_7_001\":\"[1,null]\"},{\"C120H010_7_002\":\"[1,null]\"},{\"C120H010_7_003\":\"[1,null]\"},{\"C120H010_7_005\":\"[1,null]\"},{\"C120H010_7_006\":\"[1,null]\"}]";
        //String rule19 = "[{\"\"B220U073_0_001\"\":\"\"[1,null]\"\"}]";
        expressionMap.put("330033", AwiatorUtil.getExpressionFromRule(rule));
        expressionMap.put("330035", AwiatorUtil.getExpressionFromRule(rule1));
        expressionMap.put("330039", AwiatorUtil.getExpressionFromRule(rule2));
        expressionMap.put("330045", AwiatorUtil.getExpressionFromRule(rule3));
        expressionMap.put("330163", AwiatorUtil.getExpressionFromRule(rule4));
        expressionMap.put("330237", AwiatorUtil.getExpressionFromRule(rule5));
        expressionMap.put("330341", AwiatorUtil.getExpressionFromRule(rule6));
        expressionMap.put("330343", AwiatorUtil.getExpressionFromRule(rule7));
        expressionMap.put("330347", AwiatorUtil.getExpressionFromRule(rule8));
        expressionMap.put("330391", AwiatorUtil.getExpressionFromRule(rule9));
        expressionMap.put("330393", AwiatorUtil.getExpressionFromRule(rule10));
        expressionMap.put("330395", AwiatorUtil.getExpressionFromRule(rule11));
        expressionMap.put("330441", AwiatorUtil.getExpressionFromRule(rule12));
        expressionMap.put("330541", AwiatorUtil.getExpressionFromRule(rule13));
        expressionMap.put("330543", AwiatorUtil.getExpressionFromRule(rule14));
        expressionMap.put("330545", AwiatorUtil.getExpressionFromRule(rule15));
        expressionMap.put("330649", AwiatorUtil.getExpressionFromRule(rule16));
        expressionMap.put("330675", AwiatorUtil.getExpressionFromRule(rule17));
        expressionMap.put("330721", AwiatorUtil.getExpressionFromRule(rule18));
        return expressionMap;
    }
}
