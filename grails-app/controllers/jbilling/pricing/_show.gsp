
<%@page import="in.saralam.sbs.server.advancepricing.db.ProductChargeRateDAS"%>
<%@ page import="com.sapienter.jbilling.server.util.Util" %>
<%@ page import="com.sapienter.jbilling.client.util.Constants" %>
<%@ page import="com.sapienter.jbilling.server.item.db.ItemDTO; com.sapienter.jbilling.server.item.db.ItemDAS;com.sapienter.jbilling.server.process.db.PeriodUnitDTO;com.sapienter.jbilling.server.util.Constants"%>

<div class="column-hold">

   
    

    <div class="heading">
        <strong><g:message code="pricing.label.details"/>&nbsp;<em>${pDTO?.item?.description?:rDTO?.item?.description?:uDTO?.item?.description}</em></strong>
    </div>

    <!-- Order Details -->
    <div class="box">

        <table class="dataTable">
         <tr><td><g:message code="pricing.label.product"/>:</td>
                <td class="value">
                   <sec:access url="/product/show">
                   <g:remoteLink controller="product" action="show" id="${pDTO?.item?.id?:rDTO?.item?.id?:uDTO?.item?.id}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                    ${pDTO?.item?.id?:rDTO?.item?.id?:uDTO?.item?.id}
                    </g:remoteLink>
                     </sec:access>
                      <sec:noAccess url="/product/show">
                       ${pDTO?.item?.id?:rDTO?.item?.id?:uDTO?.item?.id}
                        </sec:noAccess>
                </td>
            </tr>
            <tr><td><g:message code="pricing.label.create.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${pDTO?.createdDate?:rDTO?.createdDate?:uDTO?.createdDate}" formatName="date.pretty.format"/>
                </td>
            </tr>
         
           

        </table>
    </div>

    <div class="heading">
        <strong><g:message code="pricing.label.rates"/></strong>
    </div>

    <!-- Order Lines -->
    <div class="box">
        <g:if test="${pDTO?.rates} || ${rDTO?.rates} || ${uDTO?.rates} ">
            <table class="innerTable" >
                <thead class="innerHeader">
                    
                 </thead>
                  <tbody>
                           
                 
                           
                      <g:if test="${pDTO!=null}">
                         <g:if test="${(pDTO?.chargeType.id)==(Constants.CHARGE_ONE_TIME)}">
                          
                        <div class="heading" style="width:99%" >
                         <strong><g:message code="pricing.label.One.Time"/></strong>
                          </div>
                             <table class="innerTable" style="width:99%"  >
                            <thead class="innerHeader">
                        <tr>
                      
                      <th><g:message code="pricing.label.line.currency"/></th>
                        <th><g:message code="pricing.label.line.fixed"/></th>
                        <th><g:message code="pricing.label.line.scaled"/></th>
                        <th><g:message code="pricing.label.line.unit"/></th>
                         
		
                         </tr>
		       </thead>
                     
		      <tbody>
		      <g:each var="rate" in="${new ProductChargeRateDAS().findByProductCharge(pDTO.id)}" status="idx">
		       <g:if test="${rate?.deleted==0}">
                         <tr>
                            
                             </td>
                           <td class="innerContent">
                                ${rate?.currency?.description?: ""}
                            </td>
                            <td class="innerContent">
                                <g:formatNumber number="${rate?.fixedAmount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                           <td class="innerContent">
                                <g:formatNumber number="${rate?.scaledAmount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                             </td>
                           <td class="innerContent">
                                 ${rate?.unitId?.description?:""}
                            </td>
			    
                         </tr>
                         </g:if>
                         </g:each> 
			 </tbody>
			</table>
			</g:if>
			</g:if>
			  &nbsp;
                           &nbsp;
                    <g:if test="${rDTO!=null}">
                     <g:if test="${(rDTO?.chargeType.id)==(Constants.CHARGE_RECURRING)}">
                          
                          <div class="heading" style="width:99%">
                         <strong><g:message code="pricing.label.Recurring"/></strong>
                          </div>
                             <table class="innerTable" style="width:99%"  >
                            <thead class="innerHeader">
                        <tr>
                      
                      <th><g:message code="pricing.label.line.currency"/></th>
                        <th><g:message code="pricing.label.line.fixed"/></th>
                        <th><g:message code="pricing.label.line.scaled"/></th>
                        <th><g:message code="pricing.label.line.unit"/></th>
                         
		
                         </tr>
		       </thead>
                     
		      <tbody>
		      <g:each var="rate" in="${new ProductChargeRateDAS().findByProductCharge(rDTO.id)}" status="idx">
		       <g:if test="${rate?.deleted==0}">
                         <tr>
                            
                             </td>
                           <td class="innerContent">
                                ${rate?.currency?.description?: ""}
                            </td>
                            <td class="innerContent">
                                <g:formatNumber number="${rate?.fixedAmount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                           <td class="innerContent">
                                <g:formatNumber number="${rate?.scaledAmount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                             </td>
                           <td class="innerContent">
                                 ${rate?.unitId?.description?:""}
                            </td>
			    
                         </tr>
                         </g:if>
                         </g:each> 
			 </tbody>
			</table>
			</g:if>
