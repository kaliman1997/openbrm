package in.saralam.sbs.server.advancepricing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import in.saralam.sbs.server.advancepricing.db.ChargeTypeDAS;
import in.saralam.sbs.server.advancepricing.db.ChargeTypeDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeDAS;
import in.saralam.sbs.server.advancepricing.db.ProductChargeDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeRateDAS;
import in.saralam.sbs.server.advancepricing.db.ProductChargeRateDTO;
import in.saralam.sbs.server.advancepricing.db.RateDependeeDAS;
import in.saralam.sbs.server.advancepricing.db.RateDependeeDTO;
import in.saralam.sbs.server.advancepricing.db.RateDependencyTypeDTO;
import in.saralam.sbs.server.advancepricing.db.RumTypeDTO;
import in.saralam.sbs.server.rating.db.EventTypeRateMapDAS;
import in.saralam.sbs.server.rating.db.EventTypeRateMapDTO;

import org.apache.log4j.Logger;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.order.OrderHelper;
import com.sapienter.jbilling.server.order.db.OrderBillingTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDAS;
import com.sapienter.jbilling.server.order.event.NewOrderEvent;
import com.sapienter.jbilling.server.process.db.PeriodUnitDAS;
import com.sapienter.jbilling.server.process.db.PeriodUnitDTO;
import com.sapienter.jbilling.server.provisioning.db.ProvisioningStatusDAS;
import com.sapienter.jbilling.server.provisioning.event.SubscriptionActiveEvent;
import com.sapienter.jbilling.server.system.event.EventManager;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;

public class AdvancePricingBL extends ResultList{

	private static final Logger LOG = Logger.getLogger(AdvancePricingBL.class);
	private EventLogger eLogger = null;
	private ProductChargeDTO productChargeDTO = null;
	private ProductChargeRateDTO productChargeRateDTO = null;
	private RateDependeeDTO rateDependeeDTO = null;
	private ProductChargeDAS productChargeDAS = null;
	private ProductChargeRateDAS productChargeRateDAS = null;
	private RateDependeeDAS rateDependeeDAS = null;
	 
	public AdvancePricingBL(){
		init();
	}
	
	public AdvancePricingBL(Integer chargeId){
		init();
		set(chargeId);
	}
	
	public void set(Integer id){
		productChargeRateDTO = productChargeRateDAS.find(id);
	}
	
	private void init(){
		eLogger = EventLogger.getInstance();
		productChargeDTO = new ProductChargeDTO();
		productChargeDAS = new ProductChargeDAS();
		productChargeRateDTO = new ProductChargeRateDTO();
		productChargeRateDAS = new ProductChargeRateDAS();
		rateDependeeDTO = new RateDependeeDTO();
		rateDependeeDAS = new RateDependeeDAS();
	}
	
	//Product Charge
	public Integer createProductCharge(ProductChargeWS product){

		LOG.debug("in new product charge ");
		ProductChargeDTO newProductCharge = new ProductChargeDTO();
		newProductCharge.setItem(product.getItem());
		newProductCharge.setCreatedDate(new Date());
		newProductCharge.setChargeType(product.getChargeType());
                newProductCharge.setTaxCode(product.getTaxCode());
		newProductCharge.setVersionNum(0);
		newProductCharge.setDeleted(0);
		//LOG.debug("new product charge "+newProductCharge);
		try{
		newProductCharge = productChargeDAS.save(newProductCharge);
		LOG.debug("new product charge "+newProductCharge);
		new AdvancePricingBL().createProductChargeRate(newProductCharge, product.getRates());
	 } catch (Exception e) {
         LOG.error("adding product charge and rates", e);
         TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
     } 
		return newProductCharge.getId();
	}
	
