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

<%@ page import="com.sapienter.jbilling.server.util.Util" %>
<%@ page import="com.sapienter.jbilling.client.util.Constants" %>
<%@ page import="com.sapienter.jbilling.server.item.db.ItemDTO; com.sapienter.jbilling.server.item.db.ItemDAS"%>

<div class="column-hold">

    <div class="heading">
        <strong><g:message code="device.label.details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

    <div class="box">
        <table class="dataTable">
            <tr><td><g:message code="device.details.id"/>:</td>
                <td class="value">
                    ${devicews?.id}
                </td>
            </tr>
	     <tr><td><g:message code="device.detail.serialNum"/>:</td>
                <td class="value">
                    ${devicews?.serialNum}
                </td>
            </tr>
		<tr><td><g:message code="device.detail.code"/>:</td>
                <td class="value">
                    ${devicews?.deviceCode}
                </td>
            </tr>

	      <tr><td><g:message code="device.detail.vendor"/>:</td>
                <td class="value">
                    ${devicews?.vendorCode}
                </td>
            </tr>

	    <tr><td><g:message code="device.detail.icc"/>:</td>
                <td class="value">
                    ${devicews?.icc}
                </td>
            </tr>

	<tr><td><g:message code="device.detail.imsi"/>:</td>
                <td class="value">
                    ${devicews?.imsi}
                </td>
            </tr>
            <tr><td><g:message code="device.detail.pin1"/>:</td>
                <td class="value">
		
                   ${devicews?.pin1}
                </td>
            </tr>
	<tr><td><g:message code="device.detail.puk1"/>:</td>
                <td class="value">
		<%-- <g:set var="PUK1" value="${devicews?.puk1?.toString()?.replaceAll('^\\d{4}','****')}"/> --%>
                   ${devicews?.puk1}
                </td>
            </tr>
             <tr><td><g:message code="device.detail.pin2"/>:</td>
                <td class="value">
		
                   ${devicews?.pin2}
                </td>
            </tr>
	<tr><td><g:message code="device.detail.puk2"/>:</td>
                <td class="value">
                    ${devicews?.puk2}
                </td>
            </tr>


            <tr><td><g:message code="device.detail.created.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${devicews?.createdDate}" formatName="date.pretty.format"/>
                </td>
            </tr>

	     <tr><td><g:message code="device.detail.updated.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${devicews?.lastUpdatedDate}" formatName="date.pretty.format"/>
                </td>
            </tr>
            <tr><td><g:message code="device.detail.status"/>:</td>
                <td class="value">
                 ${devicews?.getDeviceStatus()?.getDescription(session['language_id'])}   
                </td>
            </tr>
	<%--
          <tr><td><g:message code="device.detail.type"/>:</td>
                <td class="value">
                 ${devicews?.getDeviceType().getDescription(session['language_id'])}   
                </td>
            </tr>		--%> 
           </table>
    </div>


 <div class="btn-box">
        <div class="row">
		<%--
                <a href="${createLink (controller: 'device', action: 'edit', params: [id: dev?.id])}" class="submit edit">
                    <span><g:message code="device.button.edit"/></span>
                </a> --%>
		 <a onclick="showConfirm('deleteDevice-' + ${dev?.id});" class="submit delete">
                    <span><g:message code="device.button.delete"/></span>
                </a>
        </div>
    </div>
</div>
<g:render template="/confirm"
     model="[message: 'device.prompt.are.you.sure',
             controller: 'device',
             action: 'deleteDevice',
             id: dev?.id,
            ]"/>

