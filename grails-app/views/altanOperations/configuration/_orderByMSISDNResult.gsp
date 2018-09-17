<div class="column-hold">
	<div class="heading">
		<strong><g:message code="Ordenes para MSISDN ${msisdn}" /></strong>
	</div>
	<div class="box">
		<div class="sub-box">
			<table class="dataTable" cellspacing="3" cellpadding="3">
				<thead>
					<tr>
						<th>Fecha</th>
						<th>Operación</th>
						<th>Order Id</th>
					</tr>
				</thead>

				<tbody>
					<g:each var="order" in="${orders}">
						<tr>
							<td><g:formatDate format="yyyy-MM-dd HH:mm:ss"
									date="${order.date}" /></td>
							<td>
								${order.requestType}
							</td>
							<td>
								${order.orderId}
							</td>
						</tr>
					</g:each>
				</tbody>
			</table>
		</div>
	</div>
</div>