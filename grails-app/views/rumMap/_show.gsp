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
        <strong><g:message code="rum.map.label.details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

    <div class="box">
        <table class="dataTable">
            <tr><td><g:message code="rum.map.detail.id"/>:</td>
                <td class="value">
                    ${rumMapws?.id}
                </td>
            </tr>
	     <tr><td><g:message code="rum.map.detail.price.group"/>:</td>
                <td class="value">
                    ${rumMapws?.priceGroup}
                </td>
            </tr>
		<tr><td><g:message code="rum.map.detail.step"/>:</td>
                <td class="value">
                    ${rumMapws?.step}
                </td>
            </tr>
			

	      <tr><td><g:message code="rum.map.detail.price.model"/>:</td>
                <td class="value">
                 ${rumMapws?.priceModel}
                </td>
            </tr>
	    <tr><td><g:message code="rum.map.detail.rum"/>:</td>
                <td class="value">
                  ${rumMapws?.rum}
                </td>
            </tr>
		<tr><td><g:message code="rum.map.detail.resource"/>:</td>
                <td class="value">
                    ${rumMapws?.resource}
                </td>
            </tr>
	    <tr><td><g:message code="rum.map.detail.resource.id"/>:</td>
                <td class="value">
                   ${rumMapws?.resourceId}
                </td>
            </tr>
		<tr><td><g:message code="rum.map.detail.rum.type"/>:</td>
                <td class="value">
                  ${rumMapws?.rumType}
                </td>
            </tr>
		<tr><td><g:message code="rum.map.detail.consume.flag"/>:</td>
                <td class="value">
                  ${rumMapws?.consumeFlag}
                </td>
            </tr>

	    <tr><td><g:message code="rum.map.detail.created.date"/>:</td>
                <td class="value">
                   <g:formatDate date="${rumMapws?.createdDate}" formatName="date.pretty.format"/>
                </td>
            </tr>

	
	<tr><td><g:message code="rum.map.detail.rum.map.plan"/>:</td>
                <td class="value">
                   <sec:access url="/product/show">
                   <g:remoteLink controller="product" action="show" id="${rumMapws?.rumMapPlan}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                    ${rumMapws?.rumMapPlan}
                    </g:remoteLink>
                     </sec:access>
                      <sec:noAccess url="/product/show">
                       ${rumMapws?.rumMapPlan}
                        </sec:noAccess>
                </td>
            </tr>
	


            

	     <tr><td><g:message code="rum.map.detail.last.updated.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${rumMapws?.lastUpdatedDate}" formatName="date.pretty.format"/>
                </td>
            
	
           </table>
    </div>


 <div class="btn-box">
        <div class="row"><%--
		
                <a href="${createLink (controller: 'rumMap', action: 'edit', params: [id: rumMapws?.id])}" class="submit edit">
                    <span><g:message code="rum.map.button.edit"/></span>
                </a>
		 
		
				 <a onclick="showConfirm('deleteRumMap-' + ${rumMapws?.id});" class="submit delete">
                    <span><g:message code="rum.map.button.delete"/></span>
                </a>
                --%>
               		 <a onclick="showConfirm('delete-${rumMapws.id}');" class="submit delete"><span><g:message code="rum.map.button.delete"/></span></a>
        </div>
    </div>
</div>
<g:render template="/confirm"
     model="[message: 'rum.map.prompt.are.you.sure',
             controller: 'rumMap',
             action: 'deleteRumMap',
             id: irum?.id,
            ]"/>

