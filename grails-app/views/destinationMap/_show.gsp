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



<div class="column-hold" >

    <div class="heading">
        <strong><g:message code="destination.map.label.details"/>&nbsp;<em>${selected?.id}</em></strong>
    </div>

    <div class="box" >
        <table class="dataTable">
	
            <tr><td><g:message code="destination.map.detail.id"/>:</td>
                <td class="value">
                  ${destinationMapWS.id}
                </td>
            </tr>
	     <tr><td><g:message code="destination.map.detail.prefix"/>:</td>
                <td class="value">
                    ${destinationMapWS?.prefix}
                </td>
            </tr>
		<tr><td><g:message code="destination.map.detail.tier.code"/>:</td>
                <td class="value">
                    ${destinationMapWS?.tierCode}
                </td>
            </tr>

	      <tr><td><g:message code="destination.map.detail.map.group"/>:</td>
                <td class="value">
                ${destinationMapWS?.mapGroup}
                </td>
            </tr>
	    <tr><td><g:message code="destination.map.detail.description"/>:</td>
                <td class="value">
                   ${destinationMapWS?.description}
                </td>
            </tr>
	    
	 <tr><td><g:message code="destination.map.detail.category"/>:</td>
                <td class="value">
                   ${destinationMapWS?.category}
                </td>
            </tr>
	     <tr><td><g:message code="destination.map.detail.rank"/>:</td>
                <td class="value">
                   ${destinationMapWS?.rank}
                </td>
            </tr>
	  
           </table>

    </div>


 <div class="btn-box" >
      
 <div class="row">
       
                 
                <a onclick="showConfirm('deleteDestinationMap-' + ${destinationMapWS.id});" class="submit delete">
                    <span><g:message code="destination.map.button.delete"/></span>
                </a>
            
               		 
     </div>
    </div>
    </div> 

<g:render template="/confirm"
     model="[message: 'destination.map.prompt.are.you.sure',
             controller: 'destinationMap',
             action: 'deleteDestinationMap',
             id: destinationMap?.id,
            ]"/>

