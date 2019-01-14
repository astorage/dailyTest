package com.java.aviatortest.usercategory;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Boris
 * Date: 2018/12/25 11:44
 * Description:
 */
public class Test1 {
    public static void main(String[] args)throws Exception {
        String rule =
                //"[{\"A121U032_0_001\":\"\"}]![{\"B220U073_0_001\":\"[1,null]\"}]![{\"A121U002_0_002\":\"\"},{\"B220U074_0_001\":\"[10,null]\"}]";
                //"[{\"A121U032_0_001\":\"\"},{\"B220U073_0_001\":\"[1,null]\"}]";
                //"[{\"B220U073_0_001\":\"[1,null]\"}]";
                "[{\"B220U073_0_001\":\"[1.2,null]\"}]";
                //"[{\"A121U032_0_001\":\"\"}]";

        //String rule  = "[{\"A111H008_0_001\":\"\",\"A111H008_0_002\":\"\"},{\"A121H030_0_002\":\"\"},{\"B220H011_0_001\":\"[1,null]\"},{\"B220H026_0_001\":\"[5,null]\"}]";
        //String rule  = "[{\"A121U032_0_001\":\"\"}]![{\"B220U073_0_001\":\"[1,null]\"}]![{\"A121U002_0_002\":\"\"},{\"B220U074_0_001\":\"[10,null]\"}]";
        //String rule  = "[{\"A121H013_0_006\":\"\"},{\"A121H002_0_001\":\"\",\"A121H002_0_002\":\"\",\"A121H002_0_003\":\"\",\"A121H002_0_004\":\"\",\"A121H002_0_005\":\"\",\"A121H002_0_006\":\"\"},{\"B220H027_0_001\":\"[1,1]\"},{\"B220H102_0_001\":\"[7,7]\"}]";
        //String rule  = "[{\"C120H042_7_002\":\"[1,null]\"},{\"C120H010_7_001\":\"[1,null]\"},{\"C120H010_7_002\":\"[1,null]\"},{\"C120H010_7_003\":\"[1,null]\"},{\"C120H010_7_005\":\"[1,null]\"},{\"C120H010_7_006\":\"[1,null]\"}]";
        //String rule  = "[{\"A121H002_0_001\":\"\",\"A121H002_0_002\":\"\",\"A121H002_0_003\":\"\",\"A121H002_0_008\":\"\",\"A121H002_0_009\":\"\",\"A121H002_0_004\":\"\",\"A121H002_0_010\":\"\",\"A121H002_0_005\":\"\",\"A121H002_0_011\":\"\",\"A121H002_0_012\":\"\",\"A121H002_0_006\":\"\"},{\"B220H102_0_001\":\"[3,3]\"}]";
        //String rule  = "[{\"C120U042_5_002\":\"[1,null]\"},{\"C120U010_5_001\":\"[1,null]\"},{\"C120U010_5_002\":\"[1,null]\"},{\"C120U010_5_003\":\"[1,null]\"},{\"C120U010_5_005\":\"[1,null]\"},{\"C120U010_5_006\":\"[1,null]\"}]";

        //String rule = "[{\"B220H027_0_001\":\"[2,3]\"}]![{\"A121H013_0_006\":\"\"},{\"A121H002_0_001\":\"\",\"A121H002_0_002\":\"\",\"A121H002_0_003\":\"\",\"A121H002_0_004\":\"\",\"A121H002_0_005\":\"\",\"A121H002_0_006\":\"\"},{\"B220H027_0_001\":\"[1,1]\"},{\"B220H102_0_001\":\"[7,7]\"}]";


        String resultRule = AnalyzeRuleUtil.analyze(rule);
        System.out.println(resultRule);



        Expression expression = AviatorEvaluator.compile(resultRule);
        Map<String, Object> param = new HashMap<>();
//        param.put("A121U032_0_001", true);
        param.put("B220U073_0_001", "1.2");
//        param.put("A121U002_0_002", false);
//        param.put("B220U074_0_001", 0);



        System.out.println("结果：" + expression.execute(param));

        //AviatorEvaluator.compile(null);
    }
}
