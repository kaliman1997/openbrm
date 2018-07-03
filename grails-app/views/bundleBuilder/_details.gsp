

<%@ page import="com.sapienter.jbilling.server.util.Constants" %>


<div id="details-box">
    <g:formRemote name="order-details-form" url="[action: 'edit']" update="column2" method="GET">
        <g:hiddenField name="_eventId" value="update"/>
        <g:hiddenField name="execution" value="${flowExecutionKey}"/>

        <div class="form-columns">
	 <table class="dataTable">
	    <tbody>
        <tr>
	    <td>
		<g:applyLayout name="form/input">
                <content tag="label"><g:message code="prompt.bundle.name"/><span id="mandatory-meta-field">*</span></content>
                <content tag="label.for">bundleName</content>
                <g:textField class="field text" name="bundleName" value="${bundle?.bundleName}"/>
         </g:applyLayout>
		 
	     </td>
	     </tr>
        
        <tr>
		<td>
            <g:applyLayout name="form/date">
                <content tag="label"><g:message code="bundle.label.active.since"/></content>
                <content tag="label.for">activeSince</content>
                <g:textField class="field" name="activeSince" value="${formatDate(date: bundle?.activeSince, formatName: 'datepicker.format')}"/>
            </g:applyLayout>
        </td>
	    </tr>
		<tr>
		<td>
            <g:applyLayout name="form/date">
                <content tag="label"><g:message code="bundle.label.active.until"/></content>
                <content tag="label.for">activeUntil</content>
                <g:textField class="field" name="activeUntil" value="${formatDate(date: bundle?.activeUntil, formatName: 'datepicker.format')}"/>
            </g:applyLayout>

        </td>
	    </tr>
	    <tr>
        <td>
	   
	      <g:applyLayout name="form/checkbox" name="form/text">
	   
	        <g:checkBox name="mbgCheck" class="cb check" value="${true}" />
	        <content tag="label"><g:message code="prompt.bundle.mbg.days"/></content>
            <content tag="label.for">mbgDays</content>  
           
            <g:textField class="field text"  size="small" name="mbgDays" value="${bundle?.mbgDays}"/>
	      </g:applyLayout>
           
	    </td>
	    </tr>
		 
		<tr>
         <td>		 
		    <g:render template="/metaFields/editMetaFields" model="[availableFields: availableFields, fieldValues: bundle?.metaFields]"/>
	    </td>
        </tr>
		   
		   
		   
		   
	    
	     </tbody>
	     </table>
            
        </div>
	

        <hr/>

            
        </div>
    </g:formRemote>

    <script type="text/javascript">
        var orderStatus = $('#statusId').val();

        $(function() {
            $('#period').change(function() {
                if ($(this).val() == ${Constants.ORDER_PERIOD_ONCE}) {
                    $('#billingTypeId').val(${Constants.ORDER_BILLING_POST_PAID});
                    $('#billingTypeId').attr('disabled', true);
                } else {
                    $('#billingTypeId').attr('disabled', false);
                }
            }).change();

            $('#statusId').change(function() {
                if ($(this).val() == ${Constants.ORDER_STATUS_SUSPENDED}) {
                    $('#status-suspended-dialog').dialog('open');
                } else {
                    orderStatus = $(this).val();
                }
            });

            $('#status-suspended-dialog').dialog({
                 autoOpen: false,
                 height: 200,
                 width: 375,
                 modal: true,
                 buttons: {
                     '<g:message code="prompt.yes"/>': function() {
                         $(this).dialog('close');
                     },
                     '<g:message code="prompt.no"/>': function() {
                         $('#statusId').val(orderStatus);
                         $(this).dialog('close');
                     }
                 }
             });

            $('#order-details-form').find('select').change(function() {
                $('#order-details-form').submit();
            });

            $('#order-details-form').find('input:checkbox').change(function() {
                $('#order-details-form').submit();
            });

            $('#order-details-form').find('input.text').blur(function() {
                $('#order-details-form').submit();
            });

            $('#order-details-form').find('textarea').blur(function() {
                $('#order-details-form').submit();
            });
        });
    </script>

    <!-- confirmation dialog for status changes -->
    <div id="status-suspended-dialog" title="${message(code: 'popup.confirm.title')}">
        <table style="margin: 3px 0 0 10px">
            <tbody>
            <tr>
                <td valign="top">
                    <img src="${resource(dir:'images', file:'icon34.gif')}" alt="confirm">
                </td>
                <td class="col2" style="padding-left: 7px">
                    <g:message code="order.prompt.set.suspended" args="[order?.id]"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
