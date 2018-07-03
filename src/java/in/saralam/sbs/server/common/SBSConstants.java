package in.saralam.sbs.server.common;

import com.sapienter.jbilling.common.CommonConstants;
import com.sapienter.jbilling.server.util.Constants;

public final class SBSConstants implements CommonConstants {
  
    public static final String TABLE_DEFERRED_ACTION_STATUS = "deferred_action_status";
    public static final Integer DEFERRED_ACTION_STATUS_PENDING = new Integer(1);
    public static final Integer DEFERRED_ACTION_STATUS_DONE = new Integer(2);
    public static final Integer DEFERRED_ACTION_STATUS_ERROR = new Integer(3);
    public static final Integer PLUGGABLE_TASK_MEDIATION_PROCESS_FILTER = new Integer(100);
 
}
