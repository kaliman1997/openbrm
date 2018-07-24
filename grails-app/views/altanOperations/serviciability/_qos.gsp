<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Cobertura" /></strong>
	</div>
	<div class="box">
		<g:form name="operation_form" controller="altanOperations"
			action="operationResult">
			<label>Calle: </label>
			<g:textField class="field" name="calle" />
			<br />
			<label>No. Exterior: </label>
			<g:textField class="field" name="noExterior" />
			<br />
			<label>Colonia: </label>
			<g:textField class="field" name="colonia" />
			<br />
			<label>CP: </label>
			<g:textField class="field" name="cp" />
			<br />
			<label>Ciudad: </label>
			<g:textField class="field" name="ciudad" />
			<br />
			<label>Estado: </label>
			<g:textField class="field" name="estado" />
			<br />
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