</g:if>
			 &nbsp;
                           &nbsp;
                    <g:if test="${uDTO!=null}">
                     <g:if test="${(uDTO?.chargeType.id)==(Constants.CHARGE_USAGE)}">
                          
                          <div class="heading" style="width:99%">
                         <strong><g:message code="pricing.label.Usage"/></strong>
                          </div>
                             <table class="innerTable" style="width:99%"  >
                            <thead class="innerHeader">
                        <tr>
                      
                      <th><g:message code="pricing.label.line.currency"/></th>
                        <th><g:message code="pricing.label.line.fixed"/></th>
                        <th><g:message code="pricing.label.line.scaled"/></th>
                        <th><g:message code="pricing.label.line.unit"/></th>
						<th><g:message code="pricing.label.line.min"/></th>	
						<th><g:message code="pricing.label.line.max"/></th>	
                         </tr>
		       </thead>
                     
		      <tbody>
		      <g:each var="rate" in="${new ProductChargeRateDAS().findByProductCharge(uDTO.id)}" status="idx">
		       <g:if test="${rate?.deleted==0}">
                         <tr>
                            
                             </td>
                           <td class="innerContent">
                                ${rate?.currency?.description?: ""}
                            </td>
                            <td class="innerContent">
                                <g:formatNumber number="${rate?.fixedAmount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                           <td class="innerContent">
                                <g:formatNumber number="${rate?.scaledAmount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                             </td>
                           <td class="innerContent">
                                 ${rate?.unitId?.description?:""}
                            </td>
                            
			    			 <td class="innerContent">
                                <g:formatNumber number="${rate?.rateDependee?.minBalance ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                             <td class="innerContent">
                                <g:formatNumber number="${rate?.rateDependee?.maxBalance ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                         </tr>
                         </g:if>
                         </g:each> 
			 </tbody>
			</table>
			</g:if>
			</g:if>
                 </tbody>
           </table>
        </g:if>
        <g:else>
            <em><g:message code="bundle.prompt.no.lines"/></em>
        </g:else>
    </div>          
                           
                        
                      
             


    <div class="btn-box">
        <div class="row">
        <sec:ifAllGranted roles="ORDER_21">
    <g:link action='edit' params="[itemId: pDTO?.item?.id?:rDTO?.item?.id?:uDTO?.item?.id]" class="submit edit"><span><g:message code="pricing.button.edit"/></span></g:link>
     </sec:ifAllGranted>
            <sec:ifAllGranted roles="ORDER_22">
                <g:link action='delete' params="[itemId: pDTO?.item?.id?:rDTO?.item?.id?:uDTO?.item?.id]" class="submit delete"><span><g:message code="pricing.button.delete"/></span></g:link>
            </sec:ifAllGranted>          
	   </div>
    </div>
</div>

