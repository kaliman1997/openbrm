
<%@ page import="java.io.*" contentType="text/html;charset=UTF-8" %>

<div class="table-box">
   <div class="table-scroll">
    <table id="products" cellspacing="0" cellpadding="0">
        <thead>
            <tr>
		<th>
		<g:remoteSort action="list" sort="id" update="column1">
			<g:message code="pricing.product.id"/>
                </g:remoteSort>
		</th>
            <th class="large">
              <g:message code="pricing.product.description"/>
	    </th>
           
            </tr>
        </thead>

        <tbody>
           <g:each var="dev" in="${products}">
                <tr id="type-1" class="active">
			
                    <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${dev.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${dev.id}
                            </g:remoteLink>
                        </td>    
		
				 <td>
                            <g:remoteLink breadcrumb="id" class="cell" action="show" id="${dev.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                ${dev.description}
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
        <util:remotePaginate controller="pricing" action="list" params="${sortableParams(params: [partial: true])}" total="${ProductCharges?.totalCount ?: 0}" update="column1"/>
    </div>
</div>


<div class="btn-box">
 <sec:access url="/pricing/edit">
    <g:link action='edit' class="submit add"><span><g:message code="button.create"/></span></g:link>
     </sec:access>
</div>
