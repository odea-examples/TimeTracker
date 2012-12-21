package com.odea.shiro;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.wicket.Page;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ShiroSecurityConstraint
{

	ShiroAction action() default ShiroAction.INSTANTIATE;

	ShiroConstraint constraint();

	// optional
	String loginMessage() default ""; // goes to INFO

	// optional
	Class<? extends Page> loginPage() default Page.class;

	String unauthorizedMessage() default ""; // goes to ERROR

	Class<? extends Page> unauthorizedPage() default Page.class;

	String value() default "";
}
