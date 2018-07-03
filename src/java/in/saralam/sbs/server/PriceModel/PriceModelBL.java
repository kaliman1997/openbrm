package in.saralam.sbs.server.PriceModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.audit.EventLogger;

import in.saralam.sbs.server.PriceModel.db.PriceModelDAS;
import in.saralam.sbs.server.PriceModel.db.PriceModelDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

public class PriceModelBL extends ResultList{

	private PriceModelDTO price = null;
	private PriceModelDAS priceDas = null;
	private static final Logger LOG = Logger.getLogger(PriceModelBL.class );
	private EventLogger eLogger = null;
	
	public PriceModelBL(Integer priceId) {
		init();
		set(priceId);
	}
	
	public PriceModelBL() {
		init();
	}
	
	public PriceModelBL(PriceModelDTO price) {
		init();
		this.price = price;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		priceDas = new PriceModelDAS();
	}
	
	public PriceModelDTO getEntity() {
		return price;
	}
	
	public void set(Integer id) {
		price = priceDas.find(id);
	}

	public void setForUpdate(Integer id) {
		price = priceDas.findForUpdate(id);
	}
	
	public void set(PriceModelDTO newPrice) {
		price = newPrice;
	}
	
	public PriceModelDTO getDTO() {
		return price;
	}
	
	
	
	public Integer create(String priceModel, Integer qtyStep, Integer tierFrom, Integer  tierTo, BigDecimal beat, BigDecimal factor, Integer chargeBase, Integer priceModelPlan, Date createdDate, Date lastUpdatedDate, CompanyDTO entityId) {
		
		
		LOG.debug("create priceDTO method...");
		PriceModelDTO newPrice = new PriceModelDTO();
		priceDas = new PriceModelDAS();
		
		newPrice.setPriceModel(priceModel);
		newPrice.setQtyStep(qtyStep);
		newPrice.setTierFrom(tierFrom);
		newPrice.setTierTo(tierTo);
		newPrice.setBeat(beat);
		newPrice.setFactor(factor);
		newPrice.setChargeBase(chargeBase);
		newPrice.setDeleted(0);
		newPrice.setPriceModelPlan(priceModelPlan);
		newPrice.setCreatedDate(createdDate);
		newPrice.setLastUpdatedDate(lastUpdatedDate);
		newPrice.setEntity(entityId);
		
		
		price = priceDas.save(newPrice);
		LOG.debug("price is created..." + price);
		return price.getId();
		
	}
	
	
	public void update(Integer executorId, PriceModelDTO dto) {
		
		PriceModelDTO priceDTO = new PriceModelDTO();
		priceDTO.setPriceModel(dto.getPriceModel());
		priceDTO.setQtyStep(dto.getQtyStep());
		priceDTO.setTierFrom(dto.getTierFrom());
		priceDTO.setTierTo(dto.getTierTo());
		priceDTO.setBeat(dto.getBeat());
		priceDTO.setFactor(dto.getFactor());
		priceDTO.setChargeBase(dto.getChargeBase());
		priceDTO.setPriceModelPlan(dto.getPriceModelPlan());
		priceDTO.setCreatedDate(dto.getCreatedDate());
		priceDTO.setLastUpdatedDate(dto.getLastUpdatedDate());
		priceDas.save(priceDTO);
		
		
	}
	
	public void delete(Integer executorId) {
		PriceModelDTO priceDTO = new PriceModelDTO();
		priceDTO.setDeleted(1);
		priceDas.save(priceDTO);
	}
	
	
	public PriceModelWS getPriceModelWS(Integer languageId) {
		
		PriceModelWS retValue = new PriceModelWS(price.getId(), price.getPriceModel(), 
				price.getQtyStep(), price.getTierFrom(), 
				price.getTierTo(), price.getBeat(), price.getFactor(),
			    price.getChargeBase(), price.getPriceModelPlan(), price.getCreatedDate(), price.getLastUpdatedDate(), price.getEntity());
		
		return retValue;
	}

	/*public List<PriceModelDTO> getRateByPrefix(String prefix) {

                return priceDas.getRateByPrefix(prefix);
        }

	public List<PriceModelDTO> getRateByImpCat(String impCat) {

                return priceDas.getRateByImpCat(impCat);
        }

	public List getRateByPrefixAndDate(String prefix, Date date, Integer item) {
				//String pdate = date.toString();
                return  priceDas.getRateByPrefixAndDate(prefix, date, item);
        }
		*/

}
