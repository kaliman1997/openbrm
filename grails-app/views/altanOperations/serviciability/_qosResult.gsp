<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Cobertura" /></strong>
	</div>
	<div class="box">
		<div class="sub-box">
			<table class="dataTable" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td><g:message code="Ubicación" /></td>
						<td class="value">
							${location}
						</td>
					</tr>

					<g:if test="${coverage.status=='success'}">
						<tr>
							<td><g:message code="Cobertura" /></td>
							<td class="value">
								${coverage.result}
							</td>
						</tr>
					</g:if>
					<g:else>
						<tr>
							<td><g:message code="Error" /></td>
							<td class="value">
								${coverage.errorCode}
							</td>
						</tr>
						<tr>
							<td><g:message code="description" /></td>
							<td class="value">
								${coverage.description}
							</td>
						</tr>

					</g:else>
				</tbody>
			</table>
		</div>
	</div>
</div>