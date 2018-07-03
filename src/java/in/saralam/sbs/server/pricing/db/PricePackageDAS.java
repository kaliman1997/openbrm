
package in.saralam.sbs.server.pricing.db;

import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Query;
import java.util.List;
import org.joda.time.DateMidnight;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDTO;

import java.util.Collections;
import java.util.List;

public class PricePackageDAS extends AbstractDAS<PricePackageDTO> {
    private static final Logger LOG = Logger.getLogger(PricePackageDTO.class);
	
	 /**
     * Returns the list of linked orders by given Primary Order Id
     *
     * @param primaryOrderId
     * @return List<OrderDTO> - List of linked orders for the given Primary Order
     */
    @SuppressWarnings("unchecked")
    public List<PricePackageDTO> findByPrimaryBundleId(Integer primaryBundleId) {
        Criteria criteria = getSession().createCriteria(PricePackageDTO.class)
                .add(Restrictions.eq("parentBundle.id", primaryBundleId));
                

        return criteria.list();
    }
}