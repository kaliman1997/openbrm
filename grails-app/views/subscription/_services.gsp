<%@ page import="com.sapienter.jbilling.server.user.contact.db.ContactDTO"%>

<div class="table-box">
    <div class="table-scroll">
        <table id="orders" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th class="small">
                        <g:remoteSort action="list" sort="id" update="column1">
                            <g:message code="service.label.id"/>
                        </g:remoteSort>
                    </th>
                    <th class="large">
                        <g:remoteSort action="list" sort="contact.firstName, contact.lastName, contact.organizationName, u.userName" alias="[contact: 'baseUserByUserId.contact']" update="column1">
                            <g:message code="service.label.customer"/>
                        </g:remoteSort>
                    </th>
                    <th class="small">
                        <g:remoteSort action="list" sort="createDate" update="column1">
                            <g:message code="order.label.date"/>
                        </g:remoteSort>
                    </th>
                </tr>
            </thead>
            <tbody>
                <g:each var="srv" in="${services}">
                    <g:set var="contact" value="${ContactDTO.findByUserId(srv?.baseUserByUserId?.id)}"/>
                    <tr id="order-${srv.id}" class="${(service?.id == ordr?.id) ? 'active' : ''}">
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${srv.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${srv.id}
                            </g:remoteLink>
                        </td>
                        <td>
                            <g:remoteLink breadcrumb="id" class="double cell" action="show" id="${srv.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                <strong>
                                    <g:if test="${contact?.firstName || contact?.lastName}">
                                        ${contact.firstName} &nbsp;${contact.lastName}
                                    </g:if> 
                                    <g:else>
                                        ${srv?.baseUserByUserId?.userName}
                                    </g:else>
                                </strong>
                                <em>${contact?.organizationName}</em>
                            </g:remoteLink>
                        </td>
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${srv.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                <g:formatDate date="${srv?.createDate}" formatName="date.pretty.format"/>
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
        <util:remotePaginate controller="service" action="list" params="${sortableParams(params: [partial: true])}" total="${services?.totalCount ?: 0}" update="column1"/>
    </div>
</div>
