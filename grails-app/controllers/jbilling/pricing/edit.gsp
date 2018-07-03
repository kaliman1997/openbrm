
<%@ page import="com.sapienter.jbilling.server.util.Util" %>
<%@ page import="com.sapienter.jbilling.client.util.Constants" %>
<%@ page import="com.sapienter.jbilling.server.item.db.ItemDTO; com.sapienter.jbilling.server.item.db.ItemDAS"%>
<%@ page import=" com.sapienter.jbilling.server.device.db.DeviceDTO"%>
<%@ page import=" com.sapienter.jbilling.server.util.Constants;  in.saralam.sbs.server.rating.db.RatingEventTypeDAS"%>

<html>
<head>
    <meta name="layout" content="main" />
	<TITLE> Add/Remove dynamic rows in HTML table </TITLE>	
		<SCRIPT language="javascript">
		
		
		
		 function addRow(tableID) {
	         var table = document.getElementById(tableID);
	         var rowCount = table.rows.length;
    	     var row = table.insertRow(rowCount);
	         var colCount = table.rows[0].cells.length;
	         var l=0;
	         for(var i=0; i<colCount; i++) {
	             var newcell = row.insertCell(i);
	             newcell.innerHTML = table.rows[1].cells[i].innerHTML;
	            
	             switch(newcell.childNodes[0].type) {
	             
    	             case "text":
        	                 newcell.childNodes[0].value = '';
            	             break;
	                 case "select-one":
    	                     newcell.childNodes[0].selectedIndex = '';
        	                 break;
	                 
            	 }
        	 }
	        
   	  }

		 function delRow(x)
		  {
			 try{
				 var table = document.getElementById(x);
		         var rowCount = table.rows.length;
			    var current = window.event.srcElement;
			    
			    if (rowCount > 2){
			    while ( (current = current.parentElement)  && current.tagName !="TR");
			         current.parentElement.removeChild(current);
			    }else{
			    	return false;
				    }
				  }catch(e) {
			             alert(e);
			         }
		  }

		 
    </script>
</head>
<body >
<div class="form-edit">
<g:set var="editable" value="${index == params.int('newLineIndex')}"/>

<g:if test="${pId==0 && rId==0 && uId==0}">
    <div class="heading">
        <strong>
                <g:message code="pricing.add.title"/>
        </strong>
    </div>
    </g:if>
     <g:else>
    <div class="heading" align="center">
        <strong>
               <g:applyLayout name="form/text">
            		<content tag="label"> <g:message code="pricing.edit.title"/> ${description}</content>
            		</g:applyLayout>
        </strong>
    </div>
</g:else>

    <div class="form-hold" align="center" >
        <g:form name="ud-edit-form" action="saveAdvancePricing"  >
               <g:hiddenField name="pDTO" value="${pId}"/>
               <g:hiddenField name="rDTO" value="${rId}"/>
               <g:hiddenField name="uDTO" value="${uId}"/>
                <g:hiddenField name="description" value="${description}"/>
			 <div class="box" style="height: 20px" >
			
