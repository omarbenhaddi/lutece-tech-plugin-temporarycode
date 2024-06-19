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


 package fr.paris.lutece.plugins.temporarycode.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;


import java.util.List;
import java.util.Optional;

/**
 * This class provides instances management methods (create, find, ...) for TemporaryCode objects
 */
public final class TemporaryCodeHome
{
    // Static variable pointed at the DAO instance
    private static ITemporaryCodeDAO _dao = SpringContextService.getBean( "temporarycode.temporaryCodeDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "temporarycode" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private TemporaryCodeHome(  )
    {
    }

    /**
     * Create an instance of the temporaryCode class
     * @param temporaryCode The instance of the TemporaryCode which contains the informations to store
     * @return The  instance of temporaryCode which has been created with its primary key.
     */
    public static TemporaryCode create( TemporaryCode temporaryCode )
    {
        _dao.insert( temporaryCode, _plugin );

        return temporaryCode;
    }

    /**
     * Update of the temporaryCode which is specified in parameter
     * @param temporaryCode The instance of the TemporaryCode which contains the data to store
     * @return The instance of the  temporaryCode which has been updated
     */
    public static TemporaryCode update( TemporaryCode temporaryCode )
    {
        _dao.store( temporaryCode, _plugin );

        return temporaryCode;
    }

    /**
     * Remove the temporaryCode whose identifier is specified in parameter
     * @param nKey The temporaryCode Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a temporaryCode whose identifier is specified in parameter
     * @param nKey The temporaryCode primary key
     * @return an instance of TemporaryCode
     */
    public static Optional<TemporaryCode> findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
    
    /**
     * Returns an instance of a temporaryCode by user id and code
     * @param strUserId the user id
     * @param strCode the code
     * @param strActionName the action name
     * @return an instance of TemporaryCode
     */
    public static Optional<TemporaryCode> findByUserIdAndCode( String strUserId, String strCode, String strActionName )
    {
        return _dao.loadByUserIdAndCode( strUserId, strCode, strActionName, _plugin );
    }
    
    /**
     * Returns an instance of a temporaryCode by user id and action name
     * @param strUserId the user id
     * @param strActionName the action name
     * @return an instance of TemporaryCode
     */
    public static Optional<TemporaryCode> findByUserIdAndActionName( String strUserId, String strActionName )
    {
        return _dao.loadByUserAndActionName( strUserId, strActionName, _plugin );
    }
    
    /**
     * Returns an instance of a temporaryCode by temporary code and action name
     * @param strCode the code
     * @param strActionName the action name
     * @return an instance of TemporaryCode
     */
    public static Optional<TemporaryCode> findByCodeAndActionName( String strCode, String strActionName )
    {
        return _dao.loadByCodeAndActionName( strCode, strActionName, _plugin );
    }
    

    
    /**
     * Load the data of all the temporaryCode objects and returns them as a list
     * @return the list which contains the data of all the temporaryCode objects
     */
    public static List<TemporaryCode> getTemporaryCodesList( )
    {
        return _dao.selectTemporaryCodesList( _plugin );
    }
    
    /**
     * Load the id of all the temporaryCode objects and returns them as a list
     * @return the list which contains the id of all the temporaryCode objects
     */
    public static List<Integer> getIdTemporaryCodesList( )
    {
        return _dao.selectIdTemporaryCodesList( _plugin );
    }
    
    /**
     * Load the data of all the temporaryCode objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the temporaryCode objects
     */
    public static ReferenceList getTemporaryCodesReferenceList( )
    {
        return _dao.selectTemporaryCodesReferenceList( _plugin );
    }
    
	
    /**
     * Load the data of all the avant objects and returns them as a list
     * @param listIds liste of ids
     * @return the list which contains the data of all the avant objects
     */
    public static List<TemporaryCode> getTemporaryCodesListByIds( List<Integer> listIds )
    {
        return _dao.selectTemporaryCodesListByIds( _plugin, listIds );
    }

}

