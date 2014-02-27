package com.eventpool.common.dto;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DTOValidator {

	/**
	 * Validate order/suborder commands for JSR constraints
	 * @param command
	 */
	
    @Autowired
    private Validator validator;

	public void validate(Object command) throws ConstraintViolationException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(command, Default.class);  
        int count = constraintViolations.size();  
        //logger.debug("Total voilations: "+count);
        Iterator<ConstraintViolation<Object>> it = constraintViolations.iterator();
        String errorMsg = "";
        while(it.hasNext()) {  
         ConstraintViolation<Object> cv = it.next();
         errorMsg = errorMsg + cv.getMessage() + "\n";
        // logger.debug("Violated constraint message:" + cv.getMessage());  
        }          
        if(count > 0)
        	throw new ConstraintViolationException(errorMsg, null);		
	}
	
}
