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
import java.util.List;

import fr.paris.lutece.plugins.temporarycode.business.TemporaryCode;
import fr.paris.lutece.plugins.temporarycode.business.TemporaryCodeHome;
import fr.paris.lutece.portal.service.daemon.Daemon;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * 
 * ClearExperidTemporaryCodeDaemon
 *
 */
public class ClearExpiredTemporaryCodeDaemon extends Daemon
{

    @Override
    public void run( )
    {
        List<TemporaryCode> listTemporaryCode = TemporaryCodeHome.getTemporaryCodesList( );
        
        for ( TemporaryCode temporaryCode : listTemporaryCode )
        {
            Timestamp ts = Timestamp.from( Instant.now( ) );
            
            //Remove expired temporary code
            if( ts.after( temporaryCode.getValidityDate( ) ) )
            {
                TemporaryCodeHome.remove( temporaryCode.getId( ) );
                
                //Listener after remove temporaryCode
                List<IRemoveTemporaryCodeListener> listListener = SpringContextService.getBeansOfType( IRemoveTemporaryCodeListener.class );
                
                if( listListener != null )
                {
                    listListener.forEach( l -> l.onRemove( temporaryCode, true ) );
                }
            }
        }
    }
    
}
