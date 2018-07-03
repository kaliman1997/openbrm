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

<div class="column-hold">
     <div class="heading">
        <strong>Schedule &nbsp;<em>${scheduleWS?.id}</em></strong>
    </div>

    <!-- Order Details -->
    <div class="box">

        <table class="dataTable">
         
            <tr>
                <td>User Name :</td>
                <td class="value">${scheduleWS?.userName}</td>
            </tr>
            <tr><td>Subject :</td>
                <td class="value">
                   ${scheduleWS?.subject }
                </td>
            </tr>
            <tr><td>Created Date :</td>
                <td class="value">
                    <g:formatDate date="${scheduleWS?.createdDate}" formatName="date.pretty.format"/>
                </td>
            </tr>
			<tr><td>Active Since :</td>
                <td class="value">
                    <g:formatDate date="${scheduleWS?.activeSince}" formatName="date.pretty.format"/>
                </td>
            </tr>
             
			 <tr><td><g:message code="UserId"/>:</td>
                <td class="value">
                    ${scheduleWS?.baseUser.id}
                </td>
            </tr>
			
			<tr><td><g:message code="PeriodId"/>:</td>
                <td class="value">
                    ${scheduleWS?.periodId}
                </td>
            </tr>
        </table>
    </div>
<div class="btn-box">
	
	 <a onclick="showConfirm('delete-${scheduleWS.id}');" class="submit delete"><span><g:message code="Delete"/></span></a>
	
	</div>

</div>
