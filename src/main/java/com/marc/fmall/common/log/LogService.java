package com.marc.fmall.common.log;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志切面
 */
@Slf4j
@Component
@Aspect
public class LogService {



    /**
     * 切到所有@EagleEye修饰的方法
     */
    @Pointcut("@annotation(com.marc.fmall.common.log.EagleEye)")
    public void eagleEye(){

    }

    @Around("eagleEye()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //请求开始时间
        long begin = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();


        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        EagleEye eagleEye = method.getAnnotation(com.marc.fmall.common.log.EagleEye.class);

        //接口描述信息
        String desc = eagleEye.desc();
        log.info("==================请求开始==================");
        //请求链接
        log.info("请求链接：{}",request.getRequestURL().toString());
        //接口描述信息
        log.info("接口描述信息：{}",desc);
        //请求类型
        log.info("请求类型：{}",request.getMethod());
        //请求方法
        log.info("请求方法：{}",signature.getDeclaringTypeName(),((MethodSignature) signature).getMethod());
        //请求Ip
        log.info("请求IP:{}",request.getRemoteAddr());
        //请求入参
        log.info("请求入参：{}", JSON.toJSONString(pjp.getArgs()));
        
        log.info("请求入参：{}", pjp.getArgs());

        Object result = pjp.proceed();

        //请求结束时间戳
        long end = System.currentTimeMillis();
        //请求耗时
        log.info("请求耗时：{}ms",end-begin);
        //请求返回
        log.info("请求返回：{}",JSON.toJSONString(result));
        //请求结束
        log.info("==================请求结束==================");

        return result;
    }
}
