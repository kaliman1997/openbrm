/*
 * JBILLING CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise jBilling Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise jBilling Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise jBilling Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */
package com.sapienter.jbilling.common;

import java.util.Comparator;

import com.sapienter.jbilling.server.user.permisson.db.PermissionDTO;

/**
 * @author Emil
 */
public class PermissionIdComparator implements Comparator<PermissionDTO> {

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(PermissionDTO perA, PermissionDTO perB) {
        return new Integer(perA.getId()).compareTo(perB.getId());
    }

}
