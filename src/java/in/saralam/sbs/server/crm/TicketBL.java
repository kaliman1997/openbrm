package in.saralam.sbs.server.crm;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import in.saralam.sbs.server.crm.event.CRMEvent;

import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.db.UserDAS;
import in.saralam.sbs.server.crm.SupportTicketWS;
import in.saralam.sbs.server.crm.db.SupportTicketDAS;
import in.saralam.sbs.server.crm.db.SupportTicketDTO;
import in.saralam.sbs.server.crm.db.TicketDetailsDAS;
import in.saralam.sbs.server.crm.db.TicketDetailsDTO;
import in.saralam.sbs.server.crm.db.TicketStatusDAS;
import in.saralam.sbs.server.crm.db.TicketStatusDTO;
//import in.saralam.sbs.server.notification.OpenBRMNotificationBL;
import com.sapienter.jbilling.server.system.event.EventManager;
import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.Constants;

//import com.sapienter.jbilling.server.pluggableTask.admin.TestWS;

public class TicketBL extends ResultList{

	private static final Logger LOG = Logger.getLogger(TicketBL.class);
	
	private SupportTicketDTO supportTicketDTO = null;
	private SupportTicketDAS supportTicketDAS = null;
	private TicketDetailsDTO ticketDetailsDTO = null;
	private TicketDetailsDAS ticketDetailsDAS = null;
	private	UserDTO baseUser = null , assignedUser = null;
	
	public TicketBL(){
		init();
	}
	
	public TicketBL(Integer ticketId){
		init();
		set(ticketId);
	}

	public void set(Integer ticketId) {
		supportTicketDTO = supportTicketDAS.find(ticketId);
	}

	private void init() {
		supportTicketDTO = new SupportTicketDTO();
		supportTicketDAS = new SupportTicketDAS();
		ticketDetailsDTO = new TicketDetailsDTO();
		ticketDetailsDAS = new TicketDetailsDAS();
	}
	
	public Integer createTicket(SupportTicketWS ticketWS){
		LOG.debug("in create tickets method in TicketBL....");
		SupportTicketDTO ticket = null;
		baseUser = new UserDAS().find(ticketWS.getBaseUserID());
		assignedUser = new UserDAS().find(ticketWS.getBaseUserID());
		try {
			supportTicketDTO.setSubject(ticketWS.getSubject());
			supportTicketDTO.setBaseUser(baseUser);
			supportTicketDTO.setAssignedUser(assignedUser);
			TicketStatusDTO ticketStatus = new TicketStatusDAS().find(Constants.TICKET_STATUS_NEW);
			supportTicketDTO.setTicketStatus(ticketStatus);
			supportTicketDTO.setCreatedDate(new Date());
			supportTicketDTO.setLastModified(new Date());
			ticket = supportTicketDAS.save(supportTicketDTO);
			ticketDetailsDTO.setTicket(ticket);
			ticketDetailsDTO.setCreatedDate(new Date());
			ticketDetailsDTO.setTicketBody(ticketWS.getTicketBody());
			TicketDetailsDTO ticketDetails = ticketDetailsDAS.save(ticketDetailsDTO);
		
			//getTicketDetails("ravi@sois.com");
			}catch (Exception e) {
				LOG.debug("error creating Ticket...."+e);
		}
		try {
		if(ticket!=null){
			CRMEvent event = new CRMEvent(ticket.getBaseUser().getCompany().getId(), ticket.getBaseUser());
			event.setEventName("CRM Create");
			EventManager.process(event);
		}
		}catch (Exception e) {
			LOG.debug("exception in send notification...."+e);
		}
		return ticket.getId();
	}
	
