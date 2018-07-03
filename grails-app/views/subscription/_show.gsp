<%@ page import="com.sapienter.jbilling.server.util.Util" %>
<%@ page import="com.sapienter.jbilling.client.util.Constants" %>
<%@ page import="com.sapienter.jbilling.server.item.db.ItemDTO; com.sapienter.jbilling.server.item.db.ItemDAS"%>
<%@ page import="in.saralam.sbs.server.subscription.db.ServiceDTO; com.sapienter.jbilling.server.device.db.DeviceDTO"%>
<%@ page import="in.saralam.sbs.server.subscription.db.ServiceDTO; com.sapienter.jbilling.server.util.Constants"%>
<%@ page import="in.saralam.sbs.server.subscription.db.ServiceFeatureDTO; in.saralam.sbs.server.subscription.db.ServiceFeatureStatusDTO"%>
<%@ page import="in.saralam.sbs.server.subscription.SubscriptionConstants"%>

<div class="column-hold">

    <div class="heading">
        <strong><g:message code="service.label.details"/>&nbsp;<em>${service?.id}</em></strong>
    </div>

    <!-- Order Details -->
    <div class="box">
        <table class="dataTable">
            <tr>
                <td colspan="2">
                    <strong>
                        <g:if test="${user?.contact?.firstName || user?.contact?.lastName}">
                            ${user.contact.firstName}&nbsp;${user.contact.lastName}
                        </g:if>
                        <g:else>
                            ${user?.userName}
                        </g:else>
                    </strong><br>
                    <em>${user?.contact?.organizationName}</em>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td><td></td>
            </tr>
            <tr>
                <td><g:message code="service.label.user.id"/>:</td>
                <td class="value">
                    <sec:access url="/customer/show">
                        <g:remoteLink controller="customer" action="show" id="${user?.id}" before="register(this);" onSuccess="render(data, next);">
                            ${user?.id}
                        </g:remoteLink>
                    </sec:access>
                    <sec:noAccess url="/customer/show">
                        ${user?.id}
                    </sec:noAccess>
                </td>
            </tr>
            <tr>
                <td><g:message code="service.label.user.name" />:</td>
                <td class="value">${user?.userName}</td>
            </tr>
        </table>

        <table class="dataTable">
            <tr><td><g:message code="service.label.create.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${service?.createDate}" formatName="date.pretty.format"/>
                </td>
            </tr>
            <tr><td><g:message code="service.label.name"/>:</td>
                <td class="value">${service?.name}</td>
            </tr>
              <tr><td><g:message code="service.label.order.id"/>:</td>
                <td class="value">
		  <sec:access url="/order/show">
                        <g:remoteLink controller="order" action="show" id="${service?.orderDTO?.id}" before="register(this);" onSuccess="render(data, next);">
		${service?.orderDTO?.id}
		</g:remoteLink>
                    </sec:access>
                    <sec:noAccess url="/order/show">
                       ${service?.orderDTO?.id}
                    </sec:noAccess>
		</td>
            </tr>
            <tr>
                <td><g:message code="service.label.status"/>:</td>
                <td class="value">${service?.getServiceStatus()?.getDescription(session['language_id'])}</td>
            </tr>
        </table>
    </div>
    <div class="heading">
        <strong><g:message code="service.label.number"/></strong>
    </div>

    <!-- Order Notes -->
    <div class="box">
	<div class="box">
	<g:if test="${userDevices?.length > 0 }">
		
			<g:message code="service.label.add.device"/>
			<a href="${createLink (controller: 'subscription', action: 'addDevice', params: [serviceId: service?.id])}" onclick="submit edit">
			<img src="${resource(dir:'images', file:'add.png')}" alt="Add Device"/> </a>
		
		<table id="dataTable1" class="dataTable">
			<g:each var="userDevice" in="${userDevices}">
				<tr>
					<td><g:message code="service.label.device.imsi"/>:</td>
					<td class="value">
						${userDevice?.device?.imsi}
						<a href="${createLink (controller: 'subscription', action: 'deleteDevice', params: [userDeviceId: userDevice?.id])}" onclick="submit edit">
						<img src="${resource(dir:'images', file:'cross.png')}" alt="Remove Device"/>
						</a>
					</td>
				</tr>
			</g:each>
		</table>
	</g:if>
	<g:else>
		<em><g:message code="service.label.no.device"/></em>
		
			<a href="${createLink (controller: 'subscription', action: 'addDevice', params: [serviceId: service?.id])}" onclick="submit edit">
			<img src="${resource(dir:'images', file:'add.png')}" alt="Add Device"/> </a>
		
	</g:else>
