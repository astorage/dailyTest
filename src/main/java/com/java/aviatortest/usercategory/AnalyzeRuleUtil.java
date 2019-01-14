package com.java.aviatortest.usercategory;

import com.java.util.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Boris
 * Date: 2018/12/24 18:02
 * Description:
 */
public class AnalyzeRuleUtil {
    private static final Logger logger = LoggerFactory.getLogger(AnalyzeRuleUtil.class);

    /**
     * 括号常量
     */
    private static final char LEFT_PARENTHESIS='(',  RIGHT_PARENTHESIS=')', LEFT_BRACKET='[',  RIGHT_BRACKET=']';
    /**
     * 空字符串
     */
    private static final String EMPTY_STR = "";
    /**
     * 标点符号常量
     */
    private static final String COMMA = ",";
    /**
     * null字符串
     */
    private static final String NULL = "null", FALSE = "false";

    /**
     * 运算符常量
     */
    private static final String OPERATOR1=">=", OPERATOR2=">", OPERATOR3="<=", OPERATOR4="<",  OPERATOR5="==", AND=" && ", OR=" || ", NO="!", TERNARY="==nil ? false : true";

    /**
     * 带减法的规则解析
     * @param rule
     * @return
     * @throws Exception
     */
    public static String analyze(String rule){
        try {
            String[] ruleArray = rule.split(NO);
            List<String> resultRuleList = new ArrayList<>();
            for (String sub : ruleArray) {
                if (StringUtils.isEmpty(sub) || sub.equals("[]")){
                    throw new Exception("人群规则不能为空");
                }
                String subRule = analyzeRule(sub);
                subRule = LEFT_PARENTHESIS + subRule + RIGHT_PARENTHESIS;
                resultRuleList.add(subRule);
            }
            return String.join(AND  + NO, resultRuleList);
        }catch (Exception e) {
            logger.error("解析规则异常" , e);
        }
        return null;
    }

    /**
     * 解析一个规则
     * @param rule
     * @return
     * @throws Exception
     */
    private static String analyzeRule(String rule) throws Exception{
        List<String> list = new ArrayList<>();
        List<Map<String, String>> tagList = JacksonUtil.deserialize(rule, List.class);
        StringBuilder cExp = new StringBuilder();

        for (Map<String, String> map : tagList) {
            if (map.keySet().iterator().next().startsWith("C")) {
                handleCTag(map, cExp);
                continue;
            }
            String normalTag = handleNormalTag(map);
            list.add(normalTag);
        }
        if (cExp.toString().endsWith(OR)) {
            cExp.delete(cExp.lastIndexOf(OR), cExp.length());
            list.add(cExp.toString());
        }
        return String.join(AND, list);
    }

    /**
     * 解析普通标签
     * @param map
     * @return
     */
    private static String handleNormalTag(Map<String, String> map) {
        StringBuilder exp = new StringBuilder();
        if (map.size() > 1) {
            exp.append(LEFT_PARENTHESIS);
        }
        String value = map.values().iterator().next();
        if (StringUtils.isEmpty(value)) {
            for (String key : map.keySet()) {
                exp.append(LEFT_PARENTHESIS);
                exp.append(key).append(OPERATOR5).append(FALSE).append(OR).append(key).append(TERNARY);
                exp.append(RIGHT_PARENTHESIS);
                exp.append(OR);
            }
        }else {
            handleStatisticTag(exp, map.entrySet().iterator().next());
            exp.append(OR);
        }
        exp.delete(exp.lastIndexOf(OR), exp.length());
        if (map.size() > 1) {
            exp.append(RIGHT_PARENTHESIS);
        }
        return exp.toString();
    }

    /**
     * 解析C类标签
     * @param map
     * @param cExp
     */
    private static void handleCTag(Map<String, String> map,  StringBuilder cExp) {
        if (map.size() > 1) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                cExp.append(LEFT_PARENTHESIS);
                cExp.append(entry.getKey()).append(OPERATOR5).append(FALSE).append(OR).append(entry.getKey()).append(TERNARY);
                cExp.append(RIGHT_PARENTHESIS);
                cExp.append(OR);
            }
        }else {
            handleStatisticTag(cExp, map.entrySet().iterator().next());
            cExp.append(OR);
        }
    }


    /**
     * 将[1, 5] 或者 (1, 5)转换成(tagkey>=1 && tagkey<=5) 或者(tagkey>1 && tagkey<5)
     * @param cSqlCondition
     * @param tagEntry
     * @return
     */
    private static void handleStatisticTag(StringBuilder cSqlCondition, Map.Entry<String, String> tagEntry) {
        String leftOperator = EMPTY_STR, rightOperator = EMPTY_STR;
        char left = LEFT_PARENTHESIS, right = RIGHT_PARENTHESIS;
        String tagValue = tagEntry.getValue();
        if (tagValue.indexOf(LEFT_PARENTHESIS) != -1) {
            leftOperator = OPERATOR2;
            left = LEFT_PARENTHESIS;
        }else if (tagValue.indexOf(LEFT_BRACKET) != -1) {
            leftOperator = OPERATOR1;
            left = LEFT_BRACKET;
        }
        if (tagValue.indexOf(RIGHT_PARENTHESIS) != -1) {
            rightOperator = OPERATOR4;
            right = RIGHT_PARENTHESIS;
        }else if (tagValue.indexOf(RIGHT_BRACKET) != -1) {
            rightOperator = OPERATOR3;
            right = RIGHT_BRACKET;
        }

        String subTagValue = tagValue.substring(tagValue.indexOf(left) + 1, tagValue.indexOf(right));
        String[] array = subTagValue.split(COMMA);
        for (int i=0; i<array.length; i++) {
            array[i] = array[i].trim();
        }
        cSqlCondition.append(LEFT_PARENTHESIS);
        if (StringUtils.isNotEmpty(array[0])&&!NULL.equals(array[0].toLowerCase())) {
            cSqlCondition.append(tagEntry.getKey()).append(leftOperator).append(array[0]);
            cSqlCondition.append(AND);
        }
        if (StringUtils.isNotEmpty(array[1])&&!NULL.equals(array[1].toLowerCase())) {
            cSqlCondition.append(tagEntry.getKey()).append(rightOperator).append(array[1]);
        }
        if (cSqlCondition.toString().endsWith(AND)) {
            cSqlCondition.delete(cSqlCondition.lastIndexOf(AND), cSqlCondition.length());
        }
        cSqlCondition.append(RIGHT_PARENTHESIS);
    }


}
