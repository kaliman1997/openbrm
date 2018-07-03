
<%@ page import="com.sapienter.jbilling.server.util.Util" %>
<%@ page import="com.sapienter.jbilling.client.util.Constants" %>
<%@ page import="com.sapienter.jbilling.server.item.db.ItemDTO; com.sapienter.jbilling.server.item.db.ItemDAS"%>
<%@ page import="com.sapienter.jbilling.server.service.db.ServiceDTO; com.sapienter.jbilling.server.device.db.DeviceDTO"%>
<%@ page import="com.sapienter.jbilling.server.service.db.ServiceDTO; com.sapienter.jbilling.server.util.Constants"%>

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
          <g:message code="plan.edit.title"/>
        </strong>
    </div>

    <div class="form-hold">
        <g:form name="ud-edit-form" action="saveChangePlan">
            <fieldset>
                <div class="form-columns">
			<g:hiddenField name="serviceId" value="${serviceId}" />

                        <g:applyLayout name="form/text">
                            <content tag="label"><g:message code="prompt.service.plan.from"/></content>
			     <span>
                               ${fromItem?.getDescription()}
                             </span>
			     <g:hiddenField name="fromItemId" value="${fromItem?.id}" />
                        </g:applyLayout>

			<g:applyLayout name="form/select">
                	  <content tag="label"><g:message code="prompt.service.plan.to"/></content>
                	  <g:select name="toItemId" from="${planList}"
                          noSelection="['': message(code: 'filters.item.type.empty')]"
                          optionKey="id" optionValue="description"
                          value=""/>
            		</g:applyLayout>
					<g:applyLayout name="form/date">
                           <content tag="label"><g:message code="prompt.service.plan.change.date"/></content>
                           <content tag="label.for">changeDate</content>
               
                           <g:textField class="field" name="changeDate" value="${formatName(date:reqDate, formatName: 'datepicker.format')}"/>
                         </g:applyLayout>

		</div>&nbsp;
			
		<div class="buttons">
                    <ul>
                        <li>
                            <a onclick="$('#ud-edit-form').submit()" class="submit save"><span><g:message code="button.save"/></span></a>
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
