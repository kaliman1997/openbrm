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

<%--
  Shows user role.

  @author Brian Cowdery
  @since  02-Jun-2011
--%>


%{-- initialize the authority name used in the security context --}%
%{
    selected.initializeAuthority()
}%


<div class="column-hold">
    <div class="heading">
        <strong>
            ${selected.getTitle(session['language_id'])}
        </strong>
    </div>

    <div class="box">
        <table class="dataTable" cellspacing="0" cellpadding="0">
            <tbody>
            <tr>
                <td><g:message code="role.label.id"/></td>
                <td class="value">${selected.id}</td>
            </tr>
            <tr>
                <td><g:message code="role.label.authority"/></td>
                <td class="value">${selected.getAuthority()}</td>
            </tr>
            <tr>
                <td><g:message code="role.label.description"/></td>
                <td class="value">${selected.getDescription(session['language_id'])}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="btn-box">
        <div class="row">
            <g:link action="edit" id="${selected.id}" class="submit edit"><span><g:message code="button.edit"/></span></g:link>
            <a onclick="showConfirm('delete-${selected.id}');" class="submit delete"><span><g:message code="button.delete"/></span></a>
        </div>
    </div>

    <g:render template="/confirm"
              model="['message': 'role.delete.confirm',
                      'controller': 'role',
                      'action': 'delete',
                      'id': selected.id,
                      'ajax': true,
                      'update': 'column1',
                      'onYes': 'closePanel(\'#column2\')'
                     ]"/>
</div>