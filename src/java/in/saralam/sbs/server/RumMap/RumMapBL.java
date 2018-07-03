package in.saralam.sbs.server.RumMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.audit.EventLogger;

import in.saralam.sbs.server.RumMap.db.RumMapDAS;
import in.saralam.sbs.server.RumMap.db.RumMapDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

public class RumMapBL extends ResultList{

	private RumMapDTO rumMap = null;
	private RumMapDAS rumDas = null;
	private static final Logger LOG = Logger.getLogger(RumMapBL.class );
	private EventLogger eLogger = null;
	
	public RumMapBL(Integer rumId) {
		init();
		set(rumId);
	}
	
	public RumMapBL() {
		init();
	}
	
	public RumMapBL(RumMapDTO rumMap) {
		init();
		this.rumMap =rumMap;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		rumDas = new RumMapDAS();
	}
	
	public RumMapDTO getEntity() {
		return rumMap;
	}
	
	public void set(Integer id) {
		rumMap =rumDas.find(id);
	}

	public void setForUpdate(Integer id) {
		rumMap = rumDas.findForUpdate(id);
	}
	
	public void set(RumMapDTO newRum) {
		rumMap = newRum;
	}
	
	public RumMapDTO getDTO() {
		return rumMap;
	}
	
	
	
	public Integer create(String priceGroup, Integer step, String priceModel, String rum, String resource, Integer resourceId, String rumType, Integer consumeFlag, Integer rumMapPlan, Date createdDate, Date lastUpdatedDate, CompanyDTO entityId) {
		
		
		LOG.debug("create RumMapDTO method...");
		RumMapDTO newRum = new RumMapDTO();
		rumDas = new RumMapDAS();
		
		newRum.setPriceGroup(priceGroup);
		newRum.setStep(step);
		newRum.setPriceModel(priceModel);
		newRum.setRum(rum);
		newRum.setResource(resource);
		newRum.setResourceId(resourceId);
		newRum.setRumType(rumType);
		newRum.setConsumeFlag(consumeFlag);
		newRum.setDeleted(0);
		newRum.setRumMapPlan(rumMapPlan);
		newRum.setCreatedDate(createdDate);
		newRum.setLastUpdatedDate(lastUpdatedDate);
		newRum.setEntity(entityId);
		
		
		rumMap = rumDas.save(newRum);
		LOG.debug("rum is created..." + rumMap);
		return rumMap.getId();
		
	}
	
	
	public void update(Integer executorId, RumMapDTO dto) {
		
		RumMapDTO rumDTO = new RumMapDTO();
		rumDTO.setPriceGroup(dto.getPriceGroup());
		rumDTO.setStep(dto.getStep());
		rumDTO.setPriceModel(dto.getPriceModel());
		rumDTO.setRum(dto.getRum());
		rumDTO.setResource(dto.getResource());
		rumDTO.setResourceId(dto.getResourceId());
		rumDTO.setRumType(dto.getRumType());
		rumDTO.setConsumeFlag(dto.getConsumeFlag());
		rumDTO.setRumMapPlan(dto.getRumMapPlan());
		rumDTO.setCreatedDate(dto.getCreatedDate());
		rumDTO.setLastUpdatedDate(dto.getLastUpdatedDate());
		rumDas.save(rumDTO);
		
		
	}
	
	public void delete(Integer executorId) {
		RumMapDTO rumDTO = new RumMapDTO();
		rumDTO.setDeleted(1);
		rumDas.save(rumDTO);
	}
	
	
		
	public RumMapWS getRumMapWS(Integer languageId) {
		
		RumMapWS retValue = new RumMapWS(rumMap.getId(), rumMap.getPriceGroup(), 
				rumMap.getStep(), rumMap.getPriceModel(), 
				rumMap.getRum(), rumMap.getResource(), rumMap.getResourceId(),
			    rumMap.getRumType(), rumMap.getConsumeFlag(), rumMap.getRumMapPlan(),
				rumMap.getCreatedDate(), rumMap.getLastUpdatedDate(), rumMap.getEntity());
		
		return retValue;
	}

	/*public List<PriceModelDTO> getRateByPrefix(String prefix) {

                return rumDas.getRateByPrefix(prefix);
        }

	public List<PriceModelDTO> getRateByImpCat(String impCat) {

                return rumDas.getRateByImpCat(impCat);
        }

	public List getRateByPrefixAndDate(String prefix, Date date, Integer item) {
				//String pdate = date.toString();
                return  rumDas.getRateByPrefixAndDate(prefix, date, item);
        }
		*/

}
