
<%@ page import="com.sapienter.jbilling.server.item.db.ItemDTO;com.sapienter.jbilling.server.user.UserDTOEx; com.sapienter.jbilling.server.user.contact.db.ContactTypeDTO; com.sapienter.jbilling.server.user.db.CompanyDTO; com.sapienter.jbilling.server.user.permisson.db.RoleDTO; com.sapienter.jbilling.common.Constants; com.sapienter.jbilling.server.util.db.LanguageDTO ; com.sapienter.jbilling.server.item.ItemBL ;com.sapienter.jbilling.server.util.WebServicesSessionSpringBean "  %>



<g:set var="product" value="${ItemDTO.get(pproduct.productId)}"/>
<g:set var="quantityNumberFormat" value="${product?.hasDecimals ? 'money.format' : 'default.number.format'}"/>
<g:set var="editable" value="${index == params.int('newLineIndex')}"/>

<g:formRemote name="pproduct-${index}-update-form" url="[action: 'edit']" update="column2" method="GET">
    <g:hiddenField name="_eventId" value="updatePackageProduct"/>
    <g:hiddenField name="execution" value="${flowExecutionKey}"/>

    <li id="pproduct-${index}" class="pproduct ${editable ? 'active' : ''}">
        <span class="description">
            ${product.description}
        </span>
       
        <span class="qty-price">
            <g:set var="quantity" value="${formatNumber(number: pproduct.getQuantity(), formatName: quantityNumberFormat)}"/>
            <g:if test="${product?.percentage}">
                <g:set var="percentage" value="%${formatNumber(number: product.percentage)}"/>
                <g:message code="order.review.quantity.by.price" args="[quantity, percentage]"/>
            </g:if>
            <g:else>
                <g:set var="price" value="${formatNumber(number: new ItemBL().getPriceByCurrency(product, new WebServicesSessionSpringBean().getCallerCompanyId(), new WebServicesSessionSpringBean().getCallerCurrencyId()), type: 'currency', maxFractionDigits: 4)}"/>
                <g:message code="order.review.quantity.by.price" args="[quantity, price]"/>
            </g:else>
            <g:set var="priceTemp" value="${new ItemBL().getPriceByCurrency(product, new WebServicesSessionSpringBean().getCallerCompanyId(), new WebServicesSessionSpringBean().getCallerCurrencyId())}"/>    
        </span>
        <div style="clear: both;"></div>
    </li>

    <li id="pproduct-${index}-editor" class="editor ${editable ? 'open' : ''}">
        <div class="box">
            <div class="form-columns">

                <g:applyLayout name="form/input">
                    <content tag="label"><g:message code="bundle.label.quantity"/></content>
                    <content tag="label.for">pproduct-${index}.getQuantity</content>
                    <g:textField name="pproduct-${index}.getQuantity" class="field quantity" value="${formatNumber(number: pproduct.getQuantity() ?: BigDecimal.ONE, formatName: quantityNumberFormat)}"/>
                </g:applyLayout>
                
                
                                
                <g:set var="oneoff" value=""/>
                
                <div id="oneoff" class="box-cards ${oneoff ? 'box-cards-open' : ''}">
                    <div class="box-cards-title">
                        <a class="btn-open" href="#"><span><g:message code="prompt.bundle.oneoff"/></span></a>
                    </div>
                    <div class="box-card-hold">
                        <div class="form-columns">
                            <div class="column">
                            
                                       <g:applyLayout name="form/checkbox">
                        <content tag="label">
                            
                                <g:message code="bundle.label.line.use.item.oneoff"/>
                            
                                </content>
                        <content tag="label.for">pproduct-${index}</content>
                      <g:checkBox  name="oneoff" pproduct="${index}" class="cb check" value="1" checked="${pproduct.getOneTimeCbValue()}" />
                </g:applyLayout>
                            
                   <g:applyLayout name="form/input">
                        <content tag="label"><g:message code="bundle.label.line.price"/></content>
                        
                        <g:textField name="pproduct-${index}.price" class="field" value="${formatNumber(number:  pproduct.getOneTimePrice() ?: priceTemp, formatName: 'money.format',maxFractionDigits: 4) }" />
                    </g:applyLayout>
                    
                    <g:applyLayout name="form/input">
                        <content tag="label"><g:message code="bundle.label.line.discount"/></content>
                        <content tag="label.for">pproduct-${index}.discount</content>
                        <g:textField name="pproduct-${index}.discount" class="field price" value="${formatNumber(number: pproduct.getOneTimeDiscount() ?: BigDecimal.ZERO, formatName: 'money.format')}" />
                    </g:applyLayout>
                    
                 

                      <g:applyLayout name="form/select">
                	  <content tag="label"><g:message code="prompt.bundle.product.start"/></content>
                              <div class="inp-bg inp4">
                                <g:textField class="field" name="start" value="${pproduct.getOneTimeStartOffset()}"/>
                            </div>
                            <div class="select4">
                                <g:select name="startOffset" from="${periodUnits}"
                                          optionKey="id"
                                          optionValue="${{it.getDescription(session['language_id'])}}"
                                          value="${pproduct.getOneTimeStartOffsetUnit() ?:com.sapienter.jbilling.server.util.Constants.PERIOD_UNIT_DAY}"/>
                            </div>
            		</g:applyLayout>

					          
                     <g:applyLayout name="form/text">
                            <content tag="label"><g:message code="prompt.bundle.product.end"/></content>
                            <content tag="label.for">activeUntil</content>

                            <div class="inp-bg inp4">
                                <g:textField class="field" name="end" value="${pproduct.getOneTimeEndOffset()}"/>
                            </div>
                            <div class="select4">
                                <g:select name="endOffset" from="${periodUnits}"
                                          optionKey="id"
                                          optionValue="${{it.getDescription(session['language_id'])}}"
                                          value="${pproduct.getOneTimeEndOffsetUnit() ?: com.sapienter.jbilling.server.util.Constants.PERIOD_UNIT_DAY}"/>
                            </div>
                        </g:applyLayout>
                            
                              
                            </div>
                        </div>
                    </div>
                </div>     
                
                
                <g:set var="recurring" value=""/>
                
                <div id="recurring" class="box-cards ${recurring ? 'box-cards-open' : ''}">
                    <div class="box-cards-title">
                        <a class="btn-open" href="#"><span><g:message code="prompt.bundle.recurring"/></span></a>
                    </div>
                    <div class="box-card-hold">
                        <div class="form-columns">
                            <div class="column">
                            
                            <g:applyLayout name="form/checkbox">
                        <content tag="label">
                            
                                <g:message code="bundle.label.line.use.item.recurring"/>
                            
                                </content>
                        <content tag="label.for">pproduct-${index}</content>
                         <g:checkBox  name="recurring" pproduct="${index}" class="cb check" value="2" checked="${pproduct.getRecurringCbValue()}" />
                </g:applyLayout>
                            
                             <g:applyLayout name="form/input">
                        <content tag="label"><g:message code="bundle.label.line.price"/></content>
                        <content tag="label.for">pproduct-${index}.recurringPrice</content>
                        <g:textField name="pproduct-${index}.recurringPrice" class="field price" value="${formatNumber(number:  pproduct.getRecurringPrice() ?: priceTemp, formatName: 'money.format',maxFractionDigits: 4)}" />
                    </g:applyLayout>
                    
                    <g:applyLayout name="form/input">
                        <content tag="label"><g:message code="bundle.label.line.discount"/></content>
                        <content tag="label.for">pproduct-${index}.recurringDiscount</content>
                        <g:textField name="pproduct-${index}.recurringDiscount" class="field price" value="${formatNumber(number:  pproduct.getRecurringDiscount() ?: BigDecimal.ZERO, formatName: 'money.format')}" />
                    </g:applyLayout>
                    
                     
                          <g:applyLayout name="form/text">
                            <content tag="label"><g:message code="prompt.bundle.product.frequency"/></content>
                            <content tag="label.for">activeSince</content>

                            
                            <div class="select4">
                             <g:select name="frequency" from="${orderPeriods}"
                                optionKey="id" optionValue="${{it.getDescription(session['language_id'])}}"
                                  value="${pproduct?.frequency}"/>
                             </div>
                        </g:applyLayout>

                         <g:applyLayout name="form/text">
                            <content tag="label"><g:message code="prompt.bundle.product.billing.type"/></content>
                            <content tag="label.for">billingType</content>

                            
                            <div class="select4">
                             <g:select name="billingTypeId" from="${orderBillingTypes}"
                                optionKey="id" optionValue="${{it.getDescription(session['language_id'])}}"
                                  value="${pproduct?.billingType}"/>
                             </div>
                        </g:applyLayout>

			 

                         <g:applyLayout name="form/text">
                            <content tag="label"><g:message code="prompt.bundle.product.start"/></content>
                            <content tag="label.for">activeSince</content>

                            <div class="inp-bg inp4">
                                <g:textField class="field" name="recurringStartOffset" value="${pproduct.getRecurringStartOffset()}"/>
                            </div>
                            <div class="select4">
                                <g:select  name="recurringStartOffsetUnit" from="${periodUnits}"
                                          optionKey="id"
                                          optionValue="${{it.getDescription(session['language_id'])}}"
                                          value="${pproduct.getRecurringStartOffsetUnit() ?: com.sapienter.jbilling.server.util.Constants.PERIOD_UNIT_DAY}"/>
                            </div>
                        </g:applyLayout>

					          
                     <g:applyLayout name="form/text">
                            <content tag="label"><g:message code="prompt.bundle.product.end"/></content>
                            <content tag="label.for">activeUntil</content>

                            <div class="inp-bg inp4">
                                <g:textField class="field" name="recurringEndOffset" value="${pproduct.getRecurringEndOffset()}"/>
                            </div>
                            <div class="select4">
                                <g:select name="recurringEndOffsetUnit" from="${periodUnits}"
                                          optionKey="id"
                                          optionValue="${{it.getDescription(session['language_id'])}}"
                                          
                                          value="${pproduct.getRecurringEndOffsetUnit() ?:com.sapienter.jbilling.server.util.Constants.PERIOD_UNIT_DAY}"/>
                            </div>
                        </g:applyLayout>                         
                               
                            </div>
                        </div>
                    </div>
                </div>          
                    
                     <g:set var="Cancel" value=""/>
                
                <div id="cancel" class="box-cards ${cancel ? 'box-cards-open' : ''}">
                    <div class="box-cards-title">
                        <a class="btn-open" href="#"><span><g:message code="prompt.bundle.cancel"/></span></a>
                    </div>
                    <div class="box-card-hold">
                        <div class="form-columns">
                            <div class="column">
                            
                            <g:applyLayout name="form/checkbox">
                        <content tag="label">
                            
                                <g:message code="bundle.label.line.use.item.cancel"/>
                            
                                </content>
                        <content tag="label.for">pproduct-${index}</content>
			
                      <g:checkBox  name="cancel" pproduct="${index}" class="cb check" value="4" checked="${pproduct.getCancelCbValue()}" />
                </g:applyLayout>
                            
                             <g:applyLayout name="form/input">
                        <content tag="label"><g:message code="bundle.label.line.price"/></content>
                        <content tag="label.for">pproduct-${index}.cancelPrice</content>
                        <g:textField name="pproduct-${index}.cancelPrice" class="field price" value="${formatNumber(number:  pproduct.getCancelPrice() ?: priceTemp, formatName: 'money.format',maxFractionDigits: 4)}" />
                    </g:applyLayout>
                    
                    <g:applyLayout name="form/input">
                        <content tag="label"><g:message code="bundle.label.line.discount"/></content>
                        <content tag="label.for">pproduct-${index}.cancelDiscount</content>
                        <g:textField name="pproduct-${index}.cancelDiscount" class="field price" value="${formatNumber(number:  pproduct.getCancelDiscount() ?: BigDecimal.ZERO, formatName: 'money.format')}" />
                    </g:applyLayout>
                             
                     <g:applyLayout name="form/text">
                            <content tag="label"><g:message code="prompt.bundle.product.start"/></content>
                            <content tag="label.for">activeSince</content>

                            <div class="inp-bg inp4">
                                <g:textField class="field" name="cancelStartOffset" value="${pproduct.getCancelStartOffset()}"/>
                            </div>
                            <div class="select4">
                                <g:select name="cancelStartOffsetUnit" from="${periodUnits}"
                                          optionKey="id"
                                          optionValue="${{it.getDescription(session['language_id'])}}"
                                          value="${pproduct.getCancelStartOffsetUnit() ?: com.sapienter.jbilling.server.util.Constants.PERIOD_UNIT_DAY}"/>
                            </div>
                        </g:applyLayout>

					          
                     <g:applyLayout name="form/text">
                            <content tag="label"><g:message code="prompt.bundle.product.end"/></content>
                            <content tag="label.for">activeUntil</content>

                            <div class="inp-bg inp4">
                                <g:textField class="field" name="cancelEndOffset" value="${pproduct.getCancelEndOffset()}"/>
                            </div>
                            <div class="select4">
                                <g:select name="cancelEndOffsetUnit" from="${periodUnits}"
                                          optionKey="id"
                                          optionValue="${{it.getDescription(session['language_id'])}}"
                                          value="${pproduct.getCancelEndOffsetUnit() ?: com.sapienter.jbilling.server.util.Constants.PERIOD_UNIT_DAY}"/>
                            </div>
                        </g:applyLayout>
                            
                            </div>
                        </div>
                    </div>
                </div>          
                             
                      

                      
                                 
                <g:applyLayout name="form/checkbox">
                        <content tag="label">
                            <g:message code="order.label.line.use.item"/>
                       </content>
                        <content tag="label.for">pproduct-${index}</content>
                        <g:checkBox name="pproduct-${index}" pproduct="${index}" class="cb check" value="1" />

                        <script type="text/javascript">
                            $(function() {
                                $('#pproduct-${index}').change(function() {
                                    var pproduct = $(this).attr('pproduct');

                                    if ($(this).is(':checked')) {
                                        //$('#pproduct-' + pproduct + '\\.priceAsDecimal').attr('disabled', true);
                                        //$('#pproduct-' + pproduct + '\\.description').attr('disabled', true);
                                    } else {
                                        //$('#pproduct-' + pproduct + '\\.priceAsDecimal').attr('disabled', false);
                                        //$('#pproduct-' + pproduct + '\\.description').attr('disabled', false);
                                    }
                                }).change();
                            });
                        </script>
                </g:applyLayout>
                
                <g:hiddenField name="index" value="${index}"/>
            </div>
        </div>

        <div class="btn-box">
		    <a class="submit save" onclick="$('#pproduct-${index}-update-form').submit();"><span><g:message code="button.update"/></span></a>
            <g:remoteLink class="submit cancel" action="edit" params="[_eventId: 'removePackageProduct', index: index]" update="column2" method="GET">
                <span><g:message code="button.remove"/></span>
            </g:remoteLink>
        </div>
    </li>

</g:formRemote>