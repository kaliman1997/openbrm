
package in.saralam.sbs.server.pricing.db;

import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.joda.time.DateMidnight;
import org.hibernate.Criteria;
import java.util.Collections;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class PurchasedBundleProductDAS extends AbstractDAS<PurchasedBundleProductDTO> {
    private static final Logger LOG = Logger.getLogger(PurchasedBundleProductDTO.class);

  public List<PurchasedBundleProductDTO>  findByPurchaseId( Integer id){
 
   Criteria  criteria= getSession().createCriteria(PurchasedBundleProductDTO.class);
              criteria.add(Restrictions.eq("pbId", id));
              return criteria.list();
}
}