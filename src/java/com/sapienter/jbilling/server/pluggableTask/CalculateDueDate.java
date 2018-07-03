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

package com.sapienter.jbilling.server.pluggableTask;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.invoice.NewInvoiceContext;
import com.sapienter.jbilling.server.process.PeriodOfTime;
import com.sapienter.jbilling.server.util.CalendarUtils;
import com.sapienter.jbilling.server.util.MapPeriodToCalendar;

/**
 * This simple task gets the days to add to the invoice date from the billing process configuration. It doesn't get into
 * any other consideration, like business days, etc ...
 * 
 * @author Emil
 */
public class CalculateDueDate extends PluggableTask implements InvoiceCompositionTask {

    private static final FormatLogger LOG = new FormatLogger(Logger.getLogger(CalculateDueDate.class));

    /*
     * (non-Javadoc)
     * 
     * @see com.sapienter.jbilling.server.pluggableTask.InvoiceCompositionTask#apply(com.sapienter.betty.server.invoice.
     * NewInvoiceContext)
     */
    public void apply (NewInvoiceContext invoice, Integer userId) throws TaskException {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(invoice.getBillingDate());

        LOG.debug("Calculating due date from " + invoice.getBillingDate() + " using period "
                + invoice.getDueDatePeriod());

        try {
            // add the period of time
            if (CalendarUtils.isSemiMonthlyPeriod(invoice.getDueDatePeriod().getUnitId())) {
                calendar.setTime(CalendarUtils.addSemiMonthyPeriod(calendar.getTime()));
            } else {
                calendar.add(MapPeriodToCalendar.map(invoice.getDueDatePeriod().getUnitId()), invoice
                        .getDueDatePeriod().getValue());
            }

            // set the due date
            invoice.setDueDate(calendar.getTime());

        } catch (Exception e) {
            LOG.error("Unhandled exception calculating due date.", e);
            throw new TaskException(e);
        }
    }
}
