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
        <strong><g:message code="Voucher Details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

    <div class="box">
        <table class="dataTable">
            <tr><td><g:message code="Id"/>:</td>
                <td class="value">
                    ${voucherWS?.id}
                </td>
            </tr>
	     <tr><td><g:message code="SerialNo"/>:</td>
                <td class="value">
                    ${voucherWS?.serialNo}
                </td>
            </tr>
		<tr><td><g:message code="PIN"/>:</td>
                <td class="value">
                    ${voucherWS?.pinCode}
                </td>
            </tr>
         	     
	    <tr><td><g:message code="CreatedDateTime"/>:</td>
                <td class="value">
                   <g:formatDate date="${voucherWS?.createdDateTime}" formatName="date.pretty.format"/>
                </td>
            </tr>                       
            
            <tr><td><g:message code="EntityId"/>:</td>
                <td class="value">
                    ${voucherWS?.entityId}
                </td>
            </tr>
         
         <tr><td><g:message code="ProductId"/>:</td>
                <td class="value">
                    ${voucherWS?.productId}
                </td>
            </tr>
           </table>
    </div>

<div class="btn-box">
    <g:remoteLink action='update' id="${voucherWS.id}" class="submit save" before="register(this);" onSuccess="render(data, next);">
        <span><g:message code="Update Status"/></span>
    </g:remoteLink>
	
	 <a onclick="showConfirm('delete-${voucherWS.id}');" class="submit delete"><span><g:message code="Voucher.button.delete"/></span></a>
	
	</div>
<g:render template="/confirm"
     model="[message: 'voucher.prompt.are.you.sure',
             controller: 'voucher',
             action: 'deleteVoucher',
             id: ivouchers?.id,
            ]"/>

</div>
