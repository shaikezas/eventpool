package com.eventpool.common.commands;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Command {
	/**
	 * Suggestion for a Server that this commands may be run in asynchronous way.
	 * <br>
	 * If true than {@link CommandHandler} must return void - otherwise Serwer will throw an exception 
	 * @return
	 */
    boolean asynchronous() default false;

    /**
     * Suggestion for a Server that this commands should checked if the same commands is sent again.<br>
     * If true than commands class must implement equals and hashCode
     * @return
     */
    boolean unique() default false;

    /**
     * If unique is true than this property may specify maximum timeout in miliseconds before same commands can be executed
     * @return
     */
    long uniqueStorageTimeout() default 0L;
}
