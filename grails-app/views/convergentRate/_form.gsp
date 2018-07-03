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

<%@page import="com.sapienter.jbilling.server.process.db.PeriodUnitDTO" %>
<%@ page import="com.sapienter.jbilling.server.util.db.CountryDTO" %>

<%-- 
<g:javascript library="ui.core"/>
<g:javascript library="ui.spinner"/>
--%>

<div class="column-hold">
     <div class="heading">
        <strong><g:message code="customer.convergent.rate.config.title"/></strong>
    </div>

    <div class="box">
	                    <div class="form-columns">
                	                 
                     <table>
                      <tbody>
				<tr> <td>  <input type="radio" name="" value=""></td>
		         <td><g:message code="customer.rate.Id"/>&nbsp; </td>
			    <td class="innerContent">
                                    <div class="inp-bg inp4">
                                        <g:textField name="customer.rate.Id" class="field" value="${params.Id}"/>
                                    </div>
                                </td>
                <td>  <input type="radio" name="" value=""></td>
		         <td><g:message code="customer.rate.prefix"/>&nbsp; </td>
			    <td class="innerContent">
                                    <div class="inp-bg inp4">
                                        <g:textField name="customer.rate.prefix" class="field" value="${params.prefix}"/>
                                    </div>    
									</td>
              <td><input type="image" class="btn" src="${resource(dir:'images', file:'icon-search.gif')}" onclick="$('#search-form').submit()"/></td></tr>
			  </table>
			  	  
		</div> 
        
	</div>	
         
		<div class="btn-box buttons">
			<g:remoteLink action='edit' class="submit add" before="register(this);" onSuccess="render(data, next);">
        <span><g:message code="button.load"/></span>
    </g:remoteLink>
		
       </div>

				
	          
			  
				
            <script type="text/javascript">
                $(function() {
                    $(".numericOnly").keydown(function(event){
                    	// Allow only backspace, delete, left & right 
                        if ( event.keyCode==37 || event.keyCode== 39 || event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 ) {
                            // let it happen, don't do anything
                        }
                        else {
                            // Ensure that it is a number and stop the keypress
                            if (event.keyCode < 48 || event.keyCode > 57 ) {
                                event.preventDefault(); 
                            }   
                        }
                    });
                });
            </script>
       
    
</div>