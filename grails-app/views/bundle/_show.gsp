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

<%@ page import="com.sapienter.jbilling.server.util.Util" %>
<%@ page import="com.sapienter.jbilling.client.util.Constants" %>
<%@ page import="com.sapienter.jbilling.server.item.db.ItemDTO; com.sapienter.jbilling.server.item.db.ItemDAS;com.sapienter.jbilling.server.process.db.PeriodUnitDTO;com.sapienter.jbilling.server.util.Constants"%>

<%--
  Shows details of a selected user.

  @author Brian Cowdery
  @since  23-Nov-2010
--%>

<style>
	.ui-widget-content .ui-state-error{
		background-color:white;
		background: none;
	}
	.row .inp-bg{
		display: table-caption;
	}
</style>
<div class="column-hold">
    <div class="heading">
        <strong><g:message code="bundle.label.details"/>&nbsp;<em>${dto?.id}</em></strong>
    </div>

    <!-- Order Details -->
    <div class="box">

        <table class="dataTable">
            <tr><td><g:message code="bundle.label.create.date"/>:</td>
                <td class="value">
                    <g:formatDate date="${dto?.createdDate}" formatName="date.pretty.format"/>
                </td>
            </tr>
            <tr><td><g:message code="bundle.label.active.since"/>:</td>
                <td class="value">
                    <g:formatDate date="${dto?.activeSince}" formatName="date.pretty.format"/>
                </td>
            </tr>
            <tr><td><g:message code="bundle.label.active.until"/>:</td>
                <td class="value">
                    <g:formatDate date="${dto?.activeUntil}" formatName="date.pretty.format"/>
                </td>
            </tr>
			
	    <tr><td><g:message code="bundle.label.mbg.days"/>:</td>
                <td class="value">
                    ${dto?.mbgDays}
                </td>
            </tr>

			
			<g:if test="${dto?.metaFields}">
                    <!-- empty spacer row -->
            <tr>
                        <td colspan="2"><br/></td>
                    
                   <td> <g:render template="/metaFields/metaFields" model="[metaFields: dto?.metaFields]"/>
					</td>
			</tr>
			   </g:if>
			
			
			
        </table>
    </div>

    <div class="heading">
        <strong><g:message code="bundle.label.products"/></strong>
    </div>

    <!-- Order Lines -->
    <div class="box">
        <g:if test="${dto?.packageProducts}">
            <table class="innerTable" >
                <thead class="innerHeader">
                    
                 </thead>
                 <tbody>
                           <g:each var="pproduct" in="${dto?.packageProducts}" status="idx">
                       <g:each var="price" in="${pproduct?.packagePrices}" status="idxp">
                         <g:each var="priceType" in="${price?.packagePriceType}" status="idxpt">
                          
                           
                        
                      
                         <g:if test="${(priceType?.id)==(Constants.ONE_TIME)}">
                          
                          <div class="heading" style="width:99%">
                         <strong><g:message code="bundle.label.One.Time"/></strong>
                          </div>
                             <table class="innerTable" style="width:99%"  >
                            <thead class="innerHeader">
                        <tr>
                        <th style="min-width: 75px;"><g:message code="bundle.label.line.item"/></th>
                      
                        <th><g:message code="bundle.label.line.qty"/></th>
                        <th><g:message code="bundle.label.line.price"/></th>
                         <th><g:message code="bundle.label.line.discount"/></th>
			 <th><g:message code="bundle.label.line.start.date"/></th>
			  <th><g:message code="bundle.label.line.end.date"/></th>
                         </tr>
		       </thead>
                     
		      <tbody>
                         <tr>
                            <td class="innerContent">
                                <sec:access url="/product/show">
                                   <g:remoteLink controller="product" action="show" id="${pproduct?.productId}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                        ${pproduct?.productId}
                                   </g:remoteLink>
                                </sec:access>
                                <sec:noAccess url="/product/show">
                                    ${pproduct?.productId}
                                </sec:noAccess>
                            </td>
                            
                            <td class="innerContent">
                                <g:formatNumber number="${pproduct.quantity ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                           <td class="innerContent">
                                <g:formatNumber number="${price?.amount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                            <td class="innerContent">
                                <g:formatNumber number="${price?.discount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
			     <td class="innerContent">
                             ${price?.startOffset}
                             ${new PeriodUnitDTO(price?.startOffsetUnit).getDescription(langauageId) } 
                            </td>
			  <td class="innerContent">
                        ${price?.endOffset}
                        ${new PeriodUnitDTO(price?.endOffsetUnit).getDescription(langauageId)}
                         </td>
                         </tr>
			 </tbody>
			</table>
			</g:if>
                           &nbsp;
                           &nbsp;
                     
                           <g:if test="${(priceType?.id)==(Constants.RECURRING)}">
                          <div class="heading" style="width:99%" >
                         <strong><g:message code="bundle.label.recurring"/></strong>
                          </div>
                             <table class="innerTable" style="width:99%"  >
                            <thead class="innerHeader">
                        <tr>
                        <th style="min-width: 75px;"><g:message code="bundle.label.line.item"/></th>
                         
                        <th><g:message code="bundle.label.line.qty"/></th>
                        <th><g:message code="bundle.label.line.price"/></th>
                        <th><g:message code="bundle.label.line.discount"/></th>
                         <th><g:message code="bundle.label.line.start.date"/></th>
			  <th><g:message code="bundle.label.line.end.date"/></th>
                         <!--<th><g:message code="bundle.label.line.total"/></th>-->
                       </thead>
                       
                     </tr>
		     <tbody>
                         <tr>
                            <td class="innerContent">
                                <sec:access url="/product/show">
                                   <g:remoteLink controller="product" action="show" id="${pproduct?.productId}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                        ${pproduct?.productId}
                                   </g:remoteLink>
                                </sec:access>
                                <sec:noAccess url="/product/show">
                                    ${pproduct?.productId}
                                </sec:noAccess>
                            
                            <td class="innerContent">
                                <g:formatNumber number="${pproduct.quantity ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                           <td class="innerContent">
                                <g:formatNumber number="${price?.amount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                            <td class="innerContent">
                                <g:formatNumber number="${price?.discount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                        <td class="innerContent">
                          ${price?.startOffset}
                          ${new PeriodUnitDTO(price?.startOffsetUnit).getDescription(langauageId) } 
                         </td>
			 <td class="innerContent">
                        ${price?.endOffset}
                        ${new PeriodUnitDTO(price?.endOffsetUnit).getDescription(langauageId)}
                         </td>
                      </tr>
                        </tbody>
			</table>
                       </g:if>
                        
                     
                          
                        &nbsp;
                           &nbsp;
                          <g:if test="${(priceType?.id)==(Constants.CANCEL)}">
                          <div class="heading" style="width:99%" >
                         <strong><g:message code="bundle.label.cancel"/></strong>
                          </div>
                             <table class="innerTable"  style="width:99%" >
                            <thead class="innerHeader">
                        <tr>
                        <th style="min-width: 75px;"><g:message code="bundle.label.line.item"/></th>
                        <th><g:message code="bundle.label.line.qty"/></th>
                        <th><g:message code="bundle.label.line.price"/></th>
                        <th><g:message code="bundle.label.line.discount"/></th>
			<th><g:message code="bundle.label.line.start.date"/></th>
			<th><g:message code="bundle.label.line.end.date"/></th>
                        </thead>
                       
                     </tr>
		     <tbody>
                         <tr>
                            <td class="innerContent">
                                <sec:access url="/product/show">
                                   <g:remoteLink controller="product" action="show" id="${pproduct?.productId}" params="['template': 'show']" before="register(this);" onSuccess="render(data, next);">
                                        ${pproduct?.productId}
                                   </g:remoteLink>
                                </sec:access>
                                <sec:noAccess url="/product/show">
                                    ${pproduct?.productId}
                                </sec:noAccess>
                           
                            <td class="innerContent">
                                <g:formatNumber number="${pproduct.quantity ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                           <td class="innerContent">
                                <g:formatNumber number="${price?.amount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                        <td class="innerContent">
                                <g:formatNumber number="${price?.discount ?: BigDecimal.ZERO}" formatName="decimal.format"/>
                            </td>
                      
		       <td class="innerContent">
                       ${price?.startOffset}
                        ${new PeriodUnitDTO(price?.startOffsetUnit).getDescription(langauageId) } 
                         </td>
			 <td class="innerContent">
                       ${price?.endOffset}
                        ${new PeriodUnitDTO(price?.endOffsetUnit).getDescription(langauageId)}
                         </td>
                          </tr>
                        </tbody>
                            </table>
                        </g:if>
                        &nbsp;
                           &nbsp;
                        


                         </g:each>  
                     </g:each>
                     </g:each>
                    

                 </tbody>
           </table>
        </g:if>
        <g:else>
            <em><g:message code="bundle.prompt.no.lines"/></em>
        </g:else>
    </div>


    <div class="btn-box">
        <div class="row">
            <a href="${createLink (controller: 'bundleBuilder', action: 'edit', params: [id: dto?.id])}" class="submit edit">
                    <span><g:message code="bundle.button.edit"/></span>
                </a>
            <g:link controller="bundle" action="purchaseBundle" params="[bundleId: dto?.id]" class="submit order"><span><g:message code="button.purchase.bundle"/></span></g:link>
        </div>
        <div class="row">
           <a onclick="showConfirm('deleteBundle-' + ${dto?.id});" class="submit delete">
                    <span><g:message code="bundle.button.delete"/></span>
           </a>
       </div>
    </div>
</div>

<g:render template="/confirm"
     model="[message: 'bundle.prompt.are.you.sure',
             controller: 'bundle',
             action: 'deleteBundle',
             id: dto?.id,
            ]"/>