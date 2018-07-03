
package in.saralam.sbs.server.taxCode.db;

import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.joda.time.DateMidnight;

import java.util.Collections;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class TaxCodeDAS extends AbstractDAS<TaxCodeDTO> {
    private static final Logger LOG = Logger.getLogger(TaxCodeDTO.class);


    public List<TaxCodeDTO>   getTaxCodeDtO(){
    Criteria criteria = getSession().createCriteria(TaxCodeDTO.class);
    return criteria.list();
}

   public TaxCodeDTO  findTaxCodeDTOByTaxCode(String taxCode){
    Criteria criteria = getSession().createCriteria(TaxCodeDTO.class);
     criteria.add(Restrictions.eq("taxCode", taxCode));
   return findFirst(criteria);
}
}