<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Resultado ${id=='4_1'?'Bloqueo':'Desbloqueo'} IMEI ${imei}" /></strong>
	</div>
	<div class="box">
		<div class="sub-box">
			<table class="dataTable" cellspacing="0" cellpadding="0">
				<tbody>
					<g:if test="${ir.status=='success'}">
						<tr>
							<td><g:message code="IMEI" /></td>
							<td class="value">
								${ir.imei}
							</td>
						</tr>
						<tr>
							<td><g:message code="Fecha" /></td>
							<td class="value">
								${ir.effectiveDate}
							</td>
						</tr>
					</g:if>
					<g:else>
						<tr>
							<td><g:message code="Error" /></td>
							<td class="value">
								${ir.errorCode}
							</td>
						</tr>
						<tr>
							<td><g:message code="description" /></td>
							<td class="value">
								${ir.description}
							</td>
						</tr>

					</g:else>
				</tbody>
			</table>
		</div>
	</div>
</div>