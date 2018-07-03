package in.saralam.sbs.server.notification;

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
import com.sapienter.jbilling.server.user.contact.db.ContactFieldDTO;
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



public class OpenBRMNotificationBL  {

	public static final Integer TYPE_USER_OVERDUE = new Integer(3);
	public static final Integer TYPE_USER_OVERDUE2 = new Integer(4);
	public static final Integer TYPE_USER_OVERDUE3 = new Integer(5);
	public static final Integer TYPE_USER_SUSPENDED = new Integer(6);
	public static final Integer TYPE_USER_SUSPENDED2 = new Integer(7);
	public static final Integer TYPE_USER_SUSPENDED3 = new Integer(8);
	public static final Integer TYPE_USER_DELETED = new Integer(9);
	public static final Integer TYPE_USER_SMS = new Integer(25);
	public static final Integer TYPE_NEWDEVICE_EMAIL = new Integer(21);
	public static final Integer TYPE_SUBSCRIPTION_ACTIVE_EMAIL = new Integer(22);
	public static final Integer TYPE_SUBSCRIPTION_INACTIVE_EMAIL = new Integer(23);
       public static final Integer TYPE_USAGE_MONITOR_EMAIL=new Integer(24);
       public static final Integer TYPE_USER_SMS_MONITOR = new Integer(26);
       public static final Integer TYPE_CHANGE_PLAN_EMAIL = new Integer(27);
       public static final Integer TYPE_CANCEL_PLAN_EMAIL = new Integer(28);
       public static final Integer TYPE_CRM_STATUS_CHANGE = new Integer(29);
	private NotificationMessageDAS messageDas = null;
    	private NotificationMessageDTO messageRow = null;
   	private NotificationMessageSectionDAS messageSectionHome = null;
    	private NotificationMessageLineDAS messageLineHome = null;
    	private NotificationBL notif = new NotificationBL();
    	private static final Logger LOG = Logger.getLogger(NotificationBL.class);

    	public OpenBRMNotificationBL(Integer messageId)  {
        	init();
        	LOG.debug("Constructor ...");
        	messageRow = messageDas.find(messageId);
    	}
	
	public OpenBRMNotificationBL() {
                init();
        }


    	private void init() {

        	messageDas = new NotificationMessageDAS();
        	messageSectionHome = new NotificationMessageSectionDAS();
        	messageLineHome = new NotificationMessageLineDAS();
    	}

	public MessageDTO getNotificationMail(Integer entityId,Integer userId, NotificationMessageLineDTO messageLineDTO,
            Integer languageId)  throws SessionInternalError,
            NotificationNotFoundException {
		MessageDTO message = initializeMessage(entityId, userId);
		Integer notificationTypeId =  messageLineDTO.getNotificationMessageSection().getNotificationMessage().getNotificationMessageType().getId();
		 message.setTypeId(notificationTypeId);
         setContent(message, notificationTypeId, entityId, languageId);
		return message;
	}
	
	public boolean sendNotificationMail(UserDTO userdto, NotificationMessageLineDTO messageLineDTO) {
		LOG.debug("in sendSignup miail method ");
		Boolean retValue;
		UserBL user = new UserBL(userdto.getId());
		try {
			
				LOG.debug("contact is "+userdto.getContact());
			MessageDTO message = getNotificationMail(userdto.getCompany().getId(), userdto.getId(), messageLineDTO,userdto.getLanguageIdField());
			LOG.debug("message is "+message);
			INotificationSessionBean notificationSess =
					(INotificationSessionBean) Context.getBean(
							Context.Name.NOTIFICATION_SESSION);
			LOG.debug("notification sess is "+notificationSess);
			retValue = notificationSess.notify(user.getEntity(), message);
			LOG.debug("ret values is "+retValue);
		} catch (Exception e) {
			LOG.error("exception is "+e);
			retValue = new Boolean(false);
			return retValue;
		}
		return retValue;
	}
	
