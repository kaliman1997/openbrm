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
package com.sapienter.jbilling.server.provisioning.db;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;


@Entity
@DiscriminatorValue("order_line_provisioning_status")
public class ProvisioningStatusDTO extends AbstractGenericStatus 
        implements java.io.Serializable {

//    private Set<OrderLineDTO> orderLines = new HashSet<OrderLineDTO>(0);

    public ProvisioningStatusDTO() {
    }
    
    public ProvisioningStatusDTO(int statusValue) {
        this.statusValue = statusValue;
    }
/*
    public ProvisioningStatusDTO(int statusValue, Set<OrderLineDTO> orderLines) {
       this.statusValue = statusValue;
       this.orderLines = orderLines;
    }
*/
    @Transient
    protected String getTable() {
        return Constants.TABLE_ORDER_LINE_PROVISIONING_STATUS;
    }
/*
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="provisioningStatus")
    public Set<OrderLineDTO> getOrderLines() {
        return this.orderLines;
    }
    
    public void setOrderLines(Set<OrderLineDTO> orderLines) {
        this.orderLines = orderLines;
    }
*/
}
