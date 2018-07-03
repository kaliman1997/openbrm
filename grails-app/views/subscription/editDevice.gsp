
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
          <g:message code="userdevice.edit.title"/>
        </strong>
    </div>

    <div class="form-hold">
        <g:form name="ud-edit-form" action="save">
            <fieldset>
                <div class="form-columns">
                   <!--  <div class="column"> -->
                        <g:applyLayout name="form/text">
                            <content tag="label"><g:message code="prompt.userdevice.id"/></content>
			     <span>
                               ${userDevice?.id}
                             </span>
                        </g:applyLayout>

			<g:hiddenField name="temp" value="${userDevice?.baseUser?.id}" />
			<g:hiddenField name="userDeviceId" value="${userDevice?.id}" />

                         <g:applyLayout name="form/input">
                           <content tag="label"><g:message code="prompt.userdevice.telephone"/></content>
                           <g:textField class="field" name="telephoneNumber" value="${userDevice?.telephoneNumber}"/>
                         </g:applyLayout>

			 <g:applyLayout name="form/input">
                           <content tag="label"><g:message code="prompt.userdevice.icc"/></content>
                           <g:textField class="field" name="icc" value="${device?.icc}"/>
                         </g:applyLayout>
		
			 <g:applyLayout name="form/input">
                           <content tag="label"><g:message code="prompt.userdevice.imsi"/></content>
                           <g:textField class="field" name="imsi" value="${device?.imsi}" disabled="${device != null}"/>
                         </g:applyLayout>
		</div>
			
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
