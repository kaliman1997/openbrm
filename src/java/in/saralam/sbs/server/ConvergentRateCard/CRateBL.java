package in.saralam.sbs.server.ConvergentRateCard;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.audit.EventLogger;

import in.saralam.sbs.server.ConvergentRateCard.db.CRateDAS;
import in.saralam.sbs.server.ConvergentRateCard.db.CRateDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

public class CRateBL extends ResultList{

	private CRateDTO crate = null;
	private CRateDAS crateDas = null;
	private static final Logger LOG = Logger.getLogger(CRateBL.class );
	private EventLogger eLogger = null;
	
	public CRateBL(Integer rateId) {
		init();
		set(rateId);
	}
	
	public CRateBL() {
		init();
	}
	
	public CRateBL(CRateDTO crate) {
		init();
		this.crate = crate;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		crateDas = new CRateDAS();
	}
	
	public CRateDTO getEntity() {
		return crate;
	}
	
	public void set(Integer id) {
		crate = crateDas.find(id);
	}

	public void setForUpdate(Integer id) {
		crate = crateDas.findForUpdate(id);
	}
	
	public void set(CRateDTO newRate) {
		crate = newRate;
	}
	
	public CRateDTO getDTO() {
		return crate;
	}
	
	
	
	public Integer create(String prefix, String destination, BigDecimal flatRate, BigDecimal conncharge, BigDecimal scaledRate, Integer ratePlan, Date createdDate, Date validFrom, Date validTo, Date lastUpdatedDate,String rateType,CompanyDTO entityId,String callType) {
		
		int x = 1; 
		LOG.debug("create rateDTO method...");
		CRateDTO newRate = new CRateDTO();
		crateDas = new CRateDAS();
		List<CRateDTO> dto = crateDas.findByPrefix(prefix);
		Iterator<CRateDTO> dt = dto.iterator();
		if (dto != null) {
			while (dt.hasNext()) {
			   CRateDTO dt1 = dt.next();
			   x = dt1.getVersion()+1;
			}
		}
			
		newRate.setPrefix(prefix);
		newRate.setDestination(destination);
		newRate.setFlatRate(flatRate);
		newRate.setconncharge(conncharge);
		newRate.setscaledRate(scaledRate);
		newRate.setratePlan(ratePlan);
		newRate.setCreatedDate(createdDate);
		newRate.setValidFrom(validFrom);
		newRate.setValidTo(validTo);
		newRate.setRateType(rateType);
		newRate.setDeleted(0);
		newRate.setVersion(x);
//		newRate.setValidTo(validTo);
		newRate.setLastUpdatedDate(lastUpdatedDate);
		newRate.setEntity(entityId);
		newRate.setCallType(callType);
		crate = crateDas.save(newRate);
		LOG.debug("CRate is created..." + crate);
		return crate.getId();
		
	}
	
	
	public void update(Integer executorId, CRateDTO dto) {
		
		CRateDTO crateDTO = new CRateDTO();
		crateDTO.setPrefix(dto.getPrefix());
		crateDTO.setDestination(dto.getDestination());
		crateDTO.setVersion(dto.getVersion());
		crateDTO.setFlatRate(dto.getFlatRate());
		crateDTO.setconncharge(dto.getconncharge());
		crateDTO.setscaledRate(dto.getscaledRate());
		crateDTO.setratePlan(dto.getratePlan());
		crateDTO.setCreatedDate(dto.getCreatedDate());
		crateDTO.setValidFrom(dto.getValidFrom());
		crateDTO.setValidTo(dto.getValidTo());
		crateDTO.setLastUpdatedDate(dto.getLastUpdatedDate());
		crateDTO.setRateType(dto.getRateType());
		crateDTO.setCallType(dto.getCallType());
		crateDas.save(crateDTO);
		
		
	}
	
	public void delete(Integer executorId) {
		CRateDTO crateDTO = new CRateDTO();
		crateDTO.setDeleted(1);
		crateDas.save(crateDTO);
	}
	
	
	public CRateWS getCRateWS(Integer languageId) {
		
		CRateWS retValue = new CRateWS(crate.getId(), crate.getPrefix(), 
				crate.getDestination(), crate.getVersion(), 
				crate.getFlatRate(),crate.getconncharge(),
			    crate.getscaledRate(), crate.getratePlan(), 
				crate.getCreatedDate(), crate.getValidFrom(), 
				crate.getValidTo(), crate.getLastUpdatedDate(),crate.getRateType(),crate.getEntity(),crate.getCallType());
		
		return retValue;
	}

	public List<CRateDTO> getRateByPrefix(String prefix) {

                return crateDas.getRateByPrefix(prefix);
        }

	public List<CRateDTO> getRateByImpCat(String impCat) {

                return crateDas.getRateByImpCat(impCat);
        }

	public List getRateByPrefixAndDate(String prefix, Date date, Integer item) {
				//String pdate = date.toString();
                return  crateDas.getRateByPrefixAndDate(prefix, date, item);
        }

}
