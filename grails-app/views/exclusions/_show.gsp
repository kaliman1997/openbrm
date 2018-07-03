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
        <strong><g:message code="exclusion.label.details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

    <div class="box">
        <table class="dataTable">
            <tr><td><g:message code="exclusion.detail.id"/>:</td>
                <td class="value">
                    ${exclusionws?.id}
                </td>
            </tr>
	     <tr><td><g:message code="exclusion.detail.prefix"/>:</td>
                <td class="value">
                    ${exclusionws?.prefix}
                </td>
            </tr>
		<tr><td><g:message code="exclusion.detail.destination"/>:</td>
                <td class="value">
                    ${exclusionws?.destination}
                </td>
            </tr>

			<tr><td><g:message code="exclusion.detail.field1"/>:</td>
                <td class="value">
                    ${exclusionws?.field1}
                </td>
            </tr>
			
			<tr><td><g:message code="exclusion.detail.field2"/>:</td>
                <td class="value">
                    ${exclusionws?.field2}
                </td>
            </tr>

			
	    <tr><td><g:message code="exclusion.detail.created.date"/>:</td>
                <td class="value">
                   <g:formatDate date="${exclusionws?.createdDate}" formatName="date.pretty.format"/>
                </td>
            </tr>

	<tr><td><g:message code="exclusion.detail.version"/>:</td>
                <td class="value">
                    ${exclusionws?.version}
                </td>
            </tr>
	<tr><td><g:message code="exclusion.detail.rate.plan"/>:</td>
                <td class="value">
                   <sec:access url="/product/show">
                   <g:remoteLink controller="product" action="show" id="${exclusionws?.ratePlan}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                    ${exclusionws?.ratePlan}
                    </g:remoteLink>
                     </sec:access>
                      <sec:noAccess url="/product/show">
                       ${exclusionws?.ratePlan}
                        </sec:noAccess>
                </td>
            </tr>
	<tr><td><g:message code="exclusion.detail.valid.from"/>:</td>
                <td class="value">
                    <g:formatDate date="${exclusionws?.validFrom}" formatName="date.pretty.format"/>
                </td>
            </tr>


            <tr><td><g:message code="exclusion.detail.valid.to"/>:</td>
                <td class="value">
                    <g:formatDate date="${exclusionws?.validTo}" formatName="date.pretty.format"/>
                </td>
            </tr>

	     <tr><td><g:message code="exclusion.detail.last.updated.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${exclusionws?.lastUpdatedDate}" formatName="date.pretty.format"/>
                </td>
            </tr>
               
	
           </table>
    </div>


 <div class="btn-box">
        <div class="row"><%--
		
                <a href="${createLink (controller: 'exclusions', action: 'edit', params: [id: exclusionws?.id])}" class="submit edit">
                    <span><g:message code="exclusion.button.edit"/></span>
                </a>
		 
		
				 <a onclick="showConfirm('deleteExclusion-' + ${exclusionws?.id});" class="submit delete">
                    <span><g:message code="exclusion.button.delete"/></span>
                </a>
                --%>
               		 <a onclick="showConfirm('delete-${exclusionws.id}');" class="submit delete"><span><g:message code="exclusion.button.delete"/></span></a>
        </div>
    </div>
</div>
<g:render template="/confirm"
     model="[message: 'exclusion.prompt.are.you.sure',
             controller: 'exclusions',
             action: 'deleteExclusion',
             id: iexclusion?.id,
            ]"/>

