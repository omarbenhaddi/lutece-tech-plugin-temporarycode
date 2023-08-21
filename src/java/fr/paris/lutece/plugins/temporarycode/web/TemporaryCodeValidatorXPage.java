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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

import fr.paris.lutece.plugins.temporarycode.service.TemporaryCodeService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;

/**
 * 
 * TemporaryCodeValidatorXPage
 *
 */
@Controller( xpageName = "temporaryCodeValidator", pageTitleI18nKey = "temporarycode.pageTitle", pagePathI18nKey = "temporarycode.pagePathLabel" )
public class TemporaryCodeValidatorXPage extends MVCApplication
{

    /**
     * 
     */
    private static final long   serialVersionUID               = -1476813027037005360L;

    // TEMPLATE
    private static final String TEMPLATE_VIEW                  = "skin/plugins/temporarycode/temporary_code_validator.html";

    // VIEW
    private static final String VIEW_TEMPORARYCODE_VALIDATOR   = "viewTemporaryCode";

    // ACTION
    private static final String ACTION_TEMPORARYCODE_VALIDATOR = "doCheckTemporaryCode";

    // MARKS
    private static final String MARK_CONTENT                   = "content";

    // PARAMETERS
    private static final String PARAMETER_VALIDATOR_ID         = "validator_id";
    private static final String PARAMETER_TEMPORARY_CODE       = "temporary_code";

    // CONSTANTS
    private static final String ERROR_MESSAGE                  = "error_message";
    private static final String STATUS                         = "status";
    private static final String MESSAGE_EMPTY_CODE             = "temporarycode.temporarycode_validator.error.code.empty";
    private static final String MESSAGE_EXPIRED_CODE           = "temporarycode.temporarycode_validator.error.code.expired";
    private static final String MESSAGE_EMPTY_ID_VALIDATOR     = "temporarycode.temporarycode_validator.error.unknown.id_validator";
    private static final String MESSAGE_TREATMENT_KO           = "temporarycode.temporarycode_validator.error.specific_treatment.ko";

    @View( defaultView = true, value = VIEW_TEMPORARYCODE_VALIDATOR )
    public XPage getViewTemporaryCode( HttpServletRequest request )
    {
        String strIdValidator = request.getParameter( PARAMETER_VALIDATOR_ID );
        Map<String, Object> model = getModel( );

        if ( StringUtils.isNotEmpty( strIdValidator ) )
        {
            model.put( MARK_CONTENT, getTemporaryCodeValidator( strIdValidator ) );
        }

        return getXPage( TEMPLATE_VIEW, request.getLocale( ), model );
    }

    @SuppressWarnings( "unchecked" )
    @Action( ACTION_TEMPORARYCODE_VALIDATOR )
    public XPage doCheckTemporaryCode( HttpServletRequest request )
    {
        String strIdValidator = request.getParameter( PARAMETER_VALIDATOR_ID );
        String strTemporaryCode = request.getParameter( PARAMETER_TEMPORARY_CODE );

        LuteceUser user = getRegistredUser( request );

        JSONObject json = new JSONObject( );

        if ( StringUtils.isNotEmpty( strIdValidator ) && StringUtils.isNotEmpty( strTemporaryCode ) )
        {
            if ( TemporaryCodeService.getInstance( ).isValidTemporaryCode( user.getName( ), strTemporaryCode ) )
            {
                ITemporaryCodeValidator temporaryCodeValidator = getTemporaryCodeValidator( strIdValidator );
                if ( temporaryCodeValidator != null && temporaryCodeValidator.treatment( request, strTemporaryCode ) )
                {
                    TemporaryCodeService.getInstance( ).useTemporaryCode( user.getName( ), strTemporaryCode );
                    json.put( STATUS, HttpStatus.OK.name( ) );
                } else
                {
                    json.put( ERROR_MESSAGE, I18nService.getLocalizedString( MESSAGE_TREATMENT_KO, getLocale( request ) ) );
                }
            } else
            {
                json.put( ERROR_MESSAGE, I18nService.getLocalizedString( MESSAGE_EXPIRED_CODE, getLocale( request ) ) );
            }
        } else if ( StringUtils.isEmpty( strIdValidator ) )
        {
            json.put( ERROR_MESSAGE, I18nService.getLocalizedString( MESSAGE_EMPTY_ID_VALIDATOR, getLocale( request ) ) );
        } else if ( StringUtils.isEmpty( strTemporaryCode ) )
        {
            json.put( ERROR_MESSAGE, I18nService.getLocalizedString( MESSAGE_EMPTY_CODE, getLocale( request ) ) );
        }

        return responseJSON( json.toJSONString( ) );
    }

    /**
     * Get temporary code validator
     * 
     * @param strIdValidator
     * @return
     */
    private ITemporaryCodeValidator getTemporaryCodeValidator( String strIdValidator )
    {
        List<ITemporaryCodeValidator> temporaryCodeComponentList = SpringContextService.getBeansOfType( ITemporaryCodeValidator.class );

        if ( CollectionUtils.isNotEmpty( temporaryCodeComponentList ) )
        {
            for ( ITemporaryCodeValidator validator : temporaryCodeComponentList )
            {
                if ( validator.getValidatorId( ).equals( strIdValidator ) )
                {
                    return validator;
                }
            }
        }

        return null;
    }

}
