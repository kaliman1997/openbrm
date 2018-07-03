/*
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
 */

package com.sapienter.jbilling.client.metafield

import com.sapienter.jbilling.server.metafields.db.MetaField
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import com.sapienter.jbilling.server.metafields.MetaFieldValueWS
import com.sapienter.jbilling.server.metafields.DataType
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.metaclass.BindDynamicMethod

/**
 * @author Alexander Aksenov
 * @since 12.10.11
 */
class MetaFieldUtils {

    private static def log = Logger.getLogger(this)

    static def List<MetaFieldValueWS> bindMetaFields(Collection<MetaField> metaFields, GrailsParameterMap params) {
        List<MetaFieldValueWS> fieldsArray = new LinkedList<MetaFieldValueWS>();
        metaFields.each{
            def fieldValue = it.createValue();
            bindData(fieldValue, params, "metaField_${it.id}")

            def metaFieldWS = new MetaFieldValueWS(fieldValue);

            if (metaFieldWS == null && it.isMandatory() && it.getDataType().equals(DataType.BOOLEAN)) {
                // FALSE for unselected checkbox
                fieldValue = it.createValue();
                fieldValue.setValue(false)
                metaFieldWS = new MetaFieldValueWS(fieldValue)
            }

            if (metaFieldWS != null) {
                fieldsArray << metaFieldWS;
            }
        };
        return fieldsArray;
    }


    private static def bindData(Object model, modelParams, String prefix) {
        def args = [ model, modelParams, [exclude:[], include:[]]]
        if (prefix) args << prefix

        new BindDynamicMethod().invoke(model, 'bind', (Object[]) args)
    }
}
