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
package com.sapienter.jbilling.server.user.permisson.db;


import com.sapienter.jbilling.client.authentication.InitializingGrantedAuthority;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDescription;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@TableGenerator(
        name="role_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="role",
        allocationSize = 10
        )
public class RoleDTO extends AbstractDescription implements Serializable, InitializingGrantedAuthority {

    public static final String ROLE_AUTHORITY_PREFIX = "ROLE_";
    public static final Integer AUTHORITY_LANGUAGE_ID = 1; // authority values in english

    private int id;
    private CompanyDTO company;
    private Integer roleTypeId;
    private Set<UserDTO> baseUsers = new HashSet<UserDTO>(0);
    private Set<PermissionDTO> permissions = new HashSet<PermissionDTO>(0);

    private String authority;

    public RoleDTO() {
    }

    public RoleDTO(int id) {
        this.id = id;
    }

    public RoleDTO(int id, CompanyDTO company, int roleTypeId, Set<UserDTO> baseUsers, Set<PermissionDTO> permissions) {
        this.id = id;
        this.company = company;
        this.roleTypeId = roleTypeId;
        this.baseUsers = baseUsers;
        this.permissions = permissions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "role_GEN")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	@Column(name = "role_type_id", length = 10)
	public Integer getRoleTypeId() {
		return roleTypeId;
	}
	
	public void setRoleTypeId(Integer roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_map",
               joinColumns = {@JoinColumn(name = "role_id", updatable = false)},
               inverseJoinColumns = {@JoinColumn(name = "user_id", updatable = false)}
    )
    public Set<UserDTO> getBaseUsers() {
        return this.baseUsers;
    }

    public void setBaseUsers(Set<UserDTO> baseUsers) {
        this.baseUsers = baseUsers;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "permission_role_map",
               joinColumns = {@JoinColumn(name = "role_id", updatable = false)},
               inverseJoinColumns = {@JoinColumn(name = "permission_id", updatable = false)}
    )
    @OrderBy(clause = "permission_id")
    public Set<PermissionDTO> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<PermissionDTO> permissions) {
        this.permissions = permissions;
    }

    @Transient
    protected String getTable() {
        return Constants.TABLE_ROLE;
    }

    @Transient
    public String getTitle(Integer languageId) {
        return getDescription(languageId, "title");
    }

    /**
     * Initialize the authority value
     */
    public void initializeAuthority() {
        String title = getTitle(AUTHORITY_LANGUAGE_ID);
        if (title != null && !title.equals(""))
            authority = ROLE_AUTHORITY_PREFIX + title.toUpperCase().trim().replaceAll(" ", "_");
    }

    /**
     * Returns an authority string representing the granted role of a user. This
     * string is constructed of the role "title" in the format "ROLE_TITLE".
     *
     * Authority strings are in uppercase with all spaces replaced with underscores.
     *
     * e.g., "ROLE_ADMIN", "ROLE_CLERK", "ROLE_USER"
     * 
     * @return authority string
     */
    @Transient
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return getAuthority();
    }
}


