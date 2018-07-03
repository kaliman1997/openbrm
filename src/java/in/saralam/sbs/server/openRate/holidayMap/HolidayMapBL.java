package in.saralam.sbs.server.openRate.holidayMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.audit.EventLogger;

import in.saralam.sbs.server.RateCard.db.RateDAS;
import in.saralam.sbs.server.RateCard.db.RateDTO;
import in.saralam.sbs.server.openRate.holidayMap.db.HolidayMapDTO;
import in.saralam.sbs.server.openRate.holidayMap.HolidayMapWS;
import in.saralam.sbs.server.openRate.holidayMap.HolidayMapBL;
import in.saralam.sbs.server.openRate.holidayMap.db.HolidayMapDAS;
public class HolidayMapBL extends ResultList{

	private HolidayMapDTO holidayMap = null;
	private HolidayMapDAS holidayMapDas = null;
	private static final Logger LOG = Logger.getLogger(HolidayMapBL.class );
	private EventLogger eLogger = null;
	
	public HolidayMapBL(Integer holidayMapId) {
		init();
		set( holidayMapId);
	}
	
	public HolidayMapBL() {
		init();
	}
	
	public HolidayMapBL(HolidayMapDTO holidayMap) {
		init();
		this.holidayMap = holidayMap;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		holidayMapDas= new HolidayMapDAS();
	}
	
	public HolidayMapDTO getEntity() {
		return holidayMap;
	}
	
	public void set(Integer id) {
		 holidayMap = holidayMapDas.find(id);
	}

	public void setForUpdate(Integer id) {
		holidayMap= holidayMapDas.findForUpdate(id);
	}
	
	public void set(HolidayMapDTO newHolidayMap) {
		holidayMap = newHolidayMap;
	}
	
	public HolidayMapDTO getDTO() {
		return holidayMap;
	}
	
	
	
	public Integer create(String mapGroup,int day, int month, int year,String description) {
		
		int x = 1; 
		LOG.debug("create deination map method..."+mapGroup);
		HolidayMapDTO holidayMapDTO = new HolidayMapDTO();
		holidayMapDas = new HolidayMapDAS();
		
			
		holidayMapDTO.setMapGroup(mapGroup);
		holidayMapDTO.setDay(day);
		holidayMapDTO.setMonth(month);
                holidayMapDTO.setYear(year);
		holidayMapDTO.setDescription(description);
		
		
		
		HolidayMapDTO holidayMapDTOTemp = holidayMapDas.save(holidayMapDTO);
		LOG.debug("destinationMapDTO is created..." + holidayMapDTOTemp);
		return holidayMapDTOTemp.getId();
		
	}
	
	
	/*public void update(Integer executorId, DestinationMapDTO dto) {
		
		DestinationMapDTO destinationMapDTO = new DestinationMapDTO();
		destinationMapDTO.setMaxGroup(maxGroup);
		destinationMapDTO. setPrefix(prefix);
		destinationMapDTO.setTierCode(tierCode);
		destinationMapDTO.setDescription(description);
		destinationMapDTO.setCategory(category);
		destinationMapDTO.setRank(rank);
		destinationMapDas.save(destinationMapDTO);
		
		
	}*/
	
	public void delete(Integer executorId) {
		HolidayMapDTO holidayMapDTO = new HolidayMapDTO();
		//destinationMapDTO.setDeleted(1);
		//destinationMapDas.save(rateDTO);
	}
	
	
	public HolidayMapWS getHolidayMapWS(Integer languageId) {
		
		HolidayMapWS retValue = new HolidayMapWS(holidayMap.getId(),holidayMap.getMapGroup(),holidayMap.getDay(),holidayMap.getMonth(),holidayMap.getYear(),holidayMap.getDescription());
		
		return retValue;
	}

	

}
