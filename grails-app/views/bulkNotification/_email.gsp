

<%@ page contentType="text/html;charset=UTF-8" %>

<%@page import="in.saralam.sbs.server.notification.db.NotificationConfigDAS;com.sapienter.jbilling.server.user.db.CompanyDTO"%>

<%--
  Shows edit form for a contact type.

  @author Vikas Bodani
  @since  30-Sept-2011
--%>

<div class="column-hold">
    
   <g:set var="isNew" value="${!notifications || !notifications?.id || notifications.id == 0}"/>

    <div class="heading">
        <strong>
            <g:if test="${isNew}">
                <g:message code="Email"/>
            </g:if>
            <g:else>
                <g:message code="Email"/>
            </g:else>
        </strong>
    </div>
   <div class="form-hold">
     <g:form id="email-form"  name="email-form" url="[action: 'save']">
   	
	<div style="height: 20px"></div>
		<table width="550px">
		<tbody>
			<tr><td>           
				<label> &nbsp &nbsp Recepient :</label></td><td>
				<g:textField class="field" name="recepient" value="${params?.recepient }" size="40"/>
				<div style="height: 5px"></div>
				</td>
			</tr>
			<tr><td class="medium_notify_config" >
					<label>  &nbsp &nbsp Message :</label></td><td>
					<g:select name="message" from="${new NotificationConfigDAS().findNotificationMessages(new CompanyDTO(session['company_id']).id)}"
                    optionKey="id" optionValue="content"
                    value="${params?.message }" style="float: center;width: 100%"/>
				</td>
			</tr><br/>
		
		</tbody>
		</table></g:form>
   	

    <div class="btn-box buttons">
        <ul>
            <li><a class="submit save" onclick="$('#email-form').submit();"><span><g:message code="Send Email"/></span></a></li>
        </ul>
    </div>

</div>


<div class="table-box">
    <table id="bulkEmail" cellspacing="0" cellpadding="0">
        <thead>
            <tr>
			<th>
                    <g:remoteSort action="list" sort="recepients" update="column1">
                        <g:message code="Recepients"/>
                    </g:remoteSort>
                </th>
				 <th>
                    <g:remoteSort action="list" sort="message" update="column1">
                        <g:message code="Message"/>
                    </g:remoteSort>
                </th>
                            
            </tr>
        </thead>

        <tbody>
       <g:each var="imapPair" in="${valueMap.entrySet()}">
	   
					<tr id="type-1" onclick="FillBilling(this.form)" class="active">
			
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell"  onclick="FillBilling(this.form)"  before="register(this);" onSuccess="render(data, next);">
                                ${imapPair.key}
                            </g:remoteLink>
                        </td>

						<td>
                            <g:remoteLink breadcrumb="id" class="cell"  before="register(this);" onSuccess="render(data, next);">
                                ${imapPair.value}
                            </g:remoteLink>
							
                        </td>	                 
                </tr>
              
            </g:each>
        
        
        </tbody>
    </table>
</div>





</div>