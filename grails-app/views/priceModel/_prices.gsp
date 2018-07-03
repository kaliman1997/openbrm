
<%@ page import="java.io.*;com.sapienter.jbilling.server.util.WebServicesSessionSpringBean "   contentType="text/html;charset=UTF-8" %>

<div class="table-box">
   <div class="table-scroll">
    <table id="pricemodel" cellspacing="0" cellpadding="0">
        <thead>
            <tr>
		<th class="small">
		<g:remoteSort action="list" sort="id" update="column1">
			<g:message code="price.model.detail.id"/>
                </g:remoteSort>
		</th>
            <th class="large">
              <g:message code="price.model.detail.priceModel"/>
	    </th>
           <th class="large">
		<g:message code="price.model.detail.tier.from"/>
	   </th>
      
	   <th class="large">
	   <g:remoteSort action="list" sort="priceModelPlan" update="column1">
		<g:message code="price.model.detail.price.model.plan"/>
		</g:remoteSort>
	  </th>
	  
	  <th class="large">
		<g:message code="price.model.detail.tier.to"/>
	  </th>
	  </tr>
        </thead>

        <tbody>
	 
           <g:each var="iprice" in="${prices}">

                <tr id="type-1" class="active">
			
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${iprice.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${iprice.id}
                            </g:remoteLink>
                        </td>
		
		            <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${iprice.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${iprice.priceModel}
                            </g:remoteLink>
                        </td>
		
		                 <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${iprice.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${iprice.tierFrom}
                            </g:remoteLink>
                        </td>
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${iprice.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${iprice.priceModelPlan}
                            </g:remoteLink>
                        </td>
						<td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${iprice.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
							${iprice.tierTo}
                           
			                
                           
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
        <util:remotePaginate controller="priceModel" action="list" params="${sortableParams(params: [partial: true])}" total="${prices?.totalCount ?: 0}" update="column1"/>
    </div>
</div>


<div class="btn-box">
    <g:remoteLink action='edit' class="submit add" before="register(this);" onSuccess="render(data, next);">
        <span><g:message code="device.button.create"/></span>
    </g:remoteLink>
</div>