	public void sendNotificationSMS(UserDTO userdto, ContactDTO cdto, NotificationMessageLineDTO messageLineDTO, String userName, String password, String apiId) {
		LOG.debug("sendSignupSMS........ ");
		
		Integer userId = userdto.getId();
		//String url = null;  
		UserDAS userDas = new UserDAS();
		UserDTO user=userDas.find(userId);
		LOG.debug("user...."+user);
		LOG.debug("userId...."+userId);
			LOG.debug("apiId...."+apiId);
		//String url = getUrl();
		
			try {
				MessageDTO message = getNotificationMail(userdto.getCompany().getId(), userdto.getId(), messageLineDTO, userdto.getLanguageIdField());
				UserBL ubl = new UserBL(userId); 
				String number = cdto.getCompletePhoneNumber();
				String ph = number.replaceAll(" ", "");
				MessageSection[] mess = message.getContent();
				String str =mess[1].getContent();
				if(str.length()==0){
					str =mess[0].getContent();
				}
				ContactBL contact = new ContactBL();
				contact.set(userId);
				
				
				// The username, password and apiid is sent to the clickatell transport
				// in a Properties				
				Properties props = new Properties();

				props.setProperty("smsj.clickatell.username", userName);
				props.setProperty("smsj.clickatell.password", password);
				props.setProperty("smsj.clickatell.apiid", apiId);

				// Load the clickatell transport
				SmsTransport transport = SmsTransportManager.getTransport("org.marre.sms.transport.clickatell.ClickatellTransport", props);

				// Connect to clickatell
				transport.connect();

				// Create the sms message
				SmsTextMessage textMessage = new SmsTextMessage(str);

				// Send the sms to "461234" from "461235"
				transport.send(textMessage, new SmsAddress(ph), null);
								
				// Disconnect from clickatell
				transport.disconnect();
				LOG.debug("message sent");
			} catch (Exception e) {
				LOG.error("Exception occurred posting ageing HTTP callback for URL: " , e);
			}
	}
		
	public MessageDTO getServiceActivationEmail(Integer entityId, Integer orderId,
            Integer userId, Integer languageId) throws SessionInternalError,
            NotificationNotFoundException {

        	MessageDTO message = initializeMessage(entityId, userId);
        	message.setTypeId(TYPE_SUBSCRIPTION_ACTIVE_EMAIL);
	        setContent(message, TYPE_SUBSCRIPTION_ACTIVE_EMAIL, entityId, languageId);
		OrderBL order = new OrderBL(orderId);
		message.addParameter("orderId", order.getEntity().getId());
        	return message;
    	}
	
	

	public MessageDTO getNewDeviceEmail(Integer entityId,Integer userId, Integer deviceId,
            Integer languageId) throws SessionInternalError,
            NotificationNotFoundException {

                MessageDTO message = initializeMessage(entityId, userId);
                message.setTypeId(TYPE_NEWDEVICE_EMAIL);
                setContent(message, TYPE_NEWDEVICE_EMAIL, entityId, languageId);
                DeviceDTO dDto = new DeviceDAS().find(deviceId);
                message.addParameter("puk", dDto.getPuk1());
                return message;
        }

	public MessageDTO getCRMStatusMail(Integer entityId,Integer userId, String status,
            Integer languageId) throws SessionInternalError,
            NotificationNotFoundException {

                MessageDTO message = initializeMessage(entityId, userId);
                message.setTypeId(TYPE_CRM_STATUS_CHANGE);
                setContent(message, TYPE_CRM_STATUS_CHANGE, entityId, languageId);
                message.addParameter("status", status);
                return message;
        }
	
