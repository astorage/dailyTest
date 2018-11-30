package com.java.feltest;

import com.greenpineyu.fel.Expression;
import com.greenpineyu.fel.Fel;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.common.FelBuilder;
import com.greenpineyu.fel.common.ObjectUtils;
import com.greenpineyu.fel.context.AbstractContext;
import com.greenpineyu.fel.context.ContextChain;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.context.MapContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import com.greenpineyu.fel.security.SecurityMgr;
import com.greenpineyu.fel.security.SecurityMgrImpl;
import lombok.Data;
import org.junit.Test;

import java.util.*;

/**
 * Author: Boris
 * Date: 2018/11/29 14:07
 * Description:
 */
public class FelArithmeticalOperationTest {

    /**
     * 算数表达式
     */
    @Test
    public void testFel1() {
        FelEngine fel = new FelEngineImpl();
        Object result = fel.eval("5000*12+7500");
        System.out.println(result);
    }

    /**
     * 算数表达式——使用变量
     */
    @Test
    public void testFel2() {
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        ctx.set("单价", 5000);
        ctx.set("数量", 12);
        ctx.set("运费", 7500);
        Object result = fel.eval("单价*数量+运费");
        System.out.println(result);
    }

    /**
     * 访问对象属性
     */
    @Test
    public void testFel3() {
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        Foo foo = new Foo();
        foo.setSize(2);
        foo.setSample("sample");
        //foo.setName("name");
        ctx.set("foo", foo);
        Map<String,String> map = new HashMap<String,String>();
        map.put("ElName", "fel");
        ctx.set("m",map);

        //调用foo.getSize()方法。
        Object result = fel.eval("foo.size");
        System.out.println(result);
        //调用foo.isSample()方法。
        result = fel.eval("foo.sample");
        System.out.println(result);
        //foo没有name、getName、isName方法
        //foo.name会调用foo.get("name")方法。
        result = fel.eval("foo.name");
        System.out.println(result);
        //m.ElName会调用m.get("ElName");
        result = fel.eval("m.ElName");
        System.out.println(result);
    }

    /**
     * 访问数组
     */
    @Test
    public void testFel4() {
        FelEngine fel = new FelEngineImpl();
        FelContext felContext = fel.getContext();

        //数组
        int[] intArray = {1,2,3};
        felContext.set("intArray",intArray);
        //获取intArray[0]
        String exp = "intArray[0]";
        System.out.println(exp+"->"+fel.eval(exp));
    }

    /**
     * 访问List
     */
    @Test
    public void testFel5() {
        FelEngine fel = new FelEngineImpl();
        FelContext felContext = fel.getContext();

        String exp;
        //List
        List<Integer> list = Arrays.asList(1,2,3);
        felContext.set("list",list);
        //获取list.get(0)
        exp = "list[0]";
        System.out.println(exp+"->"+fel.eval(exp));
    }


    /**
     * 访问Collection
     */
    @Test
    public void testFel6() {
        FelEngine fel = new FelEngineImpl();
        FelContext felContext = fel.getContext();

        String exp;
        //集合
        Collection<String> coll = Arrays.asList("a","b","c");
        felContext.set("coll",coll);
        //获取集合最前面的元素。执行结果为"a"
        exp = "coll[0]";
        System.out.println(exp+"->"+fel.eval(exp));
        //迭代器
        Iterator<String> iterator = coll.iterator();
        felContext.set("iterator", iterator);
        //获取迭代器最前面的元素。执行结果为"a"
        exp = "iterator[1]";
        System.out.println(exp+"->"+fel.eval(exp));
    }


    /**
     * 访问Map
     */
    @Test
    public void testFel7() {
        FelEngine fel = new FelEngineImpl();
        FelContext felContext = fel.getContext();

        String exp;
        //Map
        Map<String,String> map = new HashMap<String, String>();
        map.put("name", "HashMap");
        felContext.set("map",map);
        exp = "map.name";
        System.out.println(exp+"->"+fel.eval(exp));
    }


    /**
     * 访问二维数组
     */
    @Test
    public void testFel8() {
        FelEngine fel = new FelEngineImpl();
        FelContext felContext = fel.getContext();

        String exp;
        //多维数组
        int[][] intArrays= {{12,12},{21,22}};
        felContext.set("intArrays",intArrays);
        exp = "intArrays[0][0]";
        System.out.println(exp+"->"+fel.eval(exp));
    }


    /**
     * 访问复杂集合
     */
    @Test
    public void testFel9() {
        FelEngine fel = new FelEngineImpl();
        FelContext felContext = fel.getContext();

        String exp;

        //多维综合体，支持数组、集合的任意组合。
        List<int[]> listArray = new ArrayList<int[]>();
        listArray.add(new int[]{1,2,3});
        listArray.add(new int[]{4,5,6});
        felContext.set("listArray",listArray);
        exp = "listArray[0][0]";
        System.out.println(exp+"->"+fel.eval(exp));
    }

