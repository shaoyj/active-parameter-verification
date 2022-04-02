package com.mylomen.params;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: Shaoyongjun
 * @date: 2020/9/3
 * @time: 10:21 AM
 * @copyright
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface XxqParams {


    /**
     * 组
     *
     * @return
     */
    XxqParamsGroup[] groups() default {};

    /**
     * map 类型参数专用
     *
     * @return
     */
    String key() default "";

    /**
     * 不为空
     *
     * @return
     */
    String noNull() default "";

    String noEmpty() default "";

    String noBlank() default "";

    String noNullAndZero() default "";

    String minLength() default "";

    String maxLength() default "";

    String maxValue() default "";

    String minValue() default "";

    /**
     * Defines several {@code @NotEmpty} constraints on the same element.
     *
     * @see XxqParams
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    public @interface MapList {
        XxqParams[] value();
    }
}
