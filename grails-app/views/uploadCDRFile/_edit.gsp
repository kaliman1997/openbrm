<%@ page contentType="text/html;charset=UTF-8" %>

<%--
  Shows edit form for a contact type.

  @author Brian Cowdery
  @since  27-Jan-2011
--%>

<div class="column-hold">
    <g:set var="isNew" value="${!uploadCDRFile || !uploadCDRFile?.id || uploadCDRFile.id == 0}"/>

    <div class="heading">
        <g:if test="${isNew}">
            <strong><g:message code="uploadcdr.add.title"/></strong>
        </g:if>
        <g:else>
            <strong><g:message code="uploadcdr.edit.title"/></strong>
        </g:else>
    </div>

    <g:uploadForm id="upload-cdr-file-edit-form" name="upload-cdr-edit-form" url="[action: 'save']">

    <div class="box">
        <fieldset>
            <div class="form-columns">
                <g:hiddenField name="id" value="${uploadCDRFile?.id}"/>

           <%--<g:if test="${!isNew}">
                    <g:applyLayout name="form/text">
                        <content tag="label"><g:message code="uploadcdr.table.name"/></content>
                        <content tag="label.for">tableName</content>
                        <g:textField class="field" name="tableName" value="${uploadcdr?.tableName}"/>
                    </g:applyLayout>
                </g:if> --%>

                <g:applyLayout name="form/text">
                    <content tag="label"><g:message code="uploadcdr.csv.file"/></content>
                    <input type="file" name="UploadcdrFile"/>
                </g:applyLayout> 
              
            </div>
        </fieldset>
    </div>

    </g:uploadForm>

    <div class="btn-box buttons">
        <ul>
            <li><a class="submit save" onclick="$('#upload-cdr-file-edit-form').submit();"><span><g:message code="button.save"/></span></a></li>
            <li><a class="submit cancel" onclick="closePanel(this);"><span><g:message code="button.cancel"/></span></a></li>
        </ul>
    </div>
</div>
