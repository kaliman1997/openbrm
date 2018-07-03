package in.saralam.sbs.server.PriceMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.audit.EventLogger;

import in.saralam.sbs.server.PriceMap.db.PriceMapDAS;
import in.saralam.sbs.server.PriceMap.db.PriceMapDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

public class PriceMapBL extends ResultList{

	private PriceMapDTO priceMap = null;
	private PriceMapDAS priceMapDas = null;
	private static final Logger LOG = Logger.getLogger(PriceMapBL.class );
	private EventLogger eLogger = null;
	
	public PriceMapBL(Integer priceMapId) {
		init();
		set(priceMapId);
	}
	
	public PriceMapBL() {
		init();
	}
	
	public PriceMapBL(PriceMapDTO priceMap) {
		init();
		this.priceMap = priceMap;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		priceMapDas = new PriceMapDAS();
	}
	
	public PriceMapDTO getEntity() {
		return priceMap;
	}
	
	public void set(Integer id) {
		priceMap = priceMapDas.find(id);
	}

	public void setForUpdate(Integer id) {
		priceMap = priceMapDas.findForUpdate(id);
	}
	
	public void set(PriceMapDTO newPriceMap) {
		priceMap = newPriceMap;
	}
	
	public PriceMapDTO getDTO() {
		return priceMap;
	}
	
	
	public Integer create(String mapGroup, String originZone, String destZone, String zoneResult,String timeResult, String priceGroup, String description, BigDecimal ratePrice, BigDecimal setUpPrice, String ratingType, Integer priceMapPlan, Date createdDate, Date startDate, Date endDate, Date lastUpdatedDate,CompanyDTO entityId) {
		
		
		LOG.debug("create priceMapDTO method...");
		PriceMapDTO newPriceMap = new PriceMapDTO();
		priceMapDas = new PriceMapDAS();
		
			
		newPriceMap.setMapGroup(mapGroup);
		newPriceMap.setOriginZone(originZone);
		newPriceMap.setDestZone(destZone);
		newPriceMap.setZoneResult(zoneResult);
		newPriceMap.setTimeResult(timeResult);
		newPriceMap.setPriceGroup(priceGroup);
		newPriceMap.setDescription(description);
		newPriceMap.setRatePrice(ratePrice);
		newPriceMap.setSetUpPrice(setUpPrice);
		newPriceMap.setRatingType(ratingType);
		newPriceMap.setPriceMapPlan(priceMapPlan);
		newPriceMap.setCreatedDate(createdDate);
		newPriceMap.setStartDate(startDate);
		newPriceMap.setEndDate(endDate);
		newPriceMap.setDeleted(0);
		newPriceMap.setLastUpdatedDate(lastUpdatedDate);
		newPriceMap.setEntity(entityId);
		priceMap = priceMapDas.save(newPriceMap);
		LOG.debug("pricemap is created..." + priceMap);
		return priceMap.getId();
		
	}
	
	
	public void update(Integer executorId, PriceMapDTO dto) {
		
		PriceMapDTO priceMapDTO = new PriceMapDTO();
		priceMapDTO.setMapGroup(dto.getMapGroup());
		priceMapDTO.setOriginZone(dto.getOriginZone());
		priceMapDTO.setDestZone(dto.getDestZone());
		priceMapDTO.setZoneResult(dto.getZoneResult());
		priceMapDTO.setTimeResult(dto.getTimeResult());
		priceMapDTO.setPriceGroup(dto.getPriceGroup());
		priceMapDTO.setDescription(dto.getDescription());
		priceMapDTO.setRatePrice(dto.getRatePrice());
		priceMapDTO.setSetUpPrice(dto.getSetUpPrice());
		priceMapDTO.setRatingType(dto.getRatingType());
		priceMapDTO.setPriceMapPlan(dto.getPriceMapPlan());
		priceMapDTO.setCreatedDate(dto.getCreatedDate());
		priceMapDTO.setStartDate(dto.getStartDate());
		priceMapDTO.setEndDate(dto.getEndDate());
		priceMapDTO.setLastUpdatedDate(dto.getLastUpdatedDate());
		
		priceMapDas.save(priceMapDTO);
		
		
	}
	
	public void delete(Integer executorId) {
		PriceMapDTO priceMapDTO = new PriceMapDTO();
		priceMapDTO.setDeleted(1);
		priceMapDas.save(priceMapDTO);
	}
	
	
	public PriceMapWS getPriceMapWS(Integer languageId) {
		
		PriceMapWS retValue = new PriceMapWS(priceMap.getId(), priceMap.getMapGroup(), 
				priceMap.getOriginZone(), priceMap.getDestZone(), 
				priceMap.getZoneResult(),priceMap.getTimeResult(),
				priceMap.getPriceGroup(),
			    priceMap.getDescription(), priceMap.getRatePrice(), 
				priceMap.getSetUpPrice(), priceMap.getRatingType(),
				priceMap.getPriceMapPlan(),
				priceMap.getCreatedDate(), priceMap.getStartDate(), 
				priceMap.getEndDate(), priceMap.getLastUpdatedDate(),priceMap.getEntity());
		
		return retValue;
	}

	/*public List<PriceMapDTO> getRateByPrefix(String prefix) {

                return rateDas.getRateByPrefix(prefix);
        }

	public List<RateDTO> getRateByImpCat(String impCat) {

                return rateDas.getRateByImpCat(impCat);
        }

	public List getRateByPrefixAndDate(String prefix, Date date, Integer item) {
				//String pdate = date.toString();
                return  rateDas.getRateByPrefixAndDate(prefix, date, item);
        }
	*/

}
