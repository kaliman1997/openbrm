<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Estado de la orden ${orderId} ${osr.status}" /></strong>
	</div>
	<div class="box">
		<div class="sub-box">
			<table class="dataTable" cellspacing="0" cellpadding="0">
				<tbody>
					<g:if test="${osr.status=='success'}">
						<tr>
							<td><g:message code="orden" /></td>
							<td class="value">
								${osr.orderId}
							</td>
						</tr>
						<tr>
							<td><g:message code="estado" /></td>
							<td class="value">
								${osr.orderStatus}
							</td>
						</tr>
						<tr>
							<td><g:message code="tipo" /></td>
							<td class="value">
								${osr.type}
							</td>
						</tr>
					</g:if>
					
					<g:else>
						<tr>
							<td><g:message code="Error al obtener estado" /></td>
						</tr>

					</g:else>
				</tbody>
			</table>
		</div>
	</div>
</div>