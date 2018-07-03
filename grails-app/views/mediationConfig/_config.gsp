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

<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript">
$(function() {
    $(".numericOnly").keydown(function(event){
    	// Allow only backspace, delete, left & right 
        if ( event.keyCode==37 || event.keyCode== 39 || event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 ) {
            // let it happen, don't do anything
        }
        else {
            // Ensure that it is a number and stop the keypress
            if (event.keyCode < 48 || event.keyCode > 57 ) {
                event.preventDefault(); 
            }   
        }
    });
});
</script>

<div class="form-edit" style="width:100%">

    <div class="heading">
        <strong><g:message code="mediation.config.title"/></strong>
    </div>

    <div class="form-hold">
        <g:form name="save-customcontactfields-form" action="save">
            <g:hiddenField name="recCnt" value="${types?.size()}"/>
            <fieldset>
                <div class="form-columns" style="width:100%">
                    <div class="one_column" style="width: 100%;padding-right: 0px;">
                    <table class="innerTable" id="custom_fields" style="width: 100%;">
                        <thead class="innerHeader">
                             <tr>
                                <th><g:message code="mediation.config.id"/></th>
                                <th class="medium"><g:message code="mediation.config.name"/></th>
                                <th class="small"><g:message code="mediation.config.order"/></th>
                                <th class="large"><g:message code="mediation.config.plugin"/></th>
                                <th></th>
                             </tr>
                         </thead>
                         <tbody>
                            <g:each var="type" in="${types}">
                                <tr>
                                    <td>${type.id}</td>
                                    <td class="medium">
                                        <g:textField class="inp-bg" style="width: 180px" name="obj[${type.id}].name" 
                                            value="${type.name}"/>
                                    </td>
                                    <td class="small">
	                                    <g:textField class="inp-bg numericOnly inp2" name="obj[${type.id}].orderValue"  
                                            value="${type.orderValue}"/>
                                    </td>
                                    <td class="large">
                                        <g:select name="obj[${type.id}].pluggableTaskId" from="${readers}"
                                              optionKey="id" optionValue="${{'(Id:' + it.id + ') ' + it.type?.getDescription(session.language_id)}}"
                                              value="${type?.pluggableTaskId}" style="float: center;width: 100%"/>
                                    </td>
                                    <td>
                                        <a onclick="showConfirm('delete-${type.id}');" class="delete" style="
                                            width:9px;
                                            height:9px;
                                            text-indent:-9999px;
                                            background:url(../images/icon03.gif) no-repeat;
                                            float:right;
                                            margin:11px 8px 0 0;
                                            padding:0;"/>
                                        <g:render template="/confirm" model="['message': 'mediation.config.delete.confirm',
                                                  'controller': 'mediationConfig',
                                                  'action': 'delete',
                                                  'id': type.id,
                                                  'ajax': true,
                                                  'update': 'column1',
                                                  'onYes': 'closePanel(\'#column2\')'
                                                 ]"/>
                                    </td>
                                </tr>
                            </g:each>
                            <tr>
                                <td>New</td>
                                <td class="medium">
                                    <g:textField class="inp-bg" style="width: 180px" name="name" value=""/>
                                </td>
                                <td class="small"><g:textField class="inp-bg numericOnly inp2" name="orderValue"  
                                    value=""/></td>
                                <td class="large">
                                    <g:select name="pluggableTaskId" from="${readers}"
                                         optionKey="id" 
                                         optionValue="${{'(Id:' + it.id + ') ' + it.type?.getDescription(session.language_id)}}" 
                                         value="" style="float: center;width: 100%"/>
                                </td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                    </div>
                    <div class="row">&nbsp;<br></div>
                </div>
            </fieldset>
             <div class="btn-box">
                <a onclick="$('#save-customcontactfields-form').submit();" class="submit save"><span><g:message code="button.save"/></span></a>
                <g:link controller="config" action="index" class="submit cancel"><span><g:message code="button.cancel"/></span></g:link>
            </div>
	    <div class="btn-box">
		<g:link controller="mediationConfig" action="runMediation" class="submit "><span><g:message code="button.run.mediation"/></span></g:link>
	    </div>
        </g:form>
    </div>
</div>
