
<%@ page import="com.sapienter.jbilling.server.util.Util" %>
<%@ page import="com.sapienter.jbilling.client.util.Constants" %>
<%@ page import="com.sapienter.jbilling.server.item.db.ItemDTO; com.sapienter.jbilling.server.item.db.ItemDAS"%>
<%@ page import="com.sapienter.jbilling.server.service.db.ServiceDTO; com.sapienter.jbilling.server.device.db.DeviceDTO"%>
<%@ page import="com.sapienter.jbilling.server.service.db.ServiceDTO; com.sapienter.jbilling.server.util.Constants"%>
<%@ page import="java.util.Date" %>
<html>
<head>
    <meta name="layout" content="main" />

    <script type="text/javascript">
        $(document).ready(function() {
            $('#contactType').change(function() {
                var selected = $('#contact-' + $(this).val());
                $(selected).show();
                $('div.contact').not(selected).hide();
            }).change();
        });
    </script>
</head>
<body>
<div class="form-edit">

   <g:set var="isNew" value="${!userDevice || !userDevice?.id || userDevice?.id == 0}"/>

    <div class="heading">
        <strong>
          <g:message code="plan.cancel.title"/>
        </strong>
    </div>

    <div class="form-hold">
        <g:form name="plan-cancel-form" action="saveCancelPlan">
            <fieldset>
                <div class="form-columns">
                   <!--  <div class="column"> -->
		        	<g:hiddenField name="serviceId" value="${serviceId}" />
                               
                        <g:applyLayout name="form/text">
                            <content tag="label"><g:message code="prompt.service.plan"/></content>
			     <span>
                              ${fromItem?.getDescription()}
                             </span>
                        </g:applyLayout>

			<g:applyLayout name="form/input">
                           <content tag="label"><g:message code="prompt.service.plan.billing.date"/></content>
                           <p>
                    </em> <strong><g:formatDate date="${ orderDto?.nextBillableDay}" formatName="date.pretty.format"/></strong></em>
                       </p>
                         </g:applyLayout>

			   <g:applyLayout name="form/date">
                           <content tag="label"><g:message code="prompt.service.plan.cancel.date"/></content>
                           <content tag="label.for">cancelDate</content>
               
                           <g:textField class="field" name="cancelDate" value="${formatName(date:reqDate, formatName: 'datepicker.format')}"/>
                         </g:applyLayout>
		
			 
		</div>
                   &nbsp;
                   &nbsp;
                   &nbsp;
                   &nbsp;
                   &nbsp;
                   &nbsp;
			
		<div class="buttons">
                    <ul>
                        <li>
                            <a onclick="$('#plan-cancel-form').submit()" class="submit save"><span><g:message code="button.save"/></span></a>
                        </li>
                        <li>
                            <g:link action="list" class="submit cancel"><span><g:message code="button.cancel"/></span></g:link>
                        </li>
                    </ul>
                </div>
	</div>

	</fieldset>
        </g:form>
    </div>
</div>
</body>
</html>
