
<%@ page import="java.io.*" contentType="text/html;charset=UTF-8" %>

<div class="table-box">
   <div class="table-scroll">
    <table id="ratecard" cellspacing="0" cellpadding="0">
        <thead>
            <tr>
		<th class="large">
		<g:remoteSort action="list" sort="id" update="column2">
			<g:message code="rate.detail.id"/>
                </g:remoteSort>
		</th>
            <th class="large">
              <g:message code="rate.detail.prefix"/>
	    </th>
          	   <th class="large">
	   <g:remoteSort action="list" sort="ratePlan" update="column2">
		<g:message code="rate.detail.rate.plan"/>
		</g:remoteSort>
	  </th>
	  <th class="large">
		<g:message code="rate.detail.scaled.rate"/>
	  </th>
	  </tr>
        </thead>

        <tbody>
           <g:each var="irate" in="${rates}">
                <tr id="type-1" class="active">
			
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irate.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${irate.id}
                            </g:remoteLink>
                        </td>
		
		            <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irate.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${irate.prefix}
                            </g:remoteLink>
                        </td>
		
		               <%--   <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irate.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${irate.destination}
                            </g:remoteLink>
                        </td> --%>
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irate.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${irate.ratePlan}
                            </g:remoteLink>
                        </td>
						<td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irate.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                           
			                <g:formatNumber number="${irate.scaledRate}" type="currency" currencySymbol="${currency.symbol}" />
                           
                            </g:remoteLink>
                        </td>
		              
                    <%--          <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irate.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                            
			    <g:formatNumber number="${irate.conncharge}" type="currency" currencySymbol="${currency.symbol}" />
                           
                            </g:remoteLink>
                        </td>
		              <td>
                            <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irate.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                            
                           <g:formatNumber number="${irate.flatRate}" type="currency" currencySymbol="${currency.symbol}" />
                            </g:remoteLink>
                        </td>
		
			 <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${dev.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${dev.id}
                            </g:remoteLink>
                        </td>
			 old commented from <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${dev.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${dev.serialNum}
                            </g:remoteLink>
                        </td>  
			  <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${dev.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                <g:formatDate date="${dev?.createdDate}" formatName="date.pretty.format"/>
                            </g:remoteLink>
                        </td>
                  <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${dev.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                            ${dev?.getDeviceStatus().getDescription(session['language_id'])}
                           
                            </g:remoteLink>
                        </td> --%>
                </tr>
            </g:each>
        </tbody>
    </table>
</div>
</div>

<div class="pager-box">
    <div class="row">
        <div class="results">
            <g:render template="/layouts/includes/pagerShowResults" model="[steps: [10, 20, 50], update: 'column2']"/>
        </div>
    </div>

    <div class="row">
        <util:remotePaginate controller="rate" action="list" params="${sortableParams(params: [partial: true])}" total="${rates?.totalCount ?: 0}" update="column1"/>
    </div>
</div>


<div class="btn-box">
    <g:remoteLink action='edit' class="submit add" before="register(this);" onSuccess="render(data, next);">
        <span><g:message code="device.button.create"/></span>
    </g:remoteLink>
</div>
