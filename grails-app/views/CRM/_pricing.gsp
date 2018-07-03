
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="form-edit" style="height: 80px">

    <div class="heading">
        <strong>CRM Details</strong>
    </div>

     <div class="form-hold">
        <g:form name="form-search" action="list"  >
            <div class="btn-box">
     <g:link controller="CRM" action="editList"  class="submit add" style="padding-right: 200px"><span>Open Ticket</span></g:link>
     <input type="text" style="height: 20px;width: 110px;padding:3px 3px" name="uname" value="${params.uname }" size="30" />
    <input type="image" class="btn" src="${resource(dir:'images', file:'icon-search.gif')}" onclick="$('#form-search').submit();"/>
</div></g:form>
    </div>

</div>
<div id="target" class="form-edit">

    <div class="table-box">
    <div class="table-scroll">
        <table id="orders" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th class="small">
                        <g:remoteSort action="list" sort="id" update="column1">
                            Created
                        </g:remoteSort>
                    </th>
                    <th class="small">
                        <g:remoteSort action="list" sort="contact.firstName, contact.lastName, contact.organizationName, u.userName" alias="[contact: 'baseUserByUserId.contact']" update="column1">
                            Subject
                        </g:remoteSort>
                    </th>
                    <th class="small">
                        <g:remoteSort action="list" sort="createDate" update="column1">
                            Status
                        </g:remoteSort>
                    </th><th class="small">
                        <g:remoteSort action="list" sort="createDate" update="column1">
                            Last Updated
                        </g:remoteSort>
                    </th>
                    <th class="small">
                        <g:remoteSort action="list" sort="createDate" update="column1">
                            User Name
                        </g:remoteSort>
                    </th>
                </tr>
            </thead>
            <tbody>
                <g:each var="ticket" in="${tickets}">
                	  <tr id="type-1" class="active">
			
                    <td>
                           <g:remoteLink breadcrumb="id" class="cell" action="show" id="${ticket.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                <g:formatDate date="${ticket?.createdDate}" formatName="date.pretty.format"/>
                            </g:remoteLink>
                        </td>    
		
				 <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${ticket.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${ticket.subject}
                            </g:remoteLink>
                       </td> 
                        
                         <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${ticket.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${ticket.ticketStatus.type}
                            </g:remoteLink>
                        </td> 
                         <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${ticket.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                <g:formatDate date="${ticket?.lastModified}" formatName="date.pretty.format"/>
                            </g:remoteLink>
                        </td> 
                         <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${ticket.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${ticket.baseUser.userName}
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
        <util:remotePaginate controller="CRM" action="list" params="${sortableParams(params: [partial: true])}" total="${tickets?.totalCount ?: 0}" update="column1"/>
    </div>
</div>

<div class="btn-box" style="height: 0px">
    
</div></div>
