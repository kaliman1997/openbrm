package in.saralam.sbs.server.pricing;
import com.sapienter.jbilling.common.CommonConstants;
import org.apache.log4j.Logger;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.user.UserBL;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDAS;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.sapienter.jbilling.server.item.db.ItemPriceDAS;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import in.saralam.sbs.server.pricing.PackageProductWS;
import com.sapienter.jbilling.server.util.Constants;
public class PackagePriceTypeBL {
 private static final Logger LOG = Logger.getLogger(PackagePriceTypeBL.class);
 public static Integer create( PackagePriceTypeDTO price) {
 PackagePriceTypeDAS priceDas = new PackagePriceTypeDAS();
 PackagePriceTypeDTO newPrice = priceDas.save(price);
 LOG.debug(" saved  the dto");
 return newPrice.getId();
}
 public List<PackagePriceTypeDTO> getPackagePriceTypeDTO(PackageProductWS ws){
  List<PackagePriceTypeDTO> packagePriceTypeDTO =new ArrayList<PackagePriceTypeDTO>();
  if((ws.getOneTimeCbValue())!=null && (ws.getOneTimeCbValue()==Constants.ONE_TIME)){
      packagePriceTypeDTO.add(new PackagePriceTypeDAS().find(Constants.ONE_TIME));
     } 
       if((ws.getRecurringCbValue())!=null && ws.getRecurringCbValue()==Constants.RECURRING){
          packagePriceTypeDTO.add(new PackagePriceTypeDAS().find(Constants.RECURRING));
   } 
    
    
     return packagePriceTypeDTO;
}

public static void setDefaults(PackagePriceTypeDTO price) {
    	
    	
    	
    	
    }

    
}
