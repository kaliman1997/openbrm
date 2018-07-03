
<%@ page import="java.io.*;com.sapienter.jbilling.server.util.WebServicesSessionSpringBean "   contentType="text/html;charset=UTF-8" %>


<div class="table-box">
   <div class="table-scroll">
    <table id="pricemodel" cellspacing="0" cellpadding="0">
        <thead>
            <tr>
		<th class="small">
		<g:remoteSort action="list" sort="id" update="column1">
			<g:message code="rum.map.detail.id"/>
                </g:remoteSort>
		</th>
            <th class="large">
              <g:message code="rum.map.detail.price.group"/>
	    </th>
           <th class="large">
		<g:message code="rum.map.detail.price.model"/>
	   </th>
      
	   <th class="large">
	   <g:remoteSort action="list" sort="rumMapPlan" update="column1">
		<g:message code="rum.map.detail.rum.map.plan"/>
		</g:remoteSort>
	  </th>
	  
	  <th class="large">
		<g:message code="rum.map.detail.resource.id"/>
	  </th>
	  <th class="large">
		<g:message code="rum.map.detail.consume.flag"/>
	  </th>
	  </tr>
        </thead>

        <tbody>
	 
           <g:each var="irum" in="${rummap}">

                <tr id="type-1" class="active">
			
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irum.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${irum.id}
                            </g:remoteLink>
                        </td>
		
		            <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irum.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${irum.priceGroup}
                            </g:remoteLink>
                        </td>
		
		                 <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irum.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${irum.priceModel}
                            </g:remoteLink>
                        </td>
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irum.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${irum.rumMapPlan}
                            </g:remoteLink>
                        </td>
						<td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irum.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
							${irum.resourceId}
                           
			                
                           
                            </g:remoteLink>
                        </td>
						<td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irum.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
							${irum.consumeFlag}
                           
			                
                           
                            </g:remoteLink>
                        </td>
		              
                    <%--          <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${iprice.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                            
			    <g:formatNumber number="${irate.conncharge}" type="currency" currencySymbol="${currency.symbol} />
                           
                            </g:remoteLink>
                        </td>
		              <td>
                            <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${irate.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                            
                           <g:formatNumber number="${irate.flatRate}" type="currency" currencySymbol="${currency.symbol} />
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
            <g:render template="/layouts/includes/pagerShowResults" model="[steps: [10, 20, 50], update: 'column1']"/>
        </div>
    </div>

    <div class="row">
        <util:remotePaginate controller="rummap" action="list" params="${sortableParams(params: [partial: true])}" total="rummap?.totalCount ?: 0}" update="column1"/>
    </div>
</div>


<div class="btn-box">
    <g:remoteLink action='edit' class="submit add" before="register(this);" onSuccess="render(data, next);">
        <span><g:message code="device.button.create"/></span>
    </g:remoteLink>
</div>
