package com.java.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by chauncey on 16/5/11.
 */
public class JacksonUtil {

    private static final Logger logger = Logger.getLogger(JacksonUtil.class);

    /**
     * 精简输出Mapper
     */
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
        //空值不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }


    /**
     * 异常必须抛给上层处理，否则可能会出现很多坑爹问题
     */
    public static String serialize(Object serialized) throws Exception {
        if (serialized == null) {
            return null;
        }
        return objectMapper.writeValueAsString(serialized);
    }

    /**
     * 使用JSON格式化数据
     */
    public static String serializeWithPretty(Object serialized) throws Exception {
        if (serialized == null) {
            return null;
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(serialized);
    }

    public static String serializeIgnoreException(Object serialized) {
        try {
            return serialize(serialized);
        } catch (Exception e) {
            logger.error(" serializeIgnoreException execute error ", e);
        }
        return null;
    }


    public static <T> T deserialize(String jsonStr, Class<T> transferClass) throws Exception {
        if (StringUtils.isEmpty(jsonStr) || transferClass == null) {
            return null;
        }
        return objectMapper.readValue(jsonStr, transferClass);
    }

    public static <T> T deserializeIgnoreException(String jsonStr, Class<T> transferClass) {
        try {
            if (StringUtils.isEmpty(jsonStr) || transferClass == null) {
                return null;
            }
            return objectMapper.readValue(jsonStr, transferClass);
        } catch (Exception e) {
            logger.error(" deserialize use JacksonUtils error , jsonStr is : " + jsonStr, e);
        }
        return null;
    }


    public static <T> List<T> deserializeList(String jsonStr, TypeReference reference) throws Exception{
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        return objectMapper.readValue(jsonStr, reference);
    }

    public static <T> List<T> deserializeListIgnoreException(String jsonStr, TypeReference reference) {
        try {
            if (StringUtils.isEmpty(jsonStr)) {
                return null;
            }
            return objectMapper.readValue(jsonStr, reference);
        } catch (Exception e) {
            logger.error(" deserialize use JacksonUtils error , jsonStr is : " + jsonStr, e);
        }
        return null;
    }

}