	public void updateProductCharge(ProductChargeWS pricing){
		
		ProductChargeDAS pcDAS = new ProductChargeDAS();
		ProductChargeDTO pcDTO = pcDAS.findByItemAndType(pricing.getItem().getId(), pricing.getChargeType().getId());
		if(pcDTO!=null){
		pcDTO.setVersionNum(pcDTO.getVersionNum()+1);
                pcDTO.setTaxCode(pricing.getTaxCode());
		ProductChargeDTO charge = pcDAS.save(pcDTO);
		new AdvancePricingBL().updateProductChargeRate(charge, pricing.getRates());
		if(charge!=null)
			LOG.debug("product charge updated");
		}else{
			ProductChargeDTO dpcDTO = pcDAS.findByItemAndTypeAndDeleted(pricing.getItem().getId(), pricing.getChargeType().getId());
			if(dpcDTO!=null){
				dpcDTO.setDeleted(0);
				dpcDTO.setVersionNum(dpcDTO.getVersionNum()+1);
                                dpcDTO.setTaxCode(pricing.getTaxCode());
				ProductChargeDTO charge = pcDAS.save(dpcDTO);
				new AdvancePricingBL().updateProductChargeRate(charge, pricing.getRates());
			}else{
				new AdvancePricingBL().createProductCharge(pricing);
			}
		}
	}
	
	public void deleteProductCharge(Integer itemId){
		ProductChargeDAS pcDAS = new ProductChargeDAS();
		List<ProductChargeDTO> pcList = pcDAS.findByProduct(itemId);
		for(ProductChargeDTO pcDTO : pcList){
			pcDTO.setDeleted(1);
			productChargeDAS.save(pcDTO);
		}
	}
	
	//Product Charge Rate 
	public void createProductChargeRate(ProductChargeDTO charge, List<ProductChargeRateWS> rates ){
		for(ProductChargeRateWS chargeRate : rates){
			
			ProductChargeRateDTO newProductChargeRate = new ProductChargeRateDTO();
			newProductChargeRate.setProductCharge(charge);
			newProductChargeRate.setCurrency(chargeRate.getCurrency());
			newProductChargeRate.setOrder(chargeRate.getOrder());
			newProductChargeRate.setFixedAmount(chargeRate.getFixedAmount());
			newProductChargeRate.setScaledAmount(chargeRate.getScaledAmount());
			//PeriodUnitDTO unit = new PeriodUnitDAS().find(chargeRate.getUnitId());
			//LOG.debug("unit DTO is "+unit);
			newProductChargeRate.setUnitId(chargeRate.getUnitId());
			RateDependeeDTO rateDependeeDTO = null;
			if(chargeRate.getRateDependeeWS()!=null)
			rateDependeeDTO = new AdvancePricingBL().createRateDependee(chargeRate.getRateDependeeWS());
			newProductChargeRate.setRateDependee(rateDependeeDTO);
			newProductChargeRate.setRum(chargeRate.getRum());
			newProductChargeRate.setActiveSince(chargeRate.getActiveSince());
			newProductChargeRate.setActiveUntil(chargeRate.getActiveUntil());
			newProductChargeRate.setVersionNum(0);
			newProductChargeRate.setDeleted(0);
			newProductChargeRate.setDestinationMap(chargeRate.getDestinationMap());
			ProductChargeRateDTO rate = productChargeRateDAS.save(newProductChargeRate);
			LOG.debug("rate id is "+rate.getId());
			if(charge.getChargeType().getId() == Constants.CHARGE_USAGE){
				EventTypeRateMapDTO eventMap = new EventTypeRateMapDTO();
				eventMap.setProductChargeRateDTO(rate);
				eventMap.setRatingEventTypeDTO(chargeRate.getRatingEvent());
				new EventTypeRateMapDAS().save(eventMap);
			}
		}
	}
	