	public Boolean sendCRMStatusMail(Integer entityId, Integer userId, String status,
            Integer languageId) throws SessionInternalError {
		Boolean retValue;
		UserBL user = new UserBL(userId);
		try {
			MessageDTO message = getCRMStatusMail(entityId, userId, status,languageId);
        		INotificationSessionBean notificationSess =
                	(INotificationSessionBean) Context.getBean(
                		Context.Name.NOTIFICATION_SESSION);

        		retValue = notificationSess.notify(user.getEntity(), message);
		} catch (Exception e) {
           		retValue = new Boolean(false);
			return retValue;
       	}
		return retValue;
    	}
	
	
	 public MessageDTO getServiceInactivationEmail(Integer entityId, Integer orderId,
            Integer userId, Integer languageId) throws SessionInternalError,
            NotificationNotFoundException {

                MessageDTO message = initializeMessage(entityId, userId);
                message.setTypeId(TYPE_SUBSCRIPTION_INACTIVE_EMAIL);
                setContent(message, TYPE_SUBSCRIPTION_INACTIVE_EMAIL, entityId, languageId);
		OrderBL order = new OrderBL(orderId);
                message.addParameter("orderId", order.getEntity().getId());
                return message;
        }



	public Boolean sendNewDeviceEmail(Integer entityId, Integer userId, Integer deviceId,
             Integer languageId) throws SessionInternalError {
		Boolean retValue;
		UserBL user = new UserBL(userId);
		try {
			MessageDTO message = getNewDeviceEmail(entityId, userId, deviceId,languageId);
         		INotificationSessionBean notificationSess =
                 	(INotificationSessionBean) Context.getBean(
                 		Context.Name.NOTIFICATION_SESSION);

         		retValue = notificationSess.notify(user.getEntity(), message);
		} catch (Exception e) {
            		retValue = new Boolean(false);
			return retValue;
        	}
		return retValue;
     	}

	public Boolean sendServiceActivationEmail(Integer entityId, Integer userId, Integer orderId,
             Integer languageId) throws SessionInternalError {

                Boolean retValue;
                UserBL user = new UserBL(userId);
                try {
                        MessageDTO message = getServiceActivationEmail(entityId, orderId, userId, languageId);
                        INotificationSessionBean notificationSess =
                        (INotificationSessionBean) Context.getBean(
                                Context.Name.NOTIFICATION_SESSION);

                        retValue = notificationSess.notify(user.getEntity(), message);
                } catch (Exception e) {
                        retValue = new Boolean(false);
                        return retValue;
                }
                return retValue;
        }

	public Boolean sendServiceInactivationEmail(Integer entityId, Integer userId, Integer orderId,
             Integer languageId) throws SessionInternalError {

                Boolean retValue;
                UserBL user = new UserBL(userId);
                try {
                        MessageDTO message = getServiceInactivationEmail(entityId, userId, orderId, languageId);
                        INotificationSessionBean notificationSess =
                        (INotificationSessionBean) Context.getBean(
                                Context.Name.NOTIFICATION_SESSION);

                        retValue = notificationSess.notify(user.getEntity(), message);
                } catch (Exception e) {
                        retValue = new Boolean(false);
                        return retValue;
                }
                return retValue;
        }

