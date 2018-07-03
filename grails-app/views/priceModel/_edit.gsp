<%@ page contentType="text/html;charset=UTF-8" %>

<%--
  Shows edit form for a contact type.

  @author Brian Cowdery
  @since  27-Jan-2011
--%>

<div class="column-hold">
    <g:set var="isNew" value="${!priceModel || !priceModel?.id || priceModel?.id == 0}"/>

    <div class="heading">
        <g:if test="${isNew}">
            <strong><g:message code="price.model.add.title"/></strong>
        </g:if>
        <g:else>
            <strong><g:message code="price.model.edit.title"/></strong>
        </g:else>
    </div>

    <g:uploadForm id="price-model-edit-form" name="price-model-edit-form" url="[action: 'save']">

    <div class="box">
        <fieldset>
            <div class="form-columns">
                <g:hiddenField name="id" value="${priceModel?.id}"/>

           <%--<g:if test="${!isNew}">
                    <g:applyLayout name="form/text">
                        <content tag="label"><g:message code="price.model.table.name"/></content>
                        <content tag="label.for">tableName</content>
                        <g:textField class="field" name="tableName" value="${priceModel?.tableName}"/>
                    </g:applyLayout>
                </g:if> --%>

                <g:applyLayout name="form/text">
                    <content tag="label"><g:message code="price.model.csv.file"/></content>
                    <input type="file" name="pricefile"/>
                </g:applyLayout> 
              
			  <g:applyLayout name="form/select">
                	  <content tag="label"><g:message code="price.model.detail.price.plan"/></content>
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
            <li><a class="submit save" onclick="$('#price-model-edit-form').submit();"><span><g:message code="button.save"/></span></a></li>
            <li><a class="submit cancel" onclick="closePanel(this);"><span><g:message code="button.cancel"/></span></a></li>
        </ul>
    </div>
</div>
