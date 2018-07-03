package in.saralam.sbs.server.deferredAction;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sapienter.jbilling.common.InvalidArgumentException;
import com.sapienter.jbilling.common.SessionInternalError;
//import com.sapienter.jbilling.server.mediation.db.*;
//import com.sapienter.jbilling.server.mediation.task.IMediationProcess;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskBL;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskDAS;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskManager;
import com.sapienter.jbilling.server.user.EntityBL;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import java.util.ArrayList;
import java.util.Map;
import java.util.Calendar;
import org.springframework.util.StopWatch;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import in.saralam.sbs.server.deferredAction.action.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import in.saralam.sbs.server.deferredAction.db.DeferredActionDAS;
import in.saralam.sbs.server.deferredAction.db.DeferredActionDTO;
import in.saralam.sbs.server.deferredAction.db.DeferredActionStatusDTO;
import in.saralam.sbs.server.deferredAction.db.DeferredActionStatusDAS;
import in.saralam.sbs.server.deferredAction.db.DeferredActionDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import in.saralam.sbs.server.common.SBSConstants;
import in.saralam.sbs.server.subscription.ServiceBL;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import com.sapienter.jbilling.server.util.Context;
import in.saralam.sbs.server.deferredAction.db.DeferredActionDTO;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.lang.Object.*;
import in.saralam.sbs.server.deferredAction.action.PricePlanTransititon;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;

@Transactional( propagation = Propagation.REQUIRED )
public class DeferredActionSessionBean implements IDeferredActionSessionBean,java.io.Serializable {

   private static final Logger LOG = Logger.getLogger(DeferredActionSessionBean.class);
   
   private static StopWatch stopWatch = null;
   Date activeUntilD=null;
   Integer entityId=null;
  
   public void trigger(Integer entityId) {
		LOG.debug("Running deferred action trigger for entity " + entityId);
        StopWatch watch = new StopWatch("trigger watch");
        watch.start();
        // local instance of this bean to invoke transactional methods
        IDeferredActionSessionBean local = (IDeferredActionSessionBean) Context.getBean(Context.Name.DEFERRED_ACTION_SESSION);
        // find the root user of this entity, will be used as the executor for order updates
        Integer executorId = new EntityBL().getRootUser(entityId);
        List<String> errorMessages = new ArrayList<String>();

        // fetch deffered objects stored in DB and process each 
	    List<DeferredActionDTO> actions =  new DeferredActionDAS().findDeferredActionsToProcess(entityId, new Date());
		if(actions != null){
		local.processBatch(actions);
        LOG.debug(" called   processBatch of DASB class");
		}
		watch.stop();
        LOG.debug("Deferred Action process done. Duration (ms):" + watch.getTotalTimeMillis());
    }


    private static java.sql.Date getCurrentDate() {
	   java.util.Date today = new java.util.Date();
	   return new java.sql.Date(today.getTime());
	}

	
	

    public void create(IDeferredAction deferredAction) {
		
      try{            
			PricePlanTransititon priceObj= (PricePlanTransititon)deferredAction;
            UserDTO user= null;
            LOG.debug(" we are in create   method price obj"+priceObj);
            
			if(priceObj!=null){
				LOG.debug(" we are in create   method price obj is not null"+priceObj);
			    activeUntilD=priceObj.getScheduleDate();
                LOG.debug(" we are in create  method active until"+activeUntilD);
				entityId=priceObj.getEntityId();
                LOG.debug(" we are in create  data method entity id"+entityId);
				user=priceObj.getBaseUser();
				LOG.debug(" we are in create  data method withe user"+user);			     
			}
			
		    DeferredActionDAS defredDas=new DeferredActionDAS();
			LOG.debug(" we are in create method"+deferredAction);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
			
		    if(deferredAction!=null){
                Object obj= (Object)deferredAction;
		        oos.writeObject(obj);
		        oos.flush();
                oos.close();
                bos.close();
                byte[] data = bos.toByteArray();
		        LOG.debug(" we are in create method below data"+data);
		        DeferredActionDTO ddto= new DeferredActionDTO();
			    DeferredActionDAS ddas= new DeferredActionDAS();
				DeferredActionStatusDAS daDas= new DeferredActionStatusDAS();
				DeferredActionStatusDTO daDto=daDas.find( SBSConstants.DEFERRED_ACTION_STATUS_PENDING);
				ddto.setId(0);
				ddto.setDeferredActionStatus(daDto);
				Date  date = new Date();
				ServiceBL serviceBl=new ServiceBL();
		        ddto.setWhenDate(activeUntilD);
				ddto.setObject(data);
			    ddto.setCreatedDate(new Date());
				ddto.setBaseUser(user);
				ddto.setDeleted(0);
				CompanyDTO companyDto= new CompanyDTO(entityId);
				ddto.setEntity(companyDto);
				ddas.save(ddto); 
				LOG.debug(" we are in create method below save metho");

            }
	  }catch(Exception e){
            e.printStackTrace();
      }
    }
    
