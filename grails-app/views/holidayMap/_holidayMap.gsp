
<%@ page import="java.io.*" contentType="text/html;charset=UTF-8" %>

<div class="table-box">
   <div class="table-scroll">
    <table id="ratecard" cellspacing="0" cellpadding="0">
        <thead>
            <tr>
		<th class="small">
		<g:remoteSort action="list" sort="id" update="column1">
			<g:message code="holiday.map.detail.id"/>
                </g:remoteSort>
		</th>
		<th class="large">
		<g:message code="holiday.map.detail.map.group"/>
	   </th>
	    
            <th class="large">
              <g:message code="holiday.map.detail.description"/>
	    </th>
         
        </thead>

        <tbody>
           <g:each var="holidayMap" in="${holidmap}">
                <tr id="type-1" class="active">
			
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${holidayMap.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${holidayMap.id}
                            </g:remoteLink>
                        </td>
		
		            <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${holidayMap.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${holidayMap.mapGroup}
                            </g:remoteLink>
                        </td>
		
		                 <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${holidayMap.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${holidayMap.description}
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
        <util:remotePaginate controller="holidayMap" action="list" params="${sortableParams(params: [partial: true])}" total="${rates?.totalCount ?: 0}" update="column1"/>
    </div>
</div>


<div class="btn-box">
    <g:remoteLink action='edit' class="submit add" before="register(this);" onSuccess="render(data, next);">
        <span><g:message code="holiday.map.button.create"/></span>
    </g:remoteLink>
</div>
