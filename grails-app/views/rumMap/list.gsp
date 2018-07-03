<html>
<head>
    <meta name="layout" content="openrate" />
</head>
<body>
    <!-- selected configuration menu item -->
    <content tag="menu.item">RumMap</content>

    <content tag="column1">
        <g:render template="rummap" model="[rummap: rummap,currency:currency]" />
    </content>

    <content tag="column2">
        <g:if test="${selected}">
            <!-- show selected rate card -->
           <g:render template="show" model="['selected': selected,currency:currency]"/>
        </g:if>
    </content>
</body>
</html>
