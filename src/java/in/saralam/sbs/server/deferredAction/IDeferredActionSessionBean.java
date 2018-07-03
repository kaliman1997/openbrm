package in.saralam.sbs.server.deferredAction;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sapienter.jbilling.common.InvalidArgumentException;
//import com.sapienter.jbilling.server.mediation.db.*;
//import com.sapienter.jbilling.server.mediation.task.IMediationProcess;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import in.saralam.sbs.server.deferredAction.action.*;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import in.saralam.sbs.server.deferredAction.db.DeferredActionDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
public interface IDeferredActionSessionBean {

    public void trigger(Integer entityId);
	public void create(IDeferredAction deferredAction);
    public void create(IDeferredAction deferredAction,Date aud, Integer entity,UserDTO user);
    public void delete(Integer id);
    public void updateStatus(Integer objectId, Integer newStatus);
    public void processBatch( List<DeferredActionDTO> actions);
    public void execute(IDeferredAction deferredAction);


}

