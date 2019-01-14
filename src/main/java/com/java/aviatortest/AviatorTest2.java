package com.java.aviatortest;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.java.util.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Boris
 * Date: 2018/11/29 16:08
 * Description:
 */
public class AviatorTest2 {

    /**
     * 简单规则
     * [{"A111H008_0_001":"","A111H008_0_002":""},{"A121H030_0_002":""},{"B220H011_0_001":"[1,null]"},{"B220H026_0_001":"[5,null]"}]
     * Aviator中数据类型只有：Doule，Long
     */
    @Test
    public void test1() throws Exception{
        String rule = "[{\"A111H008_0_001\":\"\",\"A111H008_0_002\":\"\"},{\"A121H030_0_002\":\"\"},{\"B220H011_0_001\":\"[1,null]\"},{\"B220H026_0_001\":\"[5,null]\"}]";
        long s1 = System.currentTimeMillis();
        List<Map<String, String>> tagList = JacksonUtil.deserialize(rule, List.class);
        long e1 = System.currentTimeMillis();
        System.out.println("解压缩耗时：" + (e1- s1));
        long s2 = System.currentTimeMillis();
        List<String> list = new ArrayList<>();

        for (Map<String, String> map : tagList) {
            StringBuilder exp = new StringBuilder();
            if (map.size()>0) {
                exp.append("(");
            }else {
                exp.append("");
            }
            String value = map.values().iterator().next();
            if (StringUtils.isEmpty(value)) {
                for (String key : map.keySet()) {
                    exp.append(key);
                    exp.append(" || ");

                }
            }else {
                for (String key : map.keySet()) {
                    String valule1 = map.get(key);
                    String sub = valule1.substring(1, valule1.length()-1);
                    String[] arry = sub.split(",");
                    if (!"null".equals(arry[0])) {
                        exp.append(key);
                        exp.append(" >= ");
                        exp.append(arry[0]);
                    }
                    if (!"null".equals(arry[1])) {
                        exp.append( " && ");
                        exp.append(key);
                        exp.append(" <= ");
                        exp.append(arry[1]);
                    }

                    exp.append(" || ");

                }
            }
            exp.delete(exp.lastIndexOf(" || "), exp.length());
            if (map.size()>0) {
                exp.append(")");
            }
            list.add(exp.toString());
        }
        System.out.println(String.join(" && ", list));
        String expss = String.join(" && ", list);
        long e2 = System.currentTimeMillis();
        System.out.println("表达式生成耗时：" + (e2-s2));
        Expression expression = AviatorEvaluator.compile(expss);
        Map<String, Object> param = new HashMap<>();
        param.put("A111H008_0_001", false);
        param.put("A111H008_0_002", true);
        param.put("A121H030_0_002", false);
        param.put("B220H011_0_001", 3.3);
        param.put("B220H026_0_001", 6);
       Boolean resultss = (Boolean) expression.execute(param);
        System.out.println(resultss);
        String sfdf = " (A111H008_0_001||A111H008_0_002) &&  (A121H030_0_002 == nil ? true : false) &&  (B220H011_0_001 >= 1) &&  (B220H026_0_001 >= 5)";
        Expression expression1 =  AviatorEvaluator.compile(sfdf);
        long start = System.currentTimeMillis();
        Boolean re = (Boolean) expression1.execute( param);
        long end = System.currentTimeMillis();
        System.out.println(re);
        System.out.println("耗时：" + (end - start));
        Long result = (Long) AviatorEvaluator.execute("1+2+3");
        System.out.println(result);
    }

    /**
     * 逻辑运算
     */
    @Test
    public void test2() throws Exception{
        Boolean f1 = false;
        Boolean f2 = false;
        Map<String, Object> param = new HashMap<>();
        param.put("f1", f1);
        param.put("f2", f2);
        Boolean result = (Boolean) AviatorEvaluator.execute("!f1 || f2", param);
        System.out.println(result);
    }

    /**
     * 逻辑运算
     */
    @Test
    public void test3() throws Exception{
        System.out.println("1,null".split(",")[1]);
    }

    /**
     * 逻辑运算非,测试取值为空的情况
     */
    @Test
    public void test4() throws Exception{
        Boolean f1 = false;
        Boolean f2 = true;
        Map<String, Object> param = new HashMap<>();
        param.put("f1", f1);
        //param.put("f2", f2);
        Boolean result = (Boolean) AviatorEvaluator.execute("!(f1 || f2)", param);
        System.out.println(result);
    }

    /**
     * 逻辑运算非,Boolean类型的值为空的情况
     */
    @Test
    public void test5() throws Exception{
        Boolean f1 = false;
        Boolean f2 = false;
        Map<String, Object> param = new HashMap<>();
        //param.put("f1", f1);
        param.put("f2", f2);
        Boolean result = (Boolean) AviatorEvaluator.execute("!(f1==nil? false : true || f2)", param);
        System.out.println(result);
    }


    /**
     * 逻辑运算非,数值型为空的情况
     */
    @Test
    public void test6() throws Exception{
        Boolean f1 = true;
        Boolean f2 = false;
        Integer f3 = 4;
        Map<String, Object> param = new HashMap<>();
        param.put("f1", f1);
        param.put("f2", f2);
        param.put("f3", f3);
        Boolean result = (Boolean) AviatorEvaluator.execute("!(f1==nil? false : true || f2) && f3>= 2", param);
        System.out.println(result);
    }


    /**
     * 逻辑运算非,数值型为空的情况
     */
    @Test
    public void test7() throws Exception{
        Boolean f1 = true;
        Boolean f2 = false;
        Integer f3 = 4;
        Map<String, Object> param = new HashMap<>();
        param.put("f1", f1);
        param.put("f2", f2);
        param.put("f3", f3);
        Expression expression = AviatorEvaluator.compile("f1==nil? false : true");
        System.out.println("expression结果：" + expression.execute(param));
        Boolean result = (Boolean) AviatorEvaluator.execute("!(f1==nil? false : true || f2) && f3>= 2", param);
        System.out.println(result);
    }


    /**
     * 逻辑运算非,数值型为空的情况
     */
    @Test
    public void test8() throws Exception{
        Boolean f1 = true;
        Boolean f2 = false;
        Integer f3 = 4;
        Map<String, Object> param = new HashMap<>();
        param.put("f1", f1);
        param.put("f2", f2);
        param.put("f3", f3);
        param.put("dfsdfs", f3);
        Expression expression = AviatorEvaluator.compile("dfsdfs");
        System.out.println("expression结果：" + expression.execute(param));
        Boolean result = (Boolean) AviatorEvaluator.execute("!(f1==nil? false : true || f2) && f3>= 2", param);
        System.out.println(result);
    }

}
