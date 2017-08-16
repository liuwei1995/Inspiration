package com.xinaliu.inspiration.db.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.TYPE })
public @interface Table {
	/**
	 * 表名字
	 * 
	 * @return
	 */
	 abstract String name();
}