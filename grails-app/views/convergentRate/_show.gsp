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
        <strong><g:message code="convergent.rate.label.details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

    <div class="box">
        <table class="dataTable">
            <tr><td><g:message code="convergent.rate.detail.id"/>:</td>
                <td class="value">
                    ${cratews?.id}
                </td>
            </tr>
	     <tr><td><g:message code="convergent.rate.detail.prefix"/>:</td>
                <td class="value">
                    ${cratews?.prefix}
                </td>
            </tr>
		<tr><td><g:message code="convergent.rate.detail.destination"/>:</td>
                <td class="value">
                    ${cratews?.destination}
                </td>
            </tr>

	      <tr><td><g:message code="convergent.rate.detail.flat.rate"/>:</td>
                <td class="value">
                 <g:formatNumber number="${cratews?.flatRate}" type="currency" currencySymbol="${currency?.symbol}"/>
                </td>
            </tr>
	    <tr><td><g:message code="convergent.rate.detail.conn.charge"/>:</td>
                <td class="value">
                  <g:formatNumber number="${cratews?.conncharge}" type="currency" currencySymbol="${currency?.symbol}"/>
                </td>
            </tr>
	    <tr><td><g:message code="convergent.rate.detail.scaled.rate"/>:</td>
                <td class="value">
                  <g:formatNumber number="${cratews?.scaledRate}" type="currency" currencySymbol="${currency?.symbol}"/>
                </td>
            </tr>

	    <tr><td><g:message code="convergent.rate.detail.created.date"/>:</td>
                <td class="value">
                   <g:formatDate date="${cratews?.createdDate}" formatName="date.pretty.format"/>
                </td>
            </tr>

	<tr><td><g:message code="convergent.rate.detail.version"/>:</td>
                <td class="value">
                    ${cratews?.version}
                </td>
            </tr>
	<tr><td><g:message code="convergent.rate.detail.rate.plan"/>:</td>
                <td class="value">
                   <sec:access url="/product/show">
                   <g:remoteLink controller="product" action="show" id="${cratews?.ratePlan}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                    ${cratews?.ratePlan}
                    </g:remoteLink>
                     </sec:access>
                      <sec:noAccess url="/product/show">
                       ${cratews?.ratePlan}
                        </sec:noAccess>
                </td>
            </tr>
	<tr><td><g:message code="convergent.rate.detail.valid.from"/>:</td>
                <td class="value">
                    <g:formatDate date="${cratews?.validFrom}" formatName="date.pretty.format"/>
                </td>
            </tr>


            <tr><td><g:message code="convergent.rate.detail.valid.to"/>:</td>
                <td class="value">
                    <g:formatDate date="${cratews?.validTo}" formatName="date.pretty.format"/>
                </td>
            </tr>

	     <tr><td><g:message code="convergent.rate.detail.last.updated.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${cratews?.lastUpdatedDate}" formatName="date.pretty.format"/>
                </td>
            </tr>
               <tr><td><g:message code="convergent.rate.detail.rate.type"/>:</td>
                <td class="value">
                   ${cratews?.rateType}
                </td>
            </tr>
			<tr><td><g:message code="convergent.rate.detail.call.type"/>:</td>
                <td class="value">
                   ${cratews?.callType}
                </td>
            </tr>
	
           </table>
    </div>


 <div class="btn-box">
        <div class="row"><%--
		
                <a href="${createLink (controller: 'convergentRate', action: 'edit', params: [id: cratews?.id])}" class="submit edit">
                    <span><g:message code="convergent.rate.button.edit"/></span>
                </a>
		 
		
				 <a onclick="showConfirm('deleteRate-' + ${cratews?.id});" class="submit delete">
                    <span><g:message code="convergent.rate.button.delete"/></span>
                </a>
                --%>
               		 <a onclick="showConfirm('delete-${cratews.id}');" class="submit delete"><span><g:message code="convergent.rate.button.delete"/></span></a>
        </div>
    </div>
</div>
<g:render template="/confirm"
     model="[message: 'convergent.rate.prompt.are.you.sure',
             controller: 'convergentRate',
             action: 'deleteConvergentRate',
             id: irate?.id,
            ]"/>

