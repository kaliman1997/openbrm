%{--
  JBILLING CONFIDENTIAL
  _____________________

  [2003] - [2012] Enterprise jBilling Software Ltd.
  All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Enterprise jBilling Software.
  The intellectual and technical concepts contained
  herein are proprietary to Enterprise jBilling Software
  and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden.
  --}%



<div class="column-hold">

    <div class="heading">
        <strong><g:message code="price.model.label.details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

    <div class="box">
        <table class="dataTable">
            <tr><td><g:message code="price.model.detail.id"/>:</td>
                <td class="value">
                    ${pricews?.id}
                </td>
            </tr>
	     <tr><td><g:message code="price.model.detail.price.model"/>:</td>
                <td class="value">
                    ${pricews?.priceModel}
                </td>
            </tr>
		<tr><td><g:message code="price.model.detail.qty.step"/>:</td>
                <td class="value">
                    ${pricews?.qtyStep}
                </td>
            </tr>
			

	      <tr><td><g:message code="price.model.detail.tier.from"/>:</td>
                <td class="value">
                 ${pricews?.tierFrom}
                </td>
            </tr>
	    <tr><td><g:message code="price.model.detail.tier.to"/>:</td>
                <td class="value">
                  ${pricews?.tierTo}
                </td>
            </tr>
		<tr><td><g:message code="price.model.detail.beat"/>:</td>
                <td class="value">
                    ${pricews?.beat}
                </td>
            </tr>
	    <tr><td><g:message code="price.model.detail.factor"/>:</td>
                <td class="value">
                  <g:formatNumber number="${pricews?.factor}" type="currency" currencySymbol="${currency?.symbol}"/>
                </td>
            </tr>
		<tr><td><g:message code="price.model.detail.charge.base"/>:</td>
                <td class="value">
                  ${pricews?.chargeBase}
                </td>
            </tr>

	    <tr><td><g:message code="price.model.detail.created.date"/>:</td>
                <td class="value">
                   <g:formatDate date="${pricews?.createdDate}" formatName="date.pretty.format"/>
                </td>
            </tr>

	
	<tr><td><g:message code="price.model.detail.price.model.plan"/>:</td>
                <td class="value">
                   <sec:access url="/product/show">
                   <g:remoteLink controller="product" action="show" id="${pricews?.priceModelPlan}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                    ${pricews?.priceModelPlan}
                    </g:remoteLink>
                     </sec:access>
                      <sec:noAccess url="/product/show">
                       ${pricews?.priceModelPlan}
                        </sec:noAccess>
                </td>
            </tr>
	


            

	     <tr><td><g:message code="price.model.detail.last.updated.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${pricews?.lastUpdatedDate}" formatName="date.pretty.format"/>
                </td>
            
	
           </table>
    </div>


 <div class="btn-box">
        <div class="row"><%--
		
                <a href="${createLink (controller: 'priceModel', action: 'edit', params: [id: pricews?.id])}" class="submit edit">
                    <span><g:message code="price.model.button.edit"/></span>
                </a>
		 
		
				 <a onclick="showConfirm('deletePriceModel-' + ${pricews?.id});" class="submit delete">
                    <span><g:message code="price.model.button.delete"/></span>
                </a>
                --%>
               		 <a onclick="showConfirm('delete-${pricews.id}');" class="submit delete"><span><g:message code="price.model.button.delete"/></span></a>
        </div>
    </div>
</div>
<g:render template="/confirm"
     model="[message: 'price.model.prompt.are.you.sure',
             controller: 'priceModel',
             action: 'deletePriceModel',
             id: iprice?.id,
            ]"/>

