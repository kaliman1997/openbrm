package com.sapienter.jbilling.batch.ageing;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.batch.item.ItemWriter;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.util.Constants;

/**
 * 
 * @author Khobab
 *
 */
public class AgeingProcessWriter implements ItemWriter<Integer> {
	
	private static final FormatLogger logger = new FormatLogger(Logger.getLogger(AgeingProcessWriter.class));
	
	@Override
	public void write(List<? extends Integer> userList) throws Exception {
		for(Integer userId : userList)
		{
			logger.debug("UserId # " + userId + " +++ Read, Processed & Written Successfully!");			
		}
	}	
}
