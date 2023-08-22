/*
 * Copyright (c) 2002-2023, Mairie de Paris
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
package fr.paris.lutece.plugins.temporarycode.rs;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.plugins.temporarycode.business.TemporaryCode;
import fr.paris.lutece.plugins.temporarycode.business.TemporaryCodeConfig;
import fr.paris.lutece.plugins.temporarycode.business.TemporaryCodeConfigHome;
import fr.paris.lutece.plugins.temporarycode.service.TemporaryCodeService;
import fr.paris.lutece.plugins.temporarycode.utils.Constants;
import fr.paris.lutece.plugins.temporarycode.utils.TemporaryCodeUtils;
import fr.paris.lutece.util.json.ErrorJsonResponse;
import fr.paris.lutece.util.json.JsonResponse;

/**
 * 
 * TemporaryCodeRest
 *
 */
@Path( RestConstants.BASE_PATH + Constants.PATH_API + Constants.PATH_TEMPORARYCODE )
public class TemporaryCodeRest
{
    
    @GET
    @Path( Constants.PATH_GENERATE )
    @Produces( MediaType.APPLICATION_JSON )
    public Response generateTemporaryCode( @Context HttpServletRequest request,
            @HeaderParam( Constants.HEADER_PARAM_CONFIGURATION_ID ) String configurationId,
            @HeaderParam( Constants.HEADER_PARAM_USER_ID ) String userId,
            @HeaderParam( Constants.HEADER_PARAM_ACTION_NAME ) String actionName )
    {

        TemporaryCode temporaryCode = null;
        Optional<TemporaryCodeConfig> config = StringUtils.isEmpty( configurationId ) ? TemporaryCodeConfigHome.findDefaultConfig( )
                : TemporaryCodeConfigHome.findByPrimaryKey( Integer.parseInt( configurationId ) );

        String strMessageError = controlGenerateTemporaryCode( userId, actionName, config );

        if ( StringUtils.isNotEmpty( strMessageError ) )
        {
            return Response.status( Response.Status.BAD_REQUEST ).entity( TemporaryCodeUtils.buildJsonResponse( new ErrorJsonResponse( strMessageError, Constants.CODE_ERROR_MANDADORY ) ) ).build( );
        }

        if ( config.isPresent( ) )
        {
            temporaryCode = TemporaryCodeService.getInstance( ).generateTemporaryCode( config.get( ).getId( ), userId, actionName );
        }

        return Response.status( Response.Status.CREATED ).entity( TemporaryCodeUtils.buildJsonResponse( new JsonResponse( temporaryCode ) ) ).build( );

    }

    private String controlGenerateTemporaryCode( String userId, String actionName, Optional<TemporaryCodeConfig> config )
    {
        String strMessageError = StringUtils.EMPTY;

        if ( !config.isPresent( ) )
        {
            strMessageError = Constants.MESSAGE_ERROR_CONFIG_NOTFOUND;
        }

        if ( StringUtils.isEmpty( userId ) )
        {
            strMessageError = Constants.MESSAGE_ERROR_USER;
        }

        if ( StringUtils.isEmpty( actionName ) )
        {
            strMessageError = Constants.MESSAGE_ERROR_ACTION_NAME;
        }
        return strMessageError;
    }

    @GET
    @Path( Constants.PATH_VERIFY )
    @Produces( MediaType.APPLICATION_JSON )
    public Response verifyTemporaryCode( @Context HttpServletRequest request,
            @HeaderParam( Constants.HEADER_PARAM_USER_ID ) String userId,
            @HeaderParam( Constants.HEADER_PARAM_TEMPORARY_CODE ) String temporaryCode,
            @HeaderParam( Constants.HEADER_PARAM_ACTION_NAME ) String actionName )
    {

        String strMessageError = controlVerifyTemporaryCode( userId, temporaryCode, actionName );

        if ( StringUtils.isNotEmpty( strMessageError ) )
        {
            return Response.status( Response.Status.BAD_REQUEST ).entity( TemporaryCodeUtils.buildJsonResponse( new ErrorJsonResponse( strMessageError, Constants.CODE_ERROR_MANDADORY ) ) ).build( );
        }

        boolean isValidTemporaryCode = TemporaryCodeService.getInstance( ).isValidTemporaryCode( userId, temporaryCode, actionName );

        return Response.status( Response.Status.OK ).entity( TemporaryCodeUtils.buildJsonResponse( new JsonResponse( isValidTemporaryCode ) ) ).build( );

    }

    private String controlVerifyTemporaryCode( String userId, String temporaryCode, String actionName )
    {
        String messageError = StringUtils.EMPTY;
        if ( StringUtils.isEmpty( userId ) )
        {
            messageError = Constants.MESSAGE_ERROR_USER;
        }

        if ( StringUtils.isEmpty( temporaryCode ) )
        {
            messageError = Constants.MESSAGE_ERROR_TEMPORARY_CODE;
        }

        if ( StringUtils.isEmpty( actionName ) )
        {
            messageError = Constants.MESSAGE_ERROR_ACTION_NAME;
        }

        return messageError;
    }

    @GET
    @Path( Constants.PATH_CONSUME )
    @Produces( MediaType.APPLICATION_JSON )
    public Response consumeTemporaryCode( @Context HttpServletRequest request,
            @HeaderParam( Constants.HEADER_PARAM_USER_ID ) String userId,
            @HeaderParam( Constants.HEADER_PARAM_TEMPORARY_CODE ) String temporaryCode,
            @HeaderParam( Constants.HEADER_PARAM_ACTION_NAME ) String actionName )
    {
        String strMessageError = controlConsumeTemporaryCode( userId, temporaryCode, actionName );

        if ( StringUtils.isNotEmpty( strMessageError ) )
        {
            return Response.status( Response.Status.BAD_REQUEST ).entity( TemporaryCodeUtils.buildJsonResponse( new ErrorJsonResponse( strMessageError, Constants.CODE_ERROR_MANDADORY ) ) ).build( );
        }

        TemporaryCodeService.getInstance( ).useTemporaryCode( userId, temporaryCode, actionName );

        return Response.ok( ).build( );
    }

    private String controlConsumeTemporaryCode( String userId, String temporaryCode, String actionName )
    {
        String strMessageError = StringUtils.EMPTY;

        if ( StringUtils.isEmpty( userId ) )
        {
            strMessageError = Constants.MESSAGE_ERROR_USER;
        }

        if ( StringUtils.isEmpty( temporaryCode ) )
        {
            strMessageError = Constants.MESSAGE_ERROR_TEMPORARY_CODE;
        }

        if ( StringUtils.isEmpty( actionName ) )
        {
            strMessageError = Constants.MESSAGE_ERROR_ACTION_NAME;
        }

        return strMessageError;
    }
}
