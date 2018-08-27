<%@page import="org.xhtmlrenderer.css.parser.property.PrimitivePropertyBuilders.Src"%>
<div class="column-hold">
	<div class="heading">
		<strong><g:message code= "${operationDesc} del número ${msisdn}" /></strong>
	</div>
	<div class="box">
		<div class="sub-box">
			<table class="dataTable" cellspacing="0" cellpadding="0">
				<tbody>
					<g:if test="${sr.status=='success'}">
						<tr>
							<td><g:message code="MSISDN" /></td>
							<td class="value">
								${sr.msisdn}
							</td>
						</tr>
						<tr>
							<td><g:message code="Fecha Efectiva" /></td>
							<td class="value">
								${sr.effectiveDate}
							</td>
						</tr>
						<tr>
							<td><g:message code="Orden" /></td>
							<td class="value">
								${sr.orderId}
							</td>
						</tr>
					</g:if>
					<g:else>
						<tr>
							<td><g:message code="Error" /></td>
							<td class="value">
								${sr.errorCode}
							</td>
						</tr>
						<tr>
							<td><g:message code="description" /></td>
							<td class="value">
								${sr.description}
							</td>
						</tr>

					</g:else>
				</tbody>
			</table>
		</div>
	</div>
</div>