package in.saralam.sbs.server.voucher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.item.db.ItemPriceDAS;
import com.sapienter.jbilling.server.item.db.ItemPriceDTO;
import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.order.db.OrderBillingTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderBillingTypeDTO;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDAS;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.order.db.OrderStatusDAS;
import com.sapienter.jbilling.server.order.db.OrderStatusDTO;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;

import in.saralam.sbs.server.voucher.db.VoucherDTO;
import in.saralam.sbs.server.voucher.db.VoucherDAS;
import in.saralam.sbs.server.voucher.db.VoucherStatusDAS;
import in.saralam.sbs.server.voucher.db.VoucherStatusDTO;
import com.sapienter.jbilling.server.util.Constants;
public class VoucherBL extends ResultList{

	  public static final int CURRENCY_ID = 1; 
    private VoucherDTO voucherdto = null;
	private VoucherDAS voucherdas = null;
	private static final Logger LOG = Logger.getLogger(VoucherBL.class );
	private EventLogger eLogger = null;
	private ItemDAS itemDas = new ItemDAS();
	
	public VoucherBL(Integer voucherId) {
		init();
		set(voucherId);
	}
	
	public VoucherBL(String pinCode) {
		init();
		set(pinCode);
	}

	
	public VoucherBL() {
		init();
	}
	
	public VoucherBL(VoucherDTO voucherdto) {
		init();
		this.voucherdto = voucherdto;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		voucherdas = new VoucherDAS();
	}
	
	public VoucherDTO getEntity() {
		return voucherdto;
	}
	
	public void set(String pinCode) {
		voucherdto = voucherdas.findCustomerByPin(pinCode);
	}
	
	public void set(Integer id) {
		voucherdto = voucherdas.find(id);
	}
	public void setForUpdate(Integer id) {
		voucherdto = voucherdas.findForUpdate(id);
	}
	
	public void set(VoucherDTO newvoucherdto) {
		voucherdto = newvoucherdto;
	}
	
	public VoucherDTO getDTO() {
		return voucherdto;
	}

	public Integer create(Date createdDateTime,VoucherStatusDTO voucherStatus,Integer entityId,Integer serialNo,String pinCode,String batchId,Integer productId) {
		
		VoucherStatusDTO x ; 
		LOG.debug("create voucherDTO method...");
		VoucherDTO newvoucherdto = new VoucherDTO();
		voucherdas = new VoucherDAS();
		List<VoucherDTO> dto = voucherdas.findBySerialNo(productId,serialNo);
		Iterator<VoucherDTO> dt = dto.iterator();
		if (dto != null) {
			while (dt.hasNext()) {
			   VoucherDTO dt1 = dt.next();
			   x = dt1.getVoucherStatus();
			   LOG.debug("voucher status at create is ..'': : : " + x);	   
			   
			}
		}
			
		
		newvoucherdto.setCreatedDateTime(createdDateTime);		
	    newvoucherdto.setVoucherStatus(voucherStatus);
		newvoucherdto.setProductId(productId);
		newvoucherdto.setSerialNo(serialNo);
		newvoucherdto.setEntityId(entityId);
		newvoucherdto.setBatchId(batchId);
		newvoucherdto.setPinCode(pinCode);
		voucherdto = voucherdas.save(newvoucherdto);
		LOG.debug("voucher is created..." + voucherdto);
		return voucherdto.getId();
		
	}
	
	
	public Integer update(Integer Status, VoucherDTO dto) {
		
		
		LOG.debug("updatee in voucherbl called :" );
		LOG.debug("VoucherDTO in voucherbl called :"  + dto);
		LOG.debug("executorId in voucherbl called :" + Status);
		VoucherStatusDTO voucherstatus = null;
	    	 
		  voucherstatus = new VoucherStatusDAS().find(Status);
		  LOG.debug("executorId in voucherbl called :" + voucherstatus);
		  
		  VoucherDTO voucherdto = new VoucherDAS().findCustomerByPin(dto.getPinCode());
		LOG.debug("updatee in voucherbl called for finding voucherdto object :" + voucherdto );
			LOG.debug(" voucherdto object :" + dto.getPinCode() );
		voucherdto.setCreatedDateTime(dto.getCreatedDateTime());
		voucherdto.setBatchId(dto.getBatchId());
		voucherdto.setLastModified(dto.getLastModified());
		voucherdto.setEntityId(dto.getEntityId());
		voucherdto.setPinCode(dto.getPinCode());
		voucherdto.setProductId(dto.getProductId());
		voucherdto.setSerialNo(dto.getSerialNo());
		voucherdto.setVoucherStatus(voucherstatus);
		voucherdas.save(voucherdto);
		LOG.debug("update in updatee voucher api finished:" + voucherdto  );
		LOG.debug("voucherdas in updatee voucher api finished:" + voucherdas.save(voucherdto)  );
		
			return voucherdto.getId();	
	}
	
		
	public VoucherWS getVoucherWS(Integer languageId) {
		
		VoucherWS preValue = new VoucherWS( voucherdto.getId(), voucherdto.getCreatedDateTime(),
				voucherdto.getLastModified(),voucherdto.getVoucherStatus(),voucherdto.getEntityId(),
				voucherdto.getSerialNo(),voucherdto.getPinCode(),voucherdto.getBatchId(),
				voucherdto.getProductId());
		
		return preValue;
	}

