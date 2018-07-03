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
		      <li class="${pageProperty(name: 'page.menu.item') == 'destinationMap' ? 'active' : ''}">
                        <g:link controller="destinationMap" action="list">
                            <g:message code="open.rate.menu.destination.map"/>
                        </g:link>
                    </li>
		    <li class="${pageProperty(name: 'page.menu.item') == 'serviceMap' ? 'active' : ''}">
                        <g:link controller="serviceMap" action="list">
                            <g:message code="open.rate.menu.service.map"/>
                        </g:link>
                    </li>
					
			<li class="${pageProperty(name: 'page.menu.item') == 'exclusions' ? 'active' : ''}">
                        <g:link controller="exclusions" action="list">
                            <g:message code="open.rate.menu.exlusions"/>
                        </g:link>
                    </li>
            <li class="${pageProperty(name: 'page.menu.item') == 'rate' ? 'active' : ''}">
                        <g:link controller="rate" action="list">
                            <g:message code="open.rate.menu.rate.card"/>
                        </g:link>
                      </li>
			<li class="${pageProperty(name: 'page.menu.item') == 'priceModel' ? 'active' : ''}">
                        <g:link controller="priceModel" action="list">
                            <g:message code="open.rate.menu.price.model"/>
                        </g:link>
                      </li>
			<li class="${pageProperty(name: 'page.menu.item') == 'priceMap' ? 'active' : ''}">
                        <g:link controller="priceMap" action="list">
                            <g:message code="open.rate.menu.price.map"/>
                        </g:link>
                      </li>
			<li class="${pageProperty(name: 'page.menu.item') == 'rumMap' ? 'active' : ''}">
                        <g:link controller="rumMap" action="list">
                            <g:message code="open.rate.menu.rum.map"/>
                        </g:link>
                      </li>
			 <li class="${pageProperty(name: 'page.menu.item') == 'customerRates' ? 'active' : ''}">
                        <g:link controller="customerRates" action="list">
                            <g:message code="open.rate.menu.customer.rates"/>
                        </g:link>
                    </li>

		    <li class="${pageProperty(name: 'page.menu.item') == 'holidayMap' ? 'active' : ''}">
                        <g:link controller="holidayMap" action="list">
                            <g:message code="open.rate.menu.holiday.map"/>
                        </g:link>
                    </li>
		    <li class="${pageProperty(name: 'page.menu.item') == 'worldZoneMap' ? 'active' : ''}">
                        <g:link controller="worldZoneMap" action="list">
                            <g:message code="open.rate.menu.world.zone.map"/>
                        </g:link>
                    </li>
			<li class="${pageProperty(name: 'page.menu.item') == 'uploadCDRFile' ? 'active' : ''}">
                        <g:link controller="UploadCDRFile" action="list">
                            <g:message code="open.rate.menu.uploadcdr.file"/>
                        </g:link>
                    </li>		
			<li class="${pageProperty(name: 'page.menu.item') == 'convergentRate' ? 'active' : ''}">
                        <g:link controller="convergentRate" action="list">
                            <g:message code="open.rate.menu.convergent.rate.card"/>
                        </g:link>
                      </li>
        </ul>
