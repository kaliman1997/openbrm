package in.saralam.sbs.batch.mediation;

import java.util.Date;

import org.hibernate.ScrollableResults;

public interface IMediationProcessFilterTask {
	
	public ScrollableResults findUsersToProcess(Integer entityId, Date billingDate);

}
