<%@ page contentType="text/html;charset=UTF-8" %>

<%--
  Shows edit form for a contact type.

  @author Brian Cowdery
  @since  27-Jan-2011
--%>

<div class="column-hold">
    <g:set var="isNew" value="${!rate || !rate?.id || rate?.id == 0}"/>

    <div class="heading">
        <g:if test="${isNew}">
            <strong><g:message code="rate.add.title"/></strong>
        </g:if>
        <g:else>
            <strong><g:message code="rate.edit.title"/></strong>
        </g:else>
    </div>

    <g:uploadForm id="rate-edit-form" name="rate-edit-form" url="[action: 'save']">

    <div class="box">
        <fieldset>
            <div class="form-columns">
                <g:hiddenField name="id" value="${rate?.id}"/>

           <%--<g:if test="${!isNew}">
                    <g:applyLayout name="form/text">
                        <content tag="label"><g:message code="rate.table.name"/></content>
                        <content tag="label.for">tableName</content>
                        <g:textField class="field" name="tableName" value="${rate?.tableName}"/>
                    </g:applyLayout>
                </g:if> --%>

                <g:applyLayout name="form/text">
                    <content tag="label"><g:message code="rate.csv.file"/></content>
                    <input type="file" name="ratefile"/>
                </g:applyLayout> 
              
			  <g:applyLayout name="form/select">
                	  <content tag="label"><g:message code="rate.detail.rate.plan"/></content>
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
            <li><a class="submit save" onclick="$('#rate-edit-form').submit();"><span><g:message code="button.save"/></span></a></li>
            <li><a class="submit cancel" onclick="closePanel(this);"><span><g:message code="button.cancel"/></span></a></li>
        </ul>
    </div>
</div>