<g:if test="${pId==0 && rId==0 && uId==0}">
			<g:applyLayout name="form/select" >
			<content tag="label"><b><i><u>Select Product</u></i></b></content>
                	  <g:select name="toItem" from="${planList}"
                          noSelection="['': message(code: 'Any')]"
                          optionKey="id" optionValue="description"
                          value="${params?.toItem}" />
            		</g:applyLayout>
            		</g:if>
            		<g:else>
            		<g:hiddenField name="toItem" value="${params?.toItem}"/>
            		</g:else>
            		
	</div>

     <g:set var="oneoff" value=""/>
                
                <div id="oneoff" class="box-cards box-cards-open">
                    <div class="box-cards-title" style="height: 50px">
                        <a class="btn-open" href="#"><span>
                        <g:message code="prompt.pricing.oneoff"/></span></a>
                        <g:applyLayout name="form/checkbox" >
                        <content tag="label">
                                <g:message code="pricing.label.line.use.item.oneoff"/>
                                </content>
                        
                        <g:checkBox name="oneoff" class="cb check" value="1" checked="${params?.oneoff == '1'}" />
                </g:applyLayout>
                    </div>

	<div class="box-card-hold">

            <fieldset >
               <div class="form-columns">
                                       
               
                            
		<button type="button" onclick="addRow('dataTable1')" style="float: right">
  		  <i class="icon-shopping-cart icon-white"></i>
    	<span>Add Rate</span>
		</button>
		 
		<table>
                      <tbody>
		       <tr>
                            
			      <td>   
				
		 <g:message code="tax.code"/>
	               </td>   
		         <td> 
	             <g:select name="oneTimeTaxCode"
                                      from="${taxCodeList}"
                                     
                                        noSelection="['': message(code: 'default.no.selection')]"
                                   value="${params?.oneTimeTaxCode}"/>
			</td>	 
			 </tr>
                        <tbody>
			</table>
           <TABLE id="dataTable1" width="500px" border-color="white">
	        <thead><tr><th><u>Order</u></th><th><u>Resource</u></th><th><u>Fixed Amount</u></th><th><u>Scaled Amount</u></th><th><u>Unit</u></th><th></th></tr></thead>
    	      <tbody>
    	      
    	      <g:each var="rate" in="${params?.oneTimeFixedAmount}" status="idx">
      			  <TR> 
      			  <TD>
      			  
      			  <div class="select4"    style="width: 100px">
                              		<div class="inp-bg inp4" style="width: 100px">
                              		  <g:textField class="field" name="oneTimeOrder" value="${params?.oneTimeOrder[l] }"/>
                            		</div></div>
      			  </TD>
      			 	 <TD>
                		  <g:applyLayout name="form/select" >
	                		   <g:select name="oneTimeCurrencyId"
                                      from="${currencies}"
                                      noSelection="['': message(code: 'Any')]"
                                      optionKey="id"
                                      optionValue="${{it.getDescription(session['language_id'])}}"
                                      value="${params?.oneTimeCurrencyId[l]}" />
            				</g:applyLayout>
            				<div></div>
               		 	</TD>
                 
            			<TD>
            			 	<g:applyLayout name="form/text">
    	                    	<g:textField class="field" name="oneTimeFixedAmount" value="${params?.oneTimeFixedAmount[l]}" size="30" />
        	            	</g:applyLayout>
        	            	
        	            	</TD>
             
            			<TD>
            				<g:applyLayout name="form/text">
        		               <g:textField class="field" name="oneTimeScaledAmount" value="${params?.oneTimeScaledAmount[l]}"  size="30" />
                		    <div></div>
                		     </g:applyLayout>
                		     
                		     </TD>
             
             			 <TD>
               				<g:applyLayout name="form/select" >
			                	  <g:select name="oneTimePeriodUnits" from="${periodUnits}"
            		              
                    		      optionKey="id" optionValue="description"
                          			value="${params?.oneTimePeriodUnits[l]}" />
                          			
            					</g:applyLayout>
            					
            		
   				         </TD>
   				       <td><div class="select4" style="width: 80px" >
   				       <button type="button" onclick="delRow('dataTable1')">delete</button>
   				       </div></td>
        		</TR>
        		<%l++ %>
        		</g:each>
        		<g:hiddenField name="l" value="${l}"/>
        	</tbody>
    </TABLE>
       
      <div style="height: 10px"></div>
   
	</fieldset></div></div>
		<g:set var="oneoff" value=""/>
                
                <div id="oneoff" class="box-cards box-cards-open">
                    <div class="box-cards-title" style="height: 50px">
                        <a class="btn-open" href="#"><span><g:message code="prompt.pricing.recurring"/></span></a>
                   	<g:applyLayout name="form/checkbox" >
                        <content tag="label">
                                <g:message code="pricing.label.line.use.item.recurring"/>
                                </content>

                        <g:checkBox  name="recurring" class="cb check" value="2" checked="${params?.recurring == '2'}" />
                </g:applyLayout>
                    </div>

				<div class="box-card-hold">

            	<fieldset>
              		 <div class="form-columns">
					 <button type="button" onclick="addRow('dataTable2')" style="float: right">
	    				<i class="icon-shopping-cart icon-white"></i>
   						 <span>Add Rate</span>
						</button>
		              <table>
                      <tbody>
		       <tr>
                            
			      <td>   
				
		 <g:message code="tax.code"/>
	               </td>   
		         <td> 
	             <g:select name="recurringTaxCode"
                                      from="${taxCodeList}"
                                     
                                        noSelection="['': message(code: 'default.no.selection')]"
                                   value="${params?.recurringTaxCode}"/>
			</td>	 
			 </tr>
                        <tbody>
			</table>
           		<TABLE id="dataTable2" width="350px" border-color="white">
       				 <thead><tr><th><u>Order</u></th><th><u>Resource</u></th><th><u>Fixed Amount</u></th><th><u>Scaled Amount</u></th><th><u>Unit</u></th><th></th></tr></thead>
         			 <tbody>
         			 <g:each var="rate" in="${params?.recurringFixedAmount}" status="idx">
        					<TR>
        					<TD>
      			  
      			  <div class="select4"    style="width: 100px">
                              		<div class="inp-bg inp4" style="width: 100px">
                              		  <g:textField class="field" name="recurringOrder" value="${params?.recurringOrder[m] }"/>
                            		</div></div>
      			  </TD>
        					
        						 <TD>
                 					<g:applyLayout name="form/select" >
	                		   <g:select name="recurringCurrencyId"
                                      from="${currencies}"
                                      noSelection="['': message(code: 'Any')]"
                                      optionKey="id"
                                      optionValue="${{it.getDescription(session['language_id'])}}"
                                      value="${params?.recurringCurrencyId[m]}" />
            				</g:applyLayout>
                					</TD>
                 
            						<TD>
            							 <g:applyLayout name="form/text">
                    					    <g:textField class="field" name="recurringFixedAmount" value="${params?.recurringFixedAmount[m]}" size="30"/>
                    						</g:applyLayout>
                    						</TD>
             
            <TD><g:applyLayout name="form/text">
                        
                        <g:textField class="field" name="recurringScaledAmount" value="${params?.recurringScaledAmount[m]}" size="30"/>
                    </g:applyLayout></TD>
             
              <TD>
              <g:applyLayout name="form/select" >
			                	  <g:select name="recurringPeriodUnits" from="${periodUnits}"
            		             
                    		      optionKey="id" optionValue="description"
                          			value="${params?.recurringPeriodUnits[m] }" />
                          					
            					</g:applyLayout>
            					
            		
            </TD>
             <td><div class="select4" style="width: 80px" >
   				       <button type="button" onclick="delRow('dataTable2')">delete</button>
   				       </div></td>
        </TR>
        <%m++ %>
        		</g:each></tbody>
    </TABLE>
    <g:hiddenField name="m" value="${m}"/>
          <div style="height: 10px"></div>
    
   
	</fieldset></div></div>
	
	<g:set var="oneoff" value=""/>
                <div id="oneoff" class="box-cards box-cards-open">
                    <div class="box-cards-title" style="height: 50px">
                        <a class="btn-open" href="#"><span><g:message code="prompt.pricing.usage"/></span></a>
					 <g:applyLayout name="form/checkbox" >
                        <content tag="label">
                                <g:message code="pricing.label.line.use.item.usage"/>
                                </content>
                        <content tag="label.for">pproduct</content>
                        <g:checkBox  name="usage" class="cb check" value="3" checked="${params?.usage == '3'}" />
                </g:applyLayout>
		
                    </div>
		     
	<div class="box-card-hold">
            <fieldset>
               <div class="form-columns">
					<button type="button" onclick="n++;addRow('dataTable3')" style="float: right">
    					<i class="icon-shopping-cart icon-white"></i>
   						 <span>Add Rate</span>
					</button>
		
		 <table>
                      <tbody>
		       <tr>
                            
			      <td>   
				
		 <g:message code="tax.code"/>
	               </td>   
		         <td> 
	             <g:select name="usageTaxCode"
                                      from="${taxCodeList}"
                                     
                                        noSelection="['': message(code: 'default.no.selection')]"
                                   value="${params?.usageTaxCode}"/>
			</td>	 
			 </tr>
                        <tbody>
			</table>
		
           <TABLE id="dataTable3"   width="1000px"border-color="black" border="2px">
       			   <thead><tr><th></th></tr></thead>
       			   <tbody>
       			   <g:each var="rate" in="${params?.usageFixedAmount}" status="idx">
          				<TR  style="border-spacing:0 50px;">
          				<td > 
    		        		<g:applyLayout name="form/select" > 
    		        		<div class="select4"   style="width: 90px;padding-left: 10px  " align="center">
                             		<b><i><u>Order</u></i></b>
                              		<div class="inp-bg inp4"  style="width: 90px">
                              		  <g:textField class="field" name="usageOrder" value="${params?.usageOrder[n] }"/>
                            		</div></div>
    		        	<div class="select4" style="width: 150px ">
                            	  &nbsp;&nbsp;&nbsp;<b><i><u>Event Name</u></i></b>
                               		 <g:select name="eventName" from="${events}"
            		              noSelection="['': message(code: 'Select Event')]"
                    		      optionKey="id" optionValue="eventName"
                          			value="${params?.eventName[n]}" style="width:150px"/>
                            	</div>
	    		            	  <div class="select4" style="width: 120px " >
                				 &nbsp;&nbsp;&nbsp; <b><i><u>Measured By</u></i></b>
                            	   <g:select name="measured" from="${measured}"
            		              noSelection="['': message(code: 'Select Measured Type')]"
                    		      optionKey="id" optionValue="type"
                          			value="${params?.measured[n]}" style="width:120px"/>
                            	</div>
								<div class="select4" style="width: 120px " >
                				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b><i><u>Category</u></i></b>
                            	   <g:select name="category" from="${categoryList}"
            		              noSelection="['': message(code: 'Select Category')]"
                    		      optionKey="id" optionValue="description"
                          			value="${params?.category[n]}" style="width:120px"/>
                            	</div>
                            	<div class="select4" style="width: 150px " >
                            		&nbsp;&nbsp;&nbsp;<b><i><u>Resource</u></i></b>
                               		<g:select name="usageCurrencyId"
                                      from="${currencies}"
                                      noSelection="['': message(code: 'Any')]"
                                      optionKey="id"
                                      optionValue="${{it.getDescription(session['language_id'])}}"
                                      value="${params?.usageCurrencyId[n]}" style="width:150px"/>
                          		  </div> 
                            	<div class="select4"  style="width: 90px " >
                             		<b><i><u>Fixed Amount</u></i></b>
                              		<div class="inp-bg inp4"  style="width: 90px">
                              		  <g:textField class="field" name="usageFixedAmount" value="${params?.usageFixedAmount[n]}"/>
                            		</div></div>
                            	<div class="select4"  style="width: 90px">
                             		<b><i><u>Scaled Amount</u></i></b>
                              		<div class="inp-bg inp4"  style="width: 90px">
                                		<g:textField class="field" name="usageScaledAmount" value="${params?.usageScaledAmount[n]}"/>
                            	</div></div>
                              	<div class="select4" style="width: 100px">
                            	 &nbsp;&nbsp;&nbsp; <b><i><u> Unit</u></i></b>
                               		 <g:select name="usagePeriodUnits" from="${periodUnits}"
            		              noSelection="['': message(code: 'Select Period')]"
                    		      optionKey="id" optionValue="description"
                          			value="${params?.usagePeriodUnits[n] }" style="width:100px"/>
                            	</div>
                            	
                            	
            			</g:applyLayout><br/>
            		
            			<g:applyLayout name="form/select" > 
            		  		<content tag="label"><b><i><u>Depends on</u></i></b></content>
            	               <g:select  name="dependsOn" from="${depends}"
  	    	                    noSelection="['': message(code: 'Select Dependency Type')]"
    	        	            optionKey="id" optionValue="type"
                          		value="${params?.dependsOn[n]}" />
								
								<div class="select4" style="width: 80px;padding-left: 500px" >
   				       <button type="button" onclick="delRow('dataTable3')">delete</button>
   				       </div>
            			</g:applyLayout><br/>
            			
            		
            		 <div class="select4" style="width: 200px;padding-left: 170px " >
                              <g:select name="dependsOnCurrencyId"
                                      from="${currencies}"
                                      noSelection="['': message(code: 'Any')]"
                                      optionKey="id"
                                      optionValue="${{it.getDescription(session['language_id'])}}"
                                      value="${params?.dependsOnCurrencyId[n]}" style="width:200px"/>
                        </div>
                        <g:hiddenField name="minChecked" value="${params.int('newLineIndex')}"/>
                        <g:hiddenField name="maxChecked" value="${params.int('newLineIndex')}"/>
                         <g:applyLayout name="form/text" > 
                        	 <div class="select4"  style="width: 150px">
                         		<content tag="label"><b><i><u>Min</u></i></b></content>
	                         <span>
    			        	 <g:checkBox class="cb checkbox" value="1" name="minChecked" checked="${params?.minChecked[n]=='1'}" />
            				<b><i><u>None</u></i></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    	      	<b><i><u>Max</u></i></b>
                              <div class="inp-bg inp4"  style="width: 100px">
                                <g:textField class="field" id="hai" name="minAmount" value="${params?.minAmount[n]}"/>
                            	</div>
			       			 </span>
				        </div>
		
		 
        		       
        		       <div class="select4"  style="width: 100px">
                         <span>
			              <g:checkBox class="cb checkbox" value="1" name="maxChecked" checked="${params?.maxChecked[n]=='1'}" />
           				 <b><i><u>None</u></i></b>
			            <div class="inp-bg inp4"  style="width: 100px">
                                <g:textField class="field" name="maxAmount" value="${params?.maxAmount[n]}"/>
                            </div>
				        </span>
				        </div>
                   </g:applyLayout>
                            <br/>
      				</td>
        		</TR>

        		 <%n++ %>
        		</g:each></tbody>
   		 </TABLE>
    <g:hiddenField name="n" value="${n}"/>
          <div style="height: 10px"></div>
		</fieldset>
	</div></div>
<div style="height: 10px"></div>
 </g:form>

        <div class="buttons">
                    <ul>
                        <li><a onclick="$('#ud-edit-form').submit();" class="submit save"><span><g:message code="button.save"/></span></a></li>
                        <li><g:link controller="pricing" action="list" class="submit cancel"><span><g:message code="button.cancel"/></span></g:link></li>
                    </ul>
                </div>
		</div>
				
    </div>
		 
	</div>
</div>
</body>
</html>
