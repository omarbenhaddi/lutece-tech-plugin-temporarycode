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
package fr.paris.lutece.plugins.temporarycode.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import fr.paris.lutece.plugins.temporarycode.business.TemporaryCode;
import fr.paris.lutece.plugins.temporarycode.business.TemporaryCodeConfig;
import fr.paris.lutece.plugins.temporarycode.business.TemporaryCodeConfigHome;
import fr.paris.lutece.plugins.temporarycode.business.TemporaryCodeHome;
import fr.paris.lutece.plugins.temporarycode.utils.TemporaryCodeUtils;

/**
 * 
 * TemporaryCodeService
 *
 */
public class TemporaryCodeService
{
    
    private static TemporaryCodeService _temporaryCodeService;
    
    /**
     * Class instance
     * @return instance of temporaryCodeService
     */
    public static TemporaryCodeService getInstance( )
    {
        if ( _temporaryCodeService == null )
        {
            _temporaryCodeService = new TemporaryCodeService( );
        }
        
        return _temporaryCodeService;
    }
    
    /**
     * Generate a temporary code for an user
     * @param nIdConfig
     * @param userId
     * @return the instance created for user
     */
    public TemporaryCode generateTemporaryCode ( int nIdConfig, String userId, String strActionName )
    {
        Optional<TemporaryCodeConfig> config = TemporaryCodeConfigHome.findByPrimaryKey( nIdConfig );
        
        //Remove if exist an temporary code with same user id and actionName
        Optional<TemporaryCode> temporaryCodeExist = TemporaryCodeHome.findByUserIdAndActionName( userId, strActionName );
        if( temporaryCodeExist.isPresent( ) )
        {
            TemporaryCodeHome.remove( temporaryCodeExist.get( ).getId( ) );
        }
        
        if (  config.isPresent( ) )
        {
            TemporaryCode temporaryCode = new TemporaryCode( );
            temporaryCode.setUserId( userId );
            temporaryCode.setActionName( strActionName );
            temporaryCode.setUsed( false );
            temporaryCode.setCreatedDate( Timestamp.from( Instant.now( ) ) );
            temporaryCode.setCode( TemporaryCodeUtils.generateCode( config.get( ) ) );
            temporaryCode.setValidityDate( TemporaryCodeUtils.addMinuteToDate( config.get( ) ) );
          
            return TemporaryCodeHome.create( temporaryCode );
        }
        
        return null;
    }
    
    /**
     * 
     * @param strUserId
     * @param strTemporaryCode
     * @param strActionName
     * @return true if temparorary code is valid
     */
    public boolean isValidTemporaryCode ( String strUserId, String strTemporaryCode, String strActionName )
    {
        Optional<TemporaryCode> temporaryCode = TemporaryCodeHome.findByUserIdAndCode( strUserId, strTemporaryCode, strActionName );
        
        if( temporaryCode.isPresent( ) )
        {
            Timestamp ts = Timestamp.from( Instant.now( ) );
            return ts.before( temporaryCode.get( ).getValidityDate( ) ) && !temporaryCode.get( ).getUsed( );           
        }
        
        return false;
    }
    
    /**
     * Use temporary code
     * @param strUserId
     * @param strTemporaryCode
     * @param strActionName
     */
    public void useTemporaryCode ( String strUserId, String strTemporaryCode, String strActionName )
    {
        Optional<TemporaryCode> temporaryCode = TemporaryCodeHome.findByUserIdAndCode( strUserId, strTemporaryCode, strActionName );
        
        if( temporaryCode.isPresent( ) )
        {
            TemporaryCodeHome.remove( temporaryCode.get( ).getId( ) );
        }       
    }
}
