<?php
	//next example will insert new conversation
	$username = 'admin;10';
	$passwd = '123qwxe';
	
	$service_url = 'http://localhost:8080/openbrm/restapi/function/getCustomer';
	$url = urlencode($service_url);
	$curl = curl_init($service_url);
	$curl_post_data = array(
			'userId' => 10     
	);
	$json_payload = json_encode($curl_post_data);
	
	curl_setopt($curl, CURLOPT_USERPWD, $username . ":" . $passwd);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($curl, CURLOPT_POST, true);
	//curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
	curl_setopt($curl, CURLOPT_POSTFIELDS, $json_payload);
	
	curl_setopt($curl, CURLOPT_HTTPHEADER, array(                                                                          
		'Content-Type: application/json',                                                                                
		'Content-Length: ' . strlen($json_payload))                                                                       
	);   
	
	$curl_response = curl_exec($curl);
	//print_r($curl_response);
	$info = curl_getinfo($curl);
	$http_resp_code = $info['http_code'];
	echo "Http Response : $http_resp_code\n";
	if ($curl_response === false || $http_resp_code != 200) {	
		echo("error occured during curl exec. http response: $http_resp_code\n");
	}
	curl_close($curl);
	$decoded = json_decode($curl_response);
	if ($decoded->result == 'failed') {
		echo('error occured: ' . $decoded->message);
		exit;
	}

	var_export($decoded);
?>