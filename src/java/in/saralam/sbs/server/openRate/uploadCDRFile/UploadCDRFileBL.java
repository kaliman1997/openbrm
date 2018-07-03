package in.saralam.sbs.server.openRate.uploadCDRFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.audit.EventLogger;

import in.saralam.sbs.server.RateCard.db.RateDAS;
import in.saralam.sbs.server.RateCard.db.RateDTO;
import in.saralam.sbs.server.openRate.uploadCDRFile.db.UploadCDRFileDTO;
import in.saralam.sbs.server.openRate.uploadCDRFile.UploadCDRFileWS;
import in.saralam.sbs.server.openRate.uploadCDRFile.UploadCDRFileBL;
import in.saralam.sbs.server.openRate.uploadCDRFile.db.UploadCDRFileDAS;
public class UploadCDRFileBL extends ResultList{

	private UploadCDRFileDTO UploadCDRFile = null;
	private UploadCDRFileDAS UploadCDRFileDas = null;
	private static final Logger LOG = Logger.getLogger(UploadCDRFileBL.class );
	private EventLogger eLogger = null;
	
	public UploadCDRFileBL(Integer UploadCDRFileId) {
		init();
		set( UploadCDRFileId);
	}
	
	public UploadCDRFileBL() {
		init();
	}
	
	public UploadCDRFileBL(UploadCDRFileDTO UploadCDRFile) {
		init();
		this.UploadCDRFile = UploadCDRFile;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		UploadCDRFileDas= new UploadCDRFileDAS();
	}
	
	public UploadCDRFileDTO getEntity() {
		return UploadCDRFile;
	}
	
	public void set(Integer id) {
		 UploadCDRFile = UploadCDRFileDas.find(id);
	}

	public void setForUpdate(Integer id) {
		UploadCDRFile= UploadCDRFileDas.findForUpdate(id);
	}
	
	public void set(UploadCDRFileDTO newUploadCDRFile) {
		UploadCDRFile = newUploadCDRFile;
	}
	
	public UploadCDRFileDTO getDTO() {
		return UploadCDRFile;
	}
	
	
	
	public Integer create(String name, Date date, String status, String type) {
		
		int x = 1; 
		//LOG.debug("create deination map method..."+mapGroup);
		UploadCDRFileDTO UploadCDRFileDTO = new UploadCDRFileDTO();
		UploadCDRFileDas = new UploadCDRFileDAS();
		
			
		//holidayMapDTO.setMapGroup(mapGroup);
		//holidayMapDTO.setDay(day);
		//holidayMapDTO.setMonth(month);
              //  holidayMapDTO.setYear(year);
		UploadCDRFileDTO.setName(name);
		UploadCDRFileDTO.setDate(date);
		UploadCDRFileDTO.setType(type);
		UploadCDRFileDTO.setStatus(status);
		UploadCDRFile=UploadCDRFileDas.save(UploadCDRFileDTO);
		//UploadCDRFileDTO UploadCDRFileDTOTemp = UploadCDRFileDas.save(UploadCDRFileDTO);
		LOG.debug("UploadCDRFileDTO is created..." + UploadCDRFile);
		return UploadCDRFile.getId();
		
	}
	
	
	public void update(Integer executorId, UploadCDRFileDTO dto) {
		
		UploadCDRFileDTO uploadCDRFileDTO = new UploadCDRFileDTO();
		
		/*uploadCDRFileDTO.setName(name);
		uploadCDRFileDTO.setDate(date);*/
		UploadCDRFileDas.save(uploadCDRFileDTO);
	}
	
	public void delete(Integer executorId) {
		UploadCDRFileDTO UploadCDRFileDTO = new UploadCDRFileDTO();
		/*UploadCDRFileDTO.setDeleted(1);*/
		UploadCDRFileDas.save(UploadCDRFileDTO);
	}
	
	
	public UploadCDRFileWS getUploadCDRFileWS(Integer languageId) {
		
		UploadCDRFileWS retValue = new UploadCDRFileWS(UploadCDRFile.getId(),UploadCDRFile.getName(),UploadCDRFile.getDate(),UploadCDRFile.getStatus(),UploadCDRFile.getType());
		
		return retValue;
	}

	

}
