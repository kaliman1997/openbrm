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



<div class="column-hold">

    <div class="heading">
        <strong><g:message code="uploadcdr.label.details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

   <div class="box">
        <table class="dataTable">
	  <tbody>
            <tr><td><g:message code="uploadcdr.detail.id"/>:</td>
                <td class="value">
                  ${uploadCDRFileWS?.id}
                </td>
            </tr>
	 <tr><td><g:message code="uploadcdr.detail.name"/>:</td>
                <td class="value">
                   ${uploadCDRFileWS?.name}
                </td>
            </tr>
			<tr>
                <td><g:message code="uploadcdr.detail.date"/></td>
                <td class="value">
                    <g:formatDate date="${uploadCDRFileWS?.date}" formatName="date.pretty.format"/>
                </td>
            </tr>
			 <tr><td><g:message code="Status"/>:</td>
                <td class="value">
                   ${uploadCDRFileWS?.status}
                </td>
            </tr>
	       </tbody>
           </table>
    </div>  


 <div class="btn-box">
        
      <a onclick="showConfirm('delete-${uploadCDRFileWS.id}');" class="submit delete"><span><g:message code="Delete"/></span></a>
       
    </div>
    </div>

<g:render template="/confirm"
     model="[message: 'uploadcdr.prompt.are.you.sure',
             controller: 'uploadcdr',
             action: 'deleteUploadcdr',
             id: uploadcdr?.id,
            ]"/>

