%{--
  JBILLING CONFIDENTIAL
  _____________________

  [2003] - [2012] Enterprise jBilling Software Ltd.
  All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Enterprise jBilling Software.
  The intellectual and technical concepts contained
  herein are proprietary to Enterprise jBilling Software
  and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden.
  --}%

<%@ page import="in.saralam.sbs.server.voucher.db.VoucherDTO" %>
<html>
<head>
    <meta name="layout" content="panels" />
</head>
<body>

%{--show all vouchers details--}%
<sec:ifAllGranted roles="MENU_90">
    
	<content tag="column1">
        <g:render template="voucher" model="[vouchers: vouchers]"/>
    </content>
   
</sec:ifAllGranted>


</body>
</html>