       public MessageDTO getPlanCancelEmail(Integer entityId,Integer userId,
            Integer languageId,String planName,Date reqDate) throws SessionInternalError,
            NotificationNotFoundException {

                MessageDTO message = initializeMessage(entityId, userId);
		  message.setTypeId(TYPE_CANCEL_PLAN_EMAIL);
		  setContent(message, TYPE_CANCEL_PLAN_EMAIL, entityId, languageId);
		  message.addParameter("planName",planName);
                message.addParameter("reqDate",reqDate);
                return message;
        }
        public Boolean sendPlanCancelEmail(Integer entityId, Integer userId, 
             Integer languageId,String planName,Date reqDate) throws SessionInternalError {
		Boolean retValue;
		UserBL user = new UserBL(userId);
		try {
			LOG.debug(" in sendPlanChange email method");
			MessageDTO message = getPlanCancelEmail(entityId, userId, languageId,planName,reqDate);
         		INotificationSessionBean notificationSess =
                 	(INotificationSessionBean) Context.getBean(
                 		Context.Name.NOTIFICATION_SESSION);
                  
         		retValue = notificationSess.notify(user.getEntity(), message);
		        } catch (Exception e) {
            		retValue = new Boolean(false);
                       LOG.debug("  sendPlanCancelEmail excpetion");
			return retValue;
        	}
		return retValue;
     	}
         public MessageDTO getPlanChangeEmail(Integer entityId,Integer userId,
            Integer languageId,String oldPlanName,String newPlanName,Date newPlanDate,BigDecimal newCharge) throws SessionInternalError,
            NotificationNotFoundException {

                MessageDTO message = initializeMessage(entityId, userId);
				message.setTypeId(TYPE_CHANGE_PLAN_EMAIL);
				setContent(message, TYPE_CHANGE_PLAN_EMAIL, entityId, languageId);
				LOG.debug("  getPlanChangeEmail method"+message);
                            LOG.debug("   getPlanChangeEmail method oldPlanName"+oldPlanName);
                            LOG.debug("   getPlanChangeEmail method newPlanName"+newPlanName);
							 LOG.debug("   getPlanChangeEmail method newPlanDate"+newPlanDate);
                            LOG.debug("   getPlanChangeEmail method newCharge"+newCharge);
                            message.addParameter("oldPlanName",oldPlanName);
                            message.addParameter("newPlanName",newPlanName);
				message.addParameter("newPlanDate",newPlanDate);
                            message.addParameter("newCharge",newCharge);
			       LOG.debug(" added parameters "+message);
                return message;
        }
         public Boolean  sendPlanChangeEmail(Integer entityId, Integer userId, 
             Integer languageId,String oldPlanName,String newPlanName,Date newPlanDate,BigDecimal newCharge) throws SessionInternalError {
		Boolean retValue;
		UserBL user = new UserBL(userId);
		try {
			LOG.debug(" in sendPlanChange email method");
			MessageDTO message = getPlanChangeEmail(entityId, userId, languageId,oldPlanName,newPlanName,newPlanDate,newCharge);
         		INotificationSessionBean notificationSess =
                 	(INotificationSessionBean) Context.getBean(
                 		Context.Name.NOTIFICATION_SESSION);
                  LOG.debug("  sendPlanChangeEmail method");
         		retValue = notificationSess.notify(user.getEntity(), message);
		        } catch (Exception e) {
            		retValue = new Boolean(false);
                       LOG.error("  sendPlanChangeEmail excpetion");
			return retValue;
        	}
		return retValue;
     	}
	public MessageDTO getUsageMonitorEmail(Integer entityId,Integer userId,
            Integer languageId,BigDecimal currentBalance,Integer threshold,String description) throws SessionInternalError,
            NotificationNotFoundException {

                MessageDTO message = initializeMessage(entityId, userId);
				message.setTypeId(TYPE_USAGE_MONITOR_EMAIL);
				setContent(message, TYPE_USAGE_MONITOR_EMAIL, entityId, languageId);
				LOG.debug("  getUsageMonitorEmail method"+message);
                            LOG.debug("  getUsageMonitorEmail method threshold"+threshold);
                            LOG.debug("  getUsageMonitorEmail method resourceId description"+description);
                            message.addParameter("threshold",threshold);
                            message.addParameter("description",description);
			       LOG.debug(" added parameters ");
                return message;
        }
   public Boolean  sendUsageMonitorEmail(Integer entityId, Integer userId, 
             Integer languageId,BigDecimal currentBalance,Integer threshold,String description) throws SessionInternalError {
		Boolean retValue;
		UserBL user = new UserBL(userId);
		try {
			LOG.debug(" in sendUsageMonitorEmail method");
			MessageDTO message = getUsageMonitorEmail(entityId, userId, languageId,currentBalance,threshold,description);
         		INotificationSessionBean notificationSess =
                 	(INotificationSessionBean) Context.getBean(
                 		Context.Name.NOTIFICATION_SESSION);
                  LOG.debug("  sendUsageMonitorEmail method");
         		retValue = notificationSess.notify(user.getEntity(), message);
		        } catch (Exception e) {
            		retValue = new Boolean(false);
			return retValue;
        	}
		return retValue;
     	}
	public MessageDTO getOverDueEmail(Integer entityId, Integer userId, UserStatusDTO status,Integer languageId
			 ) throws SessionInternalError,
	            NotificationNotFoundException,
	            SQLException{
	                MessageDTO message = initializeMessage(entityId, userId);
	                if (status.getDescription().equalsIgnoreCase("Overdue")){
	                	message.setTypeId(TYPE_USER_OVERDUE);
	                	setContent(message, TYPE_USER_OVERDUE, entityId, languageId);
	                }else  if (status.getDescription().equalsIgnoreCase("Overdue 2")){
	                			message.setTypeId(TYPE_USER_OVERDUE2);
	                			setContent(message, TYPE_USER_OVERDUE2, entityId, languageId);
	                		}else  if (status.getDescription().equalsIgnoreCase("Overdue 3")){
           							message.setTypeId(TYPE_USER_OVERDUE3);
           							setContent(message, TYPE_USER_OVERDUE3, entityId, languageId);
	                				}else  if (status.getDescription().equalsIgnoreCase("Suspended")){
           									message.setTypeId(TYPE_USER_SUSPENDED);
           									setContent(message, TYPE_USER_SUSPENDED, entityId, languageId);
	                						}else  if (status.getDescription().equalsIgnoreCase("Suspended 2")){
	                								message.setTypeId(TYPE_USER_SUSPENDED2);
	                								setContent(message, TYPE_USER_SUSPENDED2, entityId, languageId);
	    	                						}else  if (status.getDescription().equalsIgnoreCase("Suspended 3")){
	    	                									message.setTypeId(TYPE_USER_SUSPENDED3);
	    	                									setContent(message, TYPE_USER_SUSPENDED3, entityId, languageId);
	    	    	                						}else  if (status.getDescription().equalsIgnoreCase("Deleted")){
	    	                									message.setTypeId(TYPE_USER_DELETED);
	    	                									setContent(message, TYPE_USER_DELETED, entityId, languageId);
	    	    	                						}
	                UserBL user = new UserBL(userId);
	                InvoiceBL invoice = new InvoiceBL();
	                Integer invoiceId = invoice.getLastByUser(userId);
	                InvoiceDTO inv = new InvoiceBL(invoiceId).getDTO();
	                message.addParameter("total", Util.decimal2string(inv.getBalance(), user.getLocale()));
	                Date dueDate = inv.getDueDate();
	                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	                String dd = df.format(dueDate);
	                message.addParameter("duedate", dd);
	                int days = (int)((new Date().getTime() - dueDate.getTime())/(1000 * 60 * 60 * 24));
	                message.addParameter("days", days);
	                return message;
	        }

