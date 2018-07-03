package in.saralam.sbs.server.notification;

import java.util.List;
import in.saralam.sbs.server.crm.event.CRMEvent;
import in.saralam.sbs.server.notification.db.NotificationConfigDAS;
import in.saralam.sbs.server.notification.db.NotificationConfigDTO;
import org.apache.log4j.Logger;
import com.sapienter.jbilling.server.order.event.NewOrderEvent;
import com.sapienter.jbilling.server.order.event.PeriodCancelledEvent;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.admin.ParameterDescription;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.service.event.ChangePlanEvent;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.user.event.NewCustomerEvent;
import java.util.Properties;
import org.marre.SmsSender;
import org.marre.sms.SmsAddress;

import org.marre.sms.transport.SmsTransport;
import org.marre.sms.transport.SmsTransportManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.List;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;
import org.marre.SmsSender;
import org.marre.sms.SmsAddress;
import org.marre.sms.SmsTextMessage;
import org.marre.sms.transport.SmsTransport;
import org.marre.sms.transport.SmsTransportManager;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.invoice.InvoiceBL;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.invoice.db.InvoiceLineDTO;
import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.notification.db.NotificationMessageDAS;
import com.sapienter.jbilling.server.notification.db.NotificationMessageDTO;
import com.sapienter.jbilling.server.notification.db.NotificationMessageLineDAS;
import com.sapienter.jbilling.server.notification.db.NotificationMessageLineDTO;
import com.sapienter.jbilling.server.notification.db.NotificationMessageSectionDAS;
import com.sapienter.jbilling.server.notification.db.NotificationMessageSectionDTO;
import com.sapienter.jbilling.server.notification.NotificationLineEntityComparator;
import com.sapienter.jbilling.server.notification.MessageSection;
import com.sapienter.jbilling.server.payment.PaymentBL;
import com.sapienter.jbilling.server.payment.PaymentDTOEx;
import com.sapienter.jbilling.server.pluggableTask.NotificationTask;
import com.sapienter.jbilling.server.pluggableTask.PaperInvoiceNotificationTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskBL;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskManager;
import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.ContactDTOEx;
import com.sapienter.jbilling.server.user.EntityBL;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
//import com.sapienter.jbilling.server.user.contact.db.ContactFieldDTO;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
//import com.sapienter.jbilling.server.user.db.CreditCardDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.db. UserDAS;
import com.sapienter.jbilling.server.user.db.UserStatusDTO;
import com.sapienter.jbilling.server.user.partner.PartnerBL;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.PreferenceBL;
import com.sapienter.jbilling.server.util.Util;
import com.sapienter.jbilling.server.order.OrderBL;

import org.apache.log4j.Logger;
import org.hibernate.collection.PersistentSet;

import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.rowset.CachedRowSet;



import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.dao.EmptyResultDataAccessException;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.apache.velocity.tools.generic.RenderTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.ResourceTool;
import org.apache.velocity.tools.generic.AlternatorTool;
import org.apache.velocity.tools.generic.ValueParser;
import org.apache.velocity.tools.generic.ListTool;
import org.apache.velocity.tools.generic.SortTool;
import org.apache.velocity.tools.generic.IteratorTool;

import java.sql.*;

import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.notification.NotificationBL;
import com.sapienter.jbilling.server.notification.NotificationSessionBean;
import com.sapienter.jbilling.server.notification.INotificationSessionBean;
import com.sapienter.jbilling.server.notification.NotificationNotFoundException;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.device.db.DeviceDAS;
import com.sapienter.jbilling.server.device.db.DeviceDTO;
import com.sapienter.jbilling.common.SessionInternalError;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import in.saralam.sbs.server.BulkNotification.DataInsertionDAS;

public class OpenBRMNotificationTask  extends PluggableTask implements IInternalEventsTask {
	
