<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Resultado Cambio de ${id=='4_3'?'Oferta Primaria': 'SIM'}" /></strong>
	</div>
	<div class="box">
		<div class="sub-box">
			<table class="dataTable" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td><g:message code="MSISDN" /></td>
						<td class="value">
							${msisdn}
						</td>
					</tr>

					<g:if test="${ar.status=='success'}">
						<tr>
							<td><g:message code="Fecha Efectiva" /></td>
							<td class="value">
								${ar.effectiveDate}
							</td>
						</tr>
						<tr>
							<td><g:message code="No. Orden" /></td>
							<td class="value">
								${ar.orderId}
							</td>
						</tr>						
					</g:if>
					<g:else>
						<tr>
							<td><g:message code="Error" /></td>
							<td class="value">
								${ar.errorCode}
							</td>
						</tr>
						<tr>
							<td><g:message code="Description" /></td>
							<td class="value">
								${ar.description}
							</td>
						</tr>

					</g:else>
				</tbody>
			</table>
		</div>
	</div>
</div>