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
        <strong><g:message code="price.map.label.details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

    <div class="box">
        <table class="dataTable">
            <tr><td><g:message code="price.map.detail.id"/>:</td>
                <td class="value">
                    ${priceMapws?.id}
                </td>
            </tr>
	     <tr><td><g:message code="price.map.detail.map.group"/>:</td>
                <td class="value">
                    ${priceMapws?.mapGroup}
                </td>
            </tr>
		<tr><td><g:message code="price.map.detail.origin.zone"/>:</td>
                <td class="value">
                    ${priceMapws?.originZone}
                </td>
            </tr>
			

	      <tr><td><g:message code="price.map.detail.dest.zone"/>:</td>
                <td class="value">
                 ${priceMapws?.destZone}
                </td>
            </tr>
	    <tr><td><g:message code="price.map.detail.zone.result"/>:</td>
                <td class="value">
                  ${priceMapws?.zoneResult}
                </td>
            </tr>
		<tr><td><g:message code="price.map.detail.time.result"/>:</td>
                <td class="value">
                    ${priceMapws?.timeResult}
                </td>
            </tr>
		<tr><td><g:message code="price.map.detail.price.group"/>:</td>
                <td class="value">
                    ${priceMapws?.priceGroup}
                </td>
            </tr>
		<tr><td><g:message code="price.map.detail.description"/>:</td>
                <td class="value">
                    ${priceMapws?.description}
                </td>
            </tr>
	    <tr><td><g:message code="price.map.detail.rate.price"/>:</td>
                <td class="value">
                  <g:formatNumber number="${priceMapws?.ratePrice}" type="currency" currencySymbol="${currency?.symbol}"/>
                </td>
            </tr>
		<tr><td><g:message code="price.map.detail.set.up.price"/>:</td>
                <td class="value">
                  <g:formatNumber number="${priceMapws?.setUpPrice}" type="currency" currencySymbol="${currency?.symbol}"/>
                </td>
            </tr>
		<tr><td><g:message code="price.map.detail.rating.type"/>:</td>
                <td class="value">
                  ${priceMapws?.ratingType}
                </td>
            </tr>
			
		<tr><td><g:message code="price.map.detail.price.map.plan"/>:</td>
                <td class="value">
                   <sec:access url="/product/show">
                   <g:remoteLink controller="product" action="show" id="${priceMapws?.priceMapPlan}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                    ${priceMapws?.priceMapPlan}
                    </g:remoteLink>
                     </sec:access>
                      <sec:noAccess url="/product/show">
                       ${priceMapws?.priceMapPlan}
                        </sec:noAccess>
                </td>
            </tr>

	    <tr><td><g:message code="price.map.detail.created.date"/>:</td>
                <td class="value">
                   <g:formatDate date="${priceMapws?.createdDate}" formatName="date.pretty.format"/>
                </td>
            </tr>
		<tr><td><g:message code="price.map.detail.start.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${priceMapws?.startDate}" formatName="date.pretty.format"/>
                </td>
            </tr>


            <tr><td><g:message code="price.map.detail.end.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${priceMapws?.endDate}" formatName="date.pretty.format"/>
                </td>
            </tr>

	
	
	


            

	     <tr><td><g:message code="price.map.detail.last.updated.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${priceMapws?.lastUpdatedDate}" formatName="date.pretty.format"/>
                </td>
            
	
           </table>
    </div>


 <div class="btn-box">
        <div class="row"><%--
		
                <a href="${createLink (controller: 'priceMap', action: 'edit', params: [id: priceMapws?.id])}" class="submit edit">
                    <span><g:message code="price.map.button.edit"/></span>
                </a>
		 
		
				 <a onclick="showConfirm('deletePriceMap-' + ${priceMapws?.id});" class="submit delete">
                    <span><g:message code="price.map.button.delete"/></span>
                </a>
                --%>
               		 <a onclick="showConfirm('delete-${priceMapws.id}');" class="submit delete"><span><g:message code="price.map.button.delete"/></span></a>
        </div>
    </div>
</div>
<g:render template="/confirm"
     model="[message: 'price.map.prompt.are.you.sure',
             controller: 'priceMap',
             action: 'deletePriceMap',
             id: iprice?.id,
            ]"/>

