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
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDTO;
public class PackagePriceDAS extends AbstractDAS<PackagePriceDTO> {
  private static final Logger LOG = Logger.getLogger(PackagePriceDTO.class);
  public PackagePriceDTO  findPackagePriceByProdcut(PackageProductDTO prodcuct,PackagePriceTypeDTO type){
  Criteria criteria = getSession().createCriteria(PackagePriceDTO.class);
  criteria.add(Restrictions.eq("packageProduct", prodcuct));
  criteria.add(Restrictions.eq("packagePriceType", type));
  return findFirst(criteria);

}



}