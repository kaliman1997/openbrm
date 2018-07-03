

<%@ page import="com.sapienter.jbilling.server.user.contact.db.ContactDTO"%>

 
<div class="table-box">
    <div class="table-scroll">
        <table id="bundles" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th class="small">
                        <g:remoteSort action="list" sort="id" update="column1">
                            <g:message code="bundle.label.id"/>
                        </g:remoteSort>
                    </th>
                      <th class="small">
                        <g:remoteSort action="list" sort="description" update="column1">
                            <g:message code="bundle.label.description"/>
                        </g:remoteSort>
                    </th>
                    <th class="small">
                        <g:remoteSort action="list" sort="createDate" update="column1">
                            <g:message code="bundle.label.date"/>
                        </g:remoteSort>
                    </th>
                  
                </tr>
            </thead>
            <tbody>
                <g:each var="bundl" in="${bundles}">
                    <tr id="bundle-${bundl.id}" class="${(bundle?.id == bundl?.id) ? 'active' : ''}">
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${bundl.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${bundl.id}
                            </g:remoteLink>
                        </td>
                        
                           <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${bundl.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${bundl?.description}
                            </g:remoteLink>
                        </td> 

                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${bundl.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                <g:formatDate date="${bundl?.createdDate}" formatName="date.pretty.format"/>
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
        <div class="download">
            <sec:access url="/bundle/csv">
                <g:link action="csv" id="${bundle?.id}">
                    <g:message code="download.csv.link"/>
                </g:link>
            </sec:access>
        </div>
    </div>

    <div class="row">
        <util:remotePaginate controller="bundle" action="list" params="${sortableParams(params: [partial: true])}" total="${bundles?.totalCount ?: 0}" update="column1"/>
    </div>
</div>

<div class="btn-box">
    <sec:access url="/bundle/create">
       <g:link controller="bundleBuilder" action="edit" params="[userId: session['user_id']]" class="submit bundle"><span><g:message code="button.create.bundle"/></span></g:link>
    </sec:access>
</div>
