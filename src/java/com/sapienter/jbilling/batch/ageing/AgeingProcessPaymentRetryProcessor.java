package com.sapienter.jbilling.batch.ageing;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.payment.IPaymentSessionBean;
import com.sapienter.jbilling.server.util.Context;

/**
 * 
 * @author Khobab
 *
 */
public class AgeingProcessPaymentRetryProcessor implements InitializingBean, ItemProcessor<AgeingStatusResult, Integer> {

	private static final FormatLogger LOG = new FormatLogger(Logger.getLogger(AgeingProcessPaymentRetryProcessor.class));
	
	private IPaymentSessionBean paymentSessionBean = null;
	
	/**
	 * Gets PaymentSessionBean class from context for use of processor. 
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		 paymentSessionBean = (IPaymentSessionBean)
	                Context.getBean(Context.Name.PAYMENT_SESSION);
	}
	
	/**
	 * gets user id from item reader one by one
	 * and retry payment on each id
	 */
	@Override
	public Integer process(AgeingStatusResult ageingStatusResult) throws Exception {
		LOG.debug("Retrying payment of user with status # " + ageingStatusResult);
		paymentSessionBean.doPaymentRetry(ageingStatusResult.getUserId(),
                ageingStatusResult.getOverdueInvoices());

		return ageingStatusResult.getUserId();
	}
	
}
