package in.saralam.sbs.server.openRate.worldZoneMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.audit.EventLogger;

import in.saralam.sbs.server.RateCard.db.RateDAS;
import in.saralam.sbs.server.RateCard.db.RateDTO;
import in.saralam.sbs.server.openRate.worldZoneMap.db.WorldZoneMapDTO;
import in.saralam.sbs.server.openRate.worldZoneMap.WorldZoneMapWS;
import in.saralam.sbs.server.openRate.worldZoneMap.db.WorldZoneMapDAS;
public class WorldZoneMapBL extends ResultList{

	private WorldZoneMapDTO worldZoneMap = null;
	private WorldZoneMapDAS worldZoneMapDas = null;
	private static final Logger LOG = Logger.getLogger(WorldZoneMapBL.class );
	private EventLogger eLogger = null;
	
	public WorldZoneMapBL(Integer worldZoneMapId) {
		init();
		set( worldZoneMapId);
	}
	
	public WorldZoneMapBL() {
		init();
	}
	
	public WorldZoneMapBL(WorldZoneMapDTO worldZoneMap) {
		init();
		this.worldZoneMap = worldZoneMap;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		worldZoneMapDas= new WorldZoneMapDAS();
	}
	
	public WorldZoneMapDTO getEntity() {
		return worldZoneMap;
	}
	
	public void set(Integer id) {
		 worldZoneMap = worldZoneMapDas.find(id);
	}

	public void setForUpdate(Integer id) {
		worldZoneMap= worldZoneMapDas.findForUpdate(id);
	}
	
	public void set(WorldZoneMapDTO newWorldZoneMap) {
		worldZoneMap = newWorldZoneMap;
	}
	
	public WorldZoneMapDTO getDTO() {
		return worldZoneMap;
	}
	
	
	
	public Integer create(String mapGroup,String tierCode, String worldZone) {
		
		int x = 1; 
		LOG.debug("create world map method..."+mapGroup);
		WorldZoneMapDTO worldZoneMapDTO = new WorldZoneMapDTO();
		worldZoneMapDas = new WorldZoneMapDAS();
		
			
		worldZoneMapDTO.setMapGroup(mapGroup);
		worldZoneMapDTO.setTierCode(tierCode);
                worldZoneMapDTO.setWorldZone(worldZone);
		
		WorldZoneMapDTO worldZoneMapDTOTemp = worldZoneMapDas.save(worldZoneMapDTO);
		LOG.debug("worldZoneMapDTO is created..." + worldZoneMapDTOTemp);
		return worldZoneMapDTOTemp.getId();
		
	}
	
	
	/*public void update(Integer executorId, DestinationMapDTO dto) {
		WorldZoneMapDTO worldZoneMapDTO = new WorldZoneMapDTO();
		worldZoneMapDTO.setMapGroup(mapGroup);
		worldZoneMapDTO.setTierCode(tierCode);
                worldZoneMapDTO.setWorldZone(worldZone);
                worldZoneMapDas = new WorldZoneMapDAS();
		 worldZoneMapDas.save(worldZoneMapDTO);
		
		
	}*/
	
	public void delete(Integer executorId) {
		WorldZoneMapDTO worldZoneMapDTO = new WorldZoneMapDTO();
		//worldZoneMapDTO.setDeleted(1);
		//worldZoneMapDas.save(worldZoneMapDTO);
	}
	
	
	public WorldZoneMapWS getWorldZoneMapWS(Integer languageId) {
		
		WorldZoneMapWS retValue = new WorldZoneMapWS(worldZoneMap.getId(), worldZoneMap.getMapGroup(), 
				 worldZoneMap.getTierCode(), 
				worldZoneMap.getWorldZone());
		
		return retValue;
	}

	

}
