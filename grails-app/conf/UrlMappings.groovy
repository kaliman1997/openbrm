class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?" {
			constraints {
				// apply constraints here
			}
		}
        /**
         * REST url mappings
         */
		 
        "/rest/users"(controller: "rest" , parseRequest: true) {
            action = [ GET: "getUser", POST: "createUser" ]
        }
		
		"/restapi/function/getCallerCompanyId"(controller: "restApi", parseRequest: true, action: "getCallerCompanyId", method: "POST", id:"getCallerCompanyId")
		"/restapi/function/getCustomer"(controller: "restApi", action: "getCustomer", parseRequest: true, method: "POST", id:"getCustomer")
        "/restapi/function/createCustomer"(controller: "restApi", action: "createCustomer", method: "POST", id:"createCustomer")
        "/restapi/function/updateCustomer"(controller: "restApi", action: "updateCustomer", method: "POST")
        "/restapi/function/getProduct"(controller: "restApi", action: "getProduct", method: "POST", id:"getProduct")
        "/restapi/function/getProductByCategory"(controller: "restApi", action: "geProductByCategory", method: "POST")
        "/restapi/function/getProductCategories"(controller: "restApi", action: "getProductCategories", method: "POST")
        "/restapi/function/createOrder"(controller: "restApi", action: "createOrder", method: "POST")
        "/restapi/function/getOrder"(controller: "restApi", action: "getOrder", method: "POST")
        "/restapi/function/updateOrder"(controller: "restApi", action: "updateOrder", method: "POST")

        "/" {
            controller = "home"
        }

        "404"(controller: 'errors', action: 'pageNotFound')

		"500"(controller: 'errors', action: 'handleErrors')
	}
}
