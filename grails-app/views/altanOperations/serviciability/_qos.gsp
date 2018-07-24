<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Cobertura" /></strong>
	</div>
	<div class="box">
		<g:form name="operation_form" controller="altanOperations"
			action="operationResult">

			<table>
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
			</table>
		</g:form>
	</div>
	<div class="btn-box">
		<g:remoteLink class="cell double" action="operationResult" id="2_1"
			params="{calle:\$('#calle').val(),
			noExterior:\$('#noExterior').val(),
			colonia:\$('#colonia').val(),
			cp:\$('#cp').val(),
			ciudad:\$('#ciudad').val(),
			estado:\$('#estado').val()}"
			before="register(this);" onSuccess="render(data, next);">
			<strong>Consultar</strong>
		</g:remoteLink>
	</div>
</div>