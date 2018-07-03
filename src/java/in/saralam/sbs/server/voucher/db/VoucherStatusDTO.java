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

import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;
import com.sapienter.jbilling.server.util.Constants;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

@Entity
@DiscriminatorValue("voucher_status")
public class VoucherStatusDTO extends AbstractGenericStatus implements Serializable {

    private Set<VoucherDTO> voucherDTOs = new HashSet<VoucherDTO>(0);

    public VoucherStatusDTO() { }

    public VoucherStatusDTO(Integer id) {
        this.id = id;
    }

    public VoucherStatusDTO(Integer id, Set<VoucherDTO> voucherDTOs) {
        this.id = id;
        this.voucherDTOs = voucherDTOs;
				
    }
	
	public VoucherStatusDTO(int  statusValue) {
        this.statusValue = statusValue;
    }

    public VoucherStatusDTO(int statusValue, Set<VoucherDTO> voucherDTOs) {
        this.statusValue = statusValue;
        this.voucherDTOs = voucherDTOs;
				
    }   

    @Transient
    protected String getTable() {
        return Constants.TABLE_VOUCHER_STATUS;
    }
}
