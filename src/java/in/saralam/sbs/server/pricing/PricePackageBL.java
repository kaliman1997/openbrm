/*
 * SARALAM CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise Saralam Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise Saralam Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise Saralam Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */



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
import com.sapienter.jbilling.server.order.OrderLineWS;
import com.sapienter.jbilling.server.order.OrderBL;
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
import in.saralam.sbs.server.pricing.db.PackagePriceDTO;
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
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.sapienter.jbilling.server.item.db.ItemPriceDAS;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import in.saralam.sbs.server.pricing.db.PricePackageDAS;
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDTO;
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
import in.saralam.sbs.server.pricing.PackageProductWS;
import in.saralam.sbs.server.pricing.PricePackageWS;
import in.saralam.sbs.server.pricing.db.PurchasedBundleDAS;
import in.saralam.sbs.server.pricing.db.PurchasedBundleDTO;
import in.saralam.sbs.server.pricing.db.PurchasedBundleProductDTO;
import in.saralam.sbs.server.pricing.db.PurchasedBundleProductDAS;
import in.saralam.sbs.server.pricing.PurchasedBundleWS;
import com.sapienter.jbilling.server.order.db.OrderStatusDTO;
import com.sapienter.jbilling.server.user.db.UserStatusDTO;

import com.sapienter.jbilling.server.metafields.MetaFieldHelper;
import com.sapienter.jbilling.server.metafields.MetaFieldBL;
import com.sapienter.jbilling.server.metafields.MetaFieldValueWS;
import com.sapienter.jbilling.server.metafields.MetaContent;

