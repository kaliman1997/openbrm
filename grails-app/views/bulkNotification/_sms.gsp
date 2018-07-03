%{--
  JBILLING CONFIDENTIAL
  _____________________

  [2003] - [2012] Enterprise jBilling Software Ltd.
  All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Enterprise jBilling Software.
  The intellectual and technical concepts contained
  herein are proprietary to Enterprise jBilling Software
  and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden.
  --}%

<%@ page contentType="text/html;charset=UTF-8" %>

<%--
  Shows edit form for a contact type.

  @author Vikas Bodani
  @since  30-Sept-2011
--%>

			<script type="text/javascript" language="JavaScript">
			function FillBilling(f) {			
			f.params.recepient = f.imapPair.key;
			f.params.message = f.imapPair.value;
			}
		</script>


<div class="column-hold">
    
   <g:set var="isNew" value="${!notifications || !notifications?.id || notifications.id == 0}"/>

    <div class="heading">
        <strong>
            <g:if test="${isNew}">
                <g:message code="SMS"/>
            </g:if>
            <g:else>
                <g:message code="SMS"/>
            </g:else>
        </strong>
    </div>
	
	 <div class="form-hold">
     <g:form id="sms-form"  name="sms-form" url="[action: 'sendsms']">
   	
	<div style="height: 20px"></div>
		<table width="550px">
		<tbody>
			<tr><td>           
				<label>	&nbsp &nbsp &nbsp	Recepient :</label></td><td>
				<g:textField class="field" name="recepient" value="${params?.recepient }" size="40"/>
				<td class="value">
                    ${params?.recepient}
                </td>
				<div style="height: 5px"></div>
				</td>
			</tr>
			<tr><td valign="top">
				<label>	&nbsp &nbsp &nbsp	Message :</label></td><td>   
				<g:textArea name="message"  cols="40" rows="5"  value="${params?.message }"></g:textArea> 
				<td class="value">
                    ${params?.message}
                </td>				
				</td>
			</tr>
		</tbody>
		</table>
		</g:form>
   	

    <div class="btn-box buttons">
        <ul>
            <li><a class="submit save" onclick="$('#sms-form').submit();"><span><g:message code="Send Now"/></span></a></li>
        </ul>
    </div>

</div>


<div class="table-box">
    <table id="bulkSms" cellspacing="0" cellpadding="0">
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