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



package com.sapienter.jbilling.server.provisioning.event;

//~--- non-JDK imports --------------------------------------------------------

import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.system.event.Event;

/**
 * This event occurs when an order's activeUntil date becomes <= than current date
 *
 * @author othman El Moulat
 *
 */
public class SubscriptionInactiveEvent implements Event {
    private final Integer  entityId;
    private final OrderDTO order;

    /**
     *     @param entityId
     *     @param order
     */
    public SubscriptionInactiveEvent(Integer entityId, OrderDTO order) {
        this.entityId = entityId;
        this.order    = order;
    }

    public Integer getEntityId() {
        return entityId;
    }

    /**
     *     @return the order
     */
    public OrderDTO getOrder() {
        return order;
    }

    public String getName() {
        return "Subscription Inactive Event - entity " + entityId;
    }

    public String toString() {
        return getName() + " - entity " + entityId;
    }
}
