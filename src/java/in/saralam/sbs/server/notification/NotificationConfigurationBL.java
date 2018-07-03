package in.saralam.sbs.server.notification;

import java.util.Date;

import in.saralam.sbs.server.notification.db.NotificationConfigDAS;
import in.saralam.sbs.server.notification.db.NotificationConfigDTO;
import in.saralam.sbs.server.notification.db.NotificationEventDAS;
import in.saralam.sbs.server.notification.db.NotificationEventDTO;
import in.saralam.sbs.server.notification.db.NotificationTypeDAS;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.notification.db.NotificationMessageLineDAS;

public class NotificationConfigurationBL {

	private static final Logger LOG = Logger.getLogger(NotificationConfigurationBL.class);
	
	private NotificationConfigDTO notificationConfigDTO = null;
	private NotificationConfigDAS notificationConfigDAS = null; 
	
	public NotificationConfigurationBL(){
		init();
	}
	
	public NotificationConfigurationBL(Integer configId){
		init();
		set(configId);
	}
	
	public void set(Integer configId){
		notificationConfigDTO = notificationConfigDAS.find(configId);
	}
	
	public void init(){
		notificationConfigDTO = new NotificationConfigDTO();
		notificationConfigDAS = new NotificationConfigDAS();
	}
	
	public Integer createConfig(NotificationsConfigurationWS configWS){
		
		try{
			notificationConfigDTO.setSubject(new NotificationEventDAS().find(configWS.getEventName()));
			notificationConfigDTO.setMessageId(new NotificationMessageLineDAS().find(configWS.getMessage()));
			notificationConfigDTO.setNotifyType(new NotificationTypeDAS().find(configWS.getNotifyType()));
			notificationConfigDTO.setCreateDateTime(new Date());
			notificationConfigDTO.setDeleted(configWS.getDeleted());
			notificationConfigDTO.setVersion(0);
			NotificationConfigDTO configDTO = notificationConfigDAS.save(notificationConfigDTO);
			return configDTO.getId();
		}catch (Exception e) {
			LOG.warn("exception in config is "+e);
		}
		return null;
	}
	
public Integer updateConfig(NotificationsConfigurationWS configWS){
	
		try{
			notificationConfigDTO = notificationConfigDAS.find(configWS.getId());
			notificationConfigDTO.setSubject(new NotificationEventDAS().find(configWS.getEventName()));
			notificationConfigDTO.setMessageId(new NotificationMessageLineDAS().find(configWS.getMessage()));
			notificationConfigDTO.setNotifyType(new NotificationTypeDAS().find(configWS.getNotifyType()));
			notificationConfigDTO.setDeleted(configWS.getDeleted());
			notificationConfigDTO.setVersion(notificationConfigDTO.getVersion()+1);
			NotificationConfigDTO configDTO = notificationConfigDAS.save(notificationConfigDTO);
			return configDTO.getId();
		}catch (Exception e) {
			LOG.debug("exception in update config "+e);
		}
		return null;
	}
}
