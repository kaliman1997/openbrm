

<%@ page import="com.sapienter.jbilling.server.order.db.OrderPeriodDTO" %>
<%@ page import="com.sapienter.jbilling.server.user.db.CompanyDTO" %>
<%@ page import="com.sapienter.jbilling.server.util.Constants" %>


<div id="details-box">
 <g:formRemote name="order-general-form" url="[action: 'edit']" >
        <g:hiddenField name="_eventId" value="general"/>
        <g:hiddenField name="execution" value="${flowExecutionKey}"/>
        <div class="form-columns">
		
		 <table class="dataTable">
	    <tbody>
          <tr>
				<td>
		
					<g:applyLayout name="form/input" >
						<content tag="label"><g:message code="Subject"/></content>
						<content tag="label.for">subject</content>   
						<content tag="onClose">
						function() {
									$('#order-general-form').submit();
									}
					</content>						
						<g:textField class="field text" name="subject" style="width: 100px;" value="${scheduler?.subject}"/>            
					</g:applyLayout>
				</td>
	     </tr>
        
               
	    <tr>
			<td>
		
				<g:applyLayout name="form/input" >
				
					<content tag="label"><g:message code="UserName"/></content>
					<content tag="label.for" >userName</content>
					<content tag="onClose">
						function() {
									$('#order-general-form').submit();
									}
					</content>
					<g:textField class="field text" name="userName" value="${params?.userName}" size="20" />  
				<td> <button name="button" value="fetch"><span>Fetch</span></button></td>
				
						<g:if test="${params.userId!=null}">
						<g:if test="${params.userId!='0'}">
						<label style="padding-left: 10px">
					<sec:access url="/customer/show">
						<g:remoteLink controller="customer" action="show" id="${params?.userId }" before="register(this);" onSuccess="render(data, next);">
                           ${scheduleWS?.baseUser.id }
						</g:remoteLink>
					 </sec:access></label></g:if></g:if><g:if test="${params?.userId=='0'}">
						<label style="color: red"><em>user not exist</em></label>
						</g:if>
						<div style="height: 5px"></div>
						
			   </g:applyLayout>
			</td>
	    </tr>
        
       <tr>
			<td>		
				<g:applyLayout name="form/select">
					<content tag="label"><g:message code="order.label.period"/></content>
					<content tag="label.for">periodId</content>
					<content tag="onClose">
						function() {
									$('#order-general-form').submit();
									}
					</content>
					<g:set var="company" value="${CompanyDTO.get(session['company_id'])}"/>
					<g:select from="${(company.orderPeriods << new OrderPeriodDTO(Constants.ORDER_PERIOD_ONCE)).sort{it.id}}"
						  noSelection="['': message(code: 'Select Period')]"
                          optionKey="id" optionValue="${{it.getDescription(session['language_id'])}}"
                          name="periodId"
                          value="${scheduler?.periodId}"/>					
				</g:applyLayout>
		   </td>
	  </tr>
        
     <tr>
		 <td>            
             <g:applyLayout name="form/date">
                <content tag="label"><g:message code="order.label.active.since"/></content>
                <content tag="label.for">activeSince</content>
                <content tag="onClose">
                     function() {
                            $('#order-general-form').submit();
                        }
                </content>
					<g:textField class="field" name="activeSince" value="${formatDate(date: scheduler?.activeSince, formatName: 'datepicker.format')}"/>
            </g:applyLayout>
		</td>
    </tr>
        
    <tr>
		<td>
            <g:applyLayout name="form/date">
                <content tag="label"><g:message code="order.label.active.until"/></content>
                <content tag="label.for">activeUntil</content>
                <content tag="onClose">
                        function() {
                            $('#order-general-form').submit();
                        }
                </content>
					<g:textField class="field" name="activeUntil" value="${formatDate(date: scheduler?.activeUntil, formatName: 'datepicker.format')}"/>
            </g:applyLayout>
		</td>
   </tr>
        
   <tr>
	   <td>
		   <g:applyLayout name="form/date">
                <content tag="label"><g:message code="DOE"/></content>
                <content tag="label.for">doe</content>
                <content tag="onClose">
                    function() {
                            $('#order-general-form').submit();
                        }
                </content>
					<g:textField class="field" name="doe" value="${formatDate(date: scheduler?.doe, formatName: 'datepicker.format')}"/>
            </g:applyLayout>
		</td>
	</tr>
        
  </tbody>
  </table>
  </div>
  <hr/>
  </g:formRemote>
		<script type="text/javascript">
			$(function() {
                    $('#order-general-form').find('select').change(function() {
					$('#order-general-form').submit();
				});

					$('#order-general-form').find('input:checkbox').change(function() {
					$('#order-general-form').submit();
				});

					$('#order-general-form').find('input.text').blur(function() {
					$('#order-general-form').submit();
				});

					$('#order-general-form').find('textarea').blur(function() {
					$('#order-general-form').submit();
				});      
        </script> 
 </div>




    
    