	public Integer updateTicket(SupportTicketWS ticketWS){
		LOG.debug("in update tickets method in TicketBL....");
		SupportTicketDTO ticket = null;
		baseUser = new UserDAS().find(ticketWS.getBaseUserID());
		try {
			supportTicketDTO = supportTicketDAS.find(ticketWS.getId());
			String status = supportTicketDTO.getTicketStatus().getType();
			supportTicketDTO.setSubject(ticketWS.getSubject());
			supportTicketDTO.setBaseUser(baseUser);
			supportTicketDTO.setTicketStatus(ticketWS.getTicketStatus());
			supportTicketDTO.setLastModified(new Date());
			ticket = supportTicketDAS.save(supportTicketDTO);
			//ticketDetailsDTO = ticketDetailsDAS.findByticket(ticket.getId());
			ticketDetailsDTO.setCreatedDate(new Date());
			ticketDetailsDTO.setTicket(ticket);
			ticketDetailsDTO.setTicketBody(ticketWS.getTicketBody());
			//LOG.debug("body is "+ticketWS.getTicketBody().length());
			if(ticketWS.getTicketBody().length()!=0)
				ticketDetailsDAS.save(ticketDetailsDTO);
			
		} catch (Exception e) {
			LOG.debug("error updating Ticket...."+e);
		}
		try {
		if(ticket!=null && ticket.getTicketStatus().getId()!=Constants.TICKET_STATUS_CLOSED){
			CRMEvent event = new CRMEvent(ticket.getBaseUser().getCompany().getId(), ticket.getBaseUser());
			event.setEventName("CRM Update");
			EventManager.process(event);
		}
		}catch (Exception e) {
			LOG.debug("exception in send notification...."+e);
		}
		return null;
	}
	
	public void deleteTicket(Integer ticketId){
		
	}
	
	
	
			
	public List<SupportTicketWS> getTicketBodyUsingTicket(Integer ticketID){	
		LOG.debug("we are in getTicketDetails");
		List<SupportTicketWS> testWS = new ArrayList<SupportTicketWS>();
		List<TicketDetailsDTO> ticketDetailsDto = new TicketDetailsDAS().findByticket(ticketID);
		for(TicketDetailsDTO ticketDetailDto : ticketDetailsDto){	
			LOG.debug("Ticket Body is : " + ticketDetailDto.getTicketBody());			
			//TestWS ticket = new TestWS(ticketID,ticketDetailDto.getTicketBody());
			SupportTicketWS ticket = new SupportTicketWS(ticketDetailDto.getCreatedDate(),ticketDetailDto.getTicketBody());
			LOG.debug("Ticket WS is : " + testWS);
			testWS.add(ticket);
		}
		LOG.debug("Final Ticket WS is : " + testWS);
		LOG.debug("no of rows written " + testWS.size());
		return testWS;	
	}
	
	public List<SupportTicketWS> getTicketDetails(String userName){
		LOG.debug("we are in getTicketDetails");
		List<SupportTicketWS> testWS = new ArrayList<SupportTicketWS>();
		LOG.debug("User Name is : " +userName);
		//UserDTO userDto = new UserDAS().findByUserName(userName,20);
		List<SupportTicketDTO> supportTicketDetails = new SupportTicketDAS().findByUser(userName);
		LOG.debug("No of Tickets : " + supportTicketDetails.size());
		//TicketStatusDTO ticketStatusDto = new TicketStatusDAS().findByticket(1);
		for(SupportTicketDTO supportTicketDetail : supportTicketDetails){
			LOG.debug("Ticket ID is : " + supportTicketDetail.getId());
			SupportTicketWS ticket = new SupportTicketWS(new Integer(supportTicketDetail.getId()),supportTicketDetail.getSubject(),supportTicketDetail.getTicketStatus(),supportTicketDetail.getCreatedDate(),supportTicketDetail.getLastModified());
			LOG.debug("Ticket WS is : " + testWS);
			testWS.add(ticket);
		}
		LOG.debug("Final Ticket WS is : " + testWS);
		LOG.debug("no of rows written " + testWS.size());
		return testWS;	
	}
	
	// public TestWS getTicketDetails(Integer id,String sub){
		// LOG.debug("we are in getTicketDetails");
		// TicketStatusDTO ticketStatus = new TicketStatusDTO(2,"Open");
		// TestWS testWS = new TestWS(id,sub,ticketStatus);
		// LOG.debug("Final Ticket WS is : " + testWS);
		// return testWS;	
	// }
	
	
			
}
