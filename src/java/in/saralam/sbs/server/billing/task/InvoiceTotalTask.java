package in.saralam.sbs.server.billing.task;

import java.math.BigDecimal;
import org.apache.log4j.Logger;
import java.math.RoundingMode;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.JRException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.MapPeriodToCalendar;
import com.sapienter.jbilling.server.util.PreferenceBL;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.common.SessionInternalError;

import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.notification.NotificationBL;
import com.sapienter.jbilling.server.notification.NotificationNotFoundException;
import com.sapienter.jbilling.server.notification.INotificationSessionBean;
import com.sapienter.jbilling.server.user.db.CustomerDTO;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.invoice.NewInvoiceEvent;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.metafields.*;
import com.sapienter.jbilling.server.metafields.db.*;
import com.sapienter.jbilling.server.report.ReportBL;
import com.sapienter.jbilling.server.report.db.ReportDTO;
import com.sapienter.jbilling.server.report.db.ReportParameterDTO;
import com.sapienter.jbilling.server.report.db.parameter.IntegerReportParameterDTO;
import net.sf.jasperreports.engine.JasperPrint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class InvoiceTotalTask extends PluggableTask implements IInternalEventsTask {
	private static final Class<Event> events[] = new Class[] 
	{ NewInvoiceEvent.class};
	private static final Logger LOG = Logger.getLogger(InvoiceTotalTask.class);
	public Class<Event>[] getSubscribedEvents() {
		return events;
	}
	public void process(Event event) throws PluggableTaskException {
		
		
		LOG.debug("InvoiceTotalTask is triggered....");
		if (event instanceof NewInvoiceEvent) {
			NewInvoiceEvent newInvoice = (NewInvoiceEvent) event;
			InvoiceDTO invoice = newInvoice.getInvoice();
			try {
				BigDecimal invoiceTotal = invoice.getTotal();
				LOG.debug("Current Invoice " + invoice.getId() + " Total is " + invoiceTotal);

				BigDecimal increment = new BigDecimal("0.05");

				BigDecimal divided = invoiceTotal.divide(increment, 0, RoundingMode.HALF_EVEN);
            			BigDecimal roundedTotal = divided.multiply(increment);

				LOG.debug("Current Invoice " + invoice.getId() + " New Total is " + roundedTotal);
                                invoice.setTotal(roundedTotal);

				/*
				BigDecimal fraction = invoiceTotal.remainder(BigDecimal.ONE);
				LOG.debug("Current Invoice fraction is " + fraction);
				double doubleValue = fraction.doubleValue();
        
        			if(((doubleValue * 100) > 0) && ((doubleValue * 100) < 9)) {
            				doubleValue = ((doubleValue * 100)  + (5 - (doubleValue * 100) ))/100;
					BigDecimal roundedTotal = invoiceTotal.setScale(0, RoundingMode.FLOOR).add(new BigDecimal(doubleValue));
                                	LOG.debug("Current Invoice " + invoice.getId() + " New Total is " + roundedTotal);
                                	invoice.setTotal(roundedTotal);

        			}*/
        
				LOG.debug("Current Invoice " + invoice.getId() + " New Total is " + invoice.getTotal());
				
			} catch (Exception ex) {
				LOG.error("Exception while sending call report", ex);
			}
			
		} else {
			//throw new PluggableTaskException("Cant not process event " + event);
		}
	}




}
