
package in.saralam.sbs.server.mediation.task;


import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.mediation.task.MediationResult;
import com.sapienter.jbilling.server.order.db.OrderBillingTypeDTO;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.order.db.OrderStatusDTO;
import com.sapienter.jbilling.server.order.db.OrderStatusDAS;
import com.sapienter.jbilling.server.order.db.OrderBillingTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderPeriodDAS;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.util.Constants;
import org.apache.log4j.Logger;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.mediation.Record;
import com.sapienter.jbilling.server.mediation.task.IMediationProcess;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskManager;
import com.sapienter.jbilling.server.pluggableTask.admin.ParameterDescription;
import com.sapienter.jbilling.server.service.db.ServiceDTO;
import com.sapienter.jbilling.server.service.db.ServiceDAS;
import com.sapienter.jbilling.server.service.db.ServiceAliasDTO;
import com.sapienter.jbilling.server.service.db.ServiceAliasDAS;
import in.saralam.sbs.server.mediation.db.GotosolrCDREventDAS;
import in.saralam.sbs.server.mediation.db.GotosolrCDREventDTO;
import com.sapienter.jbilling.server.order.OrderStatusFlag;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.time.DateUtils;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;
import org.hibernate.*;
public class EostisMediationTask extends PluggableTask implements
        IMediationProcess {

    
    private static final Logger LOG = Logger.getLogger(EostisMediationTask.class);
	
	public static final ParameterDescription PARAMETER_VAT_ITEM = 
    new ParameterDescription("VAT item", true, ParameterDescription.Type.STR);
	public static final ParameterDescription PARAMETER_CURRENCY_ID = 
    new ParameterDescription("Currency id", true, ParameterDescription.Type.STR);
    
    //initializer for pluggable params
    { 
    	descriptions.add(PARAMETER_VAT_ITEM);
		descriptions.add(PARAMETER_CURRENCY_ID);
    }

    // Variables used during the processing
    OrderStatusDTO ActiveOrderStatus = null;
    OrderPeriodDTO OrderPeriodOnce = null;
    OrderBillingTypeDTO OrderTypePostPaid = null;
    OrderLineTypeDTO OrderLineTypeItem = null;
    OrderLineTypeDTO OrderLineTypeTax = null;

    private ItemDAS itemDas = new ItemDAS();

    HashMap<Integer,Integer> TaxMap = new HashMap<Integer,Integer>();
    HashMap<String,Integer> customerCache = new HashMap<String,Integer>();

    public static final int CURRENCY_ID = 1; //- changed to Danish Krone

    protected Logger getLog() {
        return Logger.getLogger(EostisMediationTask.class);
    }
        
    public  EostisMediationTask() {

      LOG.debug("Initialising Mediation Loader task-edit1");

      // Set the status
	       ActiveOrderStatus = new OrderStatusDAS().find(Constants.ORDER_STATUS_ACTIVE);
      // Get the once order period
      OrderPeriodOnce = new OrderPeriodDAS().find(Constants.ORDER_PERIOD_ONCE);

      // get the post paid order type
      OrderTypePostPaid = new OrderBillingTypeDAS().find(Constants.ORDER_BILLING_POST_PAID);

      // get the item order line type
      OrderLineTypeItem = new OrderLineTypeDAS().find(Constants.ORDER_LINE_TYPE_ITEM);

      // get the tax item order line type
      OrderLineTypeTax = new OrderLineTypeDAS().find(Constants.ORDER_LINE_TYPE_TAX);

    }
    
    public void process(List<Record> records, List<MediationResult> results, String configurationName)
            throws TaskException {
 
        LOG.debug("Starting Mediation Loader task-edit2");

        int index = -1; // to track the results list
        if (results != null && results.size() > 0) {
        	LOG.debug("results List isn't null");
        	if (records.size() != results.size()) {
                throw new TaskException("If results are passed, there have to be the same number as" +
                        " records");
            }
        	index = 0;
        } else if (results == null) {
        	LOG.error("results List is null");
        	throw new TaskException("The results array can not be null");
        }
			ActiveOrderStatus = new OrderStatusDAS().find(Constants.ORDER_STATUS_ACTIVE);

        // Get the once order period
        OrderPeriodOnce = new OrderPeriodDAS().find(Constants.ORDER_PERIOD_ONCE);

        // get the post paid order type
        OrderTypePostPaid = new OrderBillingTypeDAS().find(Constants.ORDER_BILLING_POST_PAID);

        // get the item order line type
        OrderLineTypeItem = new OrderLineTypeDAS().find(Constants.ORDER_LINE_TYPE_ITEM);

        // get the tax item order line type
        OrderLineTypeTax = new OrderLineTypeDAS().find(Constants.ORDER_LINE_TYPE_TAX);

        processBatch(records, results, configurationName);
    }

    private void processBatch(List<Record> records, List<MediationResult> results, String configurationName) {

      String recordCategory;
      String recordDescription;
      String rateDescription = null;
      BigDecimal recordAmount = null;
      BigDecimal recordPrice = null;
      Integer orderLineId = null;
      BigDecimal recordTaxTotal = new BigDecimal("0");
      boolean taxable;
      SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date loadMonth1 = null;
      boolean updateOrder = true;
      Integer orderNumber;
	  String constantOne;
	  String  keyId;
	  Integer customerId;
	  String destinationNumber;
	  String callingNumber;
	  Date callStart= null;
	  Date callEnd= null;
	  Integer duration;
	  BigDecimal cost;
	  Date jbillingTimeStamp = null;
	  Integer rateId = null;
	  String destDescr;
	  String callType;
	  String cdrSource;
         String error=null;
         int errorCount = 0;
         recordDescription = "Unknown Event";
       
         OrderDTO newOrderDTO = null;
         List<OrderLineDTO> newOrderLines = null;
         MediationResult result = null; 

   

	  
      for (int i = 0 ; i < records.size() ;  i++ ) {
    	  UserDTO uDTO = null;
         OrderDTO oDTO = null;
    	  updateOrder = true;
    	  
    	  Record record = records.get(i);
    	  orderNumber = 0;
    	  orderLineId = 0;
    	  errorCount = 0;
    	  error = null;
    	 // Integer productId = null;
		   Integer productId = new Integer(getParameter(PARAMETER_VAT_ITEM.getName(),((String)null)));
		   int CURRENCY_ID = new Integer(getParameter(PARAMETER_CURRENCY_ID.getName(),((String)null))).intValue();
		   
		    LOG.debug("productId"+productId);
    	  ItemDTO currItem = null;
    	  Integer userId = null;
		  Integer orderId = null;
    	  result = new MediationResult(configurationName,true);
    	  LOG.debug("record id is: "+record.getKey());
    	
    	  List<OrderLineDTO> orderLineList = null;
    	  List<OrderLineDTO> resOrderLineList = new ArrayList<OrderLineDTO> ();
	     
    	  try{
    		 keyId = record.getFields().get(0).getStrValue();
    		  if( keyId== null ||keyId.isEmpty())	{
    			  error = "ERR_KEY_ID_NULL";
    			  result.setDone(true);
    			  result.addError(error);
    			  result.setRecordKey(record.getKey());
    			  results.add(result);	   
    			  errorCount++;
    			  error=null;
    			  errorCount = 0;
    			  continue;
			  }
    		
                customerId=record.getFields().get(1).getIntValue();
                 if( customerId== null)	{
    			  error = "ERR_CUSTOMER_ID_NULL";
    			  result.setDone(true);
    			  result.addError(error);
    			  result.setRecordKey(record.getKey());
    			  results.add(result);	   
    			  errorCount++;
    			  error=null;
    			  errorCount = 0;
    			  continue;
			  }
    		
                destinationNumber=record.getFields().get(2).getStrValue();
				callingNumber=record.getFields().get(3).getStrValue();
               
                 try{
					  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
    	           
					
					String callStartTemp=record.getFields().get(4).getStrValue();
                    LOG.debug("callStartTemp"+callStartTemp);
				    callStart = sdf.parse(callStartTemp);
    	            LOG.debug("   callStart"+callStart);
    		        
					/* -- don't have callend 
					String callEndTemp=record.getFields().get(5).getStrValue();
					if(!callEndTemp.isEmpty()||callEndTemp!=null){
					callEnd= sdf.parse(callEndTemp);
				    LOG.debug("  parsedate endDate"+callEnd);
					} */
    		    
    		    }
				catch (Exception e) {
					LOG.error("error while reading the dates"+e);
				}
				
                 duration = record.getFields().get(5).getIntValue();
				 String costTemp = record.getFields().get(6).getStrValue();
				 cost= new BigDecimal(costTemp);
			destDescr = record.getFields().get(7).getStrValue();
			rateId	 = Integer.parseInt(record.getFields().get(8).getStrValue() == null ? "0":record.getFields().get(8).getStrValue());
			callType = record.getFields().get(9).getStrValue();
			cdrSource = record.getFields().get(10).getStrValue();
                  LOG.debug("Read all fields form csv");
				  recordCategory = "Usage Order"+callingNumber;
			
			     OrderDAS  orderDas= new OrderDAS();
			  
  		      currItem = itemDas.find(productId);
  		      OrderDTO thisOrder = null;
  		      OrderDAS oDAS = new OrderDAS();
  		      ScrollableResults orderList = oDAS.findByUser_Status(customerId,OrderStatusFlag.INVOICE); //- Modified from OrderDTO to OrderStatusDTO
  		      loadMonth1 = DateUtils.truncate(callStart,Calendar.MONTH); 
  		      while (orderList.next()) {
  		    	  thisOrder = (OrderDTO) orderList.get()[0];
  		    	  if ((thisOrder.getNotes()!= null) && thisOrder.getNotes().equals(recordCategory)) {
  		    		  Date activeSince = DateUtils.truncate(thisOrder.getActiveSince(),Calendar.MONTH);
  		    		  if (activeSince.compareTo(loadMonth1) == 0) {
  		    			  orderNumber = thisOrder.getId();
  		    			  break;
  		    		  }
  		    	  }
  		      }	 
  		      orderList.close();
  		      LOG.debug("orderNumber: updateorder  = "+ orderNumber + " " +  updateOrder);
  		      if (orderNumber == 0 && updateOrder ) {
  		    	  thisOrder = new OrderDTO();
  		    	  thisOrder.setBaseUserByUserId(new UserDAS().find(customerId));
  		    	  thisOrder.setCurrency(new CurrencyDAS().find(CURRENCY_ID));

  		    	  // set the notes, which allows us to manage the categories
  		    	  thisOrder.setNotes(recordCategory);

  		    	  // Set the order parameters - we set to pre-paid to make sure
  		    	  // that the traffic order is always billing in the next run
				  OrderStatusDAS orderStatusDAS = new OrderStatusDAS();
        ActiveOrderStatus =orderStatusDAS.find(orderStatusDAS.getDefaultOrderStatusId(OrderStatusFlag.INVOICE, thisOrder.getUser().getCompany().getId()));

  		    	  thisOrder.setOrderStatus(ActiveOrderStatus);
  		    	  thisOrder.setOrderPeriod(OrderPeriodOnce);
  		    	  thisOrder.setOrderBillingType(OrderTypePostPaid);
  		    	  thisOrder.setNotify(0);
  		    	  thisOrder.setOwnInvoice(0);
  		    	  //thisOrder.setIsCurrent(0);
  		    	  thisOrder.setDfFm(0);
  		    	  
  		    	  // Set all the order dates to the creation date (used for GL Extract)
  		    	  thisOrder.setCreateDate(new Date());
  		    	  thisOrder.setActiveSince(loadMonth1);
  		    	  thisOrder.setActiveUntil(null);

  		    	  // set the order as not deleted
  		    	  thisOrder.setDeleted(0);

  		    	  // Get the current order lines (should be none)
  		    	  orderLineList = thisOrder.getLines();

  		    	  // add the traffic order line to this order
  		    	  OrderLineDTO orderLine = new OrderLineDTO();
  		    	  orderLine.setAmount(cost);
  		    	  orderLine.setDeleted(new Integer(0));
  		    	  rateDescription = "voice call";
  		    	  orderLine.setDescription(rateDescription);
  		    	  orderLine.setQuantity(duration);
  		    	  orderLine.setPurchaseOrder(thisOrder);
  		    	  orderLine.setPrice(cost);
  		    	  orderLine.setEditable(false);
  		    	  orderLine.setUseItem(true);
  		    	  orderLine.setOrderLineType(OrderLineTypeItem);
  		    	  orderLine.setItem(currItem);
  		    	  orderLine.setCreateDatetime(new Date());
	
  		    	  // Add to the current list
  		    	  orderLineList.add(orderLine);

  		    	  // write back the order lines to the order
  		    	  thisOrder.setLines(orderLineList);
  		    	  
  		    	  // persist
  		    	  OrderDTO order = oDAS.makePersistent(thisOrder);
  		    	  resOrderLineList.add(orderLine);
          
  		    	  // read the order id back out
  		    	  orderNumber = order.getId();
				  LOG.debug(" orderNumber is " +orderNumber);
  		    	  newOrderDTO = new OrderDAS().find(orderNumber);		  
  		    	  newOrderLines = newOrderDTO.getLines();
  		    	  for(OrderLineDTO line : newOrderLines){
  		    		  if(line.getOrderLineType().getId() == Constants.ORDER_LINE_TYPE_TAX) continue;
  		    		  orderLineId = line.getId();
  		    	  }
  		    	  LOG.debug("new order is created orderId= " +orderNumber+ " & orderLineId = "+orderLineId+"for user "+customerId);
  		      } else if (orderNumber != 0 && updateOrder) {
  		    	  if (thisOrder == null) {			
  		    		  LOG.error("Null order getting order id <" + orderNumber + ">");
  		    		  continue;
  		    	  }

  		    	  // we have the order but we have to locate the item
  		    	  orderLineList = thisOrder.getLines();
  		    	  Iterator OrderLineIterator = orderLineList.iterator();
  		    	  boolean found = false;
  		    	  while (OrderLineIterator.hasNext()){
  		    		  OrderLineDTO orderLine = (OrderLineDTO) OrderLineIterator.next();
  		    		  if(orderLine.getItem().getId() == currItem.getId()) {
  		    			  OrderLineDTO resOrderLine = new OrderLineDTO();
  		    			  resOrderLine.setId(orderLine.getId());
  		    			  rateDescription = "voice call";
  		    			  resOrderLine.setDescription(rateDescription);
  		    			  resOrderLine.setQuantity(duration);
  		    			  resOrderLine.setPurchaseOrder(thisOrder);
  		    			  resOrderLine.setPrice(cost);
  		    			  resOrderLine.setAmount(cost);
  		    			  resOrderLineList.add(resOrderLine);
  		    			  orderLine.setAmount(orderLine.getAmount().add(cost));
  		    			  orderLine.setQuantity(orderLine.getQuantity().add(new BigDecimal(duration)));
  		    			  LOG.debug("updated orderLine with  total quantity: "+ orderLine.getQuantity() + " + " + duration);
  		    			  orderLineList.add(orderLine);
  		    			  thisOrder.setLines(orderLineList);
  		    			  oDAS.makePersistent(thisOrder);
  		    			  orderLineId = orderLine.getId();
  		    			  found = true;
  		    			  break;
  		    		  }
  		    	  }
  		    	  if (!found){
  		    		  // have to create a new one
  		    		  OrderLineDTO orderLine = new OrderLineDTO();
  		    		  orderLine.setAmount(cost);
  		    		  orderLine.setDeleted(0);
  		    		  rateDescription = "voice call";
  		    		  orderLine.setDescription(rateDescription);
  		    		  orderLine.setQuantity(duration);
  		    		  orderLine.setPurchaseOrder(thisOrder);
  		    		  orderLine.setPrice(cost);
  		    		  orderLine.setEditable(false);
  		    		  orderLine.setOrderLineType(OrderLineTypeItem);
  		    		  orderLine.setItem(currItem);
  		    		  orderLine.setCreateDatetime(new Date());
  		    		  resOrderLineList.add(orderLine);
  		    		  orderLineList.add(orderLine);
  		    		  thisOrder.setLines(orderLineList);
  		    		  oDAS.makePersistent(thisOrder);
  		    		  OrderDTO exOrder = new OrderDAS().find(thisOrder.getId());
  		    		  List<OrderLineDTO> newLineOrderLines = exOrder.getLines();
  		    		  for(OrderLineDTO thisOrderLines:newLineOrderLines){
  		    			  if(thisOrderLines.getItem().getId() == currItem.getId()){
  		    				  orderLineId = thisOrderLines.getId();
  		    				  break;
  		    			  }
  		    		  }
  		    	  }

  		    	  // persist
  		    	  //oDAS.makePersistent(thisOrder);

  		    	  LOG.debug("OrderLine is updated OrderlineId is " + orderLineId);
  		      }
  		      // Update the result	  
  		      if(resOrderLineList != null && updateOrder) {
  		    	  LOG.debug("resOrderLineList isn't null, setting it on result:" +resOrderLineList.size());
               // result.addLine(currItem.getId(),billsec.intValue() );
                       result.addLine(currItem.getId(),duration );

  		    	  result.setCurrencyId(CURRENCY_ID);
  		    	  result.setCurrentOrder(thisOrder);
  		    	  result.setDiffLines(resOrderLineList);
  		    	  result.setEventDate(callStart); 
  		    	  result.setRecordKey(record.getKey());
  		    	  result.setDescription(rateDescription);
  		    	  result.setDone(true);
  		    	 
  		    	  results.add(result);
  		    	  LOG.debug("reselts are "+results);
  		      }
  		      if(orderNumber != 0) {  
  		    	try {
					  LOG.debug(" orderNumber isn't 0 so saving event details");
  		    		  //int billseci = billsec.intValue();
  		    		  GotosolrCDREventDTO  gotosolrCDREventDTO =  new GotosolrCDREventDTO();
  		    		  GotosolrCDREventDAS  gotosolrCDREventDAS =  new GotosolrCDREventDAS();
  		    		  gotosolrCDREventDTO.setProcessId(111);
					  gotosolrCDREventDTO.setOrderId(orderNumber);
					  gotosolrCDREventDTO.setRecordId(keyId);
  		    		  gotosolrCDREventDTO.setUserId(customerId);
				      gotosolrCDREventDTO.setInvoiceId(0);
  		    		  gotosolrCDREventDTO.setSrc(callingNumber);
  		    		  gotosolrCDREventDTO.setDst(destinationNumber);
  		    		  gotosolrCDREventDTO.setCallStartDate(callStart);
  		    		  gotosolrCDREventDTO.setCallEndDate(callEnd);
                      gotosolrCDREventDTO.setDuration(duration);
					  gotosolrCDREventDTO.setCost(cost);
					  gotosolrCDREventDTO.setDestinationDescr(destDescr);
					  gotosolrCDREventDTO.setRateId(rateId);
					  gotosolrCDREventDTO.setCallType(callType);
					  gotosolrCDREventDTO.setCdrSource(cdrSource);
					  gotosolrCDREventDAS.save(gotosolrCDREventDTO);
    		                LOG.debug ("myNetDisk event details are saved");

  		        } catch(Exception e) {
  		    		  error = "ERROR_GOTOSOLR_EVENT_CREATION";
  		    		  LOG.error (" erorr in cdr event save " + e);
  		    		  e.printStackTrace();
  		    	  }
  		      }
     
    	  } catch(Exception e) {
    		  LOG.error("Excepiton " + e + "processing recod :  " + record.getKey());
    		  StringWriter sw = new StringWriter();
    		  e.printStackTrace(new PrintWriter(sw));
    		  LOG.error("Exception"+sw.toString());
    		  result.setRecordKey(record.getKey());
    		  result.setDone(true);
    		  result.addError(error);
    		  results.add(result);
    		  error = null;
    	  }
      }
    }

}
