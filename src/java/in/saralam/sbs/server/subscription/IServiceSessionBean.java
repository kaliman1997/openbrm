package in.saralam.sbs.server.subscription;

import java.math.BigDecimal;
import java.util.Date;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import in.saralam.sbs.server.subscription.db.ServiceDTO;
//import com.sapienter.jbilling.server.service.db.ServicePeriodDTO;

/**
 *
 * This is the session facade for the services in general. It is a statless
 * bean that provides services not directly linked to a particular operation
 *
 * @author emilc
 **/
public interface IServiceSessionBean {
    
    public void reviewNotifications(Date today) throws SessionInternalError;

    public ServiceDTO getService(Integer serviceId) throws SessionInternalError;

    public ServiceDTO getServiceEx(Integer serviceId, Integer languageId) 
            throws SessionInternalError;

    public ServiceDTO setStatus(Integer serviceId, Integer statusId, 
            Integer executorId, Integer languageId) throws SessionInternalError;

    /**
     * This is a version used by the http api, should be
     * the same as the web service but without the 
     * security check
    public Integer create(ServiceWS service, Integer entityId,
            String rootUser, boolean process) throws SessionInternalError;
     */

    public void delete(Integer id, Integer executorId) 
            throws SessionInternalError;
 
    public Integer createUpdate(Integer entityId, Integer executorId, 
            ServiceDTO service, Integer languageId) throws SessionInternalError;
  
}
