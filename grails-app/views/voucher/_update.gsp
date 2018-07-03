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


<%@ page import="in.saralam.sbs.server.voucher.db.VoucherStatusDAS" %>


<div class="form-edit">

    <g:set var="isNew" value="${!voucherWS || !voucherWS?.id || voucherWS?.id == 0}"/>


    <div class="heading">
        <strong><g:message code="Status Details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

	  
   <g:form  id="save-voucher-form" name="save-voucher-form" url="[action: 'statusupdate']">
   
   
    <div class="box">
	
	
          <g:hiddenField name="id" value="${voucherWS?.id}"/>
	
          <table class="dataTable">
		  
		   <tr><td><g:message code="Id"/>:</td>
                <td class="value">
                    ${voucherWS?.id}
                </td>
            </tr>
		  
          <tr><td><g:message code="PIN"/>:</td>
                <td class="value">
                    ${voucherWS?.pinCode}
                </td>
            </tr>
			
			<tr><td><g:message code="Current Status"/>:</td>
                <td class="value">
                    ${status}
                </td>
            </tr>

		 <tr><td> <g:applyLayout name="form/select">
                	  <content tag="label"><g:message code="New Status"/>:</content></td>
                	<td>   &nbsp &nbsp<g:select name="toStatus" from="${new VoucherStatusDAS().findAll()}"
                          noSelection="['': message(code: 'Voucher.status.type')]"
                          optionKey="id" optionValue="description"
                          value="statusValue"/>
            		</g:applyLayout>
                  </td>
            </tr>    
			
			

          </table>
		  
    </div>
  
</g:form>


 
 <div class="btn-box buttons">
    <ul>
             <li><a class="submit save" onclick="$('#save-voucher-form').submit();" ><span><g:message code="button.save"/></span></a></li>
			<li><g:link action="show"  class="submit cancel"><span><g:message code="button.cancel"/></span></g:link></li>
 </ul>
    </div>
	
	<g:render template="/confirm"
     model="[message: 'voucher.prompt.are.you.sure',
             controller: 'voucher',
             action: 'deleteVoucher',
             id: ivouchers?.id,
            ]"/>
	
</div>