	public static final ParameterDescription PARAMETER_URL =	
			new ParameterDescription("url", false, ParameterDescription.Type.STR);
	public static final ParameterDescription PARAMETER_USERNAME =
			new ParameterDescription("userName", false, ParameterDescription.Type.STR);
	public static final ParameterDescription PARAMETER_PASSWORD =
			new ParameterDescription("password", false, ParameterDescription.Type.STR);
	public static final ParameterDescription PARAMETER_API =
			new ParameterDescription("apiID", false, ParameterDescription.Type.STR);
	public static final ParameterDescription PARAMETER_SIGNATURE =
			new ParameterDescription("signature", false, ParameterDescription.Type.STR);
	{
		descriptions.add(PARAMETER_URL);
		descriptions.add(PARAMETER_USERNAME);
		descriptions.add(PARAMETER_PASSWORD);
		descriptions.add(PARAMETER_SIGNATURE);
		descriptions.add(PARAMETER_API);
	}
	
	public String getUserName(){
		String name=getParameter(PARAMETER_USERNAME.getName(), (String)null);
		return name;
	} 
	public String getPassword(){
		String pwd=getParameter(PARAMETER_PASSWORD.getName(), (String)null);
		return pwd;
	}
	
	public String getUrl(){
		String url=getParameter(PARAMETER_URL.getName(), (String)null);
		return url;
	} 
	
	public String getApi(){
		String apiId=getParameter(PARAMETER_API.getName(), (String)null);
		return apiId;
	} 
	
	public String getSignature(){
		String pwd=getParameter(PARAMETER_SIGNATURE.getName(), (String)null);
		return pwd;
	}
	
	private static final Class<Event> events[] = new Class[] 
			{ NewCustomerEvent.class,NewOrderEvent.class, ChangePlanEvent.class, PeriodCancelledEvent.class, CRMEvent.class};
	private static final Logger LOG = Logger.getLogger(OpenBRMNotificationTask.class);
	
