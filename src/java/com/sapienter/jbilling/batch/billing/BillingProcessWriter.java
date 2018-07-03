package com.sapienter.jbilling.batch.billing;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.process.ConfigurationBL;
import com.sapienter.jbilling.server.process.IBillingProcessSessionBean;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Context;

/**
 * 
 * @author Khobab
 *
 */
public class BillingProcessWriter implements ItemWriter<Integer> {
	
	private static final FormatLogger logger = new FormatLogger(Logger.getLogger(BillingProcessWriter.class));
	
	@Override
	public void write(List<? extends Integer> list) throws Exception {
		for(Integer user : list){
			logger.debug("User # " + user + "was successfully processed");
		}
	}
}
