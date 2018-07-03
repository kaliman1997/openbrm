/*
 jBilling - The Enterprise Open Source Billing System
 Copyright (C) 2003-2011 Enterprise jBilling Software Ltd. and Emiliano Conde

 This file is part of jbilling.

 jbilling is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 jbilling is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with jbilling.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sapienter.jbilling.server.item.tasks;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.sapienter.jbilling.server.rule.RulesBaseTask;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.runtime.StatelessKnowledgeSession;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.ContactDTOEx;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.util.DTOFactory;
import java.math.BigDecimal;
import java.util.ArrayList;

@Deprecated
public class RulesPricingTask extends RulesBaseTask implements IPricing {

    private static final FormatLogger LOG = new FormatLogger(Logger.getLogger(RulesPricingTask.class));
    protected FormatLogger getLog() { return LOG; }

    public BigDecimal getPrice(ItemDTO item, BigDecimal quantity, Integer userId, Integer currencyId,
            List<PricingField> fields, BigDecimal defaultPrice, OrderDTO pricingOrder, OrderLineDTO orderLine,
            boolean singlePurchase, Date eventDate) throws TaskException {
        // now we have the line with good defaults, the order and the item
        // These have to be visible to the rules
        KnowledgeBase knowledgeBase;
        try {
            knowledgeBase = readKnowledgeBase();
        } catch (Exception e) {
            throw new TaskException(e);
        }
        StatelessKnowledgeSession mySession = knowledgeBase.newStatelessKnowledgeSession();
        List<Object> rulesMemoryContext = new ArrayList<Object>();
        
        PricingManager manager = new PricingManager(item.getId(), userId, currencyId, defaultPrice);
        mySession.setGlobal("manager", manager);
        
        if (fields != null && !fields.isEmpty()) {
            rulesMemoryContext.addAll(fields);
        }

        try {
            if (userId != null) {
                UserDTOEx user = DTOFactory.getUserDTOEx(userId); 
                rulesMemoryContext.add(user);
                ContactBL contact = new ContactBL();
                contact.set(userId);
                ContactDTOEx contactDTO = contact.getDTO();
                rulesMemoryContext.add(contactDTO);
            }
            rulesMemoryContext.add(manager);

            // Add the subscriptions
            OrderBL order = new OrderBL();
            for (OrderDTO myOrder : order.getActiveRecurringByUser(userId)) {
                for (OrderLineDTO myLine : myOrder.getLines()) {
                    rulesMemoryContext.add(new Subscription(myLine));
                }
            }

        } catch (Exception e) {
            throw new TaskException(e);
        }
        // then execute the rules
        for (Object o: rulesMemoryContext) {
            LOG.debug("in memory context=%s", o);
        }
        mySession.execute(rulesMemoryContext);

        return manager.getPrice();
    }
}
