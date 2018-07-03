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


public interface PermissionConstants {
    // permissions
    //  all these have to by on synch with the permisssion table    
    
    // if the user can create other users specifing the user type
    public static final Integer P_USER_CREATE_TYPE = new Integer(7);
    // if the user can create other users of the following types
    public static final Integer P_USER_CREATE_TYPE_ROOT = new Integer(8);
    public static final Integer P_USER_CREATE_TYPE_CLERK = new Integer(9);
    public static final Integer P_USER_CREATE_TYPE_PARTNER = new Integer(10);
    public static final Integer P_USER_CREATE_TYPE_CUSTOMER = new Integer(11);
    // which fields are viable/editable when editing a user
    public static final Integer P_USER_EDIT_CHANGE_ENTITY = new Integer(12);
    public static final Integer P_USER_EDIT_CHANGE_TYPE = new Integer(13);
    public static final Integer P_USER_EDIT_VIEW_TYPE = new Integer(14);
    public static final Integer P_USER_EDIT_CHANGE_USERNAME = new Integer(15);
    public static final Integer P_USER_EDIT_CHANGE_PASSWORD = new Integer(16);
    public static final Integer P_USER_EDIT_CHANGE_LANGUAGE = new Integer(17);
    public static final Integer P_USER_EDIT_VIEW_LANGUAGE = new Integer(18);
    public static final Integer P_USER_EDIT_CHANGE_STATUS = new Integer(20);
    public static final Integer P_USER_EDIT_VIEW_STATUS = new Integer(21);
    public static final Integer P_USER_EDIT_CHANGE_CURRENCY = new Integer(81);
    public static final Integer P_USER_EDIT_VIEW_CURRENCY = new Integer(82);
    public static final Integer P_USER_EDIT_VIEW_CC = new Integer(134);
    public static final Integer P_USER_EDIT_CHANGE_BLACKLIST = new Integer(145);
    // links to edit a user contact/cc/ach, plus other user edit options etc
    public static final Integer P_USER_EDIT_LINKS = new Integer(114);
    // initial page, link to craete new user
    public static final Integer P_USER_CREATE = new Integer(115);
    // if a user can edit an item (changing data) otherwise view only
    public static final Integer P_ITEM_EDIT = new Integer(34);
    // if a user can see all the left option of an order (g.invoice, suspend, etc).
    public static final Integer P_ORDER_LEFT_OPTIONS = new Integer(108);
    // if a user can delete invoices
    public static final Integer P_INVOICE_DELETE = new Integer(113);
    //  if a user can modify payments (delete/edit)
    public static final Integer P_PAYMENT_MODIFY = new Integer(114);
    //  if a user can modify pluggable tasks info
    public static final Integer P_TASK_MODIFY = new Integer(137);
    
    // roles
    // to be on synch with the role table
    public static final Integer ROLE_INTERNAL = new Integer(1);
    public static final Integer ROLE_ROOT = new Integer(2);
    public static final Integer ROLE_CLERK = new Integer(3);
    public static final Integer ROLE_PARTNER = new Integer(4);
    public static final Integer ROLE_CUSTOMER = new Integer(5);
}