</div>
	<!--
	<table class="dataTable">
	<tr><td><g:message code="service.label.telephone"/>:</td>
                <td class="value">
		 ${userDevice?.telephoneNumber}
                </td>
        </tr>
	<!-- <tr><td><g:message code="service.label.imsi"/>:</td>
                <td class="value">
                 ${device?.imsi}
                </td>
        </tr>
	<tr><td><g:message code="service.label.icc"/>:</td>
	     <td class="value">
		  ${device?.icc}
                </td>
                <td class="value">
                  <g:link controller="subscription" action="editDevice" id="${userDevice?.id}" class="submit edit"><span><g:message code="button.edit"/></span></g:link>
                </td>
        </tr> -->
	<!--<tr><td><g:message code="service.label.extId1"/>:</td>
             <td class="value">
                  ${userDevice?.extId1}
             </td>
        </tr>

	<tr><td><g:message code="service.label.device.status"/>:</td>
                <td class="value">
		${userDevice?.getUserDeviceStatus()?.getDescription(session['language_id'])}
                </td>
        </tr>
	</table>-->
    </div>

	 <div class="heading">
        <strong><g:message code="service.label.aliasNames"/></strong>
    </div>
	<div class="box"> 
	 <g:if test="${serviceAlias?.length > 0 }">
	
	
	<g:message code="service.label.add.aliasNames"/>
	   <a href="${createLink (controller: 'subscription', action: 'addAlias', params: [serviceId: service?.id])}" onclick="submit edit">
                 <img src="${resource(dir:'images', file:'add.png')}" alt="Add more values"/> </a>
                 
	<table id="dataTable1" class="dataTable">
	<g:each var="alias" in="${serviceAlias}">
	<tr><td><g:message code="service.label.aliasName"/>:</td>
                <td class="value">
		 ${alias?.aliasName}
				  
		<a href="${createLink (controller: 'subscription', action: 'deleteAliasName', params: [serviceAliaseId: alias?.id])}" onclick="submit edit">
                 <img src="${resource(dir:'images', file:'cross.png')}" alt="remove this value"/>
                </a>					
                </td>
        </tr>
		</g:each>
		</table>
			</g:if>
	<g:else>
	  <em><g:message code="service.label.no.AliasName"/></em>
	 
	   <a href="${createLink (controller: 'subscription', action: 'addAlias', params: [serviceId: service?.id])}" onclick="submit edit">
                 <img src="${resource(dir:'images', file:'add.png')}" alt="Add more values"/> </a>
                 
	</g:else>
	</div>

	<!--Sites-->
	<div class="heading">
        	<strong><g:message code="service.label.sites"/></strong>
	</div>
	<div class="box">
	<g:if test="${serviceSites?.length > 0 }">

			<g:message code="service.label.add.site"/>
			<a href="${createLink (controller: 'subscription', action: 'addSite', params: [serviceId: service?.id])}" onclick="submit edit">
			<img src="${resource(dir:'images', file:'add.png')}" alt="Add Site" /> </a>
		
		<table id="dataTable1" class="dataTable">
			<g:each var="site" in="${serviceSites}">
				<tr>
					<td><g:message code="service.label.site"/>:</td>
					<td class="value">
						${site?.siteAddr}
						<a href="${createLink (controller: 'subscription', action: 'deleteSite', params: [serviceSiteId: site?.id])}" onclick="submit edit">
						<img src="${resource(dir:'images', file:'cross.png')}" alt="Remove Site"/>
						</a>
					</td>
				</tr>
			</g:each>
		</table>
	</g:if>
	<g:else>
		<em><g:message code="service.label.no.site"/></em>
		
			<a href="${createLink (controller: 'subscription', action: 'addSite', params: [serviceId: service?.id])}" onclick="submit edit">
			<img src="${resource(dir:'images', file:'add.png')}" alt="Add Site"/> </a>
		
	</g:else>
