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
		

		<g:uploadForm name="upload-batch-form" action="uploadBatch">
						<g:applyLayout name="form/text">
				<content tag="label"> <g:message code="Tipo de Operacion" /></content>
				<select name="operation">
					<option>activations</option>
					<option>purchasessupplementary</option>
					<option>changesprimary</option>
					<option>suspends</option></option>
					<option>resumes</option>
					<option>deactivates</option>
					<option>reactivates</option>
				</select>
			</g:applyLayout>
			<g:applyLayout name="form/text">
				<content tag="label"> <g:message code="Seleccione Archivo" /></content>
				<input type="file" name="batchFile" />
			</g:applyLayout>
				<g:remoteLink class="cell double" action="uploadBatch" id='${id}'
			params="{operation:\$('#operation').val(),batchFile:\$('#batchFile')}"
			update="main">
			<strong>
				<button>Subir</button>
			</strong>
		</g:remoteLink>
		</g:uploadForm>
		
		
		<%--
		<g:form name="upload-form" action="uploadBatch" method="post"
			enctype="multipart/form-data" target="hidden-upload-frame">
			<select name="operation">
				<option>activations</option>
				<option>suspends</option>
			</select>
			</br>
    File: <input type="file" name="assetFile" />
			<button type="submit">Upload</button>
		</g:form>

		<iframe id="hidden-upload-frame" name="hidden-upload-frame"
			style="display: none" onload="onUploadComplete"> </iframe>

		<script type="text/javascript">
			function onUploadComplete(e) {
				// Handle upload complete
				alert("upload complete");
				// Evaluate iframe content or fire another ajax call to get the details for the previously uploaded file
			}
		</script>--%>
		
		</div>



	<div id="batchResult"></div>

</div>