	 public Boolean sendOverDueEmail(Integer entityId, Integer userId, UserStatusDTO status,
            Integer languageId) throws SessionInternalError {
			Boolean retValue;
			UserBL user = new UserBL(userId);
			try {
				
				MessageDTO message = getOverDueEmail(entityId, userId, status, languageId);
	        		INotificationSessionBean notificationSess =
	                	(INotificationSessionBean) Context.getBean(
	                		Context.Name.NOTIFICATION_SESSION);
	        		retValue = notificationSess.notify(user.getEntity(), message);
			} catch (Exception e) {
	           		retValue = new Boolean(false);
				return retValue;
	       	}
			return retValue;
	    	}

	
	 public MessageDTO getUsageMonitorSMSMessage(Integer entityId,Integer userId,Integer languageId,
			String userName, String password,Integer threshold,String description) throws SessionInternalError,
	            NotificationNotFoundException,
	            SQLException{
		// Integer userId = user.getId();           
		   MessageDTO message = initializeMessage(entityId, userId);
	          String url = null;  
		   UserDAS userDas = new UserDAS();
                 UserDTO user=userDas.find(userId);
		   LOG.debug("user...."+user);
                 LOG.debug("userId...."+userId);
                       try {
	                     LOG.debug("performAgeingCallBack called....");
	                     PreferenceBL pref = new PreferenceBL();
                            LOG.debug("  pref entity "+user.getEntity().getId());
                            pref.set(user.getEntity().getId(), Constants.PREFERENCE_URL_CALLBACK);
		              url = pref.getString();
			       LOG.debug(" below the get  String");
	                     LOG.debug("preference BL url is "+url);
	                   } catch (EmptyResultDataAccessException e) {
	                     
	                }

	                if (url != null && url.length() > 0) {
	                	   try {
					   LOG.debug(" message"+message);
	                		   message.setTypeId(TYPE_USER_SMS_MONITOR);
					   LOG.debug(" TYPE_USER_SMS_MONITOR"+TYPE_USER_SMS_MONITOR); 
					   setContent(message, TYPE_USER_SMS_MONITOR, entityId, languageId);
			                 UserBL ubl = new UserBL(userId); 
				          ContactDTO cdto = user.getContact();
					   String number = cdto.getCompletePhoneNumber();
					   LOG.debug("  number"+number);
	                               String ph = number.replaceAll(" ", "");
	                               LOG.debug("ph.no is : " +ph);
	                               LOG.debug("message is : "+message);
	                               MessageSection[] mess = message.getContent();
	                               LOG.debug("sec 1 content is : " +mess[0]);
	                         	   LOG.debug("sec 2 content is : " +mess[1]);
	                          	   LOG.debug("sec 3 content is : " +mess[2]);
	                               String str =mess[1].getContent();
                              	   String thresholdTemp = Integer.toString(threshold);
                              	  //String resourceIdTemp = Integer.toString(resourceId);
	                          	   str = str.replaceAll("&threshold", thresholdTemp);
	                          	   str = str.replaceAll("&description",description);
	                               ContactBL contact = new ContactBL();
	                               contact.set(userId);
	                               str = str.replaceAll("&first_name", contact.getEntity().getFirstName());
	                               str = str.replaceAll("&last_name", contact.getEntity().getLastName());
	                               LOG.debug("final string is : "+str);
	                               String requestUrl  = url+"?"+"username="+userName+"&password="+password+"&to="+ph+"&from="+userName+"&message="+str; 
	                               LOG.debug("url saved..."+requestUrl);
	                               message.getContent();
	                               URL url1 = new URL(requestUrl);
	                               HttpURLConnection uc = (HttpURLConnection)url1.openConnection();
                                      LOG.debug(uc.getResponseMessage());

	                               uc.disconnect();
	                               LOG.debug("message sent");
	                	   } catch (Exception e) {
	                           LOG.error("Exception occurred posting ageing HTTP callback for URL: " + url, e);
	                       }
	                }
	                
	                return message;
	        
	 }
	

