<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Cobertura" /></strong>
	</div>
	<div class="box">
		<g:form name="operation_form" controller="altanOperations"
			action="operationResult">
			<label>No. de Orden: </label>
			<g:textField class="field" name="orderId" />
			<br />
			</g:form>
	</div>
	<div class="btn-box">
		<g:remoteLink class="cell double" action="operationResult" id="3_1"
			params="{orderId:\$('#orderId').val()}"
			before="register(this);" onSuccess="render(data, next);">
			<strong>Consultar</strong>
		</g:remoteLink>
	</div>
</div>