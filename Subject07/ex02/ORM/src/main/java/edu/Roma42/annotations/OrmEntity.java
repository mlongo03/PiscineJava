package edu.Roma42.annotations;

import java.lang.annotation.Retention;

import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface OrmEntity {
	String table();
}
