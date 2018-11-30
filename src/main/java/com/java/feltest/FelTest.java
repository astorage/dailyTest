package com.java.feltest;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import org.junit.Test;


/**
 * Author: Boris
 * Date: 2018/11/30 14:38
 * Description:
 */
public class FelTest {

    /**
     * 逻辑运算
     * (A111H008_0_001或者A111H008_0_002) 且 不存在A121H030_0_002 且B220H011_0_001>=1 且 B220H026_0_001>=5
     * (A111H008_0_001||A111H008_0_002) &&  (A121H030_0_002 == null ? true : false) &&  (B220H011_0_001 >= 1) &&  (B220H026_0_001 >= 5)
     */
    @Test
    public void test1() throws Exception{
        String expsss = "(A111H008_0_001||A111H008_0_002) &&  (A121H030_0_002 == null ? true : false) &&  (B220H011_0_001 >= 1) &&  (B220H026_0_001 >= 5)";
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        ctx.set("A111H008_0_001", false);
        ctx.set("A111H008_0_002", true);
        //ctx.set("A121H030_0_002", false);
        ctx.set("B220H011_0_001", 3.3);
        ctx.set("B220H026_0_001", 6);
        //Expression exp = fel.compile(expsss,ctx);

        Object result = fel.eval(expsss,ctx);
        System.out.println(result);
    }


    /**
     * 逻辑运算
     */
    @Test
    public void test2(){
        String keyExp="你好 and 我好 or 他好 and not 不好";

        //表达式转换
        String exp=keyExp.replace(" and ","&&").replace(" or ","||").replaceAll("^not ","!").replace(" not ","!");
        System.out.println(exp);
        //替换所有的"(",")",并按照and，or，not进行分割
        String[] keyExpSplits=keyExp.replace("(","").replace(")","").split(" and | or | not |^not ");
        String verifyExp=exp;//用于验证的表达式
        for(String key:keyExpSplits){
            if(key.trim().equals(""))
                continue;
            verifyExp=verifyExp.replace(key,"false");
        }
        System.out.println(verifyExp);
        //对表达式进行验证
        FelEngine fel=new FelEngineImpl();
        FelContext ctx=fel.getContext();
        try{
            fel.eval(verifyExp,ctx);
        }catch(Exception e){
            System.out.println("表达式出错");
            e.printStackTrace();
        }
        System.out.println("表达式正确");
    }


    /**
     * 逻辑运算
     */
    @Test
    public void test3() {
        FelEngine fel=new FelEngineImpl();
        String exp1 = "a || b";
        FelContext context1 = fel.getContext();
        context1.set("a", true);
        context1.set("b", true);
        Object obj1 = fel.eval(exp1, context1);
        System.out.println(obj1);


    }

    @Test
    public void test4() {
        FelEngine fel=new FelEngineImpl();
        String exp2 = "a && b";
        FelContext context2 = fel.getContext();
        context2.set("a", true);
        context2.set("b", false);
        Object obj2 = fel.eval(exp2, context2);
        System.out.println(obj2);


    }

    @Test
    public void test5() {
        FelEngine fel=new FelEngineImpl();
        String exp3 = "(a || b) && c";
        FelContext context3 = fel.getContext();
        context3.set("a", true);
        context3.set("b", false);
        context3.set("c", false);
        Object obj3 = fel.eval(exp3, context3);
        System.out.println(obj3);


    }
    @Test
    public void test6() {
        FelEngine fel=new FelEngineImpl();
        String exp4 = "!a";
        FelContext context4 = fel.getContext();
        context4.set("a", false);
        Object obj4 = fel.eval(exp4, context4);
        System.out.println(obj4);
    }

    @Test
    public void test7() {
        FelEngine fel=new FelEngineImpl();

        String exp5 = "a==null?true:false";
        FelContext context5 = fel.getContext();
        //context5.set("a", 2);

        Object obj5 = fel.eval(exp5, context5);
        System.out.println(obj5);

    }

    @Test
    public void test8() {
        FelEngine fel=new FelEngineImpl();
        String exp6 = "a";
        FelContext context6 = fel.getContext();

        Object obj6 = fel.eval(exp6, context6);
        System.out.println("sdfdsf: " + obj6.getClass().getName());
    }

}
