%{--
  JBILLING CONFIDENTIAL
  _____________________

  [2003] - [2012] Enterprise jBilling Software Ltd.
  All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Enterprise jBilling Software.
  The intellectual and technical concepts contained
  herein are proprietary to Enterprise jBilling Software
  and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden.
  --}%

<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="form-edit">

    <g:set var="isNew" value="${!role || !role?.id || role?.id == 0}"/>

    <div class="heading">
        <strong>
            <g:if test="${isNew}">
                <g:message code="role.add.title"/>
            </g:if>
            <g:else>
                <g:message code="role.edit.title"/>
            </g:else>
        </strong>
    </div>

    <div class="form-hold">
        <g:form name="role-edit-form" action="save">
            <fieldset>

                <!-- role information -->
                <div class="form-columns">
                    <div class="column">
                        <g:applyLayout name="form/text">
                            <content tag="label"><g:message code="role.label.id"/></content>

                            <g:if test="${!isNew}">
                                <span>${role.id}</span>
                            </g:if>
                            <g:else>
                                <em><g:message code="prompt.id.new"/></em>
                            </g:else>

                            <g:hiddenField name="role.id" value="${role?.id}"/>
                        </g:applyLayout>

                        <g:applyLayout name="form/input">
                            <content tag="label"><g:message code="role.label.name"/></content>
                            <content tag="label.for">role.title</content>
                            <g:textField class="field" name="role.title" value="${role?.getTitle(session['language_id'])}"/>
                        </g:applyLayout>

                        <g:applyLayout name="form/input">
                            <content tag="label"><g:message code="role.label.description"/></content>
                            <content tag="label.for">role.description</content>
                            <g:textField class="field" name="role.description" value="${role?.getDescription(session['language_id'])}"/>
                        </g:applyLayout>
                    </div>
                </div>

                <!-- role permissions -->
                <g:each var="permissionType" status="n" in="${permissionTypes}">
                    <div class="form-columns">
                        <h3>${permissionType.description}</h3>

                        <!-- column 1 -->
                        <div class="column">
                            <g:each var="permission" status="i" in="${permissionType.permissions}">
                                <g:set var="rolePermission" value="${role.permissions.find{ it.id == permission.id }}"/>

                                <g:if test="${i % 2 == 0}">

                                    %{
                                        permission.initializeAuthority()
                                    }%

                                    <g:applyLayout name="form/checkbox">
                                        <content tag="group.label">${permission.id}:</content>
                                        <content tag="label">${permission.getDescription(session['language_id']) ?: permission.authority}</content>
                                        <content tag="label.for">permission.${permission.id}</content>

                                        <g:checkBox name="permission.${permission.id}" class="check cb" checked="${rolePermission}"/>
                                    </g:applyLayout>
                                </g:if>
                            </g:each>
                        </div>

                        <!-- column 2 -->
                        <div class="column">
                            <g:each var="permission" status="i" in="${permissionType.permissions}">
                                <g:set var="rolePermission" value="${role.permissions.find{ it.id == permission.id }}"/>

                                <g:if test="${i % 2 == 1}">

                                    %{
                                        permission.initializeAuthority()
                                    }%

                                    <g:applyLayout name="form/checkbox">
                                        <content tag="group.label">${permission.id}:</content>
                                        <content tag="label">${permission.getDescription(session['language_id']) ?: permission.authority}</content>
                                        <content tag="label.for">permission.${permission.id}</content>

                                        <g:checkBox name="permission.${permission.id}" class="check cb" checked="${rolePermission}"/>
                                    </g:applyLayout>
                                </g:if>
                            </g:each>
                        </div>
                    </div>

                    <g:if test="${n < permissionTypes.size()}">
                        <!-- spacer between permission types -->
                        <div>
                            &nbsp;<br/>
                        </div>
                    </g:if>
                </g:each>

                <!-- spacer -->
                <div>
                    &nbsp;<br/>
                </div>

                <div class="buttons">
                    <ul>
                        <li>
                            <a onclick="$('#role-edit-form').submit()" class="submit save"><span><g:message code="button.save"/></span></a>
                        </li>
                        <li>
                            <g:link action="list" class="submit cancel"><span><g:message code="button.cancel"/></span></g:link>
                        </li>
                    </ul>
                </div>

            </fieldset>
        </g:form>
    </div>
</div>
</body>
</html>