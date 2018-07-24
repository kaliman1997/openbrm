<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Solicitar Token" /></strong>
	</div>
	<div class="box">
		<div class="sub-box">
			<table class="dataTable" cellspacing="0" cellpadding="0">
			
			
				<tbody>
					<g:if test="${oar.status=='success'}">
						<tr>
							<td><g:message code="Token" /></td>
							<td class="value">
								${oar.accessToken}
							</td>
						</tr>
						<tr>
							<td><g:message code="Fecha" /></td>
							<td class="value">
								${oar.issuedAt}
							</td>
						</tr>
						<tr>
							<td><g:message code="Expires" /></td>
							<td class="value">
								${oar.expiresIn}
							</td>
						</tr>						
					</g:if>
					<g:else>
						<tr>
							<td><g:message code="Error" /></td>
							<td class="value">
								${oar.errorCode}
							</td>
						</tr>
						<tr>
							<td><g:message code="Description" /></td>
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