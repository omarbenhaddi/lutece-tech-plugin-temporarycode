/*
 * Copyright (c) 2002-2023, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
 	
 
package fr.paris.lutece.plugins.temporarycode.web;

import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;
import fr.paris.lutece.util.html.AbstractPaginator;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.temporarycode.business.EnumCharacterType;
import fr.paris.lutece.plugins.temporarycode.business.TemporaryCodeConfig;
import fr.paris.lutece.plugins.temporarycode.business.TemporaryCodeConfigHome;

/**
 * This class provides the user interface to manage TemporaryCodeConfig features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageTemporaryCodeConfigs.jsp", controllerPath = "jsp/admin/plugins/temporarycode/", right = "TEMPORARY_CODE_MANAGEMENT" )
public class TemporaryCodeConfigJspBean extends AbstractManageCodeJspBean <Integer, TemporaryCodeConfig>
{
    // Templates
    private static final String TEMPLATE_MANAGE_TEMPORARYCODECONFIGS = "/admin/plugins/temporarycode/manage_temporarycodeconfigs.html";
    private static final String TEMPLATE_CREATE_TEMPORARYCODECONFIG = "/admin/plugins/temporarycode/create_temporarycodeconfig.html";
    private static final String TEMPLATE_MODIFY_TEMPORARYCODECONFIG = "/admin/plugins/temporarycode/modify_temporarycodeconfig.html";

    // Parameters
    private static final String PARAMETER_ID_TEMPORARYCODECONFIG = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_TEMPORARYCODECONFIGS = "temporarycode.manage_temporarycodeconfigs.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_TEMPORARYCODECONFIG = "temporarycode.modify_temporarycodeconfig.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_TEMPORARYCODECONFIG = "temporarycode.create_temporarycodeconfig.pageTitle";

    // Markers
    private static final String MARK_TEMPORARYCODECONFIG_LIST = "temporarycodeconfig_list";
    private static final String MARK_TEMPORARYCODECONFIG = "temporarycodeconfig";
    private static final String MARK_CHARACTERTYPE_LIST = "charactertype_list";

    private static final String JSP_MANAGE_TEMPORARYCODECONFIGS = "jsp/admin/plugins/temporarycode/ManageTemporaryCodeConfigs.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_TEMPORARYCODECONFIG = "temporarycode.message.confirmRemoveTemporaryCodeConfig";
    private static final String MESSAGE_ERROR_REMOVE_TEMPORARYCODECONFIG = "temporarycode.message.errorRemoveTemporaryCodeConfig";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "temporarycode.model.entity.temporarycodeconfig.attribute.";

    // Views
    private static final String VIEW_MANAGE_TEMPORARYCODECONFIGS = "manageTemporaryCodeConfigs";
    private static final String VIEW_CREATE_TEMPORARYCODECONFIG = "createTemporaryCodeConfig";
    private static final String VIEW_MODIFY_TEMPORARYCODECONFIG = "modifyTemporaryCodeConfig";

    // Actions
    private static final String ACTION_CREATE_TEMPORARYCODECONFIG = "createTemporaryCodeConfig";
    private static final String ACTION_MODIFY_TEMPORARYCODECONFIG = "modifyTemporaryCodeConfig";
    private static final String ACTION_REMOVE_TEMPORARYCODECONFIG = "removeTemporaryCodeConfig";
    private static final String ACTION_CONFIRM_REMOVE_TEMPORARYCODECONFIG = "confirmRemoveTemporaryCodeConfig";

    // Infos
    private static final String INFO_TEMPORARYCODECONFIG_CREATED = "temporarycode.info.temporarycodeconfig.created";
    private static final String INFO_TEMPORARYCODECONFIG_UPDATED = "temporarycode.info.temporarycodeconfig.updated";
    private static final String INFO_TEMPORARYCODECONFIG_REMOVED = "temporarycode.info.temporarycodeconfig.removed";
    
    // Errors
    private static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found";
    
    // Session variable to store working values
    private TemporaryCodeConfig _temporarycodeconfig;
    private List<Integer> _listIdTemporaryCodeConfigs;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_TEMPORARYCODECONFIGS, defaultView = true )
    public String getManageTemporaryCodeConfigs( HttpServletRequest request )
    {
        _temporarycodeconfig = null;

        if ( request.getParameter( AbstractPaginator.PARAMETER_PAGE_INDEX) == null || _listIdTemporaryCodeConfigs.isEmpty( ) )
        {
        	_listIdTemporaryCodeConfigs = TemporaryCodeConfigHome.getIdTemporaryCodeConfigsList(  );
        }
        
        Map<String, Object> model = getPaginatedListModel( request, MARK_TEMPORARYCODECONFIG_LIST, _listIdTemporaryCodeConfigs, JSP_MANAGE_TEMPORARYCODECONFIGS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_TEMPORARYCODECONFIGS, TEMPLATE_MANAGE_TEMPORARYCODECONFIGS, model );
    }

	/**
     * Get Items from Ids list
     * @param listIds
     * @return the populated list of items corresponding to the id List
     */
	@Override
	List<TemporaryCodeConfig> getItemsFromIds( List<Integer> listIds ) 
	{
		List<TemporaryCodeConfig> listTemporaryCodeConfig = TemporaryCodeConfigHome.getTemporaryCodeConfigsListByIds( listIds );
		
		// keep original order
        return listTemporaryCodeConfig.stream()
                 .sorted(Comparator.comparingInt( notif -> listIds.indexOf( notif.getId())))
                 .collect(Collectors.toList());
	}
    
    /**
    * reset the _listIdTemporaryCodeConfigs list
    */
    public void resetListId( )
    {
    	_listIdTemporaryCodeConfigs = new ArrayList<>( );
    }

    /**
     * Returns the form to create a temporarycodeconfig
     *
     * @param request The Http request
     * @return the html code of the temporarycodeconfig form
     */
    @View( VIEW_CREATE_TEMPORARYCODECONFIG )
    public String getCreateTemporaryCodeConfig( HttpServletRequest request )
    {
        _temporarycodeconfig = ( _temporarycodeconfig != null ) ? _temporarycodeconfig : new TemporaryCodeConfig(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_TEMPORARYCODECONFIG, _temporarycodeconfig );
        model.put( MARK_CHARACTERTYPE_LIST, EnumCharacterType.getReferenceList( ) );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_TEMPORARYCODECONFIG ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_TEMPORARYCODECONFIG, TEMPLATE_CREATE_TEMPORARYCODECONFIG, model );
    }

    /**
     * Process the data capture form of a new temporarycodeconfig
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_TEMPORARYCODECONFIG )
    public String doCreateTemporaryCodeConfig( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _temporarycodeconfig, request, getLocale( ) );
        

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_TEMPORARYCODECONFIG ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _temporarycodeconfig, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_TEMPORARYCODECONFIG );
        }

        if( _temporarycodeconfig.isDefault( ) )
        {
            TemporaryCodeConfigHome.clearDefaultFlag( );
        }
        
        TemporaryCodeConfigHome.create( _temporarycodeconfig );
        addInfo( INFO_TEMPORARYCODECONFIG_CREATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_TEMPORARYCODECONFIGS );
    }

    /**
     * Manages the removal form of a temporarycodeconfig whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_TEMPORARYCODECONFIG )
    public String getConfirmRemoveTemporaryCodeConfig( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_TEMPORARYCODECONFIG ) );
        
        Optional<TemporaryCodeConfig> config = TemporaryCodeConfigHome.findByPrimaryKey( nId );
        String strMessageUrl;
        
        if( config.isPresent( ) && !config.get( ).isDefault( ) )
        {
            UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_TEMPORARYCODECONFIG ) );
            url.addParameter( PARAMETER_ID_TEMPORARYCODECONFIG, nId );

            strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_TEMPORARYCODECONFIG, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );
        }
        else
        {
            strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_REMOVE_TEMPORARYCODECONFIG, AdminMessage.TYPE_ERROR );
        }
        
        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a temporarycodeconfig
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage temporarycodeconfigs
     */
    @Action( ACTION_REMOVE_TEMPORARYCODECONFIG )
    public String doRemoveTemporaryCodeConfig( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_TEMPORARYCODECONFIG ) );
        
        
        TemporaryCodeConfigHome.remove( nId );
        addInfo( INFO_TEMPORARYCODECONFIG_REMOVED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_TEMPORARYCODECONFIGS );
    }

    /**
     * Returns the form to update info about a temporarycodeconfig
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_TEMPORARYCODECONFIG )
    public String getModifyTemporaryCodeConfig( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_TEMPORARYCODECONFIG ) );

        if ( _temporarycodeconfig == null || ( _temporarycodeconfig.getId(  ) != nId ) )
        {
            Optional<TemporaryCodeConfig> optTemporaryCodeConfig = TemporaryCodeConfigHome.findByPrimaryKey( nId );
            _temporarycodeconfig = optTemporaryCodeConfig.orElseThrow( ( ) -> new AppException(ERROR_RESOURCE_NOT_FOUND ) );
        }


        Map<String, Object> model = getModel(  );
        model.put( MARK_TEMPORARYCODECONFIG, _temporarycodeconfig );
        model.put( MARK_CHARACTERTYPE_LIST, EnumCharacterType.getReferenceList( ) );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_TEMPORARYCODECONFIG ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_TEMPORARYCODECONFIG, TEMPLATE_MODIFY_TEMPORARYCODECONFIG, model );
    }

    /**
     * Process the change form of a temporarycodeconfig
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_TEMPORARYCODECONFIG )
    public String doModifyTemporaryCodeConfig( HttpServletRequest request ) throws AccessDeniedException
    {   
        populate( _temporarycodeconfig, request, getLocale( ) );
		
		
        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_TEMPORARYCODECONFIG ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _temporarycodeconfig, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_TEMPORARYCODECONFIG, PARAMETER_ID_TEMPORARYCODECONFIG, _temporarycodeconfig.getId( ) );
        }
        
        if( _temporarycodeconfig.isDefault( ) )
        {
            TemporaryCodeConfigHome.clearDefaultFlag( );
        }

        TemporaryCodeConfigHome.update( _temporarycodeconfig );
        addInfo( INFO_TEMPORARYCODECONFIG_UPDATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_TEMPORARYCODECONFIGS );
    }
}
