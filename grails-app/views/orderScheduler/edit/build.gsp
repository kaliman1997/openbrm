

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

        <script type="text/javascript">
            $(document).ready(function() {
                $('#builder-tabs').tabs();
            });
        </script>
    </head>
    <body>
    <content tag="builder">
        <div id="builder-tabs">
            <ul>
                <li><a href="${createLink(action: 'edit', event: 'general')}"><g:message code="General"/></a></li>
                <li><a href="${createLink(action: 'edit', event: 'details')}"><g:message code="Details"/></a></li>              
            </ul>
        </div>
    </content>

    </body>
    </html>
</g:else>
