package com.tingfeng.ssm.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Custom exception resolver.
 * 全局异常处理器
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

    @Override
    // Exception ex表示系统抛出的异常（在这里截获）
    public org.springframework.web.servlet.ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) {
        //handler就是处理适配器执行的handler对象（只有method）

//        //错误信息
//        String messange = null;
//        //解析出异常类型
//        //如果异常是系统自定义的异常，直接取出异常信息，在错误页面展示
//        if (ex instanceof CustomException) {
//            messange = ex.getMessage();
//        } else {
//            //如果不是自定义异常，构造一个自定义异常类型（信息为：位置错误）
//            messange = "未知错误";
//        }

//        上边的代码变为
        CustomException customException = null;
        if (ex instanceof CustomException) {
            customException = (CustomException) ex;
        } else {
            //如果不是自定义异常，构造一个自定义异常类型（信息为：位置错误）
            customException = new CustomException("未知错误");
        }

        //错误信息
        String message = customException.getMessage();

        //j将错误信息传到页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", message);

        //指向错误页面
        modelAndView.setViewName("error");

        return modelAndView;
    }
}
