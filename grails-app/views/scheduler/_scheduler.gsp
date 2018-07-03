
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="com.sapienter.jbilling.server.user.contact.db.ContactDTO"%>



    <div class="table-box">
    <div class="table-scroll">
        <table id="schedulers" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th class="small">
                        <g:remoteSort action="list" sort="id" update="column1">
                            Id
                        </g:remoteSort>
                    </th>
                    <th class="small">
                        <g:remoteSort action="list" sort="description" update="column1">
                            Subject
                        </g:remoteSort>
                    </th>
                    
                    <th class="small">
                        <g:remoteSort action="list" sort="createdDateTime" update="column1">
                           activeSince
                        </g:remoteSort>
                    </th>
                </tr>
            </thead>
            <tbody>
<g:each var="schedulr" in="${schedulers}">
                   
						<tr>
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${schedulr.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${schedulr.id}
                            </g:remoteLink>
                        </td>
                        <td>
                             <g:remoteLink breadcrumb="id" class="cell" action="show" id="${schedulr.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${schedulr.subject}
                            </g:remoteLink>
                        </td>
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${schedulr.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                <g:formatDate date="${schedulr?.createdDateTime}" formatName="date.pretty.format"/>
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
        <util:remotePaginate controller="Scheduler" action="list" params="${sortableParams(params: [partial: true])}" total="${schedulers?.totalCount ?: 0}" update="column1"/>
    </div>
</div>

<div class="btn-box">
      <sec:access url = "/orderScheduler/create">                 
    <g:link controller="orderScheduler" action="edit" params="[userId: session['user_id']]" class="submit scheduler"><span><g:message code="Add New Schedule"/></span></g:link>
       </sec:access>
	</div>
    

