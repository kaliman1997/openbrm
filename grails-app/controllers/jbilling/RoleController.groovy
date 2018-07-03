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

package jbilling

import com.sapienter.jbilling.server.user.permisson.db.RoleDTO
import com.sapienter.jbilling.server.user.permisson.db.PermissionTypeDTO
import com.sapienter.jbilling.server.user.RoleBL
import com.sapienter.jbilling.server.user.permisson.db.PermissionDTO
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
//import grails.plugins.springsecurity.Secured
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import com.sapienter.jbilling.server.user.db.CompanyDAS;

/**
 * RoleController 
 *
 * @author Brian Cowdery
 * @since 02/06/11
 */
//@Secured(["MENU_99"])
class RoleController {

    def breadcrumbService

    def index = {
        redirect (action: 'list', params: params)
    }

    def private getRoleList(GrailsParameterMap params) {
        return RoleDTO.createCriteria().list() {
            order('id', 'asc')
        }
    }

    def list = {
        def roles = getRoleList(params)
        def selected = params.id ? RoleDTO.get(params.int('id')) : null

        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id, selected?.getTitle(session['language_id']))

        if (params.applyFilter) {
            render template: 'roles', model: [ roles: roles, selected: selected ]
        } else {
            render view: 'list', model: [ roles: roles, selected: selected ]
        }
    }

    def show = {
        def role = RoleDTO.get(params.int('id'))

        breadcrumbService.addBreadcrumb(controllerName, 'list', null, role.id, role.getTitle(session['language_id']))

        render template: 'show', model: [ selected: role ]
    }

    def edit = {
        def role = params.id ? RoleDTO.get(params.int('id')) : new RoleDTO()
        def permissionTypes = PermissionTypeDTO.list(order: 'asc')
		log.debug("permissions are: " +permissionTypes.size())
		
        def crumbName = params.id ? 'update' : 'create'
        def crumbDescription = params.id ? role.getTitle(session['language_id']) : null
        breadcrumbService.addBreadcrumb(controllerName, actionName, crumbName, params.int('id'), crumbDescription)

        [ role: role, permissionTypes: permissionTypes ]
    }

    def save = {
        def role = new RoleDTO();
	def webservice= new WebServicesSessionSpringBean();
	def  companyDAS=new CompanyDAS()
	int entityId= webservice.getCallerCompanyId()
	//role.setEntity(companyDAS.find(entityId))
        bindData(role, params, 'role')

        if (params.role.title) {
            List<PermissionDTO> allPermissions = PermissionDTO.list()
            params.permission.each { id, granted ->
                if (granted) {
                    role.permissions.add(allPermissions.find { it.id == id as Integer })
                }
            }
              
            def roleService = new RoleBL();

            // save or update
            if (!role.id || role.id == 0) {
                log.debug("saving new role ${role}")
                role.id = roleService.create(role)

                flash.message = 'role.created'
                flash.args = [role.id as String]

            } else {
                log.debug("updating role ${role.id}")

                roleService.set(role.id)
                roleService.update(role)

                flash.message = 'role.updated'
                flash.args = [role.id as String]
            }

            // set/update international descriptions
            roleService.setTitle(session['language_id'], params.role.title)
            roleService.setDescription(session['language_id'], params.role.description)
            chain action: 'list', params: [id: role.id]
        } else {
            flash.error = "role.error.title.empty"
            chain action: 'edit'
        }
    }

    def delete = {
        if (params.id) {
            new RoleBL(params.int('id')).delete()
            log.debug("Deleted role ${params.id}.")
        }

        flash.message = 'role.deleted'
        flash.args = [ params.id ]

        // render the partial role list
        params.applyFilter = true
        list()
    }
}
