
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

<%@ page import="com.sapienter.jbilling.server.util.db.CurrencyDTO; com.sapienter.jbilling.common.Constants" %>

<%--
  Renders an OrderWS as a quick preview of the order being built. This view also allows
  individual order lines to be edited and removed from the order.

  @author Brian Cowdery
  @since 23-Jan-2011
--%>

<div id="review-box">

        <!-- bundle header -->
        <div class="header">
            <div class="column">
                <h1><g:message code="bundle.review.id" args="${[bundle?.id > 0 ? bundle.id : '']}"/></h1> 
            </div>

            <div class="column">
                <h2 class="right capitalize">
                    
                     <g:set var="bundleName" value="${bundle.bundleName}"/>
                                      
                </h2>

                <h3 class="right capitalize">
                   
                    <g:set var="activeSince" value="${formatDate(date: bundle.activeSince ?: bundle.createDate, formatName: 'date.pretty.format')}"/>
                    <g:set var="activeUntil" value="${formatDate(date: bundle.activeUntil, formatName: 'date.pretty.format')}"/>

                    <g:if test="${bundle.activeUntil}">
                        <g:message code="order.review.active.date.range" args="[activeSince, activeUntil]"/>
                    </g:if>
                    <g:else>
                        <g:message code="order.review.active.since" args="[activeSince]"/>
                    </g:else>
                </h3>
            </div>

            <div style="clear: both;"></div>
        </div>

        <hr/>

        <!-- list of order lines -->
        <ul id="review-pproducts">
           
            <g:each var="pproduct" status="index" in="${bundle?.packageProducts}">
               <g:if test="${pproduct.deleted == 0}"> 
                    <g:render template="packageProduct" model="[ pproduct: pproduct, index: index, user: user , bundle:bundle]"/>
               </g:if> 
            </g:each>
              
            <g:if test="${!bundle.packageProducts}">
                <li><em><g:message code="bundle.review.no.order.lines"/></em></li>
            </g:if>
	     
        </ul>

        <hr/>

                
    </div>

    <div class="btn-box buttons">
        <g:link class="submit save" action="edit" params="[_eventId: 'save']">
            <span><g:message code="button.save"/></span>
            
        </g:link>

        <g:link class="submit cancel" action="edit" params="[_eventId: 'cancel']">
            <span><g:message code="button.cancel"/></span>
        </g:link>
    </div>

    <script type="text/javascript">
        $('#review-pproducts li.pproduct').click(function() {
            var id = $(this).attr('id');
            $('#' + id).toggleClass('active');
            $('#' + id + '-editor').toggle('blind');
        });
    </script>

</div>
