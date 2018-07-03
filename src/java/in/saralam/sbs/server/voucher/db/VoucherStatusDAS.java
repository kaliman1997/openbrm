/*
 * JBILLING CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise jBilling Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise jBilling Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise jBilling Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */
package in.saralam.sbs.server.voucher.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.util.db.AbstractGenericStatusDAS;


public class VoucherStatusDAS extends AbstractGenericStatusDAS<VoucherStatusDTO> {

    private static final String SQL =

           "select status_value from generic_status where id=:id";

 public Integer findById(Integer statusId){

      Query value = getSession()

                 .createSQLQuery(SQL);

            value.setParameter("id",statusId);

    return (Integer)value.uniqueResult();

 }

}