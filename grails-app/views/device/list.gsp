<html>
<head>
    <meta name="layout" content="panels" />
</head>
<body>
    

    <content tag="column1">
        <g:render template="devices" model="[ devices: devices ]" />
    </content>

    <content tag="column2">
        <g:if test="${selected}">
            <!-- show selected rate card -->
           <g:render template="show" model="['selected': selected]"/>
        </g:if>
    </content>
</body>
</html>
