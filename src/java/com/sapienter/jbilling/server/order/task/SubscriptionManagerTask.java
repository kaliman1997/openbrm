package com.sapienter.jbilling.server.order.task;

import java.math.BigDecimal;
import java.util.*;
import java.lang.*;
import java.lang.reflect.Array;
import java.text.*;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.Constants;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.item.db.ItemTypeDAS;
import com.sapienter.jbilling.server.item.db.ItemTypeDTO;
import com.sapienter.jbilling.server.mediation.Record;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDTO;
import com.sapienter.jbilling.server.order.event.NewOrderEvent;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import in.saralam.sbs.server.subscription.ServiceBL;
import in.saralam.sbs.server.subscription.ServiceFeatureBL;
import in.saralam.sbs.server.subscription.db.ServiceDTO;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.item.event.NewItemEvent;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
//import com.sapienter.jbilling.server.user.event.NewCustomerEvent;
import com.sapienter.jbilling.server.user.event.NewContactEvent;
import com.sapienter.jbilling.server.pluggableTask.admin.ParameterDescription;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.ContactWS;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import com.sapienter.jbilling.server.user.contact.db.ContactDAS;

import java.io.*;

import com.sapienter.jbilling.common.Util;

import java.util.ArrayList;

public class SubscriptionManagerTask  extends PluggableTask implements IInternalEventsTask {
	
	private static final Logger LOG =
	Logger.getLogger(SubscriptionManagerTask.class);
	
	private String orderId;
	private String orderLineId;
	private String itemCat;
	private Integer entityId;
	private OrderDTO order;
	private ContactDTO contact;
	private UserDTO user;
	private ItemDTO itemdto;
	private ServiceDTO service;
	private ServiceBL serviceBL;
	private ServiceFeatureBL serviceFeatureBL;
	private Integer serviceId=0;
	
	Boolean provisionable=false;
	Map<String, List> mailingListtwo = new HashMap<String, List>(); 
	ArrayList<String> mailingList = new ArrayList<String>();
	Integer itemId;
	List<OrderLineDTO> orderLines;
	List<Integer> itemList = new ArrayList<Integer>();
	List<String> interNum = new ArrayList<String>();   // used to store item details 
	Set<ItemDTO> items;
	



	@SuppressWarnings("unchecked")
	private static final Class<Event> events[] = new Class[] 
	{ NewOrderEvent.class};	

	public Class<Event>[] getSubscribedEvents() {
		return events;
	}
	public void process(Event event) throws PluggableTaskException {

		if (event instanceof NewOrderEvent) {
			NewOrderEvent newOrder = (NewOrderEvent) event;	
			LOG.debug("Handling NewOrderEvent in SubscriptionMangaerTask");
			
			entityId=newOrder.getEntityId();
			order=newOrder.getOrder();
			orderLines = order.getLines();
			user = order.getBaseUserByUserId();
			LOG.debug("useId is:"+user.getId());
			
			ContactBL cBl= new ContactBL();
    		cBl.set(user.getId());
    		contact = cBl.getEntity();
    		if(contact == null || contact.equals(null))
    		{
    			LOG.debug("contact is null");
    		} 
		 }    	
		
		
		
  	  List<OrderLineDTO> orderLines = order.getLines();
  	  Iterator it = orderLines.iterator();
  	    while(it.hasNext()){
  	      LOG.debug("Looping OrderLines");
  	      OrderLineDTO orderLine = (OrderLineDTO)it.next();
  	
  		  Set<ItemTypeDTO> itemType = orderLine.getItem().getItemTypes();
  		  Iterator<ItemTypeDTO> itemIt = itemType.iterator();
  		    for (Iterator<ItemTypeDTO> iti = itemType.iterator(); iti.hasNext(); ) {
  		
  			  ItemTypeDTO itemTypeDTO = (ItemTypeDTO)iti.next();
  				if(itemTypeDTO.getDescription().equals("Provisionable")){
				    LOG.debug("creating service for provisionable item");
			    	    serviceBL = new ServiceBL();    	    
			    	    serviceId = serviceBL.create(user, order, orderLine);
				    LOG.debug("Service id is:"+serviceId);
				      if(serviceId!=null && serviceId !=0){
					  LOG.debug("Creating ServiceFeature Object");
					  serviceFeatureBL= new ServiceFeatureBL();
                      Integer serviceFeatureServiceId=serviceFeatureBL.create(serviceId);
					  LOG.debug("Called ServiceFeature create method");
					  LOG.debug("serviceFeatureServiceId:"+serviceFeatureServiceId);
						}
  				}
  			  else{
  				LOG.debug("Its not a Provisionable item");
  			  }
  	        }
  	    }			

		
    		//String custrName  = contact.getFirstName()+contact.getLastName();    			
    		//Date userCreateDate = user.getCreateDatetime();  

    		
	}
}
	