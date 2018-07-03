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
        <strong><g:message code="rate.label.details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

    <div class="box">
        <table class="dataTable">
            <tr><td><g:message code="rate.detail.id"/>:</td>
                <td class="value">
                    ${ratews?.id}
                </td>
            </tr>
	     <tr><td><g:message code="rate.detail.prefix"/>:</td>
                <td class="value">
                    ${ratews?.prefix}
                </td>
            </tr>
		<tr><td><g:message code="rate.detail.destination"/>:</td>
                <td class="value">
                    ${ratews?.destination}
                </td>
            </tr>

	      <tr><td><g:message code="rate.detail.flat.rate"/>:</td>
                <td class="value">
                 <g:formatNumber number="${ratews?.flatRate}" type="currency" currencySymbol="${currency?.symbol}"/>
                </td>
            </tr>
	    <tr><td><g:message code="rate.detail.conn.charge"/>:</td>
                <td class="value">
                  <g:formatNumber number="${ratews?.conncharge}" type="currency" currencySymbol="${currency?.symbol}"/>
                </td>
            </tr>
	    <tr><td><g:message code="rate.detail.scaled.rate"/>:</td>
                <td class="value">
                  <g:formatNumber number="${ratews?.scaledRate}" type="currency" currencySymbol="${currency?.symbol}"/>
                </td>
            </tr>

	    <tr><td><g:message code="rate.detail.created.date"/>:</td>
                <td class="value">
                   <g:formatDate date="${ratews?.createdDate}" formatName="date.pretty.format"/>
                </td>
            </tr>

	<tr><td><g:message code="rate.detail.version"/>:</td>
                <td class="value">
                    ${ratews?.version}
                </td>
            </tr>
	<tr><td><g:message code="rate.detail.rate.plan"/>:</td>
                <td class="value">
                   <sec:access url="/product/show">
                   <g:remoteLink controller="product" action="show" id="${ratews?.ratePlan}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                    ${ratews?.ratePlan}
                    </g:remoteLink>
                     </sec:access>
                      <sec:noAccess url="/product/show">
                       ${ratews?.ratePlan}
                        </sec:noAccess>
                </td>
            </tr>
	<tr><td><g:message code="rate.detail.valid.from"/>:</td>
                <td class="value">
                    <g:formatDate date="${ratews?.validFrom}" formatName="date.pretty.format"/>
                </td>
            </tr>


            <tr><td><g:message code="rate.detail.valid.to"/>:</td>
                <td class="value">
                    <g:formatDate date="${ratews?.validTo}" formatName="date.pretty.format"/>
                </td>
            </tr>

	     <tr><td><g:message code="rate.detail.last.updated.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${ratews?.lastUpdatedDate}" formatName="date.pretty.format"/>
                </td>
            </tr>
           
	
           </table>
    </div>
</div>

 <div class="btn-box">
        <div class="row"><%--
		
                <a href="${createLink (controller: 'rate', action: 'edit', params: [id: ratews?.id])}" class="submit edit">
                    <span><g:message code="rate.button.edit"/></span>
                </a>
		 
		
				 <a onclick="showConfirm('deleteRate-' + ${ratews?.id});" class="submit delete">
                    <span><g:message code="rate.button.delete"/></span>
                </a>
                --%>
               		 <a onclick="showConfirm('delete-${ratews.id}');" class="submit delete"><span><g:message code="rate.button.delete"/></span></a>
        </div>
    </div>

<g:render template="/confirm"
     model="[message: 'rate.prompt.are.you.sure',
             controller: 'rate',
             action: 'deleteRate',
             id: irate?.id,
            ]"/>

