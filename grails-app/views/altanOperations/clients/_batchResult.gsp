<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Resultado Operación Batch" /></strong>
	</div>
	<div class="box">
		<div class="sub-box">
			<table class="dataTable" cellspacing="0" cellpadding="0">
				<tbody>
					<g:if test="${br.status=='success'}">
						<tr>
							<td><g:message code="Fecha" /></td>
							<td class="value">
								${br.effectiveDate}
							</td>
						</tr>
						<tr>
							<td><g:message code="Lineas" /></td>
							<td class="value">
								${br.lines}
							</td>
						</tr>
						<tr>
							<td><g:message code="Id Transacción" /></td>
							<td class="value">
								${br.transactionId}
							</td>
						</tr>
					</g:if>
					<g:else>
						<tr>
							<td><g:message code="Error" /></td>
							<td class="value">
								${pr.errorCode}
							</td>
						</tr>
						<tr>
							<td><g:message code="description" /></td>
							<td class="value">
								${pr.Description}
							</td>
						</tr>

					</g:else>
				</tbody>
			</table>
		</div>
	</div>
</div>