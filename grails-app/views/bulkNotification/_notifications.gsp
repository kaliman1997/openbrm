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
  
<%@page import="com.sapienter.jbilling.server.process.db.PeriodUnitDTO" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<%--
  Shows a list of order periods.

  @author Vikas Bodani
  @since  30-Sept-2011
--%>

<div class="table-box">
    <table id="notifications" cellspacing="0" cellpadding="0">
        <thead>
            <tr>			
			<th class="large">
				<g:remoteSort action="list" sort="id" update="column1">
					<g:message code="Notification Type"/>
				</g:remoteSort>
            </th>              
            </tr>
        </thead>

        <tbody>
             <tr id="type-1" class="active">
                    <!-- ID -->
					
					 <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="sms"  params="['template': 'sms']" before="register(this);" onSuccess="render(data, next);">
                                ${notifications.type[0]}
                            </g:remoteLink>
							
                    </td>  </tr>     
              <tr id="type-1" class="active">              
					<td>
                            <g:remoteLink breadcrumb="id" class="cell" action="email" params="['template': 'email']" before="register(this);" onSuccess="render(data, next);">
                                ${notifications.type[1]}
                            </g:remoteLink>
							
                    </td>						   
            </tr>      
        </tbody>
    </table>
</div>