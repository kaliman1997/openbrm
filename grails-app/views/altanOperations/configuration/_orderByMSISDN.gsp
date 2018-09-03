<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Buscar Ordenes por MSISDN" /></strong>
	</div>
	<div class="box">
		<g:form name="operation_form" controller="altanOperations"
			action="operationResult">
			<label>MSISDN: </label>
			<g:textField class="field" name="MSISDN" />
			<br />
			</g:form>
	</div>
	<div class="btn-box">
		<g:remoteLink class="cell double" action="operationResult" id="3_2"
			params="{MSISDN:\$('#MSISDN').val()}"
			before="register(this);" onSuccess="render(data, next);">
			<strong>Buscar</strong>
		</g:remoteLink>
	</div>
</div>