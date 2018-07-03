
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

  

    <div class="heading">
        <strong>
          <g:message code="purchase.bundle.title"/>
        </strong>
    </div>

    <div class="form-hold">
        <g:form name="plan-purchase-form" action="proceedToPurchase">
            <fieldset>
                <div class="form-columns">
                 <g:hiddenField name="bundleId" value="${bundle?.id}" />
                 
                                
                        <g:applyLayout name="form/text">
                             <div>
                              <content tag="label"><g:message code="prompt.customer.id"/></content>
                              <g:textField class="field" name="customerId" value=""/>
			       ${bundle?.getBundleName()}
			    </div>
			     
			    
                             
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
                            <a onclick="$('#plan-purchase-form').submit()" class="submit save"><span><g:message code="button.proceed"/></span></a>
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
   