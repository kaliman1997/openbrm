<?php

        $options = array('cache_wsdl' => WSDL_CACHE_NONE, 'login' => "admin;10",
                        'password' => "123qwe",
                        );
        $client = new SoapClient("http://localhost:8080/openbrm/services/api?wsdl", $options);
        #$client = new SoapClient("http://208.78.220.119:8080/openbrm/services/apiTwo?wsdl", $options);

        if(!$client ) {

                echo "Unable to connect \n";
                exit;
        }

        //var_dump($client->__getFunctions());
        //var_dump($client->__getTypes());
		//exit;

        $user = 10;
         try {
                $parameters = array ('arg0' => $user);

                //$result = $client->getSubscriptionList($parameters);
                $result = $client->getUserWS($parameters);
                print_r($result);

        }catch(Exception $e) {
                echo 'Caught exception: ',  $e->getMessage(), "\n";

        }

        exit;
?>