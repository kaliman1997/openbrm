
<%@page import="in.saralam.sbs.server.crm.db.TicketDetailsDTO"%>
<%@page import="in.saralam.sbs.server.crm.db.TicketStatusDAS"%>
<%@page import="java.lang.Integer"%>
<%@ page contentType="text/html;charset=UTF-8" %>


<div class="form-edit" style="height: 80px">

    <div class="heading">
        <strong>CRM Details</strong>
    </div>

     <div class="form-hold">
        <g:form name="form-search" action="list"  >
            <div class="btn-box">
     <g:link controller="CRM" action="editList"  class="submit add" style="padding-right: 200px"><span>Open Ticket</span></g:link>
     <input type="text" style="height: 20px;width: 110px;padding:3px 3px" name="uname" value="${params.uname }" size="30" />
    <input type="image" class="btn" src="${resource(dir:'images', file:'icon-search.gif')}" onclick="$('#form-search').submit();"/>
</div></g:form>
    </div>
</div>



<div class="form-edit"  align="center">
<g:set var="isNew" value="${tid == 0}"/>

    <div class="heading">
        <strong>
            <g:if test="${isNew}">
                New Ticket
            </g:if>
            <g:else>
                Edit Ticket
            </g:else>
        </strong>
    </div>
    
    <div class="form-hold">
    <g:form name="ud-edit-form" action="saveTicket"  >
    <div style="height: 20px"></div>
   <table width="540px"><tbody><tr><td width="80px">
	<label>Ticket Id : </label></td><td>
                            <g:if test="${!isNew}">
                                <span>
                                    ${tid}
                                </span>
                            </g:if>
                            <g:else>
                                <em><g:message code="prompt.id.new"/></em>
                            </g:else>

                            <g:hiddenField name="tid" value="${tid}"/>
   </td></tr><tr><td>
   	<label>User Name :</label></td><td>
   	 <g:textField id="un" class="field" name="userName" value="${params?.userName }" size="30"/>
   	 <button name="button" value="fetch"><span>Fetch</span></button>
   	<g:if test="${params.userId!=null}">
   	<g:if test="${params.userId!='0'}">
   	  <label style="padding-left: 10px"><sec:access url="/customer/show">
                        <g:remoteLink controller="customer" action="show" id="${params.userId }" before="register(this);" onSuccess="render(data, next);">
                           ${params.userId }
                        </g:remoteLink>
                    </sec:access></label></g:if></g:if><g:if test="${params.userId=='0'}">
                                <label style="color: red"><em>user not exist</em></label>
                            </g:if>
                    <div style="height: 5px"></div></td></tr>
                    <tr><td>
   	<label>Status :</label></td><td> <g:if test="${isNew}">
                                <span>
                                    New
                                </span>
                                <div style="height: 5px"></div>
                            </g:if>
                            <g:else><g:applyLayout name="form/select" >
   	 <g:select name="status" from="${new TicketStatusDAS().findAll()}"
            		              
                    		      optionKey="id" optionValue="type"
                          			value="${params.status}" /><div style="height: 5px"></div>
                          			</g:applyLayout></g:else></td></tr>
   	 <tr><td>
   	<label>Subject :</label></td><td>
   	 <g:textField class="field" name="subject" value="${params?.subject }" size="40"/><div style="height: 5px"></div></td></tr><tr><td valign="top">
   	<label >Details :</label></td><td><g:if test="${!isNew}">
   	<g:hiddenField name="oldDetails" value="${details}"/>
   	<label> <g:textArea name="td"  cols="50" rows="5" readonly="readonly">
   	 <g:each var="detail" in="${details}" status="idx">
   	 Date: <g:formatDate date="${detail.createdDate }" formatName="date.pretty.format" />
   	 ${detail.ticketBody }
   	 </g:each>
   	 </g:textArea></label>
   	 </g:if>
   	 <g:textArea name="details${tid }"  cols="50" rows="3" value="${params?.details }"/></td></tr>
   	 </tbody></table></g:form>
   	 <div style="height: 20px"></div>
    <div class="buttons">
                    <ul>
                        <li><a onclick="$('#ud-edit-form').submit();" class="submit save"><span><g:message code="button.save"/></span></a></li>
                        <li> <g:link controller="CRM" action="list" class="submit cancel" ><span>Cancel</span></g:link></li>
                        
                    </ul>
                </div>
   </div>
   
 	</div>

