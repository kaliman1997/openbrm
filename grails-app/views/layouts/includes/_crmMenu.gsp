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

                <ul class="list">
		      <li class="${pageProperty(name: 'page.menu.item') == 'support' ? 'active' : ''}">
                        <g:link controller="CRM" action="list">
                            Tickets
                        </g:link>
                    </li>
		<!--    <li class="${pageProperty(name: 'page.menu.item') == 'quotes' ? 'active' : ''}">
                        <g:link controller="CRM"  action="list">
                           Quotes
                        </g:link>
                    </li> -->
                  <li class="${pageProperty(name: 'page.menu.item') == 'scheduler' ? 'active' : ''}">
                        <g:link controller="scheduler" action="list">
                            Scheduler
                        </g:link>
                    </li>
			 <li class="${pageProperty(name: 'page.menu.item') == 'Bulk Notification' ? 'active' : ''}">
                        <g:link controller="bulkNotification" action="list">
                            Bulk Notification
                        </g:link>
                    </li>
                                    </ul>