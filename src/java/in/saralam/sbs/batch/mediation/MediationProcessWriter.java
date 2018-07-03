package in.saralam.sbs.batch.mediation;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import com.sapienter.jbilling.batch.ageing.AgeingProcessWriter;
import com.sapienter.jbilling.common.FormatLogger;

public class MediationProcessWriter implements ItemWriter<Integer> {
	
	private static final FormatLogger logger = new FormatLogger(Logger.getLogger(AgeingProcessWriter.class));
	
	@Override
	public void write(List<? extends Integer> userList) throws Exception {
		for(Integer userId : userList)
		{
			logger.debug("UserId # " + userId + " +++ Read, Processed & Written Successfully!");			
		}
	}	
}
