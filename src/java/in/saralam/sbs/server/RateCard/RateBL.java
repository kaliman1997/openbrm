package in.saralam.sbs.server.RateCard;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.audit.EventLogger;

import in.saralam.sbs.server.RateCard.db.RateDAS;
import in.saralam.sbs.server.RateCard.db.RateDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

public class RateBL extends ResultList{

	private RateDTO rate = null;
	private RateDAS rateDas = null;
	private static final Logger LOG = Logger.getLogger(RateBL.class );
	private EventLogger eLogger = null;
	
	public RateBL(Integer rateId) {
		init();
		set(rateId);
	}
	
	public RateBL() {
		init();
	}
	
	public RateBL(RateDTO rate) {
		init();
		this.rate = rate;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		rateDas = new RateDAS();
	}
	
	public RateDTO getEntity() {
		return rate;
	}
	
	public void set(Integer id) {
		rate = rateDas.find(id);
	}

	public void setForUpdate(Integer id) {
		rate = rateDas.findForUpdate(id);
	}
	
	public void set(RateDTO newRate) {
		rate = newRate;
	}
	
	public RateDTO getDTO() {
		return rate;
	}
	
	
	
	public Integer create(String prefix, String destination, BigDecimal flatRate, BigDecimal conncharge, BigDecimal scaledRate, Integer ratePlan, Date createdDate, Date validFrom, Date validTo, Date lastUpdatedDate,String rateType,CompanyDTO entityId) {
		
		int x = 1; 
		LOG.debug("create rateDTO method...");
		RateDTO newRate = new RateDTO();
		rateDas = new RateDAS();
		List<RateDTO> dto = rateDas.findByPrefix(prefix);
		Iterator<RateDTO> dt = dto.iterator();
		if (dto != null) {
			while (dt.hasNext()) {
			   RateDTO dt1 = dt.next();
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
		rate = rateDas.save(newRate);
		LOG.debug("Rate is created..." + rate);
		return rate.getId();
		
	}
	
	
	public void update(Integer executorId, RateDTO dto) {
		
		RateDTO rateDTO = new RateDTO();
		rateDTO.setPrefix(dto.getPrefix());
		rateDTO.setDestination(dto.getDestination());
		rateDTO.setVersion(dto.getVersion());
		rateDTO.setFlatRate(dto.getFlatRate());
		rateDTO.setconncharge(dto.getconncharge());
		rateDTO.setscaledRate(dto.getscaledRate());
		rateDTO.setratePlan(dto.getratePlan());
		rateDTO.setCreatedDate(dto.getCreatedDate());
		rateDTO.setValidFrom(dto.getValidFrom());
		rateDTO.setValidTo(dto.getValidTo());
		rateDTO.setLastUpdatedDate(dto.getLastUpdatedDate());
		rateDTO.setRateType(dto.getRateType());
		rateDas.save(rateDTO);
		
		
	}
	
	public void delete(Integer executorId) {
		RateDTO rateDTO = new RateDTO();
		rateDTO.setDeleted(1);
		rateDas.save(rateDTO);
	}
	
	
	public RateWS getRateWS(Integer languageId) {
		
		RateWS retValue = new RateWS(rate.getId(), rate.getPrefix(), 
				rate.getDestination(), rate.getVersion(), 
				rate.getFlatRate(),rate.getconncharge(),
			    rate.getscaledRate(), rate.getratePlan(), 
				rate.getCreatedDate(), rate.getValidFrom(), 
				rate.getValidTo(), rate.getLastUpdatedDate(),rate.getRateType(),rate.getEntity());
		
		return retValue;
	}

	public List<RateDTO> getRateByPrefix(String prefix) {

                return rateDas.getRateByPrefix(prefix);
        }

	public List<RateDTO> getRateByImpCat(String impCat) {

                return rateDas.getRateByImpCat(impCat);
        }

	public List getRateByPrefixAndDate(String prefix, Date date, Integer item) {
				//String pdate = date.toString();
                return  rateDas.getRateByPrefixAndDate(prefix, date, item);
        }

}
