package in.saralam.sbs.batch.mediation;

import java.util.Date;

import org.hibernate.ScrollableResults;

import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.process.db.BillingProcessDAS;
import com.sapienter.jbilling.server.process.task.IBillingProcessFilterTask;

public class BasicMediationProcessFilterTask extends PluggableTask implements IMediationProcessFilterTask {

    public ScrollableResults findUsersToProcess(Integer theEntityId, Date billingDate){        
        return new BillingProcessDAS().findUsersToProcess(theEntityId);              
    }

}
