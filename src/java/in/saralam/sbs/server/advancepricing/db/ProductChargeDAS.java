package in.saralam.sbs.server.advancepricing.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class ProductChargeDAS extends AbstractDAS<ProductChargeDTO>{

	private static final String GET_SUBS_SQL =
			" select distinct(item_id) " + 
					" from product_charge " +
					" where deleted = 0";
	
	private static final String HSQL =
			" select distinct(productCharge) " + 
					" from ProductChargeDTO productCharge " +
					" where deleted = 0";
	
	public List<Integer> getProducts() {
		Query query = getSession()
				.createSQLQuery(GET_SUBS_SQL);
		
		return query.list();
	}
	
	public ProductChargeDTO findByItemAndType(Integer item, Integer typeId){
		
		 Criteria criteria = getSession().createCriteria(ProductChargeDTO.class)
	                .createAlias("item", "i")
	                    .add(Restrictions.eq("i.id", item))
	                .add(Restrictions.eq("deleted", 0))
	                .createAlias("chargeType", "c")
	                    .add(Restrictions.eq("c.id", typeId));
		 return findFirst(criteria);
	}
	
	public ProductChargeDTO findByItemAndTypeAndDeleted(Integer item, Integer typeId){
		
		 Criteria criteria = getSession().createCriteria(ProductChargeDTO.class)
	                .createAlias("item", "i")
	                    .add(Restrictions.eq("i.id", item))
	                .createAlias("chargeType", "c")
	                    .add(Restrictions.eq("c.id", typeId));
		 return findFirst(criteria);
	}
	
	public List<ProductChargeDTO> findByProduct(Integer itemId){
		
		 Criteria criteria = getSession().createCriteria(ProductChargeDTO.class)
	                .createAlias("item", "i")
	                    .add(Restrictions.eq("i.id", itemId))
	                .add(Restrictions.eq("deleted", 0));
		 return criteria.list();
	}

         public ProductChargeDTO findByItem(Integer item){
		
		 Criteria criteria = getSession().createCriteria(ProductChargeDTO.class)
	                .createAlias("item", "i")
	                .add(Restrictions.eq("i.id", item))
	                .add(Restrictions.eq("deleted", 0));
	                
		 return findFirst(criteria);
	}
	
}
