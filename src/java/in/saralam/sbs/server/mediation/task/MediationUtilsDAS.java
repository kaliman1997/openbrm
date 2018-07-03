package in.saralam.sbs.server.mediation.task;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;
import java.util.List;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

public class MediationUtilsDAS extends AbstractDAS<OrderDTO>{



	public OrderDTO findOrderByOrderNotes(String telephone, CompanyDTO entity){
			
			Criteria criteria = getSession().createCriteria(OrderDTO.class)
                          .add(Restrictions.eq("notes", telephone))
						   .createAlias("baseUserByUserId", "u")
                .add(Restrictions.eq("u.company", entity))
						  .add(Restrictions.eq("deleted", 0))
                          .setMaxResults(1);

                          return findFirst(criteria);

	}
	
	public List<Integer> findItemIdsByOrder(Integer orderId ){
		 final String hql =
						"select line.item.id "
                        + "  from OrderLineDTO line "
                        + "where line.deleted = 0 "
						+ "  and line.purchaseOrder.id = :orderId "
						+ "  and line.purchaseOrder.deleted = 0 ";
						
		 Query query = getSession().createQuery(hql);
        query.setParameter("orderId", orderId);
     
	 return query.list();
	}	


}
