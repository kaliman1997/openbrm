package in.saralam.sbs.server.pricing.db;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.joda.time.DateMidnight;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import java.util.Collections;
import java.util.List;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

public class PackageProductDAS extends AbstractDAS<PackageProductDTO> {
    private static final Logger LOG = Logger.getLogger(PackageProductDTO.class);
  public PackageProductDTO   getPackageProductByPackageId(PricePackageDTO pricePackage, Integer productId){
    Criteria criteria = getSession().createCriteria(PackageProductDTO.class);
	criteria.add(Restrictions.eq("productId",productId));
    criteria.add(Restrictions.eq("pricePackage", pricePackage));
    return findFirst(criteria);
}

}
