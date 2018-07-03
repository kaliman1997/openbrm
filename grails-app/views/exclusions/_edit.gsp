<%@ page contentType="text/html;charset=UTF-8" %>

<%--
  Shows edit form for a contact type.

  @author Brian Cowdery
  @since  27-Jan-2011
--%>

<div class="column-hold">
    <g:set var="isNew" value="${!exclusion || !exclusion?.id || exclusion?.id == 0}"/>

    <div class="heading">
        <g:if test="${isNew}">
            <strong><g:message code="exclusion.add.title"/></strong>
        </g:if>
        <g:else>
            <strong><g:message code="exclusion.edit.title"/></strong>
        </g:else>
    </div>

    <g:uploadForm id="exclusion-edit-form" name="exclusion-edit-form" url="[action: 'save']">

    <div class="box">
        <fieldset>
            <div class="form-columns">
                <g:hiddenField name="id" value="${exclusion?.id}"/>

           <%--<g:if test="${!isNew}">
                    <g:applyLayout name="form/text">
                        <content tag="label"><g:message code="exclusion.table.name"/></content>
                        <content tag="label.for">tableName</content>
                        <g:textField class="field" name="tableName" value="${exclusion?.tableName}"/>
                    </g:applyLayout>
                </g:if> --%>

                <g:applyLayout name="form/text">
                    <content tag="label"><g:message code="Exclusion File"/></content>
                    <input type="file" name="exclusionfile"/>
                </g:applyLayout> 
              
			  <g:applyLayout name="form/select">
                	  <content tag="label"><g:message code="Rate Plan"/></content>
                	  <g:select name="toItemId" from="${planList}"
                          noSelection="['': message(code: 'filters.item.type.empty')]"
                          optionKey="id" optionValue="description"
                          value=""/>
            		</g:applyLayout>
			  
			  
			  
            </div>
        </fieldset>
    </div>

    </g:uploadForm>

    <div class="btn-box buttons">
        <ul>
            <li><a class="submit save" onclick="$('#exclusion-edit-form').submit();"><span><g:message code="button.save"/></span></a></li>
            <li><a class="submit cancel" onclick="closePanel(this);"><span><g:message code="button.cancel"/></span></a></li>
        </ul>
    </div>
</div>
