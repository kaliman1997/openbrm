/*
 * JBILLING CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise jBilling Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise jBilling Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise jBilling Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */
package com.sapienter.jbilling.server.device;

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
/*import com.sapienter.jbilling.server.order.db.OrderBillingTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDAS;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDAS;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.order.db.OrderProcessDTO;
import com.sapienter.jbilling.server.order.db.OrderStatusDAS;*/
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
import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import javax.sql.rowset.CachedRowSet;
import com.sapienter.jbilling.server.device.db.DeviceDAS;
import com.sapienter.jbilling.server.device.db.DeviceDTO;
import com.sapienter.jbilling.server.device.db.DeviceTypeDTO;
import com.sapienter.jbilling.server.device.db.DeviceStatusDTO;
import com.sapienter.jbilling.server.device.DeviceSQL;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.device.db.DeviceStatusDAS;
import com.sapienter.jbilling.server.device.db.DeviceTypeDAS;

/*--IO packages--*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;
import org.xml.sax.SAXException;
import com.sapienter.jbilling.server.pluggableTask.admin.ParameterDescription;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.mediation.Format;
import com.sapienter.jbilling.server.mediation.FormatField;
import com.sapienter.jbilling.server.mediation.Record;

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

/**
 * @author Emil
 */
public class DeviceBL extends ResultList
         {
    //private String directory;
    private DeviceDTO device = null;
    //private OrderLineDAS orderLineDAS = null;
    //private OrderPeriodDAS orderPeriodDAS = null;
    private DeviceDAS deviceDas = null;
    //private OrderBillingTypeDAS orderBillingTypeDas = null;
   // private ProvisioningStatusDAS provisioningStatusDas = null;
    private static final Logger LOG = Logger.getLogger(DeviceBL.class);
    private EventLogger eLogger = null;
    
    /*--parameters added--*/
    /*-s-public static final ParameterDescription PARAMETER_UPLOAD_FILE =
    	new ParameterDescription("buffer_size", false, ParameterDescription.Type.STR);
    
    {
    	descriptions.add(PARAMETER_UPLOAD_FILE);
    }-s-*/
    

    public DeviceBL(Integer deviceId) {
        init();
        set(deviceId);
    }

    public DeviceBL() {
        init();
    }

    public DeviceBL(DeviceDTO device) {
        init();
        this.device = device;
    }

    private void init() {
        eLogger = EventLogger.getInstance();
       // orderLineDAS = new OrderLineDAS();
        //orderPeriodDAS = new OrderPeriodDAS();
        deviceDas = new DeviceDAS();
        //orderBillingTypeDas = new OrderBillingTypeDAS();
        //provisioningStatusDas = new ProvisioningStatusDAS();
    }

    public DeviceDTO getEntity() {
        return device;
    }

    /*--public OrderPeriodDTO getPeriod(Integer language, Integer id) {
        return (orderPeriodDAS.find(id));
    }--*/

    public void set(Integer id) {
    	device = deviceDas.find(id);
    }

    public void setForUpdate(Integer id) {
    	device = deviceDas.findForUpdate(id);
    }

    public void set(DeviceDTO newDevice) {
    	device = newDevice;
    }


  /*-s-public OrderWS getWS(Integer languageId) {
        OrderWS retValue = new OrderWS(order.getId(), order.getBillingTypeId(),
                                       order.getNotify(), order.getActiveSince(), order.getActiveUntil(),
                                       order.getCreateDate(), order.getNextBillableDay(),
                                       order.getCreatedBy(), order.getStatusId(), order.getDeleted(),
                                       order.getCurrencyId(), order.getLastNotified(),
                                       order.getNotificationStep(), order.getDueDateUnitId(),
                                       order.getDueDateValue(), order.getAnticipatePeriods(),
                                       order.getDfFm(), order.getIsCurrent(), order.getNotes(),
                                       order.getNotesInInvoice(), order.getOwnInvoice(),
                                       order.getOrderPeriod().getId(),
                                       order.getBaseUserByUserId().getId(),
                                       order.getVersionNum(), order.getCycleStarts());

        retValue.setTotal(order.getTotal());

        retValue.setPeriodStr(order.getOrderPeriod().getDescription(languageId));
        retValue.setStatusStr(order.getOrderStatus().getDescription(languageId));
        retValue.setBillingTypeStr(order.getOrderBillingType().getDescription(languageId));

        List<OrderLineWS> lines = new ArrayList<OrderLineWS>();
        for (Iterator it = order.getLines().iterator(); it.hasNext();) {
            OrderLineDTO line = (OrderLineDTO) it.next();
            if (line.getDeleted() == 0) {
                lines.add(getOrderLineWS(line.getId()));
            }
        }
        //this will initialized Generated Invoices in the OrderDTO instance
        order.addExtraFields(languageId);
        retValue.setGeneratedInvoices(new InvoiceBL().DTOtoWS(new ArrayList(order.getInvoices())));
        retValue.setOrderLines(new OrderLineWS[lines.size()]);
        lines.toArray(retValue.getOrderLines());
        return retValue;
    }-s- */

    public DeviceDTO getDTO() {
        return device;
    }

    /*-s- ----
     * public void addItem(DeviceDTO device, Integer itemID, BigDecimal quantity, Integer language, Integer userId,
     
                        Integer entityId, Integer currencyId, List<Record> records) throws ItemDecimalsException {

        try {
            PluggableTaskManager<IItemPurchaseManager> taskManager =
                    new PluggableTaskManager<IItemPurchaseManager>(entityId,
                                                                   Constants.PLUGGABLE_TASK_ITEM_MANAGER);
            IItemPurchaseManager myTask = taskManager.getNextClass();

            while (myTask != null) {
                myTask.addItem(itemID, quantity, language, userId, entityId, currencyId, device, records);
                myTask = taskManager.getNextClass();
            }

        } catch (PluggableTaskException e) {
            throw new SessionInternalError("Item Manager task error", DeviceBL.class, e);
        } catch (TaskException e) {
            if (e.getCause() instanceof ItemDecimalsException) {
                throw (ItemDecimalsException) e.getCause();
            } else {
                // do not change this error text, it is used to identify the error
                throw new SessionInternalError("Item Manager task error", DeviceBL.class, e);
            }
        }

    }

    public void addItem(Integer itemID, BigDecimal quantity, Integer language, Integer userId,
                        Integer entityId, Integer currencyId, List<Record> records) {

        addItem(this.device, itemID,  quantity, language, userId, entityId, currencyId, records);
    }

    public void addItem(Integer itemID, BigDecimal quantity, Integer language, Integer userId, Integer entityId,
                        Integer currencyId) throws ItemDecimalsException {
        addItem(itemID, quantity, language, userId, entityId, currencyId, null);
    }

    public void addItem(Integer itemID, Integer quantity, Integer language, Integer userId, Integer entityId,
                        Integer currencyId, List<Record> records) throws ItemDecimalsException {
        addItem(itemID, new BigDecimal(quantity), language, userId, entityId, currencyId, records);
    }

    public void addItem(Integer itemID, Integer quantity, Integer language, Integer userId, Integer entityId,
                        Integer currencyId) throws ItemDecimalsException {
        addItem(itemID, new BigDecimal(quantity), language, userId, entityId, currencyId, null);
    }

    public void deleteItem(Integer itemID) {
    	device.removeLine(itemID);
    }
*/
    public void delete(Integer executorId) {
        // the event is needed before the deletion
       /*-s- EventManager.process(new ServiceDeletedEvent(
                device.getBaseUserByUserId().getCompany().getId(), device));*/

       /* for (ServiceLineDTO line : device.getLines()) {
            line.setDeleted(1);
        }
        device.setDeleted(1);

        eLogger.audit(executorId, device.getBaseUserByUserId().getId(),
                Constants.TABLE_PUCHASE_ORDER, device.getId(),
                EventLogger.MODULE_ORDER_MAINTENANCE,
                EventLogger.ROW_DELETED, null, null, null);*/


    }

    
    
    public void update(Integer executorId, DeviceDTO dto) {
        // update first the device own fields
        /*-s- if (!Util.equal(device.getActiveUntil(), dto.getActiveUntil())) {
            updateActiveUntil(executorId, dto.getActiveUntil(), dto);
        }
        if (!Util.equal(device.getActiveSince(), dto.getActiveSince())) {
            audit(executorId, device.getActiveSince());
            device.setActiveSince(dto.getActiveSince());
        }
        setStatus(executorId, dto.getStatusId());

         if (device.getServicePeriod().getId() != dto.getServicePeriod().getId()) {
            audit(executorId, device.getServicePeriod().getId());
            device.setServicePeriod(devicePeriodDAS.find(dto.getServicePeriod().getId()));
        }

        // set the provisioning status
        for (ServiceLineDTO line : dto.getLines()) {
            // set default provisioning status id for device lines
            if (line.getProvisioningStatus() == null) {
                line.setProvisioningStatus(provisioningStatusDas.find(
                        Constants.PROVISIONING_STATUS_INACTIVE));
            } else {
                line.setProvisioningStatus(provisioningStatusDas.find(
                        line.getProvisioningStatus().getId()));
            }
        }*/

        // this should not be necessary any more, since the device is a pojo...
       /* -s-  device.setServiceBillingType(dto.getServiceBillingType());
        device.setNotify(dto.getNotify());
        device.setDueDateUnitId(dto.getDueDateUnitId());
        device.setDueDateValue(dto.getDueDateValue());
        device.setDfFm(dto.getDfFm());
        device.setAnticipatePeriods(dto.getAnticipatePeriods());
        device.setOwnInvoice(dto.getOwnInvoice());
        device.setNotes(dto.getNotes());
        device.setNotesInInvoice(dto.getNotesInInvoice());
        device.setCycleStarts(dto.getCycleStarts());
        if (dto.getIsCurrent() != null && dto.getIsCurrent().intValue() == 1) {
            setMainSubscription(executorId);
        }
        if (dto.getIsCurrent() != null && dto.getIsCurrent().intValue() == 0) {
            unsetMainSubscription(executorId);
        }
        // this one needs more to get updated
        updateNextBillableDay(executorId, dto.getNextBillableDay());-s-*/

        /**
         *  now proces the device lines
         */

        // get new quantity events as necessary
         /*-s- List<NewQuantityEvent> events = checkServiceLineQuantities(device.getLines(), dto.getLines(),
                device.getBaseUserByUserId().getCompany().getId(), 
                device.getId(), false); // do not send them now, it will be done later when the device is saved

        ServiceLineDTO oldLine = null;
        int nonDeletedLines = 0;
        // Determine if the item of the device changes and, if it is,
        // LOG a subscription change event.
        LOG.info("Service lines: " + device.getLines().size() + "  --> new Service: " +
                dto.getLines().size());
        if (dto.getLines().size() == 1 &&
                device.getLines().size() >= 1) {
            // This event needs to LOG the old item id and description, so
            // it can only happen when updating devices with only one line.

            for (Iterator i = device.getLines().iterator(); i.hasNext();) {
                // Check which device is not deleted.
                ServiceLineDTO temp = (ServiceLineDTO) i.next();
                if (temp.getDeleted() == 0) {
                    oldLine = temp;
                    nonDeletedLines++;
                }
            }
        }

        // now update this device's lines
        device.getLines().clear();
        device.getLines().addAll(dto.getLines());
        for (ServiceLineDTO line : device.getLines()) {
            // link them all, just in case there's a new one
            line.setPurchaseService(device);
            // new lines need createDatetime set
            line.setDefaults();
        }
        device = deviceDas.save(device);

        if (oldLine != null && nonDeletedLines == 1) {
            ServiceLineDTO newLine = null;
            for (Iterator i = device.getLines().iterator(); i.hasNext();) {
                ServiceLineDTO temp = (ServiceLineDTO) i.next();
                if (temp.getDeleted() == 0) {
                    newLine = temp;
                }
            }
            if (newLine != null && !oldLine.getItemId().equals(newLine.getItemId())) {
                if (executorId != null) {
                    eLogger.audit(executorId,
                            device.getBaseUserByUserId().getId(),
                            Constants.TABLE_ORDER_LINE,
                            newLine.getId(), EventLogger.MODULE_ORDER_MAINTENANCE,
                            EventLogger.ORDER_LINE_UPDATED, oldLine.getId(),
                            oldLine.getDescription(),
                            null);
                } else {
                    // it is the mediation process
                    eLogger.auditBySystem(device.getBaseUserByUserId().getCompany().getId(),
                            device.getBaseUserByUserId().getId(),
                            Constants.TABLE_ORDER_LINE,
                            newLine.getId(), EventLogger.MODULE_ORDER_MAINTENANCE,
                            EventLogger.ORDER_LINE_UPDATED, oldLine.getId(),
                            oldLine.getDescription(),
                            null);
                }
            }
        }*/

 

    } 
              
    
    
 	/*-----------  adding create method -------------*/
    
  
  




  public Integer create(Integer entityId, String icc, String imsi, Integer puk1, Integer puk2,Date createdDate, Date lastUpdatedDate)
    {
    
    		LOG.debug("DeviceBL create method");
    	 	int x = 0;
 		DeviceDTO newDevice = new DeviceDTO();
         	deviceDas = new DeviceDAS();
 		DeviceTypeDTO deviceType = new DeviceTypeDAS().find(Constants.DEVICE_TYPE_SIM);
		CompanyDTO entity = new CompanyDTO(entityId);
 		newDevice.setIcc(icc);
 		newDevice.setImsi(imsi);
 		newDevice.setPuk1(puk1);
 		newDevice.setPuk2(puk2);
                newDevice.setCreatedDate(createdDate);;
 		newDevice.setLastUpdatedDate(lastUpdatedDate);
 		DeviceStatusDTO statusDto= new DeviceStatusDAS().find(Constants.DEVICE_STATUS_NEW);
		LOG.debug("status dto value"+statusDto.getId());
		newDevice.setDeviceStatus(new DeviceStatusDAS().find(Constants.DEVICE_STATUS_NEW));
		LOG.debug("we are in devicebl and above statement of device type");
		newDevice.setDeviceType(deviceType);
		
		newDevice.setDeleted(x);
		newDevice.setEntity(entity);
	  	device = deviceDas.save(newDevice);
	        LOG.debug("DeviceDAS save(dto) and id value is "+ device.getId());
		return device.getId();
    	
    		
    }
  public Integer create(Integer entityId, String icc, String imsi, Integer puk1, Integer puk2, String pin1, String pin2,Date createdDate, Date lastUpdatedDate)
    {
    
    		LOG.debug("DeviceBL create method");
    	 	int x = 0;
 		DeviceDTO newDevice = new DeviceDTO();
         	deviceDas = new DeviceDAS();
 		DeviceTypeDTO deviceType = new DeviceTypeDAS().find(Constants.DEVICE_TYPE_SIM);
		CompanyDTO entity = new CompanyDTO(entityId);
 		newDevice.setIcc(icc);
 		newDevice.setImsi(imsi);
 		newDevice.setPuk1(puk1);
 		newDevice.setPuk2(puk2);
                newDevice.setPin1(pin1);
 		newDevice.setPin2(pin2);
 		newDevice.setCreatedDate(createdDate);;
 		newDevice.setLastUpdatedDate(lastUpdatedDate);
 		DeviceStatusDTO statusDto= new DeviceStatusDAS().find(Constants.DEVICE_STATUS_NEW);
		LOG.debug("status dto value"+statusDto.getId());
		newDevice.setDeviceStatus(new DeviceStatusDAS().find(Constants.DEVICE_STATUS_NEW));
		LOG.debug("we are in devicebl and above statement of device type");
		newDevice.setDeviceType(deviceType);
		
		newDevice.setDeleted(x);
		newDevice.setEntity(entity);
	  	device = deviceDas.save(newDevice);
	        LOG.debug("DeviceDAS save(dto) and id value is "+ device.getId());
		return device.getId();
    	
    		
    }
    
public Integer create(Integer Id, String serialNum, String deviceCode, String vendorCode, Date createdDate, Date lastUpdatedDate) {
    
    //EntityBL entity = new EntityBL(dto.getEntityId());
        LOG.debug("DeviceBL create method");
        //dto.setDeleted(0);
        int x = 0;
		DeviceDTO newDevice = new DeviceDTO();
        deviceDas = new DeviceDAS();
		DeviceTypeDTO deviceType = new DeviceTypeDTO(1);
		//DeviceStatusDTO genstatus = new DeviceStatusDTO(1);
		CompanyDTO entity = new CompanyDTO(Id);

		//LOG.debug("genstatus value is" /*+ genstatus.getId() */);

		//newDevice.setId(0);
		newDevice.setSerialNum(serialNum);
		newDevice.setDeviceCode(deviceCode);
		newDevice.setCreatedDate(createdDate);
		newDevice.setLastUpdatedDate(lastUpdatedDate);
		newDevice.setVendorCode(vendorCode);
		DeviceStatusDTO statusDto= new DeviceStatusDAS().find(Constants.DEVICE_STATUS_NEW);
		LOG.debug("statusdo value"+statusDto);
		LOG.debug("status dto value"+statusDto.getDescription((1)));
		LOG.debug("status dto value"+statusDto.getId());
		
		newDevice.setDeviceStatus(new DeviceStatusDAS().find(Constants.DEVICE_STATUS_NEW));
		LOG.debug("we are in devicebl and above statement of device type");
		newDevice.setDeviceType(deviceType);
		LOG.debug("statusdo value"+deviceType);
		
		newDevice.setDeleted(x);
		newDevice.setEntity(entity);
		//newDevice.
		//newDevice.setOptlock(devicetype);


        device = deviceDas.save(newDevice);
        LOG.debug("DeviceDAS save(dto) and id value is"+ device.getId());
	    LOG.debug("Devicedto values:"+ newDevice.getId()+","+ newDevice.getSerialNum());
       //DeviceStatusDTO statusDto= new DeviceStatusDAS().find(Constants.DEVICE_STATUS_NEW);
       LOG.debug("DeviceDAS save(dto) and id value is"+ device.getId());
		return device.getId();
		 //return device.getDeviceId();
        /*item.setDescription(dto.getDescription(), languageId);
        updateTypes(dto);
        updateExcludedTypes(dto);
        updateCurrencies(dto);*/
        
        // trigger internal event
        //EventManager.process(new NewItemEvent(item));

        //return item.getId();
    }


/*----------- end of create method -------------*/

         public DeviceWS getDeviceWS(Integer languageId)
	{
		int xid = device.getId();
		DeviceWS retValue = new DeviceWS(device.getId(), device.getSerialNum(),
			device.getDeviceCode(), device.getVendorCode(),
			device.getDeviceStatus(), device.getDeviceType(),
			device.getIcc(),device.getImsi(),
			device.getPuk1(),device.getPuk2(),
                        device.getCreatedDate(),device.getLastUpdatedDate());

		return retValue;

	}


	public DeviceWS getDevicesWS(Integer languageId)
	{
		int xid = device.getId();
		DeviceWS retValue = new DeviceWS(device.getId(), device.getSerialNum(),
			device.getDeviceCode(), device.getVendorCode(),
			device.getDeviceStatus(), device.getDeviceType(),
			device.getIcc(),device.getImsi(),
			device.getPuk1(),device.getPuk2(),
                        device.getPin1(),device.getPin2(),
			device.getCreatedDate(),device.getLastUpdatedDate());

		return retValue;

	}


	public DeviceDTO[] getDeviceList1() throws NamingException, SQLException {
   
		List<DeviceDTO> devices = new DeviceDAS().findAll();
    		return devices.toArray(new DeviceDTO[devices.size()]);
	}

    

    public Integer getLatest(Integer userId)
            throws SessionInternalError {
        Integer retValue = null;
        try {
            prepareStatement(DeviceSQL.getLatest);
            cachedResults.setInt(1, userId.intValue());
            execute();
            if (cachedResults.next()) {
                int value = cachedResults.getInt(1);
                if (!cachedResults.wasNull()) {
                    retValue = new Integer(value);
                }
            }
            cachedResults.close();
            conn.close();
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }

        return retValue;
    }

   

    
    public void setStatus(Integer executorId, Integer statusId) {
        /*-s- if (statusId == null || device.getStatusId().equals(statusId)) {
            return;
        }-s- */
       /*-s- if (executorId != null) {
            eLogger.audit(executorId, device.getBaseUserByUserId().getId(),
                    Constants.TABLE_PUCHASE_ORDER, device.getId(),
                    EventLogger.MODULE_ORDER_MAINTENANCE,
                    EventLogger.ORDER_STATUS_CHANGE,
                    device.getStatusId(), null, null);
        } else {
            eLogger.auditBySystem(device.getBaseUserByUserId().getCompany().getId(),
            		device.getBaseUserByUserId().getId(),
                    Constants.TABLE_PUCHASE_ORDER,
                    device.getId(),
                    EventLogger.MODULE_ORDER_MAINTENANCE,
                    EventLogger.ORDER_STATUS_CHANGE,
                    device.getStatusId(), null, null);

        }-s-*/
        /* -s- NewStatusEvent event = new NewStatusEvent(
                device.getId(), device.getStatusId(), statusId);
        EventManager.process(event); -s- */ //not suitable constructor 
        //-s-  device.setServiceStatus(new ServiceStatusDAS().find(statusId));

    }

   
     
    public void reviewNotifications(Date today)
            throws NamingException, SQLException, Exception {
       INotificationSessionBean notificationSess = (INotificationSessionBean) Context.getBean(Context.Name.NOTIFICATION_SESSION);

        for (CompanyDTO ent : new CompanyDAS().findEntities()) {
            // find the devices for this entity

            // SQL args
            Object[] sqlArgs = new Object[4];
            sqlArgs[0] = new java.sql.Date(today.getTime());

            // calculate the until date

            // get the this entity preferences for each of the steps
            PreferenceBL pref = new PreferenceBL();
            int totalSteps = 3;
            int stepDays[] = new int[totalSteps];
            boolean config = false;
            int minStep = -1;
            for (int f = 0; f < totalSteps; f++) {
                try {
                    pref.set(ent.getId(), new Integer(
                            Constants.PREFERENCE_DAYS_ORDER_NOTIFICATION_S1.intValue() +
                            f));
                    if (pref.isNull()) {
                        stepDays[f] = -1;
                    } else {
                        stepDays[f] = pref.getInt();
                        config = true;
                        if (minStep == -1) {
                            minStep = f;
                        }
                    }
                } catch (EmptyResultDataAccessException e) {
                    stepDays[f] = -1;
                }
            }

            if (!config) {
                LOG.warn("Preference missing to send a notification for " +
                        "entity " + ent.getId());
                continue;
            }

            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.setTime(today);
            cal.add(Calendar.DAY_OF_MONTH, stepDays[minStep]);
            sqlArgs[1] = new java.sql.Date(cal.getTime().getTime());

            // the entity
            sqlArgs[2] = ent.getId();
            // the total number of steps
            sqlArgs[3] = totalSteps;

            JdbcTemplate jdbcTemplate = (JdbcTemplate) Context.getBean(
                    Context.Name.JDBC_TEMPLATE);

            SqlRowSet results = jdbcTemplate.queryForRowSet(
                    DeviceSQL.getAboutToExpire, sqlArgs);
            while (results.next()) {
                int deviceId = results.getInt(1);
                Date activeUntil = results.getDate(2);
                int currentStep = results.getInt(3);
                int days = -1;

                // find out how many days apply for this device step
                for (int f = currentStep; f < totalSteps; f++) {
                    if (stepDays[f] >= 0) {
                        days = stepDays[f];
                        currentStep = f + 1;
                        break;
                    }
                }

                if (days == -1) {
                    throw new SessionInternalError("There are no more steps " +
                            "configured, but the device was selected. Service " +
                            " id = " + deviceId);
                } //-s-

                // check that this device requires a notification
                cal.setTime(today);
                cal.add(Calendar.DAY_OF_MONTH, days);
                if (activeUntil.compareTo(today) >= 0 &&
                        activeUntil.compareTo(cal.getTime()) <= 0) {
                    //ok
                    LOG.debug("Selecting device " + deviceId + " today = " + 
                    today + " active unitl = " + activeUntil +
                    " days = " + days);
                     
                } else {
                    
                    LOG.debug("Skipping device " + deviceId + " today = " + 
                    today + " active unitl = " + activeUntil +
                    " days = " + days);
                     
                   continue;
                }

                set(new Integer(deviceId));
               // UserBL user = new UserBL(device.getBaseUserByUserId().getId());
                try {
                    NotificationBL notification = new NotificationBL();
                    //CompanyDTO ent = new CompanyDTO();
                    ContactBL contact = new ContactBL();
                    //contact.set(user.getEntity().getUserId());
                    /*MessageDTO message = notification.getServiceNotification(
                            ent.getId(),
                            new Integer(currentStep),
                            user.getEntity().getLanguageIdField(),
                            //device.getActiveSince(),
                            //device.getActiveUntil(),
                            user.getEntity().getUserId()
                            /*-s-device.getTotal(), device.getCurrencyId()*/
                    // update the device record only if the message is sent 
                    /*-s-if (notificationSess.notify(user.getEntity(), message).
                            booleanValue()) {
                        // if in the last step, turn the notification off, so
                        // it is skiped in the next process
                        if (currentStep >= totalSteps) {
                            device.setNotify(new Integer(0));
                        }
                        device.setNotificationStep(new Integer(currentStep));
                        device.setLastNotified(Calendar.getInstance().getTime());
                    }-s-*/

                } catch (/*NotificationNotFound*/Exception e) {
                    LOG.warn("Without a message to send, this entity can't" +
                            " notify about devices. Skipping");
                    break;
                }

            }
        }
    }

    
  /*-s-  public Integer[] getListIds(Integer userId, Integer number, Integer entityId) {

        //List<Integer> result = deviceDas.findIdsByUserLatestFirst(userId, number);
        //return result.toArray(new Integer[result.size()]);
    }*/

    
    public Integer[] getByUserAndPeriod(Integer userId, Integer statusId)
            throws SessionInternalError {
        // find the device records first
        try {
            List result = new ArrayList();
            prepareStatement(DeviceSQL.getByUserAndPeriod);
            cachedResults.setInt(1, userId.intValue());
            cachedResults.setInt(2, statusId.intValue());
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

    

   
   
    
    /*-s-public ServiceLineWS getServiceLineWS(Integer id) {
        ServiceLineDTO line = deviceLineDAS.findNow(id);
        if (line == null) {
            LOG.warn("Service line " + id + " not found");
            return null;
        }
        ServiceLineWS retValue = new ServiceLineWS(line.getId(), line.getItem().getId(), line.getDescription(),
                line.getAmount(), line.getQuantity(), line.getPrice() == null ? null : line.getPrice(), line.getCreateDatetime(),
                line.getDeleted(), line.getServiceLineType().getId(), line.getEditable(),
                line.getPurchaseService().getId(), null, line.getVersionNum(), line.getProvisioningStatusId(), line.getProvisioningRequestId());
        return retValue;
    }-s-*/
   

  
   /* public DeviceDTO getDTO(DeviceWS other) {
        DeviceDTO retValue = new DeviceDTO();
        retValue.setId(other.getId());

        //retValue.setBaseUserByUserId(new UserDAS().find(other.getUserId()));
        //retValue.setBaseUserByCreatedBy(new UserDAS().find(other.getCreatedBy()));
        //retValue.setCurrency(new CurrencyDAS().find(other.getCurrencyId()));
        //retValue.setServiceStatus(new ServiceStatusDAS().find(other.getStatusId()));
        //retValue.setServicePeriod(new ServicePeriodDAS().find(other.getPeriod()));
        //retValue.setServiceBillingType(new ServiceBillingTypeDAS().find(other.getBillingTypeId()));
        //retValue.setActiveSince(other.getActiveSince());
        //retValue.setActiveUntil(other.getActiveUntil());
        
        /*private String serialNum;
        private String deviceCode;
        private String vendorCode;
        private Date createdDate;
        private Date lastUpdatedDate;
        retValue.setSerialNum(other.getSerialNum());
        retValue.setDeviceCode(other.getDeviceCode());
        retValue.setVendorCode(other.getVendorCode());      
        retValue.setCreatedDate(other.getCreatedDate());
        retValue.setLastUpdatedDate(other.getLastUpdatedDate());
        //retValue.setDueDateUnitId(other.getDueDateUnitId());
        //retValue.setDueDateValue(other.getDueDateValue());
        //retValue.setDfFm(other.getDfFm());
        //retValue.setAnticipatePeriods(other.getAnticipatePeriods());
        //retValue.setOwnInvoice(other.getOwnInvoice());
        //retValue.setNotes(other.getNotes());
        //retValue.setNotesInInvoice(other.getNotesInInvoice());
        /*-s-for (ServiceLineWS line : other.getServiceLines()) {
            retValue.getLines().add(getServiceLine(line));
        }-s-*/
        //retValue.setIsCurrent(other.getIsCurrent());
        //retValue.setCycleStarts(other.getCycleStarts());
        //retValue.setVersionNum(other.getVersionNum());
        /*if (other.getPricingFields() != null) {
            List<PricingField> pf = new ArrayList<PricingField>();
            pf.addAll(Arrays.asList(PricingField.getPricingFieldsValue(other.getPricingFields())));
            //retValue.setPricingFields(pf);
        }

        return retValue;
    }*/

          
}

