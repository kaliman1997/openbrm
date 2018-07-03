package com.sapienter.jbilling.batch;

import org.apache.log4j.Logger;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.common.SessionInternalError;

/**
 * 
 * @author Khobab
 *
 */
public class ExceptionSkipPolicy implements SkipPolicy {
	
	private static final FormatLogger LOG = new FormatLogger(Logger.getLogger(ExceptionSkipPolicy.class));

	@Override
	public boolean shouldSkip(Throwable exception, int skipCpunt)
			throws SkipLimitExceededException {
		LOG.error("Skipping processing of user, exception:",exception);
		return true;
	}
}
