package com.how2java.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class HandlerSysException implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        SysException sys=null;
        if(e instanceof SysException){
            sys=(SysException)e;
        }
        else
        {
            sys=new SysException("请联系网站管理员");
        }
        ModelAndView view=new ModelAndView();
        view.addObject("err",sys.getMessage());
        view.setViewName("err");
        return view;
    }
}