	public void updateProductChargeRate(ProductChargeDTO productCharge, List<ProductChargeRateWS> rates){
		int length = 0;
		List<ProductChargeRateWS> rateWS = new ArrayList<ProductChargeRateWS>();
		ProductChargeRateDTO chargeRate = null;
		ProductChargeRateWS chargeRateWS = null;
		List<ProductChargeRateDTO> listRates = new ProductChargeRateDAS().findByProductCharge(productCharge.getId());
		LOG.debug("DB list of rates are "+listRates);
		LOG.debug("UI list of rates are "+rates);
		int i;
		
		if (listRates != null && rates != null){
			
			if(listRates.size() >= rates.size()){
				length = rates.size();
			}else{
				length = listRates.size();
			}
			
			for( i=0;i<length;i++){
				LOG.debug("i valu in apBL..."+i);
				chargeRate = listRates.get(i);
				chargeRateWS = rates.get(i);
				chargeRate.setProductCharge(productCharge);
				LOG.debug("order is ... "+chargeRateWS.getOrder());
				chargeRate.setOrder(chargeRateWS.getOrder());
				chargeRate.setFixedAmount(chargeRateWS.getFixedAmount());
				chargeRate.setScaledAmount(chargeRateWS.getScaledAmount());
				chargeRate.setCurrency(chargeRateWS.getCurrency());
				chargeRate.setUnitId(chargeRateWS.getUnitId());
				chargeRate.setDestinationMap(chargeRateWS.getDestinationMap());
				if(productCharge.getChargeType().getId() == Constants.CHARGE_USAGE){
				chargeRateWS.getRateDependeeWS().setVersionNum(productCharge.getVersionNum());
				RateDependeeDTO rateDependee = null; 
				if(chargeRate.getRateDependee() != null){
					LOG.debug("dependee exists......");
					chargeRateWS.getRateDependeeWS().setId(chargeRate.getRateDependee().getId());
				rateDependee = new AdvancePricingBL().updateRateDependee(chargeRateWS.getRateDependeeWS());
				}else{
					LOG.debug("creating new rate dependeee......");
					rateDependee = new AdvancePricingBL().createRateDependee(chargeRateWS.getRateDependeeWS());
				}
				chargeRate.setRateDependee(rateDependee);
				chargeRate.setRum(chargeRateWS.getRum());
				}else{
					chargeRate.setRateDependee(null);
					chargeRate.setRum(null);
				}
				
				chargeRate.setVersionNum(productCharge.getVersionNum()+1);
				chargeRate.setActiveSince(chargeRateWS.getActiveSince());
				chargeRate.setActiveUntil(chargeRateWS.getActiveUntil());
				chargeRate.setDeleted(0);
				chargeRate = productChargeRateDAS.save(chargeRate);
				if(productCharge.getChargeType().getId()==Constants.CHARGE_USAGE){
				EventTypeRateMapDTO eventType = new EventTypeRateMapDAS().findByRate(chargeRate);
				eventType.setRatingEventTypeDTO(chargeRateWS.getRatingEvent());
				new EventTypeRateMapDAS().save(eventType);
				}
				LOG.debug("charge rate updated successfully");
			}
				if(listRates.size()>rates.size()){
					LOG.debug("DB list length "+listRates.size());
					for(int j=i;j<listRates.size();j++){
						chargeRate = listRates.get(j);
						chargeRate.setDeleted(1);
						productChargeRateDAS.save(chargeRate);
					}
				}else if(listRates.size()<rates.size()){
					LOG.debug("ratesws list length "+rates.size());
					for(int j=i;j<rates.size();j++){
						LOG.debug("j valu in apBL..."+j);
						rateWS.add(rates.get(j));
					} 
					new AdvancePricingBL().createProductChargeRate(productCharge, rateWS);
				}
					
			
		}
	}
	

	

	public void deleteProductChargeRate(){
		
	}
	
