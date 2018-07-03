package in.saralam.sbs.server.advancepricing.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class ProductChargeRateDAS extends AbstractDAS<ProductChargeRateDTO>{

	
	public List<ProductChargeRateDTO> findByProductCharge(Integer productChargeId){
		
		 Criteria criteria = getSession().createCriteria(ProductChargeRateDTO.class)
	                .createAlias("productCharge", "ps")
	                    .add(Restrictions.eq("ps.id", productChargeId))
	                .add(Restrictions.eq("deleted", 0))
					.addOrder( Order.asc("order"));
		 return criteria.list();
	}
	
	
	public ProductChargeRateDTO findByItemAndTypeAndCurrency(ItemDTO item, ChargeTypeDTO chargeType, Integer currencyId){
		
		 Criteria criteria = getSession().createCriteria(ProductChargeRateDTO.class)
	                .createAlias("productCharge", "ps")
	                    .add(Restrictions.eq("ps.item", item))
	                    .add(Restrictions.eq("ps.chargeType", chargeType))
	                    .createAlias("currency", "c")
	                    .add(Restrictions.eq("c.id", currencyId))
	                .add(Restrictions.eq("ps.deleted", 0))
	                .add(Restrictions.eq("deleted", 0));
		 return findFirst(criteria);
	}
	
	public List<ProductChargeRateDTO> findByItemAndType(ItemDTO item, ChargeTypeDTO chargeType){
		
		 Criteria criteria = getSession().createCriteria(ProductChargeRateDTO.class)
	                .createAlias("productCharge", "ps")
	                    .add(Restrictions.eq("ps.item", item))
	                    .add(Restrictions.eq("ps.chargeType", chargeType))
	                .add(Restrictions.eq("ps.deleted", 0))
	                .add(Restrictions.eq("deleted", 0));
		 return criteria.list();
	}
}
