package com.java.aviatortest;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
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
 * https://blog.csdn.net/keda8997110/article/details/50782848
 */
public class AviatorTest {

    /**
     * 算数表达式计算
     * Aviator中数据类型只有：Doule，Long
     */
    @Test
    public void test1() {
        Long result = (Long) AviatorEvaluator.execute("1+2+3");
        System.out.println(result);
    }


    /**
     * 简单变量获取
     * 任何用单引号或者双引号括起来的字符序列
     * 可以参与正则匹配, 可以与任何对象相加, 任何对象与String相加结果为String。 String中也可以有转义字符,如\n、\\、\' 等
     */
    @Test
    public void test2() {
        String yourName = "Michael";
        Map<String, Object> env = new HashMap<>();
        env.put("yourName1", yourName);
        String result = (String) AviatorEvaluator.execute(" 'hello ' + yourName1 ", env);
        System.out.println(result);  // hello Michael


        Object obj1 = AviatorEvaluator.execute(" 'a\"b' ");           // 字符串 a"b
        System.out.println(obj1);
        Object obj2 = AviatorEvaluator.execute(" \"a\'b\" ");         // 字符串 a'b
        System.out.println(obj2);
        Object obj5 = AviatorEvaluator.execute(" \"a\\\' b\" ");         // 字符串 a'b
        System.out.println(obj5);
        Object obj3 = AviatorEvaluator.execute(" 'hello ' + 3 ");     // 字符串 hello 3
        System.out.println(obj3);
        Object obj4 = AviatorEvaluator.execute(" 'hello '+ unknow "); // 字符串 hello null
        System.out.println(obj4);
    }



    /**
     * exec方法
     *
     */
    @Test
    public void test3() {
        String name = "dennis";
        String ss = (String) AviatorEvaluator.exec(" 'hello ' + dfdf ", name); // hello dennis
        System.out.println(ss);
    }


    /**
     * 调用函数
     * 函数可以嵌套调用
     */
    @Test
    public void test4() {
        Long ss = (Long) AviatorEvaluator.execute("string.length('hello')");  // 5
        System.out.println(ss);
        Boolean xx = (Boolean)AviatorEvaluator.execute("string.contains(\"test\", string.substring('hello', 1, 2))");  // true
        System.out.println(xx);
    }


    /**
     * 调用函数
     * 函数可以嵌套调用
     */
    @Test
    public void test5() {
        //注册函数
        AviatorEvaluator.addFunction(new AddFunction());
        System.out.println(AviatorEvaluator.execute("add(1, 2)"));           // 3.0
        System.out.println(AviatorEvaluator.execute("add(add(1, 2), 100)")); // 103.0
    }

