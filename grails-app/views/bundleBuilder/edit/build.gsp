%{--
     jBilling - The Enterprise Open Source Billing System
   Copyright (C) 2003-2011 Enterprise jBilling Software Ltd. and Emiliano Conde

   This file is part of jbilling.
   
   jbilling is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   jbilling is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.
   
   You should have received a copy of the GNU Affero General Public License
   along with jbilling.  If not, see <http://www.gnu.org/licenses/>.
 
  --}%

<%@ page contentType="text/html;charset=UTF-8" %>



<g:if test="${params.template}">
    <!-- render the template -->
    <g:render template="${params.template}"/>
</g:if>

<g:else>
    <!-- render the main builder view -->
    <html>
    <head>
        <meta name="layout" content="builder"/>
        <r:require module="showtab"/>
		<r:script disposition="head">
        
            $(document).ready(function() {
                $('#builder-tabs ul.ui-tabs-nav li a').each(function(index, link) {
                    $(link).attr('title', $(link).text());
                });

                $('#review-tabs').tabs({active: ${displayEditChangesTab ? 1 : 0} });
                $('#review-tabs ul.ui-tabs-nav li a').each(function(index, link) {
                    $(link).attr('title', $(link).text());
                });
                $('#builder-tabs').tabs();
				// prevent the Save Changes button to be clicked more than once.
                $('.order-btn-box .submit.save').on('click', function (e) {
                    var saveInProgress = $('#saveInProgress').val();

                    if (saveInProgress == "true") {
                        e.preventDefault();
                    } else {
                        $('#saveInProgress').val("true");
                    }
                });
                $('#order-line-charges-dialog').dialog({
                    autoOpen: false,
                    height: 450,
                    width: 800,
                    modal: true,
                    buttons: [{
                        text: '<g:message code="button.close"/>',
                        click: function() {$(this).dialog('close');}}]
                });
            });

             
        </r:script>
    </head>
    <body>
	
	
    <content tag="builder">
	 <g:render template="bundleChargesDialog" />
	  <div id="builder-tabs">
            <ul>
                <li aria-controls="ui-tabs-details"><a  href="${createLink(action: 'edit', event: 'details')}"><g:message code="builder.details.title"/></a></li>
                <li aria-controls="ui-tabs-products"><a  href="${createLink(action: 'edit', event: 'products')}"><g:message code="builder.products.title"/></a></li>
            </ul>
        </div>
    </content>

    <content tag="review">
        <g:render template="review"/>
    </content>
    </body>
    </html>
</g:else>