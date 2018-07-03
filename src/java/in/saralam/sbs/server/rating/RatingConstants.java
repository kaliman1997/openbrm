package in.saralam.sbs.server.rating;
import com.sapienter.jbilling.common.CommonConstants;
import com.sapienter.jbilling.server.util.Constants;

public final class RatingConstants implements CommonConstants {
  
    //rateTierDetails : Quantity Discounty Bracket constants
    public static final int QTY_DISCOUNT_BRACKET_NONE = 0;
    public static final int QTY_DISCOUNT_BRACKET_RESOURCE_BASED = 1;
    public static final int QTY_DISCOUNT_BRACKET_CONTINUOUS = 2;
    public static final int QTY_DISCOUNT_BRACKET_RATE_DEPENDENT = 3;
    public static final int SBS_RATING_RESULT_SUCCESS = 1;
    public static final int SBS_RATING_RESULT_FAIL = 0;
    public static final int SBS_RATING_RESULT_NO_BALANCE = 2;
    public static final int SBS_RATING_RESULT_RATE_NOTFOUND = 3;

    public static final int PLUGGABLE_TASK_RATING_PROCESS = 26;
 
}

