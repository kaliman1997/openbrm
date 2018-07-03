
<%@ page import="com.sapienter.jbilling.server.util.Util" %>
<%@ page import="com.sapienter.jbilling.client.util.Constants" %>
<%@ page import="com.sapienter.jbilling.server.item.db.ItemDTO; com.sapienter.jbilling.server.item.db.ItemDAS"%>
<%@ page import="in.saralam.sbs.server.subscription.db.ServiceDTO; com.sapienter.jbilling.server.device.db.DeviceDTO"%>
<%@ page import="in.saralam.sbs.server.subscription.db.ServiceDTO; com.sapienter.jbilling.server.util.Constants"%>

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

   <g:set var="isNew" value="${!serviceSite || !serviceSite?.serviceId || serviceSite?.serviceId == 0}"/>

    <div class="heading">
        <strong>
          <g:message code="service.site.name"/>
        </strong>
    </div>

    <div class="form-hold">
        <g:form name="ud-edit-form" action="saveSite">
            <fieldset>
                <div class="form-columns">
			<g:hiddenField name="serviceId" value="${serviceId}" />
				
			 <g:applyLayout name="form/select">
                            <content tag="label"><g:message code="service.user.site"/></content>
                            <content tag="label.for">Site Address</content>
                            <g:select name="contactId" from="${userContactList}"
                             optionKey="id" optionValue="address1" value="" />
                        </g:applyLayout>

		</div>&nbsp
			
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
