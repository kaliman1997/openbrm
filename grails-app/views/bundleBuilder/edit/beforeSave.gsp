

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>

</head>
<body>
<div class="form-edit">

    <div class="heading">
        <strong>
            <g:message code="bundle.review.id" args="[bundle.id ?: '']"/>
        </strong>
    </div>

    <div class="form-hold">
        <div class="form-columns">
            <p>
                Your page content goes here.<br/>
            </p>
        </div>


        <div class="buttons">
            <ul>
                <li>
                    <g:link class="submit save" action="edit" params="[_eventId: 'save']">
                        <span><g:message code="button.save"/></span>
                    </g:link>
                </li>

                <li>
                    <g:link class="submit cancel" action="edit" params="[_eventId: 'cancel']">
                        <span><g:message code="button.cancel"/></span>
                    </g:link>
                </li>
            </ul>
        </div>
    </div>


</div>
</body>
</html>