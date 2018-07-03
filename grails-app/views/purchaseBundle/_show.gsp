

<%@ page import="com.sapienter.jbilling.server.util.Util" %>
<%@ page import="com.sapienter.jbilling.client.util.Constants" %>
<%@ page import="com.sapienter.jbilling.server.item.db.ItemDTO; com.sapienter.jbilling.server.item.db.ItemDAS;com.sapienter.jbilling.server.process.db.PeriodUnitDTO;com.sapienter.jbilling.server.util.Constants"%>

<div class="column-hold">

    <div class="heading">
        <strong><g:message code="purcahse.bundle.label.details"/>&nbsp;<em>${purchased?.id}</em></strong>
    </div>
    

  <div class="box">

        <table class="dataTable">
              <tr><td><g:message code="purchased.bundle.user.id"/>:</td>
                <td class="value">
                    ${purchased?.userDto?.id}
                </td>
            </tr>
            <tr><td><g:message code="purchased.bundle.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${purchased?.updateDateTime}" formatName="date.pretty.format"/>
                </td>
            </tr>
            <tr><td><g:message code="purchased.bundle.valid.from"/>:</td>
                <td class="value">
                    <g:formatDate date="${purchased?.validFrom}" formatName="date.pretty.format"/>
                </td>
            </tr>
            <tr><td><g:message code="purchased.bundle.valid.to"/>:</td>
                <td class="value">
                    <g:formatDate date="${purchased?.validTo}" formatName="date.pretty.format"/>
                </td>
            </tr>
			
			<tr><td><g:message code="purchased.bundle.id"/>:</td>
                <td class="value">                
					<sec:access url="/order/list">
                        <g:link controller="bundle" action="user" id="${purchased?.id}">
                            ${purchased?.bundleName}
                        </g:link>
                    </sec:access>
				</td>
            </tr>

        </table>
    </div>

      <div class="heading">
        <strong><g:message code="order.label.lines"/></strong>
    </div>

 <!-- Order Lines -->
    <div class="box">
        <g:if test="${orders?.length > 0}">
            <table class="innerTable" >
                <thead class="innerHeader">
                     <tr>
                        <th style="min-width: 75px;"><g:message code="order.label.line.item"/></th>
                        <th><g:message code="order.label.line.descr"/></th>
                        <th><g:message code="order.label.line.qty"/></th>
                        <th><g:message code="order.label.line.price"/></th>
                        <th><g:message code="order.label.line.total"/></th>
                     </tr>
                 </thead>
                 <tbody>
                      <g:each var="order" in="${orders}" status="idxp">
                     <g:each var="line" in="${order.orderLines}" status="idx">
                       <g:set var="currency" value="${currencies.find{ it.id == order.currencyId}}"/>
                         <tr>
                            <td class="innerContent">
                                <sec:access url="/product/show">
                                   <g:remoteLink controller="product" action="show" id="${line?.itemId}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                        ${line?.itemId}
                                   </g:remoteLink>
                                </sec:access>
                                <sec:noAccess url="/product/show">
                                    ${line?.itemId}
                                </sec:noAccess>
                            </td>
                            <td class="innerContent">
                                ${line.description}
                            </td>
                            <td class="innerContent">
                                <g:formatNumber number="${line.quantityAsDecimal ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                            <td class="innerContent">
                                <g:set var="product" value="${ItemDTO.get(line.itemId)}"/>
                                <g:if test="${product?.percentage}">
                                    <g:formatNumber number="${line.priceAsDecimal ?: BigDecimal.ZERO}" type="currency" currencySymbol="%" maxFractionDigits="4"/>
                                </g:if>
                                <g:else>
                                    <g:formatNumber number="${line.priceAsDecimal ?: BigDecimal.ZERO}" type="currency" currencySymbol="${currency.symbol}" maxFractionDigits="4"/>
                                </g:else>
                            </td>
                            <td class="innerContent">
                                <g:formatNumber number="${line.amountAsDecimal ?: BigDecimal.ZERO}" type="currency" currencySymbol="${currency.symbol}" maxFractionDigits="4"/>
                            </td>
                         </tr>
                     </g:each>
                      </g:each>
                 </tbody>
           </table>
        </g:if>
        <g:else>
            <em><g:message code="order.prompt.no.lines"/></em>
        </g:else>
    </div>





 <div class="btn-box">

 <sec:ifAllGranted roles="ORDER_21">
                <g:link controller="purchaseBundle" action="cancelBundle" params="[purchasedId: purchased?.id]" class="submit order"><span><g:message code="button.cancel.purchase.bundle"/></span></g:link>
                </sec:ifAllGranted>
        
        
          <!--  <sec:ifAllGranted roles="ORDER_22">
                <a onclick="showConfirm('deletePurchasedBundle-' + ${purchased?.id});" class="submit delete">
                    <span><g:message code="bundle.button.delete"/></span>
                </a>
            </sec:ifAllGranted> -->      

    </div>
</div>
