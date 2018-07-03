
<%@ page import="java.io.*;com.sapienter.jbilling.server.util.WebServicesSessionSpringBean "   contentType="text/html;charset=UTF-8" %>

<div class="table-box">
   <div class="table-scroll">
	<table id="exclusions" cellspacing="0" cellpadding="0">
		<thead>
			<tr>
		<th class="small">
		<g:remoteSort action="list" sort="id" update="column1">
			<g:message code="exclusions.detail.id"/>
				</g:remoteSort>
		</th>
			<th class="large">
			  <g:message code="exclusions.detail.prefix"/>
		</th>
		   <th class="large">
		<g:message code="exclusions.detail.destination"/>
	   </th>
	   <th class="large">
		<g:message code="exclusions.detail.valid.from"/>
		
	  </th>
	  <th class="large">
		<g:message code="exclusions.detail.valid.to"/>
	  </th>
	  </tr>
		</thead>

	   <tbody>
	 

           <g:each var="iexclusions" in="${exclusions}">

                <tr id="type-1" class="active">
			
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${iexclusions.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${iexclusions.id}
                            </g:remoteLink>
                        </td>
		
		            <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${iexclusions.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${iexclusions.prefix}
                            </g:remoteLink>
                        </td>
		
		                 <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${iexclusions.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${iexclusions.destination}
                            </g:remoteLink>
                        </td>
						
						<td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${iexclusions.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                 <g:formatDate date="${iexclusions?.validFrom}" formatName="date.pretty.format"/>
                            </g:remoteLink>
                        </td>
						
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${iexclusions.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                               <g:formatDate date="${iexclusions?.validTo}" formatName="date.pretty.format"/>
                            </g:remoteLink>
                        </td>
		
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
		<util:remotePaginate controller="exclusions" action="list" params="${sortableParams(params: [partial: true])}" total="${exclusions?.totalCount ?: 0}" update="column1"/>
	</div>
</div>


<div class="btn-box">
	<g:remoteLink action='edit' class="submit add" before="register(this);" onSuccess="render(data, next);">
		<span><g:message code="device.button.create"/></span>
	</g:remoteLink>
</div>
