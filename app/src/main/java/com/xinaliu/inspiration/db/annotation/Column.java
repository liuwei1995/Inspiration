package com.xinaliu.inspiration.db.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.FIELD })
public @interface Column {
	/**
	 * 列名
	 * 
	 * @return
	 */
	String name();

	String type() default "";

	int length() default 0;

    /**
	 * Whether to null
	 * 是否为null
	 * @return true
	 */
	boolean isNULL() default true;

}