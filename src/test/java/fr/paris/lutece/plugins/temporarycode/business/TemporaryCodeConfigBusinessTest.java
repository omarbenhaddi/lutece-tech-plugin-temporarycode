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


/**
 * This is the business class test for the object TemporaryCodeConfig
 */
public class TemporaryCodeConfigBusinessTest extends LuteceTestCase
{
    private static final int CODELENGTH1 = 1;
    private static final int CODELENGTH2 = 2;
    private static final int VALIDITYTIME1 = 1;
    private static final int VALIDITYTIME2 = 2;
    private static final String CHARACTER1 = "Character1";
    private static final String CHARACTER2 = "Character2";

	/**
	* test TemporaryCodeConfig
	*/
    public void testBusiness(  )
    {
        // Initialize an object
        TemporaryCodeConfig temporaryCodeConfig = new TemporaryCodeConfig();
        temporaryCodeConfig.setCodeLength( CODELENGTH1 );
        temporaryCodeConfig.setValidityTime( VALIDITYTIME1 );
        temporaryCodeConfig.setCharacterType( CHARACTER1 );

        // Create test
        TemporaryCodeConfigHome.create( temporaryCodeConfig );
        Optional<TemporaryCodeConfig> optTemporaryCodeConfigStored = TemporaryCodeConfigHome.findByPrimaryKey( temporaryCodeConfig.getId( ) );
        TemporaryCodeConfig temporaryCodeConfigStored = optTemporaryCodeConfigStored.orElse( new TemporaryCodeConfig ( ) );
        assertEquals( temporaryCodeConfigStored.getCodeLength( ) , temporaryCodeConfig.getCodeLength( ) );
        assertEquals( temporaryCodeConfigStored.getValidityTime( ) , temporaryCodeConfig.getValidityTime( ) );
        assertEquals( temporaryCodeConfigStored.getCharacterType( ) , temporaryCodeConfig.getCharacterType( ) );

        // Update test
        temporaryCodeConfig.setCodeLength( CODELENGTH2 );
        temporaryCodeConfig.setValidityTime( VALIDITYTIME2 );
        temporaryCodeConfig.setCharacterType( CHARACTER2 );
        TemporaryCodeConfigHome.update( temporaryCodeConfig );
        optTemporaryCodeConfigStored = TemporaryCodeConfigHome.findByPrimaryKey( temporaryCodeConfig.getId( ) );
        temporaryCodeConfigStored = optTemporaryCodeConfigStored.orElse( new TemporaryCodeConfig ( ) );
        
        assertEquals( temporaryCodeConfigStored.getCodeLength( ) , temporaryCodeConfig.getCodeLength( ) );
        assertEquals( temporaryCodeConfigStored.getValidityTime( ) , temporaryCodeConfig.getValidityTime( ) );
        assertEquals( temporaryCodeConfigStored.getCharacterType( ) , temporaryCodeConfig.getCharacterType( ) );

        // List test
        TemporaryCodeConfigHome.getTemporaryCodeConfigsList( );

        // Delete test
        TemporaryCodeConfigHome.remove( temporaryCodeConfig.getId( ) );
        optTemporaryCodeConfigStored = TemporaryCodeConfigHome.findByPrimaryKey( temporaryCodeConfig.getId( ) );
        temporaryCodeConfigStored = optTemporaryCodeConfigStored.orElse( null );
        assertNull( temporaryCodeConfigStored );
        
    }
    
    
     

}