      /* public MessageDTO getOverDueSMSMessage(Integer entityId, UserDTO user, UserStatusDTO status,Integer languageId,
			String userName, String password ) throws SessionInternalError,
	            NotificationNotFoundException,
	            SQLException{
		 Integer userId = user.getId();           
		 MessageDTO message = initializeMessage(entityId, userId);
	     String url = null;           
	                try {
	                	LOG.debug("performAgeingCallBack called....");
	                    PreferenceBL pref = new PreferenceBL();
	                    pref.set(user.getEntity().getId(), Constants.PREFERENCE_URL_CALLBACK);
	                    url = pref.getString();
	                    LOG.debug("preference BL url is "+url);
	                } catch (EmptyResultDataAccessException e) {
//	                     ignore, no callback preference configured 
	                }

	                if (url != null && url.length() > 0) {
	                	   try {
	                		   message.setTypeId(TYPE_USER_SMS);
								setContent(message, TYPE_USER_SMS, entityId, languageId);
	                	    UserBL ubl = new UserBL(userId);   
	       	                InvoiceBL invoice = new InvoiceBL();
	       	                Integer invoiceId = invoice.getLastByUser(userId);
	       	                InvoiceDTO inv = new InvoiceBL(invoiceId).getDTO();
	       	                String bal = Util.decimal2string(inv.getBalance(), ubl.getLocale());
	       	                Date dueDate = inv.getDueDate();
	       	                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	       	                String dd = df.format(dueDate);
//	       	                message.addParameter("duedate", dd);
	       	                int days = (int)((new Date().getTime() - dueDate.getTime())/(1000 * 60 * 60 * 24));
//	       	                message.addParameter("days", days);
	                           ContactDTO cdto = user.getContact();
	                          String number = cdto.getCompletePhoneNumber();
	                          String ph = number.replaceAll(" ", "");
	                          LOG.debug("ph.no is : " +ph);
	                           LOG.debug("message is : "+message);
	                          MessageSection[] mess = message.getContent();
	                          LOG.debug("sec 1 content is : " +mess[0]);
	                          LOG.debug("sec 2 content is : " +mess[1]);
	                          LOG.debug("sec 3 content is : " +mess[2]);
	                          String str =mess[1].getContent();
	                          str = str.replaceAll("&duedate", dd);
	                          str = str.replaceAll("&total", bal);
	                          String day = Integer.toString(days);
	                          str = str.replaceAll("&days", day);
	                          ContactBL contact = new ContactBL();
	                          contact.set(userId);
	                          str = str.replaceAll("&first_name", contact.getEntity().getFirstName());
	                          str = str.replaceAll("&last_name", contact.getEntity().getLastName());
	                          LOG.debug("final string is : "+str);
	                          String requestUrl  = url+"?"+"username="+userName+"&password="+password+"&to="+ph+"&from="+userName+"&message="+str; 
	                           LOG.debug("url saved..."+requestUrl);
	                           message.getContent();
	                           
	                           URL url1 = new URL(requestUrl);
	                           HttpURLConnection uc = (HttpURLConnection)url1.openConnection();

	                           LOG.debug(uc.getResponseMessage());

	                           uc.disconnect();
	                           LOG.debug("message sent");
	                	   } catch (Exception e) {
	                           LOG.error("Exception occurred posting ageing HTTP callback for URL: " + url, e);
	                       }
	                }
	                
	                return message;
	        
	 }*/

	
	public void set(Integer type, Integer languageId, Integer entityId) {
                messageRow = messageDas.findIt(type, entityId, languageId);

	}

