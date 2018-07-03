package in.saralam.sbs.server.voucher.db;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.log4j.Logger;
import java.io.*;
import java.sql.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.*;
import com.sapienter.jbilling.common.Util;
import in.saralam.sbs.server.voucher.db.VoucherStatusDAS;
import in.saralam.sbs.server.voucher.db.VoucherStatusDTO;
import in.saralam.sbs.server.voucher.db.VoucherDTO;

import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class VoucherDAS extends AbstractDAS<VoucherDTO>{

private static final Logger LOG = Logger.getLogger(VoucherDAS.class);



	private static final String findInStatusSQL = 
         "SELECT a " + 
         "  FROM voucher a " + 
         " WHERE a.voucherStatus.id = :status " +
         "   AND a.pinCode.id = :pinCode " ;
          

     private static final String findNotInStatusSQL =
         "SELECT a " +
         "  FROM voucher a " + 
         " WHERE a.voucherStatus.id <> :status " +
         "   AND a.company.id = :entity ";
     
      final String findPinByIdSQL = 
    		 "SELECT b" + 
     "FROM voucher b" + 
     " WHERE b.pin_code =  pinCode ";
    
    
    

@SuppressWarnings("unchecked")
	public List<VoucherDTO> findBySerialNo(Integer productId,Integer serialNo) {
		Criteria criteria = getSession().createCriteria(VoucherDTO.class);
				criteria.add(Restrictions.eq("productId", productId));
				criteria.add(Restrictions.eq("serialNo", serialNo));
				
	return criteria.list();
	}
	
	 @SuppressWarnings("unchecked")
	    public VoucherDTO findCustomerByPin(String pinCode) {
	        Criteria criteria = getSession().createCriteria(VoucherDTO.class);
	             criteria.add(Restrictions.eq("pinCode", pinCode));
					
	        return (VoucherDTO) criteria.uniqueResult();
	    }
}