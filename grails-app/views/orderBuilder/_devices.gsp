%{--
  jBilling - The Enterprise Open Source Billing System
  Copyright (C) 2003-2011 Enterprise jBilling Software Ltd. and Emiliano Conde

  This file is part of jbilling.

  jbilling is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  jbilling is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Affero General Public License for more details.

  You should have received a copy of the GNU Affero General Public License
  along with jbilling.  If not, see <http://www.gnu.org/licenses/>.
  --}%

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sapienter.jbilling.server.user.db.CompanyDTO; com.sapienter.jbilling.server.item.db.ItemTypeDTO" %>
<%@ page import="com.sapienter.jbilling.server.device.db.DeviceDTO" %>
<%--
  Shows the product list and provides some basic filtering capabilities.

  @author Brian Cowdery
  @since 23-Jan-2011
--%>
<SCRIPT language="javascript">
	 function addRow(tableID) {
		 
         var table = document.getElementById(tableID);

         var rowCount = table.rows.length;
         var row = table.insertRow(rowCount);

         var colCount = table.rows[0].cells.length;

         for(var i=0; i<colCount; i++) {

             var newcell = row.insertCell(i);

             newcell.innerHTML = table.rows[1].cells[i].innerHTML;
             
             switch(newcell.childNodes[0].type) {
                 case "text":
                         newcell.childNodes[0].value = "";
                         break;
                
                 case "select-one":
                         newcell.childNodes[0].selectedIndex = 0;
                         break;
             }
         }
     }

     function delRow(tableID)
{
try{
var table = document.getElementById(tableID);
var rowCount = table.rows.length;
var current = window.event._;

//here we will delete the line
if (rowCount > 2){
while ( (current = current.parentElement) && current.tagName !="TR");
current.parentElement.removeChild(current);
}else{
return false;
}
}catch(e) {
alert(e);
}
}

	 
	
    </script>

<div id="devices-box">

           <g:formRemote name="order-devices-form" url="[action: 'edit']">
            <g:hiddenField name="_eventId" value="devices"/>
            <g:hiddenField name="execution" value="${flowExecutionKey}"/>
			   <fieldset>
			       <div class="form-columns">  
				   					         		    		        
                  <TABLE id="dataTable1" width="350px" align="left">
                 <thead><tr><th>Type</th><th>Device ID</th><th></th><th></th></tr></thead>
				   <tbody>
            <TR> <TD>
		           <g:applyLayout name="form/select">
                     <content tag="onClose">
                        function() {
                            $('#order-devices-form').submit();
                        }
                </content>
                        <g:select from="${devicetypes}" 
						 noSelection="['': message(code: 'Select Device')]"
                       optionKey="id" optionValue="${{it.getDescription(session['language_id'])}}" 
                        name="deviceType" 
                        value="${params.deviceType}"/> 
						</g:applyLayout> </TD>
				<TD> <g:applyLayout name="form/input">
					 
				   <content tag="onClose">
                        function() {
                            $('#order-devices-form').submit();
                        }
                </content>
	     		    <g:textField class="field text" name="serialNum" value="${params.serialNum}"/>	
					 </g:applyLayout> </TD>
					 
					 <TD> <a onclick="delRow('dataTable1')">
                                    <img src="${resource(dir:'images', file:'cross.png')}" alt="remove this value"/>
                                </a> </TD>
				 				
				 </TR></tbody>
             </TABLE>
			   <a onclick="addRow('dataTable1')">
                                <img src="${resource(dir:'images', file:'add.png')}"
                                    alt="Add more values"/>
                            </a>
			
					 
						</div> </fieldset>
		</g:formRemote>
		<script type="text/javascript">
                $(function() {
            $('#order-devices-form').find('select').change(function() {
                $('#order-devices-form').submit();
            });

            $('#order-devices-form').find('input:checkbox').change(function() {
                $('#order-devices-form').submit();
            });

            $('#order-devices-form').find('input.text').blur(function() {
                $('#order-devices-form').submit();
            });

            $('#order-devices-form').find('textarea').blur(function() {
                $('#order-devices-form').submit();
            });
        });
    </script>	
    </div>