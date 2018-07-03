
<%@ page import="com.sapienter.jbilling.server.user.db.CompanyDTO; com.sapienter.jbilling.server.item.db.ItemTypeDTO" %>

<%@page import= "in.saralam.sbs.server.Scheduler.db.SchedulerTypeDAS"%>
<%@page import= "in.saralam.sbs.server.Scheduler.db.SchedulerStatusDAS"%>
<%@page import="in.saralam.sbs.server.crm.db.TicketDetailsDTO"%>
<%@page import="in.saralam.sbs.server.crm.db.TicketStatusDAS"%>
<%@ page import="com.sapienter.jbilling.server.util.Util" %>
<%@ page import="com.sapienter.jbilling.client.util.Constants" %>
<%@ page import="com.sapienter.jbilling.server.item.db.ItemDTO; com.sapienter.jbilling.server.item.db.ItemDAS"%>
<%@ page import=" com.sapienter.jbilling.server.device.db.DeviceDTO"%>
<%@ page import=" com.sapienter.jbilling.server.util.Constants;  in.saralam.sbs.server.rating.db.RatingEventTypeDAS"%>
<%@page import=" com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskTypeDAS "%>
<%@page import="com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskTypeDTO" %>
<%@ page import="com.sapienter.jbilling.server.order.db.OrderPeriodDTO" %>
<%@ page import="com.sapienter.jbilling.server.user.db.CompanyDTO" %>

		<SCRIPT language="javascript">
				
			function addRow(tableID) {
	         var table = document.getElementById(tableID);
	          var rowCount = table.rows.length;
    	     var row = table.insertRow(rowCount);
	         console.log("row ........" + row);
	         var colCount = table.rows[0].cells.length;
			  console.log("colCount ........" + colCount);
	         var l=0;
	         for(var i=0; i<colCount; i++) {
	             var newcell = row.insertCell(i);
	             newcell.innerHTML = table.rows[1].cells[i].innerHTML;
	            
	             switch(newcell.childNodes[0].type) {
	             
    	             case "text":
        	                 newcell.childNodes[0].value = '';
            	             break;
	                 case "select-one":
    	                     newcell.childNodes[0].selectedIndex = '';
        	                 break;	                 
            	 }
        	 }	      
		 }
		
		
		        	  
		 function delRow(x)
		  {
			 try{
				 var table = document.getElementById(x);
		         var rowCount = table.rows.length;
			    var current = window.event.srcElement;
			    
			    if (rowCount > 2){
			    while ( (current = current.parentElement)  && current.tagName !="TR");
			         current.parentElement.removeChild(current);
			    }else{
			    	return false;
				    }
				  }catch(e) {
			             alert(e);
			         }
		  }

		 
    </script>
	