    /**
     * 提前编译
     * 表达式中使用了括号来强制优先级, 这个例子还使用了>用于比较数值大小, 比较运算符!=、==、>、>=、<、<=不仅可以用于数值,
     * 也可以用于String、Pattern、Boolean等等, 甚至是任何用户传入的两个都实现了java.lang.Comparable接口的对象之间
     */
    @Test
    public void test6() {
        String expression = "a-(b-c)>100";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("a", 100.3);
        env.put("b", 45);
        env.put("c", -199.100);
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);  // false

    }

    /**
     * 缓存编译结果
     */
    @Test
    public void test7() {
        //缓存编译结果，使用的时候会自动返回已经编译的结果
        AviatorEvaluator.compile("", true);
        //缓存编译结果，取消缓存
        //AviatorEvaluator.invalidateCache("");
        //注册函数
        AviatorEvaluator.addFunction(new AddFunction());
        System.out.println(AviatorEvaluator.execute("add(1, 2)"));           // 3.0
        System.out.println(AviatorEvaluator.execute("add(add(1, 2), 100)")); // 103.0
    }

    /**
     * 访问数组和集合
     * 可以通过中括号去访问数组和java.util.List对象, 可以通过map.key访问java.util.Map中key对应的value
     */
    @Test
    public void test8() {
        final List<String> list = new ArrayList<>();
        list.add("hello");
        list.add(" world");
        final int[] array = new int[3];
        array[0] = 0;
        array[1] = 1;
        array[2] = 3;
        final Map<String, Date> map = new HashMap<>();
        map.put("date", new Date());
        Map<String, Object> env = new HashMap<>();
        env.put("list", list);
        env.put("array", array);
        env.put("mmap", map);
        System.out.println(AviatorEvaluator.execute("list[0]+list[1]", env));   // hello world
        System.out.println(AviatorEvaluator.execute("'array[0]+array[1]+array[2]=' + (array[0]+array[1]+array[2])", env));  // array[0]+array[1]+array[2]=4
        System.out.println(AviatorEvaluator.execute("'today is ' + mmap.date ", env));  // today is Wed Feb 24 17:31:45 CST 2016
    }


    /**
     * 三目运算
     * Aviator 的三元表达式对于两个分支的结果类型并不要求一致,可以是任何类型,这一点与 java 不同
     */
    @Test
    public void test9() {
        String str = (String)AviatorEvaluator.exec("a>0? 'yes':'no'", 1);  // yes
        System.out.println(str);           // 3.0
    }


    /**
     * 正则表达式
     * email与正则表达式/([\\w0-8]+@\\w+[\\.\\w+]+)/通过=~操作符来匹配,结果为一个 Boolean 类 型,
     * 因此可以用于三元表达式判断,匹配成功的时候返回$1,指代正则表达式的分组 1,也就是用户名,否则返回unknown
     *
     * Aviator 在表达式级别支持正则表达式,通过//括起来的字符序列构成一个正则表达式,
     * 正则表达式可以用于匹配(作为=~的右操作数)、比较大小,匹配仅能与字符串进行匹配。匹配成功后,
     * Aviator 会自动将匹配成功的分组放入$num的变量中,其中$0 指代整个匹配的字符串,而$1表示第一个分组,以此类推
     */
    @Test
    public void test10() {
        String email = "killme2008@gmail.com";
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("email", email);
        String username = (String) AviatorEvaluator.execute("email=~/([\\w0-8]+)@\\w+[\\.\\w+]+/ ? $1 : 'unknow' ", env);
        System.out.println(username); // killme2008
    }


    /**
     * 语法糖
     * Aviator 有个方便用户使用变量的语法糖, 当你要访问变量a中的某个属性b, 那么你可以通过a.b访问到, 更进一步,
     * a.b.c将访问变量a的b属性中的c属性值, 推广开来也就是说 Aviator 可以将变量声明为嵌套访问的形式。
     * TestAviator类符合JavaBean规范, 并且是 public 的，我们就可以使用语法糖
     */
    @Test
    public void test11() {
        TestAviator foo = new TestAviator(100, 3.14f, new Date());
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("foo", foo);
        System.out.println(AviatorEvaluator.execute("'foo.i = '+foo.i", env));   // foo.i = 100
        System.out.println(AviatorEvaluator.execute("'foo.f = '+foo.f", env));   // foo.f = 3.14
        System.out.println(AviatorEvaluator.execute("'foo.date.year = '+(foo.date.year+1990)", env));  // foo.date.year = 2106
    }


    /**
     * nil
     * nil是 Aviator 内置的常量,类似 java 中的null,表示空的值。nil跟null不同的在于,
     * 在 java 中null只能使用在==、!=的比较运算符,而nil还可以使用>、>=、<、<=等比较运算符。
     * Aviator 规定,任何对象都比nil大除了nil本身。用户传入的变量如果为null,将自动以nil替代
     */
    @Test
    public void test12() {
        AviatorEvaluator.execute("nil == nil");   //true
        AviatorEvaluator.execute(" 3> nil");      //true
        AviatorEvaluator.execute(" true!= nil");  //true
        AviatorEvaluator.execute(" ' '>nil ");    //true
        AviatorEvaluator.execute(" a==nil ");     //true, a 是 null
    }

    /**
     * 日期比较
     * Aviator 并不支持日期类型,如果要比较日期,你需要将日期写字符串的形式,并且要求是形如 “yyyy-MM-dd HH:mm:ss:SS”的字符串,
     * 否则都将报错。 字符串跟java.util.Date比较的时候将自动转换为Date对象进行比较
     *
     * 也就是说String除了能跟String比较之外,还能跟nil和java.util.Date对象比较
     */
    @Test
    public void test13() {
        Map<String, Object> env = new HashMap<String, Object>();
        final Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(date);
        env.put("date", date);
        env.put("dateStr", dateStr);
        Boolean result = (Boolean) AviatorEvaluator.execute("date==dateStr", env);
        System.out.println(result);  // true
        result = (Boolean) AviatorEvaluator.execute("date > '2010-12-20 00:00:00:00' ", env);
        System.out.println(result);  // true
        result = (Boolean) AviatorEvaluator.execute("date < '2200-12-20 00:00:00:00' ", env);
        System.out.println(result);  // true
        result = (Boolean) AviatorEvaluator.execute("date==date ", env);
        System.out.println(result);  // true
    }

    /**
     * 大数计算和精度
     * 从 2.3.0 版本开始,aviator 开始支持大数字计算和特定精度的计算, 本质上就是支持java.math.BigInteger和java.math.BigDecimal两种类型,
     * 这两种类型在 aviator 中简称 为big int和decimal类型。 类似99999999999999999999999999999999这样的数字在 Java 语言里是没办法编译通过的,
     * 因为它超过了Long类型的范围, 只能用BigInteger来封装。但是 aviator 通过包装,可 以直接支持这种大整数的计算
     *
     * big int和decimal的表示与其他数字不同,两条规则:
     *
     * 以大写字母N为后缀的整数都被认为是big int,如1N,2N,9999999999999999999999N等, 都是big int类型。
     * 超过long范围的整数字面量都将自动转换为big int类型。
     * 以大写字母M为后缀的数字都被认为是decimal, 如1M,2.222M, 100000.9999M等, 都是decimal类型。
     * 用户也可以通过变量传入这两种类型来参与计算。
     */
    @Test
    public void test14() {
        System.out.println(AviatorEvaluator.exec("99999999999999999999999999999999 + 99999999999999999999999999999999"));
    }


    /**
     * big int和decimal的运算,跟其他数字类型long,double没有什么区别,操作符仍然是一样的。
     * aviator重载了基本算术操作符来支持这两种新类型
     *
     *
     * 类型转换和提升
     * 当big int或者decimal和其他类型的数字做运算的时候,按照long < big int < decimal < double的规则做提升, 也就是说运算的数字如果类型不一致, 结果的类型为两者之间更“高”的类型。例如:
     *
     * 1 + 3N, 结果为big int的4N
     * 1 + 3.1M,结果为decimal的4.1M
     * 1N + 3.1M,结果为decimal的 4.1M
     * 1.0 + 3N,结果为double的4.0
     * 1.0 + 3.1M,结果为double的4.1
     * decimal 的计算精度
     * Java 的java.math.BigDecimal通过java.math.MathContext支持特定精度的计算,任何涉及到金额的计算都应该使用decimal类型。
     *
     * 默认 Aviator 的计算精度为MathContext.DECIMAL128,你可以自定义精度, 通过
     * AviatorEvaluator.setMathContext(MathContext.DECIMAL64);
     */
    @Test
    public void test15() {
        Object rt = AviatorEvaluator.exec("9223372036854775807100.356M * 2");
        System.out.println(rt + " " + rt.getClass());  // 18446744073709551614200.712 class java.math.BigDecimal
        rt = AviatorEvaluator.exec("92233720368547758074+1000");
        System.out.println(rt + " " + rt.getClass());  // 92233720368547759074 class java.math.BigInteger
        BigInteger a = new BigInteger(String.valueOf(Long.MAX_VALUE) + String.valueOf(Long.MAX_VALUE));
        BigDecimal b = new BigDecimal("3.2");
        BigDecimal c = new BigDecimal("9999.99999");
        rt = AviatorEvaluator.exec("a+10000000000000000000", a);
        System.out.println(rt + " " + rt.getClass());  // 92233720368547758089223372036854775807 class java.math.BigInteger
        rt = AviatorEvaluator.exec("b+c*2", b, c);
        System.out.println(rt + " " + rt.getClass());  // 20003.19998 class java.math.BigDecimal
        rt = AviatorEvaluator.exec("a*b/c", a, b, c);
        System.out.println(rt + " " + rt.getClass());  // 2.951479054745007313280155218459508E+34 class java.math.BigDecimal
    }


    /**
     * 强大的 seq 库
     * aviator 拥有强大的操作集合和数组的 seq 库。整个库风格类似函数式编程中的高阶函数。在 aviator 中,
     * 数组以及java.util.Collection下的子类都称为seq,可以直接利用 seq 库进行遍历、过滤和聚合等操作。
     *
     * 求长度: count(list)
     * 求和: reduce(list,+,0), reduce函数接收三个参数,第一个是seq,第二个是聚合的函数,如+等,第三个是聚合的初始值
     * 过滤: filter(list,seq.gt(9)), 过滤出list中所有大于9的元素并返回集合; seq.gt函数用于生成一个谓词,表示大于某个值
     * 判断元素在不在集合里: include(list,10)
     * 排序: sort(list)
     * 遍历整个集合: map(list,println), map接受的第二个函数将作用于集合中的每个元素,这里简单地调用println打印每个元素
     */
    @Test
    public void test16() {
        Map<String, Object> env = new HashMap<String, Object>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(3);
        list.add(20);
        list.add(10);
        env.put("list", list);
        Object result = AviatorEvaluator.execute("count(list)", env);
        System.out.println(result);  // 3
        result = AviatorEvaluator.execute("reduce(list,+,0)", env);
        System.out.println(result);  // 33
        result = AviatorEvaluator.execute("filter(list,seq.gt(9))", env);
        System.out.println(result);  // [10, 20]
        result = AviatorEvaluator.execute("include(list,10)", env);
        System.out.println(result);  // true
        result = AviatorEvaluator.execute("sort(list)", env);
        System.out.println(result);  // [3, 10, 20]
        AviatorEvaluator.execute("map(list,println)", env);
    }


    /**
     *运行模式
     */
    @Test
    public void test17() throws Exception{
        //默认 AviatorEvaluator 以执行速度优先:
        AviatorEvaluator.setOptimize(AviatorEvaluator.EVAL);
        //你可以修改为编译速度优先,这样不会做编译优化:
        AviatorEvaluator.setOptimize(AviatorEvaluator.COMPILE);

        //从 2.1.1.版本开始,Aviator允许设置输出每个表达式生成的字节码,只要设置trace为true即可:
        AviatorEvaluator.setTrace(true);
        //方便用户做跟踪和调试。默认是输出到标准输出,你可以改变输出指向:
        AviatorEvaluator.setTraceOutputStream(new FileOutputStream(new File("aviator.log")));
    }




}
