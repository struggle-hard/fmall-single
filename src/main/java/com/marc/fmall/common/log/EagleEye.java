package com.marc.fmall.common.log;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) //注解的生命周期
@Target(ElementType.METHOD) //该注解作用于修饰方法
@Documented //表明这个注解应该被javadoc记录
public @interface EagleEye {

    /**
     * 接口描述
     * @return
     */
    String desc() default "";
}
