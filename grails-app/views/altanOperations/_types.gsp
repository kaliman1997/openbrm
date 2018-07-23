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
  Report types list.

  @author Brian Cowdery
  @since  07-Mar-2011
--%>

<div class="table-box">
	<div class="table-scroll">
		<table id="altan-operations_types" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<th><g:message default="Tipo de Operación Disponibles"
							code="avanzada.operationsTypes" /></th>
				</tr>
			</thead>
			<tbody>

				<tr id="security"
					class="${selectedTypeId == "1" ? 'active' : ''}">
					<td><g:remoteLink class="cell double" action="operations" id="1"
							before="register(this);" onSuccess="render(data, next);">
							<strong> ${StringEscapeUtils.escapeHtml("Seguridad")}
							</strong>
							<em></em>
						</g:remoteLink></td>
				</tr>
				<tr id="serviceQuality"
					class="${selectedTypeId == "2" ? 'active' : ''}">
					<td><g:remoteLink class="cell double" action="operations" id="2"
							before="register(this);" onSuccess="render(data, next);">
							<strong> ${StringEscapeUtils.escapeHtml("Calidad de Servicio")}
							</strong>
							<em></em>
						</g:remoteLink></td>
				</tr>
				<tr id="configuration"
					class="${selectedTypeId == "3" ? 'active' : ''}">
					<td><g:remoteLink class="cell double" action="operations" id="3"
							before="register(this);" onSuccess="render(data, next);">
							<strong> ${StringEscapeUtils.escapeHtml("Configuración")}
							</strong>
							<em></em>
						</g:remoteLink></td>
				</tr>
				<tr id="clients"
					class="${selectedTypeId == "4" ? 'active' : ''}">
					<td><g:remoteLink class="cell double" action="operations" id="4"
							before="register(this);" onSuccess="render(data, next);">
							<strong> ${StringEscapeUtils.escapeHtml("Clientes")}
							</strong>
							<em></em>
						</g:remoteLink></td>
				</tr>
				<tr id="batchClients"
					class="${selectedTypeId == "5" ? 'active' : ''}">
					<td><g:remoteLink class="cell double" action="operations" id="5"
							before="register(this);" onSuccess="render(data, next);">
							<strong> ${StringEscapeUtils.escapeHtml("Clientes Batch")}
							</strong>
							<em></em>
						</g:remoteLink></td>
				</tr>				
			</tbody>
		</table>
	</div>
</div>

<div class="btn-box">
	<div class="row"></div>
</div>



