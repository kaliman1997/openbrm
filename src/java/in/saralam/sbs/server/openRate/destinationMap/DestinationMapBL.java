package in.saralam.sbs.server.openRate.destinationMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.audit.EventLogger;

import in.saralam.sbs.server.RateCard.db.RateDAS;
import in.saralam.sbs.server.RateCard.db.RateDTO;
import in.saralam.sbs.server.openRate.destinationMap.db.DestinationMapDTO;
import in.saralam.sbs.server.openRate.destinationMap.DestinationMapWS;
import in.saralam.sbs.server.openRate.destinationMap.db.DestinationMapDAS;
public class DestinationMapBL extends ResultList{

	private DestinationMapDTO destinationMap = null;
	private DestinationMapDAS destinationMapDas = null;
	private static final Logger LOG = Logger.getLogger(DestinationMapBL.class );
	private EventLogger eLogger = null;
	
	public DestinationMapBL(Integer destinationMapId) {
		init();
		set( destinationMapId);
	}
	
	public DestinationMapBL() {
		init();
	}
	
	public DestinationMapBL(DestinationMapDTO destinationMap) {
		init();
		this.destinationMap = destinationMap;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		destinationMapDas= new DestinationMapDAS();
	}
	
	public DestinationMapDTO getEntity() {
		return destinationMap;
	}
	
	public void set(Integer id) {
		 destinationMap = destinationMapDas.find(id);
	}

	public void setForUpdate(Integer id) {
		destinationMap= destinationMapDas.findForUpdate(id);
	}
	
	public void set(DestinationMapDTO newDestinationMap) {
		destinationMap = newDestinationMap;
	}
	
	public DestinationMapDTO getDTO() {
		return destinationMap;
	}
	
	
	
	public Integer create(String mapGroup,String prefix, String tierCode, String description, String category, int rank) {
		
		int x = 1; 
		LOG.debug("create deination map method..."+mapGroup);
		DestinationMapDTO destinationMapDTO = new DestinationMapDTO();
		destinationMapDas = new DestinationMapDAS();
		
			
		destinationMapDTO.setMapGroup(mapGroup);
		destinationMapDTO.setPrefix(prefix);
		destinationMapDTO.setTierCode(tierCode);
		destinationMapDTO.setDescription(description);
		destinationMapDTO.setCategory(category);
		destinationMapDTO.setRank(rank);
		
		DestinationMapDTO destinationMapDTOTemp = destinationMapDas.save(destinationMapDTO);
		LOG.debug("destinationMapDTO is created..." + destinationMapDTOTemp);
		return destinationMapDTOTemp.getId();
		
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
		//DestinationMapDTO destinationMapDTO = new DestinationMapDTO();
		LOG.debug("destinationMap bl"+destinationMap);
		//destinationMap.setDeleted(1);
		destinationMapDas.save(destinationMap);
	}
	
	
	public DestinationMapWS getDestinationMapWS(Integer languageId) {
		
		DestinationMapWS retValue = new DestinationMapWS(destinationMap.getId(), destinationMap.getMapGroup(), 
				destinationMap.getPrefix(), destinationMap.getTierCode(), 
				destinationMap.getDescription(),destinationMap.getCategory(),
			    destinationMap.getRank());
		
		return retValue;
	}

	

}