	public List<VoucherDTO> findBySerialNo(Integer productId,Integer serialNo ) {

                return voucherdas.findBySerialNo(productId,serialNo);
        }
	
	public  VoucherDTO findCustomerByPin(String pinCode){
		return voucherdas.findCustomerByPin(pinCode);
	}
	
	public Integer RedeemVoucher(VoucherDTO vdto ,Integer userId){
		
		UserBL userbl = new UserBL(userId);
		String rateDescription = null;
		Integer currencyId = userbl.getCurrencyId();
		BigDecimal duration;		
		ItemDTO currItem = null;
		Integer productId = vdto.getProductId();
		LOG.debug("product Id in vbl is :" + productId);
		ItemPriceDTO item = new ItemPriceDAS().find(productId,currencyId);
		LOG.debug("item details :" + item);
		BigDecimal cost = item.getPrice();
		 
		duration = new BigDecimal(1);
		currItem = itemDas.find(productId);
	
		VoucherDTO voucherdto = new VoucherDTO();
		Integer entityId = voucherdto.getEntityId();

		OrderStatusDTO ActiveOrderStatus = null;
		
	    	OrderPeriodDTO OrderPeriodOnce = null;
	    	OrderBillingTypeDTO OrderTypePostPaid = null;
		 
		ActiveOrderStatus = new OrderStatusDAS().find(Constants.ORDER_STATUS_ACTIVE);
		LOG.debug("orderDTO in vbl6:" + ActiveOrderStatus );
		// Get the once order period
	        OrderPeriodOnce = new OrderPeriodDAS().find(Constants.ORDER_PERIOD_ONCE);
	      
	        // get the post paid order type
	        OrderTypePostPaid = new OrderBillingTypeDAS().find(Constants.ORDER_BILLING_POST_PAID);
			
		OrderBL orderbl = new OrderBL();
		OrderLineDTO line = new OrderLineDTO();
                
                List<OrderLineDTO> orderLine = new ArrayList<OrderLineDTO>();
  		    	
		line.setItem(currItem);
		rateDescription = "voice call";
	        Boolean useItem=true;
		line.setAmount(cost.multiply(duration));
		line.setPrice(cost);
		line.setOrderLineType(new OrderLineTypeDAS().find(Constants.ORDER_LINE_TYPE_ITEM));
		line.setCreateDatetime(new Date());
		line.setUseItem(useItem);
		line.setQuantity(duration);
		line.setDescription(rateDescription);
		line.setDeleted(0);
		orderLine.add(line);
  		OrderDTO orderdto = new OrderDTO();
  				 				  
  		orderdto.setBaseUserByUserId(new UserDAS().find(userId));
		orderdto.setCurrency(new CurrencyDAS().find(CURRENCY_ID));
		orderdto.setLines( orderLine);
		orderdto.setOrderStatus(ActiveOrderStatus);
		orderdto.setOrderPeriod(OrderPeriodOnce);
  		orderdto.setOrderBillingType(OrderTypePostPaid);
  		orderdto.setNotify(0);
  		//orderdto.setIsCurrent(0);
  		orderdto.setDfFm(0);
		orderdto.setActiveSince(new Date());
  		    	  
  		Integer redeemed = orderbl.create(entityId, userId, orderdto);
  		    	 
  		VoucherStatusDTO voucherstatus = null;
  		    	 
  		voucherstatus = new VoucherStatusDAS().find(Constants.VOUCHER_STATUS_REDEEMED);
  		  
  		LOG.debug("voucher status in VBL : " + voucherstatus);
  		  
  		vdto.setVoucherStatus(voucherstatus);
  		    	 
  		LOG.debug("redeemed voucher after order creation is : " + redeemed);
  		    	 
		return redeemed;
	}
	
	public void updateVoucherStatus(VoucherStatusDTO status,VoucherDTO voucherDTO)throws SessionInternalError{
       VoucherDTO voucher = voucherdas.find(voucherDTO.getId());
       voucher.setVoucherStatus(status);
       voucherdas.save(voucher);

    }
}
