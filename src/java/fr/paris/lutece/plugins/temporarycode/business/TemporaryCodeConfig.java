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

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
/**
 * This is the business class for the object TemporaryCodeConfig
 */ 
public class TemporaryCodeConfig implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations 
    private int _nId;
    
    private int _nCodeLength;
    
    private int _nValidityTime;
    
    @NotEmpty( message = "#i18n{temporarycode.validation.temporarycodeconfig.Character.notEmpty}" )
    private String _strCharacterType;
    
    private boolean _bDefault;

    /**
     * Returns the Id
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * @param nId The Id
     */ 
    public void setId( int nId )
    {
        _nId = nId;
    }
    
    /**
     * Returns the CodeLength
     * @return The CodeLength
     */
    public int getCodeLength( )
    {
        return _nCodeLength;
    }

    /**
     * Sets the CodeLength
     * @param nCodeLength The CodeLength
     */ 
    public void setCodeLength( int nCodeLength )
    {
        _nCodeLength = nCodeLength;
    }
    
    
    /**
     * Returns the ValidityTime
     * @return The ValidityTime
     */
    public int getValidityTime( )
    {
        return _nValidityTime;
    }

    /**
     * Sets the ValidityTime
     * @param nValidityTime The ValidityTime
     */ 
    public void setValidityTime( int nValidityTime )
    {
        _nValidityTime = nValidityTime;
    }
    
    
    /**
     * Returns the Character
     * @return The Character
     */
    public String getCharacterType( )
    {
        return _strCharacterType;
    }

    /**
     * Sets the Character
     * @param strCharacter The Character
     */ 
    public void setCharacterType( String character )
    {
        _strCharacterType = character;
    }

    /**
     * @return the _bDefault
     */
    public boolean isDefault( )
    {
        return _bDefault;
    }

    /**
     * @param bDefault the _bDefault to set
     */
    public void setDefault( boolean bDefault )
    {
        this._bDefault = bDefault;
    }
    
}
