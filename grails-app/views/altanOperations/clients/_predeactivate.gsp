<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Baja Temporal" /></strong>
	</div>
	<div class="box">
		<g:form name="operation_form" controller="altanOperations"
			action="operationResult">
			<label>MSISDN: </label>
			<g:textField name="msisdn" />
			<label>Fecha 'YYYYMMDD' </label>
			<g:textField name="scheduleDate" />			
			<br/>
		</g:form>
	</div>
	<div class="btn-box">
		<g:remoteLink class="cell double" action="operationResult" id="4_8"
			params="{msisdn:\$('#msisdn').val(), scheduleDate:\$('#scheduleDate').val()}"
			before="register(this);" onSuccess="render(data, next);">
			<strong>Enviar</strong>
		</g:remoteLink>
	</div>
</div>