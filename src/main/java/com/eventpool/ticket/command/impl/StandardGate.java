/**
 * 
 */
package com.eventpool.ticket.command.impl;

import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eventpool.ticket.command.Gate;
import com.eventpool.ticket.commands.Command;



@Component
public class StandardGate implements Gate {
	
	@Resource
	private RunEnvironment runEnvironment;
	
	//@Inject
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	private static final Logger log = LoggerFactory.getLogger(StandardGate.class);


    static{
    }

	public Object dispatch(Object command) throws Exception{
        Object response = null;
		log.trace("Received Command in the Gate " + command);
        try{
            //Validate the commands
            Set<ConstraintViolation<Object>> constraintViolations =  validator.validate(command);
            if(constraintViolations.size() > 0){
                StringBuilder errMsg = new StringBuilder();
                errMsg.append("[");
                for(ConstraintViolation<Object> vio : constraintViolations){
                    errMsg.append(vio.getPropertyPath() + ":" + vio.getMessage()+",");
                }
                errMsg.append("]");
                log.error("Validations Failed: " + errMsg.toString() + " on " + command);
                throw new RuntimeException(errMsg.toString());
            }
            response = runEnvironment.run(command);
        }finally{
        }
        return response;
	}

	/**
	 * @param command
	 * @return
	 */
	private boolean isAsynchronous(Object command) {
		if (! command.getClass().isAnnotationPresent(Command.class))
			return false;
		
		Command commandAnnotation = command.getClass().getAnnotation(Command.class);		
		return commandAnnotation.asynchronous();		
	}
	
}
