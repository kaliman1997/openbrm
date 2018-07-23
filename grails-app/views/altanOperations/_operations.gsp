<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>

%{-- jBilling - The Enterprise Open Source Billing System Copyright (C)
2003-2011 Enterprise jBilling Software Ltd. and Emiliano Conde This file
is part of jbilling. jbilling is free software: you can redistribute it
and/or modify it under the terms of the GNU Affero General Public
License as published by the Free Software Foundation, either version 3
of the License, or (at your option) any later version. jbilling is
distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
License for more details. You should have received a copy of the GNU
Affero General Public License along with jbilling. If not, see
<http: //www.gnu.org/licenses />
. --}%

<%--
  Altan Operations list.

  @author Arturo Ruiz
  @since  22-Jul-2018
--%>

<div class="table-box">
	<div class="table-scroll">
		<table id="altan-operations" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<th><g:message default="Operaciones Disponibles"
							code="avanzada.operations" /></th>
				</tr>
			</thead>
			<tbody>

				<g:if test="${id == 1}">
					<tr id="AccessToken"
						class="${selectedTypeId == "1" ? 'active' : ''}">
						<td><g:remoteLink class="cell double" action="show" id="1_1"
								before="register(this);" onSuccess="render(data, next);">
								<strong> ${StringEscapeUtils.escapeHtml("AccessToken")}
								</strong>
								<em></em>
							</g:remoteLink></td>
					</tr>
				</g:if>
				<g:elseif test="${id == 2}">
					<tr id="Serviciability"
						class="${selectedTypeId == "2" ? 'active' : ''}">
						<td><g:remoteLink class="cell double" action="show" id="2_1"
								before="register(this);" onSuccess="render(data, next);">
								<strong> ${StringEscapeUtils.escapeHtml("Calidad de Servicio")}
								</strong>
								<em></em>
							</g:remoteLink></td>
					</tr>
				</g:elseif>
				<g:elseif test="${id == 3}">
					<tr id="Configuration"
						class="${selectedTypeId == "3" ? 'active' : ''}">
						<td><g:remoteLink class="cell double" action="show" id="3_1"
								before="register(this);" onSuccess="render(data, next);">
								<strong> ${StringEscapeUtils.escapeHtml("Estado de una orden")}
								</strong>
								<em></em>
							</g:remoteLink></td>
					</tr>
				</g:elseif>
				<g:elseif test="${id == 4}">
					<tr id="bloquearIMEI"
						class="${selectedTypeId == "4" ? 'active' : ''}">
						<td><g:remoteLink class="cell double" action="show" id="4_1"
								before="register(this);" onSuccess="render(data, next);">
								<strong> ${StringEscapeUtils.escapeHtml("Bloquear IMEI")}
								</strong>
								<em></em>
							</g:remoteLink></td>
					</tr>
					<tr id="desbloquearIMEI"
						class="${selectedTypeId == "4" ? 'active' : ''}">
						<td><g:remoteLink class="cell double" action="show" id="4_2"
								before="register(this);" onSuccess="render(data, next);">
								<strong> ${StringEscapeUtils.escapeHtml("Desbloquear IMEI")}
								</strong>
								<em></em>
							</g:remoteLink></td>
					</tr>
					<tr id="cambioOfertaPrimaria"
						class="${selectedTypeId == "4" ? 'active' : ''}">
						<td><g:remoteLink class="cell double" action="show" id="4_3"
								before="register(this);" onSuccess="render(data, next);">
								<strong> ${StringEscapeUtils.escapeHtml("Cambio Oferta Primaria")}
								</strong>
								<em></em>
							</g:remoteLink></td>
					</tr>
					<tr id="cambioSIM" class="${selectedTypeId == "4" ? 'active' : ''}">
						<td><g:remoteLink class="cell double" action="show" id="4_4"
								before="register(this);" onSuccess="render(data, next);">
								<strong> ${StringEscapeUtils.escapeHtml("Cambio de SIM")}
								</strong>
								<em></em>
							</g:remoteLink></td>
					</tr>
					<tr id="consultarPerfil" class="${selectedTypeId == "4" ? 'active' : ''}">
						<td><g:remoteLink class="cell double" action="show" id="4_5"
								before="register(this);" onSuccess="render(data, next);">
								<strong> ${StringEscapeUtils.escapeHtml("Consultar Perfil")}
								</strong>
								<em></em>
							</g:remoteLink></td>
					</tr>					

				</g:elseif>
			</tbody>
		</table>
	</div>
</div>

<div class="btn-box">
	<div class="row"></div>
</div>



