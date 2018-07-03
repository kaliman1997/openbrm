

<%@ page import="com.sapienter.jbilling.server.user.contact.db.ContactDTO"%>

 
<div class="table-box">
    <div class="table-scroll">
        <table id="bundles" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th class="small">
                        <g:remoteSort action="list" sort="id" update="column1">
                            <g:message code="purchased.bundle.label.id"/>
                        </g:remoteSort>
                    </th>
                      <th class="small">
                        <g:remoteSort action="list" sort="description" update="column1">
                            <g:message code="purchased.bundle.label.customer.name"/>
                        </g:remoteSort>
                    </th>
                    <th class="small">
                        <g:remoteSort action="list" sort="createDate" update="column1">
                            <g:message code="purchased.bundle.label.date"/>
                        </g:remoteSort>
                    </th>
                  
                </tr>
            </thead>
            <tbody>
                <g:each var="bundl" in="${purchasedBundles}">
                    <tr id="bundle-${bundl.id}" class="${(bundle?.id == bundl?.id) ? 'active' : ''}">
                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${bundl.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${bundl.id}
                            </g:remoteLink>
                        </td>
                        
                           <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${bundl.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${bundl?.userDto?.contact.firstName} ${bundl?.userDto?.contact.lastName}
                            </g:remoteLink>
                        </td> 

                        <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${bundl.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                <g:formatDate date="${bundl?.createdDateTime}" formatName="date.pretty.format"/>
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
        <util:remotePaginate controller="PurchaseBundleController" action="list" params="${sortableParams(params: [partial: true])}" total="${purchasedBundles?.totalCount ?: 0}" update="column1"/>
    </div>
</div>

