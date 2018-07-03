package com.sapienter.jbilling.server.device;

import com.sapienter.jbilling.common.JBCrypto;
//import com.sapienter.jbilling.common.PermissionConstants;
//import com.sapienter.jbilling.common.PermissionIdComparator;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.invoice.db.InvoiceDAS;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.notification.INotificationSessionBean;
import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.notification.NotificationBL;
import com.sapienter.jbilling.server.notification.NotificationNotFoundException;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.payment.PaymentBL;
import com.sapienter.jbilling.server.payment.blacklist.db.BlacklistDAS;
import com.sapienter.jbilling.server.payment.blacklist.db.BlacklistDTO;
import com.sapienter.jbilling.server.payment.db.PaymentDAS;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskManager;
import com.sapienter.jbilling.server.process.AgeingBL;
import in.saralam.sbs.server.subscription.db.ServiceDAS;
import in.saralam.sbs.server.subscription.db.ServiceDTO;
//import com.sapienter.jbilling.server.user.AchBL;
import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.ContactDTOEx;
//import com.sapienter.jbilling.server.user.CreditCardBL;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.UserSQL;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.contact.db.ContactDAS;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
//import com.sapienter.jbilling.server.user.db.AchDAS;
//import com.sapienter.jbilling.server.user.db.AchDTO;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
//import com.sapienter.jbilling.server.user.db.CreditCardDTO;
import com.sapienter.jbilling.server.user.db.CustomerDAS;
import com.sapienter.jbilling.server.user.db.SubscriberStatusDAS;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.db.UserStatusDAS;
import com.sapienter.jbilling.server.user.partner.PartnerBL;
//import com.sapienter.jbilling.server.user.permisson.db.PermissionDTO;
//import com.sapienter.jbilling.server.user.permisson.db.PermissionUserDTO;
import com.sapienter.jbilling.server.user.permisson.db.RoleDAS;
import com.sapienter.jbilling.server.user.permisson.db.RoleDTO;
import com.sapienter.jbilling.server.user.tasks.IValidatePurchaseTask;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.DTOFactory;
import com.sapienter.jbilling.server.util.PreferenceBL;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;
import com.sapienter.jbilling.server.util.db.LanguageDAS;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import javax.sql.rowset.CachedRowSet;

import com.sapienter.jbilling.server.device.db.DeviceDAS;
import com.sapienter.jbilling.server.device.db.DeviceDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceDAS;
import com.sapienter.jbilling.server.device.db.UserDeviceStatusDAS;
import com.sapienter.jbilling.server.device.db.UserDeviceStatusDTO;
import in.saralam.sbs.server.subscription.ServiceBL;

import javax.naming.NamingException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
//import dk.tdc.spweb.ComtalkTDCApi;
public class UserDeviceBL extends ResultList implements UserSQL  {

	private final static Logger LOG = Logger.getLogger(UserDeviceBL.class);
	UserDeviceDAS das = new UserDeviceDAS();
	UserDeviceDTO userDevice;

	 public Integer[] getByUser(Integer userId)
	            throws SessionInternalError {
	        // find the device records first
	        try {
	            List result = new ArrayList();
	            prepareStatement(DeviceSQL.getByUser);
	            cachedResults.setInt(1, userId.intValue());
	           execute();
	            while (cachedResults.next()) {
	                result.add(new Integer(cachedResults.getInt(1)));
	            }
	            cachedResults.close();
	            conn.close();
	            // now convert the vector to an int array
	            Integer retValue[] = new Integer[result.size()];
	            result.toArray(retValue);

	            return retValue;
	        } catch (Exception e) {
	            throw new SessionInternalError(e);
	        }
	    }
	 public Integer[] getByUserAndDevice(Integer userId, Integer deviceId)
	            throws SessionInternalError {
	        // find the order records first
	        try {
	            List result = new ArrayList();
	            prepareStatement(DeviceSQL.getByUserAndDevice);
	            cachedResults.setInt(1, userId.intValue());
	            cachedResults.setInt(2, deviceId.intValue());
	            execute();
	            while (cachedResults.next()) {
	                result.add(new Integer(cachedResults.getInt(1)));
	            }
	            cachedResults.close();
	            conn.close();
	            // now convert the vector to an int array
	            Integer retValue[] = new Integer[result.size()];
	            result.toArray(retValue);

	            return retValue;
	        } catch (Exception e) {
	            throw new SessionInternalError(e);
	        }
	    } 

 	 public List<UserDeviceDTO> getUserDevice(Integer userId) {
	
		return das.findByUser(userId);

	}

	public UserDeviceDTO getUserDeviceByTelephoneNumber(String telephoneNumber) {
			LOG.debug("getUserDeviceByTelephoneNumber method is called");

                return das.findByTelephoneNumber(telephoneNumber);

        }
	
public ServiceDTO getServiceByTelephoneNumber(String telephoneNumber) {
		
		UserDeviceDTO udDto = das.findByTelephoneNumber(telephoneNumber);
		if (udDto == null)
			return null;
		Integer orderId = udDto.getOrderId().getId();
	    Integer orderLineId = udDto.getOrderLineId().getId();
		ServiceDAS serviceDAS = new ServiceDAS();
		ServiceDTO sDto = serviceDAS.findByOrderLine(orderLineId);
		return sDto;
}