<div class="details-box">
 <g:formRemote name="order-details-form" url="[action: 'edit']">
        <g:hiddenField name="_eventId" value="details"/>
        <g:hiddenField name="execution" value="${flowExecutionKey}"/>
     
					
		<g:set var="oneoff" value=""/>
                
                <div id="oneoff" class="box-cards box-cards-open">
                   
				<div class="box-card-hold">
                <table>
          <tbody>
		       <tr>                          
			      <td><br/><br/>
						<b><div style="padding-left: 120px;  font-size:22px">Action</div><b>
				  </td> 
				  
				  <td> 
						<div style="padding-left: 350px;"></div>
						<button type="button" onclick="addRow('dataTable1')" style="float: right">
						<i class="icon-shopping-cart icon-white"></i>
						<span>Add Action</span>
						</button>
				 </td>
			</tr>
         </tbody> 
	  </table>
										
		  <fieldset>	
	
		<div class="form-columns">  
  	   
		<TABLE  id="dataTable1" width="600px" border="2px">		
			<thead>
			<tr><th></th></tr>
			</thead>
		 <tbody>
			 
            <TR> 
			<TD>  
		<g:applyLayout name="form/select" >
		
				                           	  
	    		 <div class="select4" style="width: 150px;padding-left: 20px; " align="center">
						<b>Type</b>
						<content tag="onClose">
						function() {
									$('#order-details-form').submit();
									}
					</content>
                 	    <g:select from="${new SchedulerTypeDAS().findAll()}"  style="width: 150px;"
						noSelection="['': message(code: 'Select Type')]" 
						optionKey="id" optionValue="description" 
                        name="typeId" 
                        value="${params.typeId}"/> 
				 </div>
									
				<div class="select4" style="width: 150px" align="center" >
						&nbsp;&nbsp;&nbsp; <b>Period</b>
						<content tag="onClose">
						function() {
									$('#order-details-form').submit();
									}
					</content>
                 	   <g:set var="company" value="${CompanyDTO.get(session['company_id'])}"/>
                       <g:select from="${(company.orderPeriods << new OrderPeriodDTO(Constants.ORDER_PERIOD_ONCE)).sort{it.id}}"  style="width: 150px;"
						noSelection="['': message(code: 'Select Period')]"
                        optionKey="id" optionValue="${{it.getDescription(session['language_id'])}}" 
                        name="actionPeriodId" 
                        value="${params.actionPeriodId}"/>
                </div>
						 
				<div class="select4" style="width: 150px "  align="center">
						 &nbsp;&nbsp;&nbsp; <b>Handler</b>
						 <content tag="onClose">
						function() {
									$('#order-details-form').submit();
									}
					</content>
						 <g:select name="pluginId" from="${new PluggableTaskTypeDAS().findAllByCategory(7)}"  style="width:150px;"
            		     noSelection="['': message(code: 'Select Handler')]" 
						 optionKey="id" optionValue="className"
					     value="${params.pluginId}" />
	    		 </div>
				 
				 <div class="select4" style="width: 100px ">
				    
				 </div>
				  </g:applyLayout> <br/>
		
			
		
	               	<div class="select4"  style="width: 120px;padding-left: 100px; "  align = "center">
					 
                         		<b>Name</b>
	                 <content tag="onClose">
						function() {
									$('#order-details-form').submit();
									}
					</content>
                                <g:textField class="field text"  name="name" value="${params.name}"/>
                            	</div>
                                  

				  &nbsp &nbsp &nbsp &nbsp &nbsp <div class="select4"  style="width: 120px" align = "center">
					 
                         		&nbsp &nbsp &nbsp <b>Value</b>
	                         <content tag="onClose">
						function() {
									$('#order-details-form').submit();
									}
					</content>
                                <g:textField class="field text"  name="value" value="${params.value}"/>
                            	</div>
						
						<div style="padding-left: 330px;"></div>
						<button type="button" onclick="delRow('dataTable1')" style="float: right">
						<i class="icon-shopping-cart icon-white"></i>
						<span>Delete</span>
						</button> <br/>
  			  	</TD>                                   								
		 		   
		   </TR>
				  	   
        	</tbody>
			 </TABLE>
			 	 
	</div>
	  	   <div style="height: 10px"></div>
   
	</fieldset></div></div>
	
		  	
	    </g:formRemote>
		     
		 <div style="height: 20px"></div>
     <div class="btn-box">
        <g:link class="submit save" action="edit" params="[_eventId: 'save']">
            <span><g:message code="button.save"/></span>
        </g:link>

        <g:link class="submit cancel" action="edit" params="[_eventId: 'cancel']">
            <span><g:message code="button.cancel"/></span>
        </g:link>
    </div>

	
	<script type="text/javascript">
                $(function() {
	
            $('#order-details-form').find('select').change(function() {
                $('#order-details-form').submit();
            });

            $('#order-details-form').find('input:checkbox').change(function() {
                $('#order-details-form').submit();
            });

            $('#order-details-form').find('input.text').blur(function() {
                $('#order-details-form').submit();
            });

            $('#order-details-form').find('textarea').blur(function() {
                $('#order-details-form').submit();
            });
        });
    </script>
	
	</div> 