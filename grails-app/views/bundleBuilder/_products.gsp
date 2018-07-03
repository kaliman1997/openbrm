
<%@ page import="com.sapienter.jbilling.server.user.db.CompanyDTO; com.sapienter.jbilling.server.item.db.ItemTypeDTO; com.sapienter.jbilling.server.util.Constants" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>

<%--
  Shows the product list and provides some basic filtering capabilities.

  @author Brian Cowdery
  @since 23-Jan-2011
--%>

<div id="product-box">

    <!-- filter -->
    <div class="form-columns">
        <g:formRemote name="products-filter-form" url="[action: 'edit']" update="ui-tabs-products" method="GET">
            <g:hiddenField name="_eventId" value="products"/>
            <g:hiddenField name="execution" value="${flowExecutionKey}"/>

            <g:applyLayout name="form/input">
                <content tag="label"><g:message code="filters.title"/></content>
                <content tag="label.for">filterBy</content>
                <g:textField name="filterBy" class="field default" placeholder="${message(code: 'products.filter.by.default')}" value="${params.filterBy}"/>
            </g:applyLayout>

            <g:applyLayout name="form/select">
                <content tag="label"><g:message code="order.label.products.category"/></content>
                <content tag="label.for">typeId</content>
                <g:select name="typeId" from="${itemTypes}"
                          noSelection="['': message(code: 'filters.item.type.empty')]"
                          optionKey="id" optionValue="description"
                          value="${params.typeId && !params.typeId.isEmpty() ? params.typeId as Integer : ''}"/>
            </g:applyLayout>
        </g:formRemote>

        <script type="text/javascript">
            $(function() {
                $('#products-filter-form :input[name=filterBy]').blur(function() { $('#products-filter-form').submit(); });
                $('#products-filter-form :input[name=typeId]').change(function() { $('#products-filter-form').submit(); });
                placeholder();
            });
        </script>
    </div>

    <!-- product list -->
    <div class="table-box tab-table">
        <div class="table-scroll">
            <table id="products" cellspacing="0" cellpadding="0">
                <tbody>

                <g:each var="product" in="${products}">
                    <tr>
                        <td>
                            <g:remoteLink class="cell double" action="edit" id="${product.id}" params="[_eventId: 'addPackageProduct']" update="column2" method="GET">
                                <strong>${product.getDescription(session['language_id'])}</strong>
                                <em><g:message code="table.id.format" args="[product.id]"/></em>
                            </g:remoteLink>
                        </td>
                        <td class="small">
                            <g:remoteLink class="cell double" action="edit" id="${product.id}" params="[_eventId: 'addPackageProduct']" update="column2" method="GET">
                                <span>${product.internalNumber}</span>
                            </g:remoteLink>
                        </td>
                        <td class="medium">
                            <g:remoteLink class="cell double" action="edit" id="${product.id}" params="[_eventId: 'addPackageProduct']" update="column2" method="GET">
                                <g:if test="${product.percentage}">
                                    %<g:formatNumber number="${product.percentage}" formatName="money.format"/>
                                </g:if>
                                <g:else>
                                    <g:set var="prices" value="${product.itemPrices?.asList()?.sort{ it.currencyDTO.id }}"/>
                                    <g:set var="price" value="${!prices.isEmpty() ? prices.first() : null}"/>
                                    <g:formatNumber number="${price?.price}" type="currency" currencySymbol="${price?.currencyDTO?.symbol}"/>
                                </g:else>
                            </g:remoteLink>
                        </td>
                    </tr>
                </g:each>

                </tbody>
            </table>
        </div>
    </div>
	
	<div class="pager-box">
        <div class="results">
            <g:message code="pager.show.max.results"/>
            <g:each var="max" in="${[10, 20, 50]}">
                <g:if test="${maxProductsShown == max}">
                    <span>${max}</span>
                </g:if>
                <g:else>
                    <g:remoteLink action="edit"
                                  params="${sortableParams(params: [partial: true, max: max, _eventId: 'products', typeId: params.typeId ?: "", filterBy: params.filterBy ?: "" ])}"
                                  update="ui-tabs-products"
                                  method="GET">${max}</g:remoteLink>
                </g:else>
            </g:each>
        </div>
        <div class="row">
            <util:remotePaginate action="edit"
                                 params="${sortableParams(params: [partial: true, _eventId: 'products', max: maxProductsShown, typeId: params.typeId ?: "", filterBy: params.filterBy ?: ""])}"
                                 total="${products.totalCount ?: 0}"
                                 update="ui-tabs-products"
                                 method="GET"/>
        </div>
    </div>

	
</div>

