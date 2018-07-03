package in.saralam.sbs.server.subscription;

import com.sapienter.jbilling.server.util.Constants;

/**
 * @author Emil
 */
public interface ServiceSQL {

    // This one is used for root and clerks
    static final String listInternal = 
        "select po.id, po.id, bu.user_name, c.organization_name , po.create_datetime " + 
        "  from purchase_service po, base_user bu, contact c " +
        "  where po.deleted = 0  " +
        "  and bu.entity_id = ? " +
        "  and po.user_id = bu.id " +
        "  and c.user_id = bu.id ";

    // PARTNER: will show only customers that belong to this partner
    static final String listPartner = 
        "select po.id, po.id, bu.user_name, c.organization_name, po.create_datetime " +
        "  from purchase_service po, base_user bu, customer cu, partner pa, contact c " +
        " where po.deleted = 0 " +
        "   and bu.entity_id = ? " +
        "   and po.user_id = bu.id" +
        "   and cu.partner_id = pa.id " +
        "   and pa.user_id = ? " +
        "   and cu.user_id = bu.id " +
        "   and c.user_id = bu.id ";

    static final String listCustomer = 
        "select po.id, po.id, bu.user_name, c.organization_name, po.create_datetime " +
        "  from purchase_service po, base_user bu, contact c " +
        " where po.deleted = 0 " +
        "   and po.user_id = ? " +
        "   and po.user_id = bu.id " +
        "   and c.user_id = bu.id ";

    static final String listByProcess = 
        "select po.id, po.id, bu.user_name, po.create_datetime " +
        "  from purchase_service po, base_user bu, billing_process bp, service_process op "+
        " where bp.id = ? " +
        "   and po.user_id = bu.id " +
        "  and op.billing_process_id = bp.id " + 
        "  and op.service_id = po.id " +
        "  service by 1 desc";
    
    static final String getAboutToExpire =
    	"select purchase_service.id, purchase_service.active_until, " +
        "       purchase_service.notification_step " +
    	" from purchase_service, base_user " +
    	"where active_until >= ? " +
    	"  and active_until <= ? " +
    	"  and notify = 1 " +
    	"  and purchase_service.status_id = (select id from generic_status " +
        "    where dtype = 'service_status' AND status_value = 1 )" +
    	"  and user_id = base_user.id " +
    	"  and entity_id = ? " +
        "  and (notification_step is null or notification_step < ?)";
    
    static final String getLatest =
        "select max(id) " +
        "  from purchase_service " +
        " where user_id = ?" +
        "   and deleted = 0";
    
    static final String getLatestByItemType =
        "select max(purchase_service.id) " +
        "  from purchase_service "+
        "  inner join service_line on service_line.service_id = purchase_service.id " +
        "  inner join item on item.id = service_line.item_id " +
        "  inner join item_type_map on item_type_map.item_id = item.id " +
        " where purchase_service.user_id = ?" +
        "   and item_type_map.type_id = ? " +
        "   and purchase_service.deleted = 0";
    
    static final String getByUserAndPeriod =
        "select id " +
        "  from purchase_service " +
        " where user_id = ? " +
        "   and period_id = ? " +
        "   and deleted = 0";

}
