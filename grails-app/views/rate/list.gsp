<html>
<head>
    <meta name="layout" content="openrate" />
</head>
<body>
    <!-- selected configuration menu item -->
    <content tag="menu.item">RateCard</content>

    <content tag="column1">
        <g:render template="rates" model="[rates: rates,currency:currency]" />
    </content>

    <content tag="column2">
        <g:if test="${selected}">
            <!-- show selected rate card -->
           <g:render template="show" model="['selected': selected,currency:currency]"/>
        </g:if>
    </content>
</body>
</html>
