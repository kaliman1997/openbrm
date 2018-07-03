package in.saralam.sbs.batch.mediation;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;

import com.sapienter.jbilling.batch.ageing.AgeingProcessUserStatusProcessor;
import com.sapienter.jbilling.batch.ageing.AgeingStatusResult;
import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.process.IBillingProcessSessionBean;
import com.sapienter.jbilling.server.util.Context;

public class MediationProcessUsageProcessor implements InitializingBean, ItemProcessor<Integer, AgeingStatusResult> {
private static final FormatLogger LOG = new FormatLogger(Logger.getLogger(AgeingProcessUserStatusProcessor.class));
	
	private IBillingProcessSessionBean local = null;
	
	private Date ageingDate;
	private Integer entityId;
	
	/**
	 * Gets BillingProcessSessionBean bean from context for processor use  
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		local = (IBillingProcessSessionBean)
                Context.getBean(Context.Name.BILLING_PROCESS_SESSION);
	}
	
	/**
	 * Gets users id from reader and then done status reviewing
	 */
	@Override
	public AgeingStatusResult process(Integer userId) throws Exception {
        AgeingStatusResult result = new AgeingStatusResult();
		LOG.debug("Review Status of the user # " + userId);
        result.setOverdueInvoices(local.reviewUserStatus(entityId, userId, ageingDate));
        result.setUserId(userId);
        return result;
	}
	
	/*
	 * setters
	 */
	public void setEntityId(String entityId) {
		this.entityId = Integer.parseInt(entityId);
	}
	
	public void setAgeingDate(Date billingDate) {
		this.ageingDate = billingDate;
	}

}
