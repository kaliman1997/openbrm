<html>
<head>
    <meta name="layout" content="openrate" />
</head>
<body>
    <!-- selected configuration menu item -->
    <content tag="menu.item">PriceModel</content>

    <content tag="column1">
        <g:render template="prices" model="[prices: prices,currency:currency]" />
    </content>

    <content tag="column2">
        <g:if test="${selected}">
            <!-- show selected rate card -->
           <g:render template="show" model="['selected': selected,currency:currency]"/>
        </g:if>
    </content>
</body>
</html>
