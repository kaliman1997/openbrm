<html>
<head>
    <meta name="layout" content="panels" />
</head>

<body>
    <content tag="column1">
        <g:render template="services" model="[services: services]"/>
    </content>

    <content tag="column2">
        <g:if test="${service}">
            <g:render template="show" model="[service: service]"/>
        </g:if>
    </content>
</body>
</html>
