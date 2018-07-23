<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Perfil del número ${msisdn}" /></strong>
	</div>
	<div class="box">
		<div class="sub-box">
			<table class="dataTable" cellspacing="0" cellpadding="0">
				<tbody>
					<g:if test="${pr.status=='success'}">
						<tr>
							<td><g:message code="Id Subscriptor" /></td>
							<td class="value">
								${pr.idSubscriber}
							</td>
						</tr>
						<tr>
							<td><g:message code="IMSI" /></td>
							<td class="value">
								${pr.imsi}
							</td>
						</tr>
						<tr>
							<td><g:message code="ICC" /></td>
							<td class="value">
								${pr.icc}
							</td>
						</tr>
						<tr>
							<td><g:message code="Estado" /></td>
							<td class="value">
								${pr.subStatus}
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