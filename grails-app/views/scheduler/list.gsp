<html>
<head>
    <meta name="layout" content="crm_configuration" />
</head>
<body>
<content tag="menu.item">scheduler</content>
    <content tag="column1">
		<g:render template="scheduler" model="[  params : params,scheduler:scheduler ]" />
    </content>
 <content tag="column2">
        <g:if test="${scheduleWS}">
            <!-- show selected exclusion -->
           <g:render template="show" model="[scheduleWS: scheduleWS,currency:currency]"/>
        </g:if>
    </content>
</body>
</html>