	//Rate Dependee
	public RateDependeeDTO createRateDependee(RateDependeeWS dependeeWS ){
		RateDependeeDTO newRateDependee = new RateDependeeDTO();
		newRateDependee.setCurrency(dependeeWS.getCurrency());
		newRateDependee.setMinBalance(dependeeWS.getMinBalance());
		newRateDependee.setMaxBalance(dependeeWS.getMaxBalance());
		newRateDependee.setDependencyType(dependeeWS.getDependencyType());
		newRateDependee.setVersionNum(0);
		LOG.debug("min bal is "+dependeeWS.getMinBalance()+" max bal is "+dependeeWS.getMaxBalance());
		if(dependeeWS.getMinBalance() == null && dependeeWS.getMaxBalance() == null){
			rateDependeeDTO = null;
		}else {
			rateDependeeDTO = rateDependeeDAS.save(newRateDependee);
		}
		LOG.debug("rate dependee is "+rateDependeeDTO);
		return rateDependeeDTO;
	}
	
	public RateDependeeDTO updateRateDependee(RateDependeeWS rateDependee){
		LOG.debug("update rate dependee...");
		RateDependeeDTO rateDependeeDTO = rateDependeeDAS.find(rateDependee.getId());
		rateDependeeDTO.setCurrency(rateDependee.getCurrency());
		rateDependeeDTO.setDependencyType(rateDependee.getDependencyType());
		rateDependeeDTO.setMaxBalance(rateDependee.getMaxBalance());
		rateDependeeDTO.setMinBalance(rateDependee.getMinBalance());
		rateDependeeDTO.setVersionNum(rateDependee.getVersionNum()+1);
		LOG.debug("rate dependee object is "+rateDependeeDTO);
		LOG.debug("min bal is "+rateDependee.getMinBalance()+" max bal is "+rateDependee.getMaxBalance());
		if(rateDependee.getMinBalance() == null && rateDependee.getMaxBalance() == null){
			LOG.debug("update rate dependee min & max null...");
			return null;
		}else{
		return rateDependeeDTO;
		}
	}
	
	public void deleteRateDependee(){
		
	}
	
	//adding advance pricing rates to order lines
	 public Integer createOrder(Integer entityId, Integer userAgentId,
             OrderDTO orderDto) throws SessionInternalError {
		 LOG.debug("in ordercreate method "+orderDto);
		 OrderDTO order = null;
		 try {
	
			 // if the order is a one-timer, force pre-paid to avoid any
			 // confusion
			 if (orderDto.getOrderPeriod().getId() == Constants.ORDER_PERIOD_ONCE) {
				 orderDto.setOrderBillingType(new OrderBillingTypeDAS().find(Constants.ORDER_BILLING_PRE_PAID));
				 // one time orders can not be the main subscription
				 //orderDto.setIsCurrent(0);
			 }
			 UserDAS user = new UserDAS();
			 if (userAgentId != null) {
				 orderDto.setBaseUserByCreatedBy(user.find(userAgentId));
			 }

			 // create the record
			 orderDto.setBaseUserByUserId(user.find(orderDto.getBaseUserByUserId().getId()));
			 orderDto.setOrderPeriod(new OrderPeriodDAS().find(orderDto.getOrderPeriod().getId()));
			 // set the provisioning status
			 for (OrderLineDTO line : orderDto.getLines()) {
				 // set default provisioning status id for order lines
				 /*if (line.getProvisioningStatus() == null) {
					 line.setProvisioningStatus(new ProvisioningStatusDAS().find(
							 Constants.PROVISIONING_STATUS_INACTIVE));
				 } else {
					 line.setProvisioningStatus(new ProvisioningStatusDAS().find(
							 line.getProvisioningStatus().getId()));
				 }*/
			 }
			// orderDto.setDefaults();

			 order = new OrderDAS().save(orderDto);

			 // link the lines to the new order
			 for (OrderLineDTO line : order.getLines()) {
				 line.setPurchaseOrder(order);
			 }

			 /*if (order.getIsCurrent() != null && order.getIsCurrent().intValue() == 1) {
				 new OrderBL().setMainSubscription(userAgentId);
			 }*/

		 } catch (Exception e) {
			 throw new SessionInternalError("Create exception creating order entity bean", OrderBL.class, e);
		 }
LOG.debug("order id is "+order.getId());
		 return order.getId();
	 }
	
	}