</div>


	<!-- end of sites-->
		
    <div class="heading">
        <strong><g:message code="service.label.features"/></strong>

    </div>
	
    <div class="box">
        <g:if test="${serviceFeatures?.length > 0 }">
	  <table class="innerTable" >
                <thead class="innerHeader">
                     <tr>
                        <th><g:message code="service.label.feature.provisioning.tag"/></th>
                        <th><g:message code="service.label.feature.provisioning.status"/></th>
                        <th><g:message code="service.label.feature.provisioning.action"/></th>
                     </tr>
                 </thead>
                 <tbody>
                     <g:each var="feature" in="${serviceFeatures}" status="idx">
                         <tr>
                            <td class="innerContent">
                                ${feature?.provisioningTagMap?.provisioningTag?.code}
                            </td>
			    <td class="innerContent">
                                ${feature?.getServiceFeatureStatus()?.getDescription(session['language_id'])}
                            </td>
			    <td  class="value">
			     <g:if test="${feature?.getLevel() != 0}">
			       <g:if test="${feature?.getServiceFeatureStatus()?.getId() == SubscriptionConstants.SERVICE_FEATURE_STATUS_DEPROVISIONED}">
			         <g:link controller="subscription" action="addFeature" params="[msisdn: userDevice?.telephoneNumber, featureId: feature?.id]">
                                   <g:message code="service.label.feature.provisioning.add"/>
                                 </g:link>
			       </g:if>
			       <g:if test="${feature?.getServiceFeatureStatus()?.getId() == SubscriptionConstants.SERVICE_FEATURE_STATUS_PROVISIONED}">
				  <g:link controller="subscription" action="removeFeature" params="[msisdn: userDevice?.telephoneNumber, featureId: feature?.id]">
                                    <g:message code="service.label.feature.provisioning.remove"/>
                                  </g:link>
			       </g:if>
			       <g:if test="${feature?.getServiceFeatureStatus()?.getId() == SubscriptionConstants.SERVICE_FEATURE_STATUS_PENDING}">
				 <g:message code="service.label.feature.provisioning.pending"/>
			       </g:if>
			     </g:if>
			    </td>


		</tr>
                     </g:each>
                 </tbody>
           </table>
	</g:if>
	<g:else>
	  <em><g:message code="service.label.no.features"/></em>
	</g:else>
    </div>

<div class="heading">
        <strong><g:message code="service.label.balances"/></strong>
    </div>

    <div class="box" style="width:95%">
        <g:if test="${userBalances?.length > 0 }">
            <table class="innerTable" style="width:95%" >
                <thead class="innerHeader">
                     <tr>
                        <th style="min-width: 75px;"><g:message code="service.label.balance.descr"/></th>
                        <th><g:message code="service.label.balance.credit"/></th>
                        <th><g:message code="service.label.balance.balance"/></th>
                        <th><g:message code="service.label.balance.since"/></th>
                        <th><g:message code="service.label.balance.till"/></th>
                     </tr>
                 </thead>
                 <tbody>
                     <g:each var="line" in="${userBalances}" status="idx">
                         <tr>
                            <td class="innerContent">
				<g:if test="${line?.currency?.id == 10000}">
                                	${line?.getCurrency()?.getDescription(session['language_id'])}(MB)
				</g:if>
				<g:else>
					 ${line?.getCurrency()?.getDescription(session['language_id'])}
				</g:else>
                            </td>
                            <td class="innerContent">
				<g:if test="${line?.currency?.id == 10000}">
                                  <g:formatNumber number="${line.getBalanceInMB() ?: BigDecimal.ZERO}" formatName="default.number.format"/>
				</g:if>
				<g:else>
				  <g:formatNumber number="${line.balance ?: BigDecimal.ZERO}" formatName="default.number.format"/>
				</g:else>
                            </td>
			    <td class="innerContent">
				<g:if test="${line?.currency?.id == 10000}">
                                  <g:formatNumber number="${line.getCurrentBalanceInMB() ?: BigDecimal.ZERO}" formatName="default.number.format"/>
				</g:if>
				<g:else>
				  <g:formatNumber number="${line.currentBalance ?: BigDecimal.ZERO}" formatName="default.number.format"/>
				</g:else>
                            </td>
                            <td class="innerContent">
				<g:formatDate date="${line?.activeSince}" formatName="date.pretty.format"/>
                            </td>
			    <td class="innerContent">
                                <g:formatDate date="${line?.activeUntil}" formatName="date.pretty.format"/>
                            </td>

                         </tr>
                     </g:each>
                 </tbody>
           </table>
        </g:if>
        <g:else>
            <em><g:message code="service.prompt.no.balance"/></em>
        </g:else>
    </div> 
<div class="btn-box">
        <div class="row">
	    
		<g:if test="${service?.getServiceStatus()?.getId() == Constants.SERVICE_STATUS_ACTIVE}">
                    <g:link controller="subscription" action="inactivate" params="[serviceId: service?.id, extId1: userDevice?.extId1]" class="submit delete"><span><g:message code="button.service.inactivate"/></span></g:link>
		</g:if>
		<g:else>
		    <g:link controller="subscription" action="activate" params="[serviceId: service?.id, extId1: userDevice?.extId1]" class="submit order"><span><g:message code="button.service.activate"/></span></g:link>
		</g:else>
                
           
		 <g:link controller="subscription" action="changePlan" params="[serviceId: service?.id]" class="submit order"><span><g:message code="button.service.plan.change"/></span></g:link>
          
			
    </div>
</div>

 <div class="btn-box">
       <div class="row">
           
		 <g:link controller="subscription" action="cancelPlan" params="[serviceId: service?.id]" class="submit order"><span><g:message code="button.service.plan.cancel"/></span></g:link>
            
    </div>

  </div>


 <g:render template="/confirm"
     model="[message: 'service.prompt.are.you.sure',
             controller: 'service',
             action: 'changePlan',
             id: service.id,
            ]"/> 
			

