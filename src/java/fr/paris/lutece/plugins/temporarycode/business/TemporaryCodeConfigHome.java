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
 * This class provides instances management methods (create, find, ...) for TemporaryCodeConfig objects
 */
public final class TemporaryCodeConfigHome
{
    // Static variable pointed at the DAO instance
    private static ITemporaryCodeConfigDAO _dao = SpringContextService.getBean( "temporarycode.temporaryCodeConfigDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "temporarycode" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private TemporaryCodeConfigHome(  )
    {
    }

    /**
     * Create an instance of the temporaryCodeConfig class
     * @param temporaryCodeConfig The instance of the TemporaryCodeConfig which contains the informations to store
     * @return The  instance of temporaryCodeConfig which has been created with its primary key.
     */
    public static TemporaryCodeConfig create( TemporaryCodeConfig temporaryCodeConfig )
    {
        _dao.insert( temporaryCodeConfig, _plugin );

        return temporaryCodeConfig;
    }

    /**
     * Update of the temporaryCodeConfig which is specified in parameter
     * @param temporaryCodeConfig The instance of the TemporaryCodeConfig which contains the data to store
     * @return The instance of the  temporaryCodeConfig which has been updated
     */
    public static TemporaryCodeConfig update( TemporaryCodeConfig temporaryCodeConfig )
    {
        _dao.store( temporaryCodeConfig, _plugin );

        return temporaryCodeConfig;
    }

    /**
     * Remove the temporaryCodeConfig whose identifier is specified in parameter
     * @param nKey The temporaryCodeConfig Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }
    
    /**
     * Clear all default flag
     */
    public static void clearDefaultFlag ( )
    {
        _dao.clearDefaultFlag( _plugin );
    }

    /**
     * Returns an instance of a temporaryCodeConfig whose identifier is specified in parameter
     * @param nKey The temporaryCodeConfig primary key
     * @return an instance of TemporaryCodeConfig
     */
    public static Optional<TemporaryCodeConfig> findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
    
    /**
     * Returns an instance of a default temporaryCode config
     * @return The instance of default temporary code config
     */
    public static Optional<TemporaryCodeConfig> findDefaultConfig ( )
    {
        return _dao.loadDefaultConfig( _plugin );
    }

    /**
     * Load the data of all the temporaryCodeConfig objects and returns them as a list
     * @return the list which contains the data of all the temporaryCodeConfig objects
     */
    public static List<TemporaryCodeConfig> getTemporaryCodeConfigsList( )
    {
        return _dao.selectTemporaryCodeConfigsList( _plugin );
    }
    
    /**
     * Load the id of all the temporaryCodeConfig objects and returns them as a list
     * @return the list which contains the id of all the temporaryCodeConfig objects
     */
    public static List<Integer> getIdTemporaryCodeConfigsList( )
    {
        return _dao.selectIdTemporaryCodeConfigsList( _plugin );
    }
    
    /**
     * Load the data of all the temporaryCodeConfig objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the temporaryCodeConfig objects
     */
    public static ReferenceList getTemporaryCodeConfigsReferenceList( )
    {
        return _dao.selectTemporaryCodeConfigsReferenceList( _plugin );
    }
    
	
    /**
     * Load the data of all the avant objects and returns them as a list
     * @param listIds liste of ids
     * @return the list which contains the data of all the avant objects
     */
    public static List<TemporaryCodeConfig> getTemporaryCodeConfigsListByIds( List<Integer> listIds )
    {
        return _dao.selectTemporaryCodeConfigsListByIds( _plugin, listIds );
    }

}