	public UserDeviceDTO getEntityByExtId1(String extId1) {
		return das.findByExtId1(extId1);
	}


	public UserDeviceWS getUserDeviceByOrderAndLine(Integer orderId, Integer orderLineId) {

                UserDeviceDTO device =  das.findUserDeviceByOrderAndLine(orderId, orderLineId);
		this.userDevice = device;
		UserDeviceWS ws = getUserDeviceWS();
		if(ws != null) {
		  ws.setExtId1(device.getExtId1());
		}
		return ws;
        }

	public List<UserDeviceDTO> getUserDeviceByServiceId(Integer serviceId) {

		LOG.debug("search the user devices per service " + serviceId);
                List<UserDeviceDTO> devices=  das.findUserDeviceByService(new ServiceDAS().find(serviceId).getOrderDTO().getId(), 
			new ServiceDAS().find(serviceId).getOrderLineDTO().getId());
                return devices;
        }


	public UserDeviceWS getUserDeviceWS(Integer languageId)
	{
		UserDeviceWS retValue = new UserDeviceWS(userDevice.getId(),
			userDevice.getDevice(),
			userDevice.getBaseUser(),
			userDevice.getUserDeviceStatus(),
			userDevice.getCreatedDate(),
			userDevice.getDeleted(),
			userDevice.getOptlock());

		return retValue;

	}

	public UserDeviceWS getUserDeviceWS()
        {
		if(userDevice == null ) return null;

                UserDeviceWS retValue = new UserDeviceWS(userDevice.getId(),
                        userDevice.getDevice(),
                        userDevice.getBaseUser(),
                        userDevice.getUserDeviceStatus(),
                        userDevice.getCreatedDate(),
                        userDevice.getDeleted(),
                        userDevice.getOptlock(),
                        userDevice.getOrderId(),
                        userDevice.getOrderLineId(),
                        userDevice.getTelephoneNumber(),
                        userDevice.getIP(),
                        userDevice.getIcc());

                return retValue;

        }


	public UserDeviceBL() {
        }

	public UserDeviceBL(Integer userId) {	

		List<UserDeviceDTO> userDeviceList= getUserDevice(userId);

	}

	public UserDeviceBL(UserDeviceDTO userDevice) {

                userDevice = userDevice;

        }
	
	 public  Integer createUserDevice(Integer userId, Integer deviceId){

		try{
		 UserDeviceDTO userDeviceDto = new UserDeviceDTO();
	
		 UserDTO user = new UserDAS().find(userId);
		 DeviceDTO device = new  DeviceDAS().find(deviceId);
		 userDeviceDto.setBaseUser(user);
		 userDeviceDto.setDevice(device);
		 userDeviceDto.setCreatedDate(new Date());
		 userDevice.setLastUpdatedDate(new Date());
		 UserDeviceStatusDTO userDeviceStatus = new UserDeviceStatusDAS().find(Constants.USER_DEVICE_STATUS_INACTIVE); 
          	 userDeviceDto.setUserDeviceStatus(userDeviceStatus);
		  userDeviceDto.setIcc(device.getIcc());
		 userDeviceDto.setDeleted(0);
		 userDeviceDto.setOptlock(0);
           	 UserDeviceDAS uDAS= new UserDeviceDAS();
         
           	UserDeviceDTO userDevice= new UserDeviceDAS().save(userDeviceDto);
 Integer userDeviceId = userDevice.getId();
		if(userDevice.getId() != null) {
			WebServicesSessionSpringBean wssb = new WebServicesSessionSpringBean();
			wssb.updateDeviceStatus(device, Constants.DEVICE_STATUS_ASSIGNED);
				}
		LOG.debug("createUserDevice method returns user-device-id is = " + userDeviceId);
		}catch (Exception ex) {
		        ex.printStackTrace();
                        return null;
		}

		return userDevice.getId(); 
	  }  


