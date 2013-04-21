package com.eventpool.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EmailAddressValidation {

	String message() default "Email address is invalid";
}
