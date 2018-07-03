%{--
     jBilling - The Enterprise Open Source Billing System
   Copyright (C) 2003-2011 Enterprise jBilling Software Ltd. and Emiliano Conde

   This file is part of jbilling.
   
   jbilling is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   jbilling is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.
   
   You should have received a copy of the GNU Affero General Public License
   along with jbilling.  If not, see <http://www.gnu.org/licenses/>.
 
  --}%

<div id = "bundle-charges-dialog" class = "bg-lightbox" title = "<g:message code='bundle.charge.dialog.title'/>"
     style = "display:none;">
    <div class = "table-box">
        <table cellspacing="5" cellpadding="5">
            <thead>
            <tr>
                <th class = "left"><g:message code="bundle.label.description"/></th>
                <th class = "left"><g:message code="bundle.label.active.since"/></th>
                <th class = "left"><g:message code="bundle.label.active.until"/></th>
                <th class = "left"><g:message code="bundle.label.mbg.days"/></th>
                
            </tr>
            </thead>
            <tbody id = "bundle-charges">
            </tbody>
        </table>
    </div>
</div>
