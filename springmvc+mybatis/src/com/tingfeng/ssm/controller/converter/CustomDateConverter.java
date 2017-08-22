package com.tingfeng.ssm.controller.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Custom date converter.
 * 自定义的日期转换器(需要实现)
 * String表示原始类型
 * Date表示转换成的类型
 */
public class CustomDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {

        //实现将字符串转换成日期（格式为yyyy-MM-dd HH:mm:ss）---根据jsp页面的要求来
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //如果参数绑定失败，就返回一个空
        return null;
    }
}
