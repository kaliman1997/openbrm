package in.saralam.sbs.server.rating;

import java.math.BigDecimal;
import java.util.Date;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import java.util.List;

public interface IGRatingSessionBean {
	public List<CRatingResult> rateItem(Integer itemId, BigDecimal quantity)
		throws SessionInternalError;

	public void rateOrder(OrderDTO order)
		throws SessionInternalError;

 	public List<CRatingResult> rateRecord(RatingRecord ratingRecord)
                throws SessionInternalError;
          

	public Integer getProductForPreRated ( RatingRecord record) 
		throws SessionInternalError;

	public boolean isTaxable(Integer userId) 
		throws SessionInternalError;
}