	@Override
	public void process(Event event) throws PluggableTaskException {
		LOG.debug("in newnotificationtask process method..");
		NotificationConfigDAS notifyDAS = new NotificationConfigDAS();
		//NotificationConfigDTO notifyDTO = new NotificationConfigDTO();
		String eventName = null;
		OpenBRMNotificationBL obNotificationBL = new OpenBRMNotificationBL();
		String requestUrl  = getUrl()+"?"+"user="+getUserName()+"&password="+getPassword()+"&api_id="+getApi();
		if(event instanceof NewCustomerEvent){
			eventName = "Sign up";
			List<NotificationConfigDTO> notifyList = notifyDAS.findConfigByEventName(eventName);
			LOG.debug("conact before if "+((NewCustomerEvent) event).getContact());
			for(NotificationConfigDTO notifyDTO : notifyList){
			if(notifyDTO!=null && notifyDTO.getDeleted()==0){
				((NewCustomerEvent) event).getUser().setContact(((NewCustomerEvent) event).getContact());
				LOG.debug("contact is "+((NewCustomerEvent) event).getUser().getContact());
				LOG.debug("notification configuration exist with event "+eventName);
				if(notifyDTO.getNotifyType().getDescription().equalsIgnoreCase("Email"))
					obNotificationBL.sendNotificationMail(((NewCustomerEvent) event).getUser(), notifyDTO.getMessageId());
				if(notifyDTO.getNotifyType().getDescription().equalsIgnoreCase("SMS"))
					obNotificationBL.sendNotificationSMS(((NewCustomerEvent) event).getUser(), ((NewCustomerEvent) event).getContact(), notifyDTO.getMessageId(), getUserName(),getPassword(), getApi());
			}
			}
		}else if(event instanceof NewOrderEvent){
			eventName = "New Order";
			List<NotificationConfigDTO> notifyList = notifyDAS.findConfigByEventName(eventName);
			for(NotificationConfigDTO notifyDTO : notifyList){
			if(notifyDTO!=null && notifyDTO.getDeleted()==0){
				LOG.debug("notification configuration exist with event "+eventName);
				if(notifyDTO.getNotifyType().getDescription().equalsIgnoreCase("Email")){
					LOG.debug("new order email......");
					obNotificationBL.sendNotificationMail(((NewOrderEvent) event).getOrder().getUser(),  notifyDTO.getMessageId());
				}
				if(notifyDTO.getNotifyType().getDescription().equalsIgnoreCase("SMS")){
					LOG.debug("new order SMS......");
					obNotificationBL.sendNotificationSMS(((NewOrderEvent) event).getOrder().getUser(), ((NewOrderEvent) event).getOrder().getUser().getContact(), notifyDTO.getMessageId(), getUserName(),getPassword(), getApi());
				}
			}
			}
		}else if(event instanceof ChangePlanEvent){
			LOG.debug("change plan event task....");
			eventName = "Change plan";
			List<NotificationConfigDTO> notifyList = notifyDAS.findConfigByEventName(eventName);
			for(NotificationConfigDTO notifyDTO : notifyList){
			if(notifyDTO!=null && notifyDTO.getDeleted()==0){
				LOG.debug("notification configuration exist with event "+eventName);
				if(notifyDTO.getNotifyType().getDescription().equalsIgnoreCase("Email"))
					obNotificationBL.sendNotificationMail(((ChangePlanEvent) event).getUser(),  notifyDTO.getMessageId());
				if(notifyDTO.getNotifyType().getDescription().equalsIgnoreCase("SMS"))
					obNotificationBL.sendNotificationSMS(((ChangePlanEvent) event).getUser(), ((ChangePlanEvent) event).getUser().getContact(), notifyDTO.getMessageId(), getUserName(),getPassword(), getApi());
			}
			}
		}else if(event instanceof CRMEvent){
			List<NotificationConfigDTO> notifyList = new NotificationConfigDAS().findConfigByEventName(((CRMEvent) event).getEventName());
			for(NotificationConfigDTO notifyDTO : notifyList){
			if(notifyDTO!=null && notifyDTO.getDeleted()==0){
				LOG.debug("notification configuration exist with event "+((CRMEvent) event).getEventName());
				
				if(notifyDTO.getNotifyType().getDescription().equalsIgnoreCase("Email"))
					obNotificationBL.sendNotificationMail(((CRMEvent) event).getUser(),  notifyDTO.getMessageId());
				if(notifyDTO.getNotifyType().getDescription().equalsIgnoreCase("SMS"))
					obNotificationBL.sendNotificationSMS(((CRMEvent) event).getUser(), ((CRMEvent) event).getUser().getContact(), notifyDTO.getMessageId(), getUserName(),getPassword(), getApi());
							
		}
			}
		}
	}
	
	
	public String sendNotificationSMS(String phoneNumber, String message) {
		
		    String sendSMS;
			String temp = phoneNumber;
			String[] splitString = temp.split("\\;");
			
		for (int i=0; i<splitString.length; i++) {
		
		try {
				String number = splitString[i];
				String ph = number.replaceAll(" ", "");
				
				String username = "labwisesms";
				String password = "lBjVRGIr";	
				String apiId = "3457486";			
				// The username, password and apiid is sent to the clickatell transport
				// in a Properties				
				Properties props = new Properties();						
				props.setProperty("smsj.clickatell.username", username);				
				props.setProperty("smsj.clickatell.password", password);		
				props.setProperty("smsj.clickatell.apiid", apiId);
				
				
				// Load the clickatell transport
				SmsTransport transport = SmsTransportManager.getTransport("org.marre.sms.transport.clickatell.ClickatellTransport", props);

				// Connect to clickatell
				transport.connect();
				// Create the sms message
				SmsTextMessage textMessage = new SmsTextMessage(message);
				// Send the sms to "461234" from "461235"
				sendSMS = transport.send(textMessage, new SmsAddress(ph), null);
				// Disconnect from clickatell
				transport.disconnect();
				LOG.debug("message sent");
				
		
			} catch (Exception e) {
				LOG.error("Exception occurred posting ageing HTTP callback for URL: " , e);
				
					}
			
			}			
			return phoneNumber;
	}
	
	@Override
	public Class<Event>[] getSubscribedEvents() {
		return events;
	}
	
}
