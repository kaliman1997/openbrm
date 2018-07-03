
<%@ page import="java.io.*" contentType="text/html;charset=UTF-8" %>

<div class="table-box">
   <div class="table-scroll">
    <table id="devices" cellspacing="0" cellpadding="0">
        <thead>
            <tr>
		<th>
		<g:remoteSort action="list" sort="id" update="column1">
			<g:message code="device.detail.id"/>
                </g:remoteSort>
		</th>
            <th class="large">
              <!--g:message code="device.detail.imsi.number"/--!>
	    </th>
           <th class="large">
		<g:message code="device.code.number"/>
	   </th>
	  <th class="small">
		<g:message code="device.detail.status"/>
	  </th>
            </tr>
        </thead>

        <tbody>
           <g:each var="dev" in="${devices}">
                <tr id="type-1" class="active">
			
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${dev.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${dev.id}
                            </g:remoteLink>
                        </td>
		
		            <td>
                            <!--g:remoteLink breadcrumb="id" class="cell" action="show" id="${dev.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);"--!>
                                
                            <!--/g:remoteLink--!>
                        </td>
		
		                 <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${dev.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                 ${dev.deviceCode}
                            </g:remoteLink>
                        </td>
                        
		              <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${dev.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                            ${dev?.getDeviceStatus().getDescription(session['language_id'])}
                           
                            </g:remoteLink>
                        </td>
		
		
		<%--	 <td>
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
        <div class="download">
            <sec:access url="/device/csv">
                <g:link action="csv" id="${dev?.id}">
                    <g:message code="download.csv.link"/>
                </g:link>
            </sec:access>
        </div>
    </div>

    <div class="row">
        <util:remotePaginate controller="device" action="list" params="${sortableParams(params: [partial: true])}" total="${devices?.totalCount ?: 0}" update="column1"/>
    </div>
</div>


<div class="btn-box">
    <g:remoteLink action='edit' class="submit add" before="register(this);" onSuccess="render(data, next);">
        <span><g:message code="device.button.create"/></span>
    </g:remoteLink>
</div>
