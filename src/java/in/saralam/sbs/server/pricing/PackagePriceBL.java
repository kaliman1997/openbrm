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
import in.saralam.sbs.server.pricing.db.PackagePriceDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceDAS;
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
import in.saralam.sbs.server.pricing.PricePackageWS;
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDTO;
import in.saralam.sbs.server.pricing.db.PackageProductDAS;
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDAS;
import in.saralam.sbs.server.pricing.db.PackagePriceDAS;
import in.saralam.sbs.server.pricing.db.PackagePriceDTO;
import com.sapienter.jbilling.server.util.Constants;
public class PackagePriceBL {
  private static final Logger LOG = Logger.getLogger(PackagePriceBL.class);
  public static Integer create(PackagePriceDTO price) {
    PackagePriceDAS priceDas = new PackagePriceDAS();
    PackagePriceDTO newPrice = priceDas.save(price);
    return newPrice.getId();
 }
public List<PackagePriceDTO> getPackagePriceDTO(PackageProductWS ws,PackageProductDTO pdto,PricePackageWS priceWS){
        List<PackagePriceDTO> packagePriceDTO =new ArrayList<PackagePriceDTO>();
		if((ws.getOneTimeCbValue())!=null && (ws.getOneTimeCbValue())==Constants.ONE_TIME){
          PackagePriceDTO  packagePriceOneTimeDto = new  PackagePriceDTO();
          PackagePriceTypeDTO dto  = new PackagePriceTypeDAS().find(Constants.ONE_TIME);
          packagePriceOneTimeDto.setPackagePriceType(dto);
          packagePriceOneTimeDto.setPackageProduct(pdto);
          packagePriceOneTimeDto.setAmount(ws.getOneTimePrice());
          LOG.debug("ws.getOneTimeDiscount()"+ws.getOneTimeDiscount());
		  LOG.debug("=====ENTERING INTO ONE TIME FEE======");
          packagePriceOneTimeDto.setDiscount(ws.getOneTimeDiscount());
          packagePriceOneTimeDto.setStartDate(priceWS.getActiveSince());
          packagePriceOneTimeDto.setEndDate(priceWS.getActiveUntil());
          packagePriceOneTimeDto.setStartOffset( ws.getOneTimeStartOffset());
          packagePriceOneTimeDto.setStartOffsetUnit(ws.getOneTimeStartOffsetUnit());
          packagePriceOneTimeDto.setEndOffset(ws.getOneTimeEndOffset());
          packagePriceOneTimeDto.setEndOffsetUnit(ws.getOneTimeEndOffsetUnit());
          packagePriceOneTimeDto.setVersionNum(0);
          packagePriceOneTimeDto.setDeleted(0);
          packagePriceDTO.add(packagePriceOneTimeDto);
      }
     if((ws.getRecurringCbValue())!=null && (ws.getRecurringCbValue())==Constants.RECURRING){
         PackagePriceDTO  packagePriceRecurringDto = new  PackagePriceDTO();
         PackagePriceTypeDTO dto  = new PackagePriceTypeDAS().find(Constants.RECURRING);
         packagePriceRecurringDto.setPackagePriceType(dto);
         packagePriceRecurringDto.setPackageProduct(pdto);
         packagePriceRecurringDto.setAmount(ws.getRecurringPrice());
		 LOG.debug("=====ENTERING INTO RECURRING FEE======");
         packagePriceRecurringDto.setDiscount(ws.getRecurringDiscount());
         packagePriceRecurringDto.setStartDate(priceWS.getActiveSince());
         packagePriceRecurringDto.setEndDate(priceWS.getActiveUntil());
         packagePriceRecurringDto.setStartOffset( ws.getRecurringStartOffset());
         packagePriceRecurringDto.setStartOffsetUnit(ws.getRecurringStartOffsetUnit());
         packagePriceRecurringDto.setEndOffset(ws.getRecurringEndOffset());
         packagePriceRecurringDto.setEndOffsetUnit(ws.getRecurringEndOffsetUnit());
         packagePriceRecurringDto.setVersionNum(0);
         packagePriceRecurringDto.setDeleted(0);
         packagePriceRecurringDto.setFrequency(ws.getFrequency());
		 packagePriceRecurringDto.setBillingType(ws.getBillingType());
         packagePriceDTO.add(packagePriceRecurringDto);
      }
       
	   if((ws.getCancelCbValue())!=null && (ws.getCancelCbValue())==Constants.CANCEL){
        PackagePriceDTO  packagePriceCancelDto = new  PackagePriceDTO();
        PackagePriceTypeDTO dto  = new PackagePriceTypeDAS().find(Constants.CANCEL);
        packagePriceCancelDto.setPackagePriceType(dto);
        packagePriceCancelDto.setPackageProduct(pdto);
        packagePriceCancelDto.setAmount(ws.getCancelPrice());
		LOG.debug("=====ENTERING INTO CANCEL FEE======");
        packagePriceCancelDto.setDiscount(ws.getCancelDiscount());
        packagePriceCancelDto.setStartDate(priceWS.getActiveSince());
        packagePriceCancelDto.setEndDate(priceWS.getActiveUntil());
        packagePriceCancelDto.setStartOffset( ws.getCancelStartOffset());
        packagePriceCancelDto.setStartOffsetUnit(ws.getCancelStartOffsetUnit());
        packagePriceCancelDto.setEndOffset(ws.getCancelEndOffset());
        packagePriceCancelDto.setEndOffsetUnit(ws.getCancelEndOffsetUnit());
        packagePriceCancelDto.setVersionNum(0);
        packagePriceCancelDto.setDeleted(0);
        packagePriceDTO.add(packagePriceCancelDto);
      }
      LOG.debug(" size of pacakge price dto in bl" +packagePriceDTO.size());
    
     return packagePriceDTO;
}

public static void  update(PackagePriceDTO price, Integer prodId) {
	LOG.debug(" product id"+prodId);
   PackageProductDTO  oldProduct=  new PackageProductDAS().find(prodId);
  
   PackagePriceDAS priceDas = new PackagePriceDAS();
   PackagePriceDTO oldPrice = priceDas.findPackagePriceByProdcut(oldProduct,price.getPackagePriceType());
   if( oldPrice!= null){
   oldPrice.setPackagePriceType(price.getPackagePriceType());
   oldPrice.setAmount(price.getAmount());
   oldPrice.setDiscount(price.getDiscount());
   oldPrice.setEndOffset(price.getEndOffset());
   oldPrice.setEndOffsetUnit(price.getEndOffsetUnit());
   oldPrice.setStartOffset(price.getStartOffset());
   oldPrice.setStartOffsetUnit(price.getStartOffsetUnit());
   oldPrice.setFrequency(price.getFrequency());
   oldPrice.setBillingType(price.getBillingType());
   oldPrice.setStartDate(price.getStartDate());
    oldPrice.setEndDate(price.getEndDate());
   priceDas.makePersistent(oldPrice);
   }
 
   else{
	    
		 PackagePriceDTO newPrice= new  PackagePriceDTO();
		  newPrice.setPackageProduct(oldProduct);
          newPrice.setPackagePriceType(price.getPackagePriceType());
		  newPrice.setAmount(price.getAmount());
          newPrice.setDiscount(price.getDiscount());
          newPrice.setEndOffset(price.getEndOffset());
          newPrice.setEndOffsetUnit(price.getEndOffsetUnit());
          newPrice.setStartOffset(price.getStartOffset());
          newPrice.setStartOffsetUnit(price.getStartOffsetUnit());
          newPrice.setFrequency(price.getFrequency());
          newPrice.setBillingType(price.getBillingType());
          newPrice.setStartDate(price.getStartDate());
          newPrice.setEndDate(price.getEndDate());
          PackagePriceDTO newPriceTemp= priceDas.save(newPrice);
          LOG.debug("created pakcage");
	 
   }
	
}

public static void setDefaults(PackagePriceDTO price) {
    	
}
 public void deleteBundle(Integer bundleId){
        PackagePriceDAS das = new  PackagePriceDAS();
        PackagePriceDTO dto = das.find(bundleId);
        dto.setDeleted(1);
         
}

    
}
