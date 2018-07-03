
<html>
<head>
   <meta name="layout" content="panels" />
</head>

<body>
<content tag="column1">
        <g:render template="purchasedBundles" model="[purchasedBundles: purchasedBundles]" />
    </content>
    <content tag="column2">
      
        <g:if test="${purchased}">
            <g:render template="show" model="[purchased: purchased]"/>
</g:if>
    
</body>
</html>