 public  Integer createUserDeviceByServiceId(Integer serviceId, Integer deviceId){

	LOG.debug("createUserDevice for " + serviceId);
	try {
		UserDeviceDTO userDeviceDto = new UserDeviceDTO();
		ServiceDTO sDto = new ServiceDAS().find(serviceId);
		Integer userId = sDto.getBaseUserByUserId().getId();
		 UserDTO user = new UserDAS().find(userId);
		 DeviceDTO device = new  DeviceDAS().find(deviceId);
		 userDeviceDto.setBaseUser(user);
		 userDeviceDto.setDevice(device);
		 userDeviceDto.setCreatedDate(new Date());
		 userDeviceDto.setLastUpdatedDate(new Date());
		 UserDeviceStatusDTO userDeviceStatus = new UserDeviceStatusDAS().find(Constants.USER_DEVICE_STATUS_INACTIVE);
		 userDeviceDto.setUserDeviceStatus(userDeviceStatus);
		 userDeviceDto.setOrderId(sDto.getOrderDTO());
		 userDeviceDto.setOrderLineId(sDto.getOrderLineDTO());
		 userDeviceDto.setIcc(device.getIcc());
		 userDeviceDto.setDeleted(0);
		 userDeviceDto.setOptlock(0);
		 UserDeviceDAS uDAS= new UserDeviceDAS();

		 userDevice= uDAS.save(userDeviceDto);
		 das.flush();

		 Integer userDeviceId = userDevice.getId();
		if(userDeviceId != null) {
				WebServicesSessionSpringBean wssb = new WebServicesSessionSpringBean();
				wssb.updateDeviceStatus(device, Constants.DEVICE_STATUS_ASSIGNED);
		}
		LOG.debug("createUserDevice method returns user-device-id is = " + userDevice.getId());
		}catch (Exception ex) {
		ex.printStackTrace();
		return null;
	}

		return userDevice.getId();
	  }  
	 
	 
	 public  Integer updateUserDevice(DeviceDTO device, UserDeviceDTO userDevice){

		 //UserDeviceDTO userDeviceDto = new UserDeviceDTO();
		 
		 LOG.debug("In updateUserDevice about to update userDevice");
		 userDevice.setDevice(device);
		 Date now = new Date();
		 userDevice.setLastUpdatedDate(now);
		 new UserDeviceDAS().save(userDevice);
 		 new UserDeviceDAS().flush();
		 
		if(userDevice.getId() != null) {
		  //This mehod should have been written in this class
		  WebServicesSessionSpringBean wssb = new WebServicesSessionSpringBean();
		  wssb.updateDeviceStatus(device, Constants.DEVICE_STATUS_ASSIGNED);
		}

		LOG.debug("updateUserDevice method returns user-device-id is = " + userDevice.getId());
		return userDevice.getId();
	  }
	  
	 public  Integer updateUserDevice(DeviceDTO device, UserDeviceWS userDeviceWS){

		 //UserDeviceDTO userDeviceDto = new UserDeviceDTO();
		 
		 LOG.debug("In updateUserDevice to update userDevice with telephone number");
		 UserDeviceDTO userDevice = new UserDeviceDAS().find(userDeviceWS.getId());
		 userDevice.setDevice(device);
 		 userDevice.setTelephoneNumber(userDeviceWS.getTelephoneNumber());
		 Date now = new Date();
		 userDevice.setLastUpdatedDate(now);
		 new UserDeviceDAS().save(userDevice);
 		 new UserDeviceDAS().flush();
		 
			if(userDevice.getId() != null) {
				WebServicesSessionSpringBean wssb = new WebServicesSessionSpringBean();
				wssb.updateDeviceStatus(device, Constants.DEVICE_STATUS_ASSIGNED);
			}
	

		LOG.debug("updateUserDevice method returns user-device-id is = " + userDevice.getId());
		return userDevice.getId();
	  }

		public void updateUserDeviceStatus(UserDeviceDTO dto, Integer newStatus){
			
			UserDeviceDAS das = new UserDeviceDAS();
			UserDeviceDTO userdevice = das.find(dto.getId());
			Integer oldStatus = userdevice.getUserDeviceStatus().getId();
			if (oldStatus == newStatus){
				return;
			}
			UserDeviceStatusDTO udd = new UserDeviceStatusDAS().find(newStatus);
			userdevice.setUserDeviceStatus(udd);
			das.save(userdevice);
		}


	public void deleteUserDevice(Integer userDeviceId){
                        UserDeviceDAS das = new UserDeviceDAS();
                        UserDeviceDTO userdevice = das.find(userDeviceId);
			userdevice.setDeleted(1);
                        das.save(userdevice);
			das.flush();
                       	WebServicesSessionSpringBean wssb = new WebServicesSessionSpringBean();
                        wssb.updateDeviceStatus(userdevice.getDevice(), Constants.DEVICE_STATUS_RELEASED);
        }

		
	public boolean changeICC(UserDeviceWS userDeviceWS,String newICC){

    	  LOG.debug("In changeICC method of serviceBL with userDeviceId:"+userDeviceWS);
    	  boolean rep = true;

	  UserDeviceDTO userDeviceDTO = new UserDeviceDAS().find(userDeviceWS.getId());
	  DeviceDTO oldDevice = userDeviceDTO.getDevice();	
	  String oldICC = oldDevice.getIcc();
	  UserDTO user = new UserDAS().find(userDeviceDTO.getBaseUser().getId());

    	  DeviceDTO device = new DeviceDAS().findByICC(newICC);
    	
    	  LOG.debug("ICC is changed for service:"+user.getId());
	  rep=true;
	//  rep = new ServiceBL().changeICC(oldICC, newICC);
	  if(rep) {
	  
    	    userDeviceDTO.setDevice(device);
	    new UserDeviceDAS().makePersistent(userDeviceDTO);
	    WebServicesSessionSpringBean wssb = new WebServicesSessionSpringBean();
            wssb.updateDeviceStatus(device, Constants.DEVICE_STATUS_ASSIGNED);
	  }
          return rep;
    }

	
	

}
