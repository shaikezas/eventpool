/**
 * 
 */
package com.eventpool.ticket.command.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.exceptions.UncheckedException;
import com.eventpool.ticket.command.audit.AuditLogger;
import com.eventpool.ticket.command.handler.CommandHandler;
import com.eventpool.ticket.core.ErrorCodes;


/**
 */
@Component
public class RunEnvironment {

	@Resource
	AuditLogger auditLogger;

	private static final Logger log = LoggerFactory.getLogger(RunEnvironment.class);

	public interface HandlersProvider {
		CommandHandler<Object, Object> getHandler(Object command);
	}

	@Resource
	private HandlersProvider handlersProvider;

	public Object run(Object command) throws Exception {
		Object result = null;
		Exception failureException = null;
		int maxRetries = 10;
		int numAttempts = 0;
		do {
			numAttempts++;
			try {
				result = runRetrying(command);
				return result;
			} catch (ConcurrencyFailureException cfe) {
				failureException = cfe;
				maxRetries = 10;
				log.error("ConcurrencyFailureException: Transaction failed for locking retrying again max({}), current({})", maxRetries, numAttempts);
			} catch(DataIntegrityViolationException dive){
				failureException = dive;
				// asume that this is caused by concurrency and retry only for 5 times
				maxRetries = 5;
				log.error("DataIntegrityViolationException: asuming it to be concurrency issue and retrying set to max({}), current({})", maxRetries, numAttempts);
				log.error("Reason: ",dive);
			} catch (Exception e) {
				log.error("failed to execute command, throwing exception", e);
				throw e;
			}
		} while (numAttempts < maxRetries);
		log.error("Failed after {} attempts to execute command after concurrency problem. throwing exception", numAttempts);
		throw new RuntimeException("Failed after " + numAttempts
				+ " attempts to execute command after concurrency problem", failureException);
	}

	@Transactional(value = "inventoryApiTransactionManager", propagation = Propagation.REQUIRES_NEW)
	private Object runRetrying(Object command) throws Exception {
		log.info("executing command: {}", command);
		String commandClassName = command.getClass().getSimpleName();
		CommandHandler<Object, Object> handler = handlersProvider
				.getHandler(command);
		if (handler == null) {
			throw new UncheckedException(ErrorCodes.COMMAND_HANDLER_NOT_FOUND,
					commandClassName);
		}

		Object result = null;
		result = handler.handle(command);

		return result;
	}

}
