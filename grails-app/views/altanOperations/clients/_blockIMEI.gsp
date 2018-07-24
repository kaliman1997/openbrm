<div class="column-hold">
	<div class="heading">
		<strong><g:message code="${id=='4_1'?'Bloquear':'Desbloquear'} IMEI" /></strong>
	</div>
	<div class="box">
		<g:form name="operation_form" controller="altanOperations"
			action="operationResult">
			<label>IMEI: </label>
			<g:textField name="imei" />
			<br />
		</g:form>
	</div>
	<div class="btn-box">
		<g:remoteLink class="cell double" action="operationResult" id='${id}'
			params="{imei:\$('#imei').val()}"
			before="register(this);" onSuccess="render(data, next);">
			<strong>
				<g:if test="${id=='4_1'}">
					Bloquear
				</g:if>
				<g:else>
					Desbloquear	
				</g:else>
			</strong>
		</g:remoteLink>
	</div>
</div>