public class PricePackageBL extends ResultList
        implements PricePackageSQL {
private PricePackageDTO pricePackage = null;
private    PackagePriceDTO  packagePrice= null;
private PackageProductDAS packageProductDas = null;
private PricePackageDAS pricePackageDas = null;
private static final Logger LOG = Logger.getLogger(PricePackageBL.class);
private EventLogger eLogger = null;

    public PricePackageBL(Integer packageId) {
        init();
        set(packageId);
    }

    public PricePackageBL() {
        init();
    }
	
    public PricePackageBL(PricePackageDTO pricePackage) {
        init();
        this.pricePackage = pricePackage;
    }

    private void init() {
        eLogger = EventLogger.getInstance();
        packageProductDas = new PackageProductDAS();
        pricePackageDas = new PricePackageDAS();
        
    }
	
	 

    public PricePackageDTO getEntity() {
        return pricePackage;
    }

    public void set(Integer id) {
        pricePackage = pricePackageDas.find(id);
    }

    public void setForUpdate(Integer id) {
        pricePackage = pricePackageDas.findForUpdate(id);
    }

    public void set(PricePackageDTO newPricePackage) {
        pricePackage = newPricePackage;
    }

    public PricePackageWS getWS(Integer languageId) {
        PricePackageWS retValue = new PricePackageWS(pricePackage.getId(), pricePackage.getEntity(),
        						pricePackage.getActiveSince(), pricePackage.getActiveUntil(), pricePackage.getCreatedDate(),
							    pricePackage.getDeleted(), pricePackage.getVersionNum());

        List<PackageProductWS> products = new ArrayList<PackageProductWS>();
        for (Iterator it = pricePackage.getPackageProducts().iterator(); it.hasNext();) {
            PackageProductDTO product = (PackageProductDTO) it.next();
            if (product.getDeleted() == 0) {
                products.add(getPackageProductWS(product.getId()));
            }
        }
        //this will initialized Generated Invoices in the PricePackageDTO instance
        retValue.setPackageProducts(new PackageProductWS[products.size()]);
        products.toArray(retValue.getPackageProducts());
        return retValue;
    }

    public PricePackageDTO getDTO() {
        return pricePackage;
    }

    public void addProduct(PricePackageDTO newPricePackage, Integer productID, BigDecimal quantity, List<PackagePriceDTO> packagePrices,
    					Integer language, Integer entityId) throws ItemDecimalsException {
			
	
            
		// Validate decimal quantity with the item
        


        PackageProductDTO myProduct = new PackageProductDTO();
        
        myProduct.setQuantity(quantity);
       

        newPricePackage.getPackageProducts().add(myProduct);          
  

        

       
    }
	 

    public Integer create(Integer entityId, PricePackageDTO pricePackageDto) throws SessionInternalError {
      //public Integer create(Integer entityId, PackagePriceDTO pricePackageDto) throws SessionInternalError {
        try {
            
            UserDAS user = new UserDAS();
            for (PackageProductDTO product : pricePackageDto.getPackageProducts()) {
                
                
            }
            LOG.debug("updating metafields in create");
			//pricePackageDto.updateMetaFieldsWithValidation(entityId,accountTypeId,pricePackageDto);
            pricePackage = pricePackageDas.save(pricePackageDto);
             // packagePrice= packagePriceDas.save(pricePackageDto);

            // link the lines to the new pricePackage
           for (PackageProductDTO product : pricePackage.getPackageProducts()) {
                product.setPricePackage(pricePackage);
            }

            /*for (PackageProductDTO product : pricePackage.getPackageProducts()) {
                product.setPricePackage(pricePackage);
            }*/
          
			            
            
        } catch (Exception e) {
            throw new SessionInternalError("Create exception creating pricePackage entity bean", PricePackageBL.class, e);
        }

        return pricePackage.getId();
    }

       public void update(Integer entityId, PricePackageDTO pricePackageDto) throws SessionInternalError {
     
        try {
            
            UserDAS user = new UserDAS();
            for (PackageProductDTO product : pricePackageDto.getPackageProducts()) {
                
                
            }
            
			pricePackageDto.updateMetaFieldsWithValidation(entityId, pricePackageDto.getEntity().getId(), pricePackageDto);
			
            pricePackageDas.makePersistent(pricePackageDto);
             
		  } catch (Exception e) {
            throw new SessionInternalError("Create exception creating pricePackage entity bean", PricePackageBL.class, e);
        }

       
    }

 
    public PackageProductWS getPackageProductWS(Integer id) {
        PackageProductDTO product = packageProductDas.findNow(id);
        if (product == null) {
            LOG.warn("Order line " + id + " not found");
            return null;
        }
        PackageProductWS retValue = new PackageProductWS();/*product.getId(), product.getItem().getId(), product.getDescription(),
                                               line.getAmount(), line.getQuantity(), line.getPrice() == null ? null : line.getPrice(), line.getCreateDatetime(),
                                               line.getDeleted(), line.getOrderLineType().getId(), line.getEditable(),
                                               line.getPurchaseOrder().getId(), line.getUseItem(), line.getVersionNum(), line.getProvisioningStatusId(), line.getProvisioningRequestId());*/
        return retValue;
    }

    public PackageProductDTO getPackageProduct(Integer id) {
        PackageProductDTO product = packageProductDas.findNow(id);
        if (product == null) {
            throw new SessionInternalError("Order line " + id + " not found");
        }
        return product;
    }

public   PricePackageDTO getDTO(PricePackageWS  bundle){
         PricePackageDTO dto =pricePackageDas.find(bundle.getId());
         LOG.debug(" bundle  id"+bundle.getId());
         dto.setActiveSince(bundle.getActiveSince());
         dto.setActiveUntil(bundle.getActiveUntil());
		 dto.setCreatedDate(bundle.getCreatedDate());
         dto.setDescription(bundle.getBundleName());
		 dto.setMbgDays(bundle.getMbgDays());
		 dto.setMetaFields(dto.getMetaFields());
		return dto;
} 

public void deleteBundle(Integer bundleId){
 PricePackageDAS das = new  PricePackageDAS();
 PricePackageDTO dto = das.find(bundleId);
 for (PackageProductDTO pdto : dto.getPackageProducts()) {
    for (PackagePriceDTO priceDto :pdto.getPackagePrices()){
        PackagePriceTypeDTO priceTypeDto=priceDto.getPackagePriceType();
        priceDto.setDeleted(1);
     }
	     pdto.setDeleted(1);
   }

         dto.setDeleted(1);
       
}


public  PricePackageWS getBundleWS(PricePackageDTO dto){
PricePackageWS pricePackageWS = new PricePackageWS();
Set<PackageProductDTO> products=dto.getPackageProducts();
List<PackageProductWS> productList=  new ArrayList<PackageProductWS>();
  for( PackageProductDTO product: products){
	PackageProductBL  pbl = new PackageProductBL();
    PackageProductWS ws= pbl.getBundleProductWS(product);
    productList.add(ws);
 }
PackageProductWS [] packageProductWS=productList.toArray( new PackageProductWS[productList.size()]);
pricePackageWS.setActiveSince(dto.getActiveSince());
pricePackageWS.setActiveUntil(dto.getActiveUntil());
pricePackageWS.setCreatedDate (dto.getCreatedDate ());
pricePackageWS.setBundleName(dto.getDescription());
pricePackageWS.setMbgDays(dto.getMbgDays());
pricePackageWS.setId(dto.getId());
pricePackageWS.setPackageProducts(packageProductWS);
pricePackageWS.setMetaFields(MetaFieldBL.convertMetaFieldsToWS(dto.getEntity().getId(), dto));
   

    return pricePackageWS;

 }
        
 
}
