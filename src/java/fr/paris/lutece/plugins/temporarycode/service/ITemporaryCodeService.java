/*
 * Copyright (c) 2002-2024, City of Paris
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

import fr.paris.lutece.plugins.temporarycode.business.TemporaryCode;


/**
 * 
 * ITemporaryCodeService
 *
 */
public interface ITemporaryCodeService {

	/**
	 * Generate a temporary code for an user
	 * @param nIdConfig
	 * @param userId
	 * @return the instance created for user
	 */
	TemporaryCode generateTemporaryCode(int nIdConfig, String userId, String strActionName, String strComplemtaryInfo);

	/**
	 * Generate a temporary code for an user
	 * @param nIdConfig
	 * @param userId
	 * @return the instance created for user
	 */
	TemporaryCode generateTemporaryCode(int nIdConfig, String userId, String strActionName);

	/**
	 * 
	 * @param strUserId
	 * @param strTemporaryCode
	 * @param strActionName
	 * @return true if temparorary code is valid
	 */
	boolean isValidTemporaryCode(String strUserId, String strTemporaryCode, String strActionName);

	/**
	 * return complementary information on the  Temporary code
	 * @param strUserId the user Id
	 * @param strTemporaryCode teh Temporary Code
	 * @param strActionName the ActionName
	 * @return complementary information on the  Temporary code
	 */
	String getComplementaryInfo(String strUserId, String strTemporaryCode, String strActionName);
	
	
	/**
     * Use temporary code
     * @param strUserId
     * @param strTemporaryCode
     * @param strActionName
     */
    public void useTemporaryCode ( String strUserId, String strTemporaryCode, String strActionName );
    
    /**
     * Get temporary code by temporaryCode and ActionName
     * @param strTemporaryCode teh Temporary Code
     * @param strActionName the ActionName
     * @return temporary code
     */
    TemporaryCode getTemporaryCode( String strTemporaryCode, String strActionName );

}