	private void setContent(MessageDTO newMessage, Integer type,
		 Integer entity, Integer language) throws SessionInternalError, NotificationNotFoundException {

        	set(type, language, entity);
        	if (messageRow != null) {
            		if (messageRow.getUseFlag() == 0) {
                	// if (messageRow.getUseFlag().intValue() == 0) {
                	throw new NotificationNotFoundException("Notification " + "flaged for not use");
            	}
            	setContent(newMessage);
        	} else {
            	String message = "Looking for notification message type " + type + " for entity " +
                    entity + " language " + language + " but could not find it. This entity has " +
                    "to specify " + "this notification message.";
            	LOG.warn(message);
            	throw new NotificationNotFoundException(message);
        	}

    	}

	
	private void setContent(MessageDTO newMessage) throws SessionInternalError {

        // go through the sections
        Collection sections = messageRow.getNotificationMessageSections();
        for (Iterator it = sections.iterator(); it.hasNext();) {
            NotificationMessageSectionDTO section = (NotificationMessageSectionDTO) it
                    .next();
            // then through the lines of this section
            StringBuffer completeLine = new StringBuffer();
            Collection lines = section.getNotificationMessageLines();
            int checkOrder = 0; // there's nothing to assume that the lines
            // will be retrived in order, but the have to!
            List vLines = new ArrayList<NotificationMessageSectionDTO>(lines);
            Collections.sort(vLines, new NotificationLineEntityComparator());
            for (Iterator it2 = vLines.iterator(); it2.hasNext();) {
                NotificationMessageLineDTO line = (NotificationMessageLineDTO) it2 .next();
                if (line.getId() <= checkOrder) {
                    LOG.error("Lines have to be retreived in order. "
                            + "See class java.util.TreeSet for solution or "
                            + "Collections.sort()");
                    throw new SessionInternalError("Lines have to be "
                            + "retreived in order.");
                } else {
                    checkOrder = line.getId();
                    // checkOrder = line.getId().intValue();
                }
                completeLine.append(line.getContent());
            }
            // add the content of this section to the message
            MessageSection sectionContent = new MessageSection(section
                    .getSection(), completeLine.toString());
            newMessage.addSection(sectionContent);
	}
	}


