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

import com.sapienter.jbilling.common.Constants;
import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.util.Record;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDAS;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderStatusDAS;
import com.sapienter.jbilling.server.order.OrderStatusFlag;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.ContactDTOEx;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.util.DTOFactory;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.runtime.rule.FactHandle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Deprecated
public class RulesItemManager extends BasicItemManager {

    private static final FormatLogger LOG = new FormatLogger(Logger.getLogger(RulesItemManager.class));
    protected OrderManager helperOrder = null;
    private List<Record> records;
    private List<OrderLineDTO> lines;

    public void addItem(Integer itemID, BigDecimal quantity, Integer language,
                        Integer userId, Integer entityId, Integer currencyId,
                        OrderDTO order, List<Record> records,
                        List<OrderLineDTO> lines, boolean singlePurchase) throws TaskException {
        super.addItem(itemID, quantity, language, userId, entityId, currencyId, order, records, lines, singlePurchase, null, null);
        this.records = records;
        this.lines = lines;
        helperOrder = new OrderManager(order, language, userId, entityId, currencyId);
        processRules(order);
    }

    protected void processRules(OrderDTO newOrder) throws TaskException {
        // now we have the line with good defaults, the order and the item
        // These have to be visible to the rules
        KnowledgeBase knowledgeBase;
        try {
            knowledgeBase = readKnowledgeBase();
        } catch (Exception e) {
            throw new TaskException(e);
        }
        session = knowledgeBase.newStatefulKnowledgeSession();
        List<Object> rulesMemoryContext = new ArrayList<Object>();
        rulesMemoryContext.add(helperOrder);

        // add OrderDTO to rules memory context
        newOrder.setCurrency(new CurrencyDAS().find(newOrder.getCurrency().getId()));
        if (newOrder.getCreateDate() == null) {
            newOrder.setCreateDate(new Date());
        }
        rulesMemoryContext.add(newOrder);

        for (OrderLineDTO line : newOrder.getLines()) {
            if (line.getItem() != null) {
                ItemBL item = new ItemBL(line.getItemId());
                rulesMemoryContext.add(item.getDTO(helperOrder.getLanguage(), helperOrder.getUserId(),
                        helperOrder.getEntityId(), helperOrder.getCurrencyId()));
            }
            rulesMemoryContext.add(line);
        }

        if (newOrder.getPricingFields() != null && newOrder.getPricingFields().size() > 0) {
            for (PricingField pf : newOrder.getPricingFields()) {
                rulesMemoryContext.add(pf);
            }
        }
        try {
            Integer userId = newOrder.getBaseUserByUserId().getId();
            UserDTOEx user = DTOFactory.getUserDTOEx(userId);
            rulesMemoryContext.add(user);
            ContactBL contact = new ContactBL();
            contact.set(userId);
            ContactDTOEx contactDTO = contact.getDTO();
            rulesMemoryContext.add(contactDTO);


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
        session.setGlobal("order", helperOrder);
        // then execute the rules


        executeStatefulRules(session, rulesMemoryContext);
    }

    public class OrderManager {

        private OrderDTO order = null;
        private Integer language = null;
        private Integer userId = null;
        private Integer entityId = null;
        private Integer currencyId = null;

        public OrderManager(OrderDTO order, Integer language, Integer userId, Integer entityId, Integer currencyId) {
            this.order = order;
            this.language = language;
            this.userId = userId;
            this.entityId = entityId;
            this.currencyId = currencyId;
        }

        public OrderDTO getOrder() {
            return order;
        }

        public void setOrder(OrderDTO order) {
            this.order = order;
        }

        public OrderLineDTO addItem(Integer itemID, Integer quantity) throws TaskException {
            return addItem(itemID, new BigDecimal(quantity));
        }

        public OrderLineDTO addItem(Integer itemID, BigDecimal quantity) throws TaskException {
            LOG.debug("Adding item %s q: %s", itemID, quantity);

            BasicItemManager helper = new BasicItemManager();
            OrderLineDTO oldLine = order.getLine(itemID);
            FactHandle h = null;
            if (oldLine != null) {
                h = handlers.get(oldLine);
            }
            helper.addItem(itemID, quantity, language, userId, entityId, currencyId, order, records, lines, false, null, null);
            OrderLineDTO retValue = helper.getLatestLine();
            if (h != null) {
                LOG.debug("updating");
                session.update(h, retValue);
            } else {
                LOG.debug("inserting");
                handlers.put(retValue, session.insert(retValue));
            }

            LOG.debug("Now order line is %s , hash: %s", retValue, retValue.hashCode());
            return retValue;
        }

        public OrderLineDTO addItem(Integer itemId) throws TaskException {
            return addItem(itemId, 1);
        }

        public void removeItem(Integer itemId) {
            removeObject(order.getLine(itemId));
            order.removeLine(itemId);
        }


        /**
         * Adds or updates an order line. Calculates a percentage item order
         * line amount based on the amount of another order line. This is added
         * to the existing percentage order line's amount.
         */
        public OrderLineDTO percentageOnOrderLine(Integer percentageItemId,
                OrderLineDTO line) throws TaskException {
            // try to get percentage item order line
            OrderLineDTO percentageLine = order.getLine(percentageItemId);
            if (percentageLine == null) {
                // add percentage item
                percentageLine = addItem(percentageItemId);
                percentageLine.setAmount(BigDecimal.ZERO);
                percentageLine.setTotalReadOnly(true);
            }

            // now add the percentage amount based on the order line item amount
            BigDecimal percentage = percentageLine.getItem().getPercentage();
            BigDecimal base = line.getPrice().multiply(line.getQuantity());
            BigDecimal result = base.divide(new BigDecimal("100"), Constants.BIGDECIMAL_SCALE,
                    Constants.BIGDECIMAL_ROUND).multiply(percentage).add(percentageLine.getAmount());
            percentageLine.setAmount(result);

            return percentageLine;
        }

        public OrderDTO createOrder(Integer itemId, Double quantity) throws TaskException {
            BigDecimal quant = new BigDecimal(quantity).setScale(Constants.BIGDECIMAL_SCALE, Constants.BIGDECIMAL_ROUND);
            return createOrder(itemId, quant);
        }

        public OrderDTO createOrder(Integer itemId, BigDecimal quantity) throws TaskException {
            // copy the current order
            OrderDTO newOrder = new OrderDTO(order);
            newOrder.setId(0);
            newOrder.setVersionNum(null);
            // the period needs to be in the session
            newOrder.setOrderPeriodId(order.getOrderPeriod().getId());
            // the status should be active
            newOrder.setOrderStatus(new OrderStatusDAS().find(new OrderStatusDAS().getDefaultOrderStatusId(OrderStatusFlag.INVOICE, entityId)));
            // but without the lines
            newOrder.getLines().clear();
            // but do get the new line in
            OrderManager helper = new OrderManager(newOrder, language, userId, entityId, currencyId);
            OrderLineDTO newLine = helper.addItem(itemId, quantity);
            newLine.setPurchaseOrder(newOrder);
            newLine.setDefaults();

            return new OrderDAS().save(newOrder);
        }

        public void removeOrder(Integer itemId) {
            List<OrderLineDTO> list = new OrderLineDAS().findByUserItem(order.getBaseUserByUserId().getId(), itemId);

            for (OrderLineDTO line : list) {
                LOG.debug("Deleting order %s", line.getPurchaseOrder().getId());

                // who is the executor? we'll use the owner.. she is cancelling
                new OrderBL(line.getPurchaseOrder()).delete(order.getBaseUserByUserId().getId());
            }
        }

        public Integer getCurrencyId() {
            return currencyId;
        }

        public Integer getEntityId() {
            return entityId;
        }

        public Integer getLanguage() {
            return language;
        }

        public Integer getUserId() {
            return userId;
        }
    }
}
