package com.tingfeng.ssm.controller;

import com.tingfeng.ssm.po.ItemsCustom;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The type Json test.
 * json数据交互测试
 */
@Controller
public class JsonTest {
    //请求json，输出相应json
    @RequestMapping(value = "/requestJson")
    //@RequestBody表示将请求的json串转换成java对象
    //@ResponseBody表示要将返回的java对象(itemsCustom)转成json串
    public @ResponseBody ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom) {
        return itemsCustom;
    }

    //请求的是key/value，所以说，不需要@RequestBody了
    @RequestMapping(value = "/responseJson")
    public @ResponseBody ItemsCustom responseJson(ItemsCustom itemsCustom) {
        return itemsCustom;
    }
}
