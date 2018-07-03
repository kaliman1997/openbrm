<%@ page contentType="text/html;charset=UTF-8" %>

<%--
  Shows edit form for a contact type.

  @author Brian Cowdery
  @since  27-Jan-2011
--%>

<%@ page import="com.sapienter.jbilling.server.user.db.UserStatusDAS" %>
<div class="column-hold">
    <g:set var="isNew" value="${!voucher || !voucher?.id || voucher.id == 0}"/>

    <div class="heading">
        <g:if test="${isNew}">
            <strong><g:message code="voucher"/></strong>
        </g:if>
        <g:else>
            <strong><g:message code="Voucher.edit.title"/></strong>
        </g:else>
    </div>

   
	 <g:form id="Voucher-edit-form"  name="Voucher-edit-form" url="[action: 'save']">

    <div class="box">
        <fieldset>
            <div class="form-columns">
                <g:hiddenField name="id" value="${voucher?.id}"/>

           <%--<g:if test="${!isNew}">
                    <g:applyLayout name="form/text">
                        <content tag="label"><g:message code="BatchId"/></content>
                        <content tag="label.for">BatchId</content>
                        <g:textField class="field" name="BatchId" value="${voucher?.BatchId}"/>
                    </g:applyLayout>
                </g:if> --%>

                <g:applyLayout name="form/text">
                    <content tag="label"><g:message code="BatchId"/></content>
                    <input type="text" name="BatchId" maxlength = '6'/>
                </g:applyLayout> 
				<g:applyLayout name="form/text">
                    <content tag="label"><g:message code="BatchSize"/></content>
                    <input type="text" name="BatchSize"/>
                </g:applyLayout> 
				
				 <g:applyLayout name="form/select">
                	  <content tag="label"><g:message code="Products"/></content>
                	  <g:select name="toItemId" from="${planList}"
                          noSelection="['': message(code: 'filters.item.type.empty')]"
                          optionKey="id" optionValue="description"
                          value=""/>
            		</g:applyLayout>
			  
			
            </div>
        </fieldset>
    </div>


    </g:form>

    <div class="btn-box buttons">
        <ul>
            <li><a class="submit save" onclick="$('#Voucher-edit-form').submit();"><span><g:message code="Generate"/></span></a></li>
        </ul>
    </div>
</div>
