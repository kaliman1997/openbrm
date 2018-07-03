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

package com.sapienter.jbilling.server.user;

import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.permisson.db.PermissionDTO;
import com.sapienter.jbilling.server.user.permisson.db.RoleDAS;
import com.sapienter.jbilling.server.user.permisson.db.RoleDTO;
import org.apache.log4j.Logger;

import java.util.Set;

/**
 * RoleBL
 *
 * @author Brian Cowdery
 * @since 03/06/11
 */
public class RoleBL {
    private static final Logger LOG = Logger.getLogger(RoleBL.class);

    private RoleDAS roleDas;
    private RoleDTO role;

    public RoleBL() {
        _init();
    }

    public RoleBL(RoleDTO role) {
        _init();
        this.role = role;
    }

    public RoleBL(Integer roleId) {
        _init();
        set(roleId);
    }

    private void _init() {
        this.roleDas = new RoleDAS();
    }

    public void set(Integer roleId) {
        this.role = roleDas.find(roleId);
    }

    public RoleDTO getEntity() {
        return role;
    }

    /**
     * Saves a new role to and sets the BL entity to the newly created role. This method does not
     * save the description or title of a permission. Use {@link #setDescription(Integer, String)} and
     * {@link #setTitle(Integer, String)} to set the international descriptions.
     *
     * @param role role to save
     * @return id of the new role
     */
    public Integer create(RoleDTO role) {
        if (role != null) {
            this.role = roleDas.save(role);
            roleDas.flush();
            return this.role.getId();
        }

        LOG.error("Cannot save a null RoleDTO!");
        return null;
    }

    /**
     * Updates this role's permissions with those of the given role. This method does not
     * update the description or title of a permission. Use {@link #setDescription(Integer, String)} and
     * {@link #setTitle(Integer, String)} to update the roles international descriptions.
     *
     * @param dto role with permissions
     */
    public void update(RoleDTO dto) {
        setPermissions(dto.getPermissions());
    }

    /**
     * Sets the granted permissions of this role to the given set.
     *
     * @param grantedPermissions list of granted permissions
     */
    public void setPermissions(Set<PermissionDTO> grantedPermissions) {
        if (role != null) {
            role.getPermissions().clear();
            role.getPermissions().addAll(grantedPermissions);

            this.role = roleDas.save(role);
            roleDas.flush();

        } else {
            LOG.error("Cannot update, RoleDTO not found or not set!");
        }
    }

    /**
     * Deletes this role.
     *
     * Any users that use this role as their primary will be left without a role. It's best to move user
     * out of this role before deleting to ensure that the user doesn't experience an interruption in
     * service (by having no role).
     */
    public void delete() {
        if (role != null) {
            role.getPermissions().clear();
            role.getBaseUsers().clear();

            roleDas.delete(role);
            roleDas.flush();

        } else {
            LOG.error("Cannot delete, RoleDTO not found or not set!");
        }
    }
    
    public void updateRoleType(int roleTypeId) {
    	
    	if (role != null) {
    		role.setRoleTypeId(roleTypeId);
    		roleDas.save(role);
    		 roleDas.flush();
    	} else {
    		LOG.error("Cannot delete, RoleDTO not found or not set!");
    	}
    }

    public void setDescription(Integer languageId, String description) {
        this.role.setDescription("description", languageId, description);
    }

    public void setTitle(Integer languageId, String title) {
        this.role.setDescription("title", languageId, title);
    }
}
