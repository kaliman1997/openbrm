 %{-- jBilling - The Enterprise Open Source Billing System Copyright (C)
2003-2011 Enterprise jBilling Software Ltd. and Emiliano Conde This file
is part of jbilling. jbilling is free software: you can redistribute it
and/or modify it under the terms of the GNU Affero General Public
License as published by the Free Software Foundation, either version 3
of the License, or (at your option) any later version. jbilling is
distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
License for more details. You should have received a copy of the GNU
Affero General Public License along with jbilling. If not, see
<http: //www.gnu.org/licenses />
. --}%

<%@ page import="com.sapienter.jbilling.server.item.db.AssetDTO"%>

<%--
  Template lets the user select a file with assets for upload

  @author Gerhard Maree
  @since  14-May-2013
--%>


<div class="column-hold">
	<div class="heading">
		<strong> <g:message code="Operaciones Batch" />
		</strong>
	</div>

	<div class="box">
		<div class="sub-box">

			<g:uploadForm name="upload-batch-form" url="[action: 'uploadAssets']">
				<g:applyLayout name="form/text">
					<content tag="label">
					<g:message code="Tipo de Operacion" /></content>
					<select name="operation">
						<option>activations</option>
						<option>suspends</option>
					</select>
				</g:applyLayout>
				<g:applyLayout name="form/text">
					<content tag="label">
					<g:message code="Seleccione Archivo" /></content>
					<input type="file" name="assetFile" />
				</g:applyLayout>
				
				<div class="btn-row">
					<br /> <a onclick="$('#upload-batch-form').submit();"
						class="submit save"><span><g:message
								code="button.upload" /></span></a>
				</div>
			</g:uploadForm>

		</div>
	</div>

</div>

