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
        <strong><g:message code="holiday.map.label.details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

    <div class="box">
        <table class="dataTable">
	  <tbody>
            <tr><td><g:message code="holiday.map.detail.id"/>:</td>
                <td class="value">
                  ${holidayMapWS?.id}
                </td>
            </tr>
	     <tr><td><g:message code="holiday.map.detail.map.group"/>:</td>
                <td class="value">
                    ${holidayMapWS?.mapGroup}
                </td>
            </tr>
		<tr><td><g:message code="holiday.map.detail.day"/>:</td>
                <td class="value">
                    ${holidayMapWS?.day}
                </td>
            </tr>

	      <tr><td><g:message code="holiday.map.detail.month"/>:</td>
                <td class="value">
                ${holidayMapWS?.month}
                </td>
            </tr>
	    <tr><td><g:message code="holiday.map.detail.year"/>:</td>
                <td class="value">
                   ${holidayMapWS?.year}
                </td>
            </tr>
	    
	 <tr><td><g:message code="holiday.map.detail.description"/>:</td>
                <td class="value">
                   ${holidayMapWS?.description}
                </td>
            </tr>
	       </tbody>
           </table>
    </div>


 <div class="btn-box">
        
      <a onclick="showConfirm('delete-${holidayMapWS.id}');" class="submit delete"><span><g:message code="rate.button.delete"/></span></a>
       
    </div>
    </div>

<g:render template="/confirm"
     model="[message: 'holiday.map.prompt.are.you.sure',
             controller: 'holidayMap',
             action: 'deleteHolidayMap',
             id: holidayMap?.id,
            ]"/>