	private MessageDTO initializeMessage(Integer entityId, Integer userId)
            throws SessionInternalError {
		LOG.debug("In intializeMessage() with userId:"+userId);
        MessageDTO retValue = new MessageDTO();
        try {
            UserBL user = new UserBL(userId);
            ContactBL contact = new ContactBL();

            // this user's info
            contact.set(userId);
            if (contact.getEntity() != null) {
                retValue.addParameter("contact", contact.getEntity());

                retValue.addParameter("first_name", contact.getEntity().getFirstName());
                retValue.addParameter("last_name", contact.getEntity().getLastName());
                retValue.addParameter("address1", contact.getEntity().getAddress1());
                retValue.addParameter("address2", contact.getEntity().getAddress2());
                retValue.addParameter("city", contact.getEntity().getCity());
                retValue.addParameter("organization_name", contact.getEntity().getOrganizationName());
                retValue.addParameter("postal_code", contact.getEntity().getPostalCode());
                retValue.addParameter("state_province", contact.getEntity().getStateProvince());
            }

            if (user.getEntity() != null) {
                retValue.addParameter("user", user.getEntity());

                retValue.addParameter("username", user.getEntity().getUserName());
                retValue.addParameter("password", user.getEntity().getPassword());
                retValue.addParameter("user_id", user.getEntity().getUserId().toString());
            }

            if (user.getCreditCard() != null) {
                retValue.addParameter("credit_card", user.getCreditCard());
            }
// the entity info
            contact.setEntity(entityId);
            if (contact.getEntity() != null) {
                retValue.addParameter("company_contact", contact.getEntity());

                retValue.addParameter("company_id", entityId.toString());
                retValue.addParameter("company_name", contact.getEntity().getOrganizationName());
            }

            //velocity tools
            retValue.addParameter("tools-date", new DateTool());
            retValue.addParameter("tools-math", new MathTool());
            retValue.addParameter("tools-number", new NumberTool());
            retValue.addParameter("tools-render", new RenderTool());
            retValue.addParameter("tools-escape", new EscapeTool());
            retValue.addParameter("tools-resource", new ResourceTool());
            retValue.addParameter("tools-alternator", new AlternatorTool());
            retValue.addParameter("tools-valueParser", new ValueParser());
            retValue.addParameter("tools-list", new ListTool());
            retValue.addParameter("tools-sort", new SortTool());
            retValue.addParameter("tools-iterator", new IteratorTool());

            //Adding a CCF Field to Email Template
            List<ContactDTOEx> listDto= contact.getAll(userId);
            if (null != listDto && listDto.size() > 0 ) {
                ContactDTOEx contactDTOEx= listDto.get(0);
                Map<String, ContactFieldDTO> fieldsMap= contactDTOEx.getFieldsTable();
                for (Iterator<?> it= fieldsMap.values().iterator();it.hasNext(); ) {
                     ContactFieldDTO contactFieldDTO= (ContactFieldDTO) it.next();
                     if ( null != contactFieldDTO && null != contactFieldDTO.getContent()
                        && null != contactFieldDTO.getType())
                     {
                        retValue.addParameter(contactFieldDTO.getType().getPromptKey(), contactFieldDTO.getContent());
                     }
}
            }

        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
        return retValue;
    }


}
