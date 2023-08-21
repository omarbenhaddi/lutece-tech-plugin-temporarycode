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
 *"
 * License 1.0
 */

package fr.paris.lutece.plugins.temporarycode.business;

import fr.paris.lutece.test.LuteceTestCase;

import java.util.Optional;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * This is the business class test for the object TemporaryCode
 */
public class TemporaryCodeBusinessTest extends LuteceTestCase
{
    private static final String USERID1 = "UserId1";
    private static final String USERID2 = "UserId2";
    private static final String CODE1 = "Code1";
    private static final String CODE2 = "Code2";
	private static final Timestamp VALIDITYDATE1 = new Timestamp( 1000000l );
    private static final Timestamp VALIDITYDATE2 = new Timestamp( 2000000l );
	private static final boolean USED1 = true;
    private static final boolean USED2 = false;

	/**
	* test TemporaryCode
	*/
    public void testBusiness(  )
    {
        // Initialize an object
        TemporaryCode temporaryCode = new TemporaryCode();
        temporaryCode.setUserId( USERID1 );
        temporaryCode.setCode( CODE1 );
        temporaryCode.setValidityDate( VALIDITYDATE1 );
        temporaryCode.setUsed( USED1 );

        // Create test
        TemporaryCodeHome.create( temporaryCode );
        Optional<TemporaryCode> optTemporaryCodeStored = TemporaryCodeHome.findByPrimaryKey( temporaryCode.getId( ) );
        TemporaryCode temporaryCodeStored = optTemporaryCodeStored.orElse( new TemporaryCode ( ) );
        assertEquals( temporaryCodeStored.getUserId( ) , temporaryCode.getUserId( ) );
        assertEquals( temporaryCodeStored.getCode( ) , temporaryCode.getCode( ) );
        assertEquals( temporaryCodeStored.getValidityDate( ).toString() , temporaryCode.getValidityDate( ).toString( ) );
        assertEquals( temporaryCodeStored.getUsed( ) , temporaryCode.getUsed( ) );

        // Update test
        temporaryCode.setUserId( USERID2 );
        temporaryCode.setCode( CODE2 );
        temporaryCode.setValidityDate( VALIDITYDATE2 );
        temporaryCode.setUsed( USED2 );
        TemporaryCodeHome.update( temporaryCode );
        optTemporaryCodeStored = TemporaryCodeHome.findByPrimaryKey( temporaryCode.getId( ) );
        temporaryCodeStored = optTemporaryCodeStored.orElse( new TemporaryCode ( ) );
        
        assertEquals( temporaryCodeStored.getUserId( ) , temporaryCode.getUserId( ) );
        assertEquals( temporaryCodeStored.getCode( ) , temporaryCode.getCode( ) );
        assertEquals( temporaryCodeStored.getValidityDate( ).toString( ) , temporaryCode.getValidityDate( ).toString( ) );
        assertEquals( temporaryCodeStored.getUsed( ) , temporaryCode.getUsed( ) );

        // List test
        TemporaryCodeHome.getTemporaryCodesList( );

        // Delete test
        TemporaryCodeHome.remove( temporaryCode.getId( ) );
        optTemporaryCodeStored = TemporaryCodeHome.findByPrimaryKey( temporaryCode.getId( ) );
        temporaryCodeStored = optTemporaryCodeStored.orElse( null );
        assertNull( temporaryCodeStored );
        
    }
    
    
     

}