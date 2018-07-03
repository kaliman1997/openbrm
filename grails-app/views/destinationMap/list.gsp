<html>
<head>
    <meta name="layout" content="openrate" />
</head>
<body>
    <!-- selected configuration menu item -->
    <content tag="menu.item">Destination Map</content>

    
   
    <content tag="column1">
        <g:render template="destinationMap" model="[destionmap: destionmap]" />
    </content>

    <content tag="column2">
        <g:if test="${selected}">
            <!-- show selected rate card -->
           <g:render template="show" model="['selected': selected]"/>
        </g:if>
    </content>
</body>
</html>
