%{--
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



<%@ page import="java.io.*;com.sapienter.jbilling.server.util.WebServicesSessionSpringBean "   contentType="text/html;charset=UTF-8" %>


<div class="table-box">
    <table id="vouchers" cellspacing="0" cellpadding="0">
        <thead>
            <tr>
			<th>
                    <g:remoteSort action="list" sort="id" update="column1">
                        <g:message code="Voucher.id"/>
                    </g:remoteSort>
                </th>
				 <th>
                    <g:remoteSort action="list" sort="serialNo" update="column1">
                        <g:message code="Voucher.serialNo"/>
                    </g:remoteSort>
                </th>
                <th>
                    <g:remoteSort action="list" sort="pin" update="column1">
                        <g:message code="Voucher.pin"/>
                    </g:remoteSort>
                </th>
                <th>
                    <g:remoteSort action="list" sort="createddate" update="column1">
                        <g:message code="Voucher.createdDate"/>
                    </g:remoteSort>
                </th>
               
               
            </tr>
        </thead>

        <tbody>
       <g:each var="ivouchers" in="${vouchers}">

                <tr id="type-1" class="active">
			
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${ivouchers.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${ivouchers.id}
                            </g:remoteLink>
                        </td>
		
						<td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${ivouchers.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${ivouchers.serialNo}
                            </g:remoteLink>
                        </td>
		
		                 <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${ivouchers.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${ivouchers.pinCode}
                            </g:remoteLink>
                        </td>
                        
					 
						<td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${ivouchers.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                <g:formatDate date="${ivouchers?.createdDateTime}" formatName="date.pretty.format"/>
                            </g:remoteLink>
                        </td>
               
                </tr>
            </g:each>
        
        
        </tbody>
    </table>
</div>

<div class="pager-box">
    %{-- remote pager does not support "onSuccess" for panel rendering, take a guess at the update column --}%
    <g:set var="updateColumn" value="${actionName == 'subaccounts' ? 'column2' : 'column1'}"/>

    <div class="row">
        <div class="results">
            <g:render template="/layouts/includes/pagerShowResults" model="[steps: [10, 20, 50], update: updateColumn]"/>
        </div>
        <div class="download">
            <sec:access url="/voucher/csv">
                <g:link action="csv"  id="${ivouchers?.id}">
                    <g:message code="download.csv.link"/>
                </g:link>
            </sec:access>
        </div>
    </div>

    <div class="row">
        <util:remotePaginate controller="voucher" action="list" params="${sortableParams(params: [partial: true])}" total="${voucher?.totalCount ?: 0}" update="${updateColumn}"/>
    </div>
</div>

<div class="btn-box">
    <g:remoteLink action='edit' class="submit add" before="register(this);" onSuccess="render(data, next);">
        <span><g:message code="button.generate"/></span>
    </g:remoteLink>
	</div>
