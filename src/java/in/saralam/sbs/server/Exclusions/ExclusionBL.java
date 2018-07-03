package in.saralam.sbs.server.Exclusions;

import in.saralam.sbs.server.Exclusions.db.ExclusionDAS;
import in.saralam.sbs.server.Exclusions.db.ExclusionDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.util.audit.EventLogger;



public class ExclusionBL extends ResultList{

	private ExclusionDTO exclusion = null;
	private ExclusionDAS exclusionDas = null;
	private static final Logger LOG = Logger.getLogger(ExclusionBL.class );
	private EventLogger eLogger = null;
	
	public ExclusionBL(Integer exclusionId) {
		init();
		set(exclusionId);
	}
	
	public ExclusionBL() {
		init();
	}
	
	public ExclusionBL(ExclusionDTO exclusion) {
		init();
		this.exclusion = exclusion;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		exclusionDas = new ExclusionDAS();
	}
	
	public ExclusionDTO getEntity() {
		return exclusion;
	}
	
	public void set(Integer id) {
		exclusion = exclusionDas.find(id);
	}

	public void setForUpdate(Integer id) {
		exclusion = exclusionDas.findForUpdate(id);
	}
	
	public void set(ExclusionDTO newExclusion) {
		exclusion = newExclusion;
	}
	
	public ExclusionDTO getDTO() {
		return exclusion;
	}
	
	
	
	public Integer create(String prefix, String destination,String field1,String field2, Integer ratePlan, Date createdDate, Date validFrom, Date validTo, Date lastUpdatedDate, CompanyDTO entityId) {
		
		int x = 1; 
		ExclusionDTO newExclusion = new ExclusionDTO();
		
		exclusionDas = new ExclusionDAS();
		List<ExclusionDTO> dto = exclusionDas.findByPrefix(prefix);
		Iterator<ExclusionDTO> dt = dto.iterator();
		if (dto != null) {
			while (dt.hasNext()) {
				ExclusionDTO dt1 = dt.next();
			   x = dt1.getVersion()+1;
			}
		}
		newExclusion.setPrefix(prefix);
		newExclusion.setDestination(destination);
		newExclusion.setField1(field1);
		newExclusion.setField2(field2);
		newExclusion.setratePlan(ratePlan);
		newExclusion.setCreatedDate(createdDate);
		newExclusion.setValidFrom(validFrom);
		newExclusion.setValidTo(validTo);	
		newExclusion.setDeleted(0);
		newExclusion.setVersion(x);
        
		newExclusion.setLastUpdatedDate(lastUpdatedDate);
		newExclusion.setEntity(entityId);
		
		exclusion = exclusionDas.save(newExclusion);
		return exclusion.getId();
		
	}
	
	
	public void update(Integer executorId, ExclusionDTO dto) {
		
		ExclusionDTO exclusionDTO = new ExclusionDTO();
		exclusionDTO.setPrefix(dto.getPrefix());
		exclusionDTO.setDestination(dto.getDestination());
		exclusionDTO.setVersion(dto.getVersion());
		exclusionDTO.setField1(dto.getField1());
		exclusionDTO.setField2(dto.getField2());
		exclusionDTO.setratePlan(dto.getratePlan());
		exclusionDTO.setCreatedDate(dto.getCreatedDate());
		exclusionDTO.setValidFrom(dto.getValidFrom());
		exclusionDTO.setValidTo(dto.getValidTo());
		exclusionDTO.setLastUpdatedDate(dto.getLastUpdatedDate());
		exclusionDas.save(exclusionDTO);
		
		
	}
	
	public void delete(Integer executorId) {
		ExclusionDTO exclusionDTO = new ExclusionDTO();
		exclusionDTO.setDeleted(1);
		exclusionDas.save(exclusionDTO);
	}
	
	
	public ExclusionWS getExclusionWS(Integer languageId) {
		
		ExclusionWS retValue = new ExclusionWS(exclusion.getId(), exclusion.getPrefix(), 
		exclusion.getDestination(), exclusion.getVersion(), 
		exclusion.getField1(),exclusion.getField2(),
		exclusion.getratePlan(),exclusion.getCreatedDate(),
		exclusion.getValidFrom(),exclusion.getValidTo(),
		exclusion.getLastUpdatedDate(), exclusion.getEntity());
		
		return retValue;
	}

	public List<ExclusionDTO> getRateByPrefix(String prefix) {

                return exclusionDas.getRateByPrefix(prefix);
        }

	public List<ExclusionDTO> getRateByImpCat(String impCat) {

                return exclusionDas.getRateByImpCat(impCat);
        }

	public List getRateByPrefixAndDate(String prefix, Date date, Integer item) {
				//String pdate = date.toString();
                return  exclusionDas.getRateByPrefixAndDate(prefix, date, item);
        }

}