    /**
     * 访问复杂集合
     */
    @Test
    public void testFel10() {
        FelEngine fel = new FelEngineImpl();
        FelContext felContext = fel.getContext();

        String exp;

        //多维综合体，支持数组、集合的任意组合。
        List<Foo> listArray = new ArrayList<>();
        Foo foo1 = new Foo();
        foo1.setSample("sample1");
        Foo foo2 = new Foo();
        foo2.setSample("sample2");
        listArray.add(foo1);
        listArray.add(foo1);
        felContext.set("listArray",listArray);
        exp = "listArray[0].sample";
        System.out.println(exp+"->"+fel.eval(exp));
    }

    /**
     * 调用java方法
     */
    @Test
    public void testFel11() {
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        //调用System.out的print方法,字符串的substring方法
        ctx.set("xxxx", System.out);
        fel.eval("xxxx.println('Hello Everybody'.substring(6))");
    }


    /**
     * 自定义上下文语境
     */
    @Test
    public void testFel12() {
        //负责提供气象服务的上下文环境
        FelContext ctx = new AbstractContext() {
            public Object get(String name) {
                if("天气".equals(name)){
                    return "晴";
                }
                if("温度".equals(name)){
                    return 25;
                }
                return null;
            }
        };
        FelEngine fel = new FelEngineImpl(ctx);
        Object eval = fel.eval("'天气:'+天气+';温度:'+温度");
        System.out.println(eval);
    }


    /**
     * 多层自定义上下文语境
     */
    @Test
    public void testFel13() {
        FelEngine fel = new FelEngineImpl();
        String costStr = "成本";
        String priceStr="价格";
        FelContext baseCtx = fel.getContext();
        //父级上下文中设置成本和价格
        baseCtx.set(costStr, 50);
        baseCtx.set(priceStr,100);

        String exp = priceStr+"-"+costStr;
        Object baseCost = fel.eval(exp);
        System.out.println("期望利润：" + baseCost);

        FelContext ctx = new ContextChain(baseCtx, new MapContext());
        //通货膨胀导致成本增加（子级上下文 中设置成本，会覆盖父级上下文中的成本）
        ctx.set(costStr,50+20 );
        Object allCost = fel.eval(exp, ctx);
        System.out.println("实际利润：" + allCost);
    }


    /**
     * 编译执行
     * 备注：适合处理海量数据，编译执行的速度基本与Java字节码执行速度一样快。
     */
    @Test
    public void testFel14() {
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        ctx.set("单价", 5000);
        ctx.set("数量", 12);
        ctx.set("运费", 7500);
        Expression exp = fel.compile("单价*数量+运费",ctx);
        Object result = exp.eval(ctx);
        System.out.println(result);
    }


    /**
     * 自定义函数
     */
    @Test
    public void testFel15() {
        //定义hello函数
        Function fun = new CommonFunction() {

            public String getName() {
                return "hello";
            }

            /*
             * 调用hello("xxx")时执行的代码
             */
            @Override
            public Object call(Object[] arguments) {
                Object msg = null;
                if(arguments!= null && arguments.length>0){
                    msg = arguments[1];
                }
                return ObjectUtils.toString(msg);
            }

        };
        FelEngine e = new FelEngineImpl();
        //添加函数到引擎中。
        e.addFun(fun);
        String exp = "hello('fel', 'sfsdf')";
        //解释执行
        Object eval = e.eval(exp);
        System.out.println("hello "+eval);
        //编译执行
        Expression compile = e.compile(exp, null);
        eval = compile.eval(null);
        System.out.println("hello "+eval);
    }

    /**
     * Fel也实现了一个"$"函数,其作用是获取class和创建对象。结合点操作符，可以轻易的调用工具类或对象的方法。
     * 通过"$('class').method"形式的语法，就可以调用任何等三方类包（commons lang等）及自定义工具类的方法，也可以创建对象，调用对象的方法。如果有需要，还可以直接注册Java Method到函数管理器中。
     */
    @Test
    public void testFel16() {
        //调用Math.min(1,2)
        Object rs1 = FelEngine.instance.eval("$('Math').min(1,2)");
        System.out.println(rs1);
        //调用new Foo().toString();
        Object rs2 = FelEngine.instance.eval("$('com.java.feltest.Foo.new').toString()");
        System.out.println(rs2);

        Foo foo = new Foo();
        foo.setSample("sdfdfdf");
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        ctx.set("foox", foo);
        Object rs3  = fel.eval("foox.toString");
        System.out.println(rs3);

    }


    /**
     * 大数值计算（始于0.9版本）
     */
    @Test
    public void testFel17() {
        //FelEngine fel = FelBuilder.bigNumberEngine();


        FelEngine fel = new FelEngineImpl();
        String input = "111111111111111111111111111111+22222222222222222222222222222222";
        Object value = fel.eval(input);
        Object compileValue = fel.compile(input, fel.getContext()).eval(fel.getContext());
        System.out.println("大数值计算（解释执行）:" + value);
        System.out.println("大数值计算（编译执行）:" + compileValue);
    }

    /**
     * 大数值计算（始于0.9版本）
     * 注释掉了
     */
    @Test
    public void testFel18() {
        FelEngine fel = new FelEngineImpl();
        SecurityMgrImpl securityMgr = new SecurityMgrImpl();

        String input = "1+2";
        Object value = fel.eval(input);
        Object compileValue = fel.compile(input, fel.getContext()).eval(fel.getContext());
        System.out.println("大数值计算（解释执行）:" + value);
        System.out.println("大数值计算（编译执行）:" + compileValue);
    }



}







