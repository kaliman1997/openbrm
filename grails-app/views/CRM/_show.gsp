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
        <strong>Ticket &nbsp;<em>${selected?.id}</em></strong>
    </div>

    <!-- Order Details -->
    <div class="box">

        <table class="dataTable">
         <tr>
                <td width="130px">User Id :</td>
                <td class="value">
                    <sec:access url="/customer/show">
                        <g:remoteLink controller="customer" action="show" id="${user?.id}" before="register(this);" onSuccess="render(data, next);">
                            ${user?.id}
                        </g:remoteLink>
                    </sec:access>
                    <sec:noAccess url="/customer/show">
                        ${user?.id}
                    </sec:noAccess>
                </td>
            </tr>
            <tr>
                <td>User Name :</td>
                <td class="value">${user?.userName}</td>
            </tr>
            <tr><td>Subject :</td>
                <td class="value">
                   ${selected?.subject }
                </td>
            </tr>
            <tr><td>Created Date :</td>
                <td class="value">
                    <g:formatDate date="${selected?.createdDate}" formatName="date.pretty.format"/>
                </td>
            </tr>
             <tr><td>Last Modified Date :</td>
                <td class="value">
                    <g:formatDate date="${selected?.lastModified}" formatName="date.pretty.format"/>
                </td>
            </tr>
            <tr>
                <td>Assigned User Id :</td>
                <td class="value">
                    <sec:access url="/customer/show">
                        <g:remoteLink controller="customer" action="show" id="${assigned?.id}" before="register(this);" onSuccess="render(data, next);">
                            ${assigned?.id}
                        </g:remoteLink>
                    </sec:access>
                    <sec:noAccess url="/customer/show">
                        ${assigned?.id}
                    </sec:noAccess>
                </td>
            </tr>
            <tr>
                <td>Assigned User Name :</td>
                <td class="value">${assigned?.userName}</td>
            </tr>
          <tr><td>Status :</td>
                <td class="value">
                   ${selected?.ticketStatus.type }
                </td>
            </tr>
            <tr><td>Details :</td>
                <td class="value">
                    <g:each var="detail" in="${details}" status="idx"><div>
   	 <g:formatDate date="${detail.createdDate }" formatName="date.pretty.format" /></div><div>
   	 ${detail.ticketBody }</div><div style="height: 10px"></div>
   	 </g:each>
                </td>
            </tr>
 
        </table>
    </div>

 <!--  <div class="btn-box">
        <div class="row">
        <sec:ifAllGranted roles="ORDER_21"> 
    <g:link action='editList' params="[id:selected?.id]" class="submit edit"><span><g:message code="pricing.button.edit"/></span></g:link>
     </sec:ifAllGranted> 
                 
	   </div>
    </div> -->
	<div class="btn-box">
        <div class="row">
        
    <g:link action='editList' params="[id:selected?.id]" class="submit edit"><span><g:message code="pricing.button.edit"/></span></g:link>
   
                 
	   </div>
    </div>

</div>