    public void create(IDeferredAction deferredAction, Date aud, Integer entityId, UserDTO user ) {

      try{		
			DeferredActionDAS defredDas=new DeferredActionDAS();
			LOG.debug(" we are in create method"+deferredAction);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
			
		    if(deferredAction!=null){
                Object obj= (Object)deferredAction;
		        oos.writeObject(obj);
		        oos.flush();
                oos.close();
                bos.close();
                byte[] data = bos.toByteArray();
		        LOG.debug(" we are in create method below data"+data);
		        DeferredActionDTO ddto= new DeferredActionDTO();
			    DeferredActionDAS ddas= new DeferredActionDAS();
				DeferredActionStatusDAS daDas= new DeferredActionStatusDAS();
				LOG.debug("DA");
				DeferredActionStatusDTO daDto=daDas.find( SBSConstants.DEFERRED_ACTION_STATUS_PENDING);
				LOG.debug("DA status is "+daDto);
				ddto.setId(0);
				ddto.setDeferredActionStatus(daDto);
				Date  date = new Date();
				ServiceBL serviceBl=new ServiceBL();
		        ddto.setWhenDate(aud);
				ddto.setObject(data);
			    ddto.setCreatedDate(new Date());
                ddto.setBaseUser(user);
				ddto.setDeleted(0);
				CompanyDTO companyDto= new CompanyDTO(entityId);
				ddto.setEntity(companyDto);
				LOG.debug("DA is "+ddto);
				ddas.save(ddto); 
				LOG.debug(" we are in create method below save metho");

        }
	  }
      catch(Exception e)
	  {
		e.printStackTrace();
      }
    }
    
    public void delete(Integer id) {
    }
	
    public void updateStatus(Integer objectId, Integer newStatus) {
    }

    public void processBatch( List<DeferredActionDTO> actions) {
		LOG.debug(" we are in DASB of  processBatch method");
        LOG.debug("actions size is"+actions.size());
		IDeferredAction actionTemp = null;
		IDeferredActionSessionBean local = (IDeferredActionSessionBean) Context.getBean(Context.Name.DEFERRED_ACTION_SESSION);
		DeferredActionDAS ddas= new DeferredActionDAS();
		DeferredActionStatusDAS daDas= new DeferredActionStatusDAS();
		DeferredActionDTO updateStatusDto=null;
			for(DeferredActionDTO action : actions) {
				try{
						updateStatusDto=action;
						LOG.debug(" in while loop DASB Process batch"+ updateStatusDto);
						byte[] data=action.getObject();
						ByteArrayInputStream in = new ByteArrayInputStream(data);
						ObjectInputStream is = new ObjectInputStream(in);
						Object obj =is.readObject();
						actionTemp = (IDeferredAction)obj;
						local.execute(actionTemp);
						LOG.debug(" we are in DASB of  processBatch below excutemethod");
		            }
					catch(Exception e){
						LOG.debug(" in catch block");
						e.printStackTrace();
						DeferredActionStatusDTO updateStatuse=daDas.find( SBSConstants.DEFERRED_ACTION_STATUS_DONE);
						updateStatusDto.setDeferredActionStatus(updateStatuse);
						LOG.debug(" updating the status with error");
						ddas.makePersistent(updateStatusDto);
						LOG.debug(" below make perstistant");
					}
					LOG.debug(" out of  catch block");
					DeferredActionStatusDTO updateStatus=daDas.find( SBSConstants.DEFERRED_ACTION_STATUS_DONE);
					updateStatusDto.setDeferredActionStatus(updateStatus);
					LOG.debug(" updating the status with done");
					ddas.makePersistent(updateStatusDto);
					LOG.debug(" below make perstistant");
           } 

    }


    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void execute(IDeferredAction deferredAction){
		try {			
			deferredAction.execute();
		} catch (Exception e) {
			LOG.error("Exception caught when processing the user " + e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // rollback !

		}
    }


}

