package in.saralam.sbs.server.rating;

import java.math.BigDecimal;
import java.util.Date;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import java.util.List;

public interface IRatingSessionBean {


	IRatingEvent rateEvent(IRatingEvent event);

	Integer loadRatedEvent(IRatingEvent ratingEvent, Integer userId, Integer id);

		

}

