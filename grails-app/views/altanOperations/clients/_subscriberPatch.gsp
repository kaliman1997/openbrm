<div class="column-hold">
	<div class="heading">
		<strong><g:message
				code="Cambio ${id=='4_3'?'De Oferta Primaria': 'De SIM'}" /></strong>
	</div>
	<div class="box">
		<g:form name="operation_form" controller="altanOperations"
			action="operationResult">

			<table>
				<tr>
					<td align="right"><label>MSISDN: </label></td>
					<td align="left"><g:textField name="msisdn" /></td>
				</tr>

				<g:if test="${id=='4_3'}">
					<tr>
						<td align="right"><label>Nuevo Plan: </label></td>
						<td align="left"><g:textField name="offeringId" /></td>
					</tr>

					<tr>
						<td align="right"><label>Calle: </label></td>
						<td align="left"><g:textField name="calle" /></td>
					</tr>
					<tr>
						<td align="right"><label>No. Exterior: </label></td>
						<td align="left"><g:textField class="field" name="noExterior" /></td>
					</tr>
					<tr>
						<td align="right"><label>Colonia: </label></td>
						<td align="left"><g:textField class="field" name="colonia" /></td>
					</tr>
					<tr>
						<td align="right"><label>CP: </label></td>
						<td align="left"><g:textField class="field" name="cp" /></td>
					</tr>
					<tr>
						<td align="right"><label>Ciudad: </label></td>
						<td align="left"><g:textField class="field" name="ciudad" /></td>
					</tr>
					<tr>
						<td align="right"><label>Estado: </label></td>
						<td align="left"><g:textField class="field" name="estado" /></td>
					</tr>
				</g:if>
				<g:else>
					<tr>
						<td align="right"><label>Nuevo ICC: </label></td>
						<td align="left"><g:textField class="field" name="icc" /></td>
					</tr>
				</g:else>
			</table>
		</g:form>
	</div>
	<div class="btn-box">
		<g:if test="${id=='4_3'}">
			<g:remoteLink class="cell double" action="operationResult" id="4_3"
				params="{msisdn:\$('#msisdn').val(),
			offeringId:\$('#offeringId').val(),
			calle:\$('#calle').val(),
			noExterior:\$('#noExterior').val(),
			colonia:\$('#colonia').val(),
			cp:\$('#cp').val(),
			ciudad:\$('#ciudad').val(),
			estado:\$('#estado').val()}"
				before="register(this);" onSuccess="render(data, next);">
				<strong>Enviar</strong>
			</g:remoteLink>
		</g:if>
		<g:else>
			<g:remoteLink class="cell double" action="operationResult" id="4_4"
				params="{msisdn:\$('#msisdn').val(),
			icc:\$('#icc').val()}"
				before="register(this);" onSuccess="render(data, next);">
				<strong>Enviar</strong>
			</g:remoteLink>
		</g:else>
	</div>
</div>