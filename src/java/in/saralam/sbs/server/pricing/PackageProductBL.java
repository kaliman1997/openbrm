package in.saralam.sbs.server.pricing;
import com.sapienter.jbilling.common.CommonConstants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.item.tasks.IItemPurchaseManager;
import com.sapienter.jbilling.server.invoice.InvoiceBL;
import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.mediation.Record;
import com.sapienter.jbilling.server.notification.INotificationSessionBean;
import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.notification.NotificationBL;
import com.sapienter.jbilling.server.notification.NotificationNotFoundException;
import com.sapienter.jbilling.server.order.db.OrderBillingTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDAS;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDAS;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.order.db.OrderProcessDTO;
import com.sapienter.jbilling.server.order.db.OrderStatusDAS;
import com.sapienter.jbilling.server.order.event.NewActiveUntilEvent;
import com.sapienter.jbilling.server.order.event.NewOrderEvent;
import com.sapienter.jbilling.server.order.event.NewQuantityEvent;
import com.sapienter.jbilling.server.order.event.NewStatusEvent;
import com.sapienter.jbilling.server.order.event.OrderDeletedEvent;
import com.sapienter.jbilling.server.order.event.PeriodCancelledEvent;
import com.sapienter.jbilling.server.pluggableTask.OrderProcessingTask;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskManager;
import in.saralam.sbs.server.pricing.db.PackageProductDAS;
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import in.saralam.sbs.server.pricing.db.PricePackageDAS;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import com.sapienter.jbilling.server.process.ConfigurationBL;
import com.sapienter.jbilling.server.process.db.PeriodUnitDAS;
import com.sapienter.jbilling.server.provisioning.db.ProvisioningStatusDAS;
import com.sapienter.jbilling.server.provisioning.event.SubscriptionActiveEvent;
import com.sapienter.jbilling.server.system.event.EventManager;
import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.PreferenceBL;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import javax.sql.rowset.CachedRowSet;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import in.saralam.sbs.server.pricing.PackageProductWS;
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceDTO;
import com.sapienter.jbilling.server.util.Constants;
public class PackageProductBL extends ResultList
        implements PricePackageSQL {

    private PackageProductDTO packageProduct = null;
    private PackageProductDAS packageProductDas = null;
    private PricePackageDTO pricePackageDto = null;
    private static final Logger LOG = Logger.getLogger(PricePackageBL.class);
    private EventLogger eLogger = null;

    public PackageProductBL(Integer productId) {
        init();
        set(productId);
    }

    public PackageProductBL() {
        init();
    }

    private void init() {
        eLogger = EventLogger.getInstance();
        packageProductDas = new PackageProductDAS();
        pricePackageDto = new PricePackageDTO();
        
    }

    public PackageProductDTO getEntity() {
        return packageProduct;
    }

    public void set(Integer id) {
    	packageProduct = packageProductDas.find(id);
    }

 
        public PackageProductDTO getDTO() {
        return packageProduct;
    }

    public Integer create(Integer pricePackageId, PackageProductDTO productDto) 
    							throws SessionInternalError {
    	PackageProductDTO newProduct = null;
        try {
            
            // create the record
        	PricePackageDAS pricepackageDas = new PricePackageDAS();
        	productDto.setPricePackage(pricepackageDas.find(pricePackageId));
        	newProduct = packageProductDas.save(productDto);

          
            
        } catch (Exception e) {
            throw new SessionInternalError("Create exception creating PackageProduct entity bean", PackageProductBL.class, e);
        }

        return newProduct.getId();
    }
public Integer update(PackageProductDTO productDto,Integer   id) 
    							throws SessionInternalError {
	Integer prodId=null;
    
   PricePackageDTO  price  = new PricePackageDAS().find(id);
     if(price!= null ){
          PackageProductDTO  oldProduct = packageProductDas.getPackageProductByPackageId(price,productDto.getProductId());
             if(oldProduct!=null){
        try {
			  LOG.debug(" in  product  create");
			    prodId = oldProduct.getId();
             oldProduct.setQuantity(productDto.getQuantity());
		     oldProduct.setProductId(productDto.getProductId());
             packageProductDas.makePersistent(oldProduct);
            } catch (Exception e) {
            throw new SessionInternalError("Update exception creating PackageProduct entity bean", PackageProductBL.class, e);
        }
	 }
   else{
	    
           prodId= create( id,productDto);
		 

   }
   }
         return prodId;
       
    }

public  PackageProductWS getBundleProductWS(PackageProductDTO dto){
   PackageProductWS productWS = new PackageProductWS();
   Set<PackagePriceDTO>  prices = dto.getPackagePrices();
   for (PackagePriceDTO  price :prices){
   Integer pricetype= price.getPackagePriceType().getId();
   if(pricetype.equals(Constants.ONE_TIME)){
       productWS.setOneTimePrice(price.getAmount());
	   productWS.setOneTimeDiscount(price .getDiscount());
	   productWS.setOneTimeStartOffset(price .getStartOffset());
       productWS.setOneTimeStartOffsetUnit(price .getStartOffsetUnit());
	   productWS.setOneTimeEndOffset(price.getEndOffset());
	   productWS.setOneTimeEndOffsetUnit(price .getEndOffsetUnit());
	   productWS.setOneTimeCbValue(price.getPackagePriceType().getId());	
				
}
   if(pricetype.equals(Constants.RECURRING)){
      productWS.setRecurringPrice(price.getAmount());
      productWS.setRecurringDiscount(price.getDiscount());
      productWS.setRecurringStartOffset(price.getStartOffset());
	  productWS.setRecurringStartOffsetUnit(price.getStartOffsetUnit());
	  productWS.setRecurringEndOffset(price.getEndOffset());
	  productWS.setRecurringEndOffsetUnit(price.getEndOffsetUnit());
      productWS.setFrequency(price.getFrequency());
	  productWS.setBillingType(price.getBillingType());	
	  productWS.setRecurringCbValue(price.getPackagePriceType().getId());
	    
}
 

if(pricetype.equals(Constants.CANCEL)){
     productWS.setCancelPrice(price.getAmount());
     productWS.setCancelDiscount(price.getDiscount());
     productWS.setCancelStartOffset(price.getStartOffset());
	 productWS.setCancelStartOffsetUnit(price.getStartOffsetUnit());
	 productWS.setCancelEndOffset(price.getEndOffset());
     productWS.setCancelEndOffsetUnit(price.getEndOffsetUnit());
	 productWS.setCancelCbValue(price.getPackagePriceType().getId());
				
}
   productWS.setProductId(dto.getProductId());
   productWS.setQuantity(dto.getQuantity().intValue());
   LOG.debug(" ws  object"+productWS);
             
}
  
               return productWS;


 }
}
