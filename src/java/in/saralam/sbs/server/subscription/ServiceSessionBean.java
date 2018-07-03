
package in.saralam.sbs.server.subscription;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import in.saralam.sbs.server.subscription.db.ServiceDAS;
import in.saralam.sbs.server.subscription.db.ServiceDTO;


/**
 *
 * This is the session facade for the services in general. It is a statless
 * bean that provides services not directly linked to a particular operation
 *
 * @author emilc
 **/
@Transactional( propagation = Propagation.REQUIRED )
public class ServiceSessionBean implements IServiceSessionBean {
    
    private static final Logger LOG = Logger.getLogger(ServiceSessionBean.class);

    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void reviewNotifications(Date today) 
    		throws SessionInternalError {
    	
    	try {
    		ServiceBL service = new ServiceBL();
    		//service.reviewNotifications(today);
    	} catch (Exception e) {
    		throw new SessionInternalError(e);
    	}
    }

    public ServiceDTO getService(Integer serviceId) throws SessionInternalError {
        try {
        	ServiceDAS das = new ServiceDAS();
        	ServiceDTO service = das.find(serviceId);
        	//service.touch();
        	return service;
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
    }

    public ServiceDTO getServiceEx(Integer serviceId, Integer languageId) 
            throws SessionInternalError {
        try {
        	ServiceDAS das = new ServiceDAS();
        	ServiceDTO service = das.find(serviceId);
        	//service.addExtraFields(languageId);
        	//service.touch();
        	das.detach(service);
        	//Collections.sort(service.getLines(), new ServiceLineComparator());
        	//LOG.debug("returning service " + service);
        	return service;
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
    }

    public ServiceDTO setStatus(Integer serviceId, Integer statusId, 
            Integer executorId, Integer languageId) 
            throws SessionInternalError {
        try {
            ServiceBL service = new ServiceBL(serviceId);
            //service.setStatus(executorId, statusId);
            ServiceDTO dto = service.getDTO();
            //dto.addExtraFields(languageId);
            //dto.touch();
            return dto;
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
    }

    public void delete(Integer id, Integer executorId) 
            throws SessionInternalError {
        try {
            // now get the service
            ServiceBL bl = new ServiceBL(id);
            //bl.delete(executorId);
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }

    }
 
    public Integer createUpdate(Integer entityId, Integer executorId, 
            ServiceDTO service, Integer languageId) throws SessionInternalError {
        Integer retValue = null;
        try {
        	ServiceBL bl = new ServiceBL();
            if (service.getId() == null) {
               //-s- retValue = bl.create(entityId, executorId, service);
            } else {
                bl.set(service.getId());
                //bl.update(executorId, service);
            }
        } catch (Exception e) {
            throw new SessionInternalError(e);
        }
        
        return retValue;
    }

   
}
