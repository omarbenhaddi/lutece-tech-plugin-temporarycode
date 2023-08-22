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

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * This is the business class for the object TemporaryCode
 */ 
public class TemporaryCode implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations 
    private int _nId;
    private String _strUserId;
    private String _strCode;
    private String _strActionName;
    private Timestamp _dateCreatedDate;
    private Timestamp _dateValidityDate;    
    private boolean _bUsed;

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
     * Returns the UserId
     * @return The UserId
     */
    public String getUserId( )
    {
        return _strUserId;
    }

    /**
     * Sets the UserId
     * @param strUserId The UserId
     */ 
    public void setUserId( String strUserId )
    {
        _strUserId = strUserId;
    }
    
    
    /**
     * Returns the Code
     * @return The Code
     */
    public String getCode( )
    {
        return _strCode;
    }

    /**
     * Sets the Code
     * @param strCode The Code
     */ 
    public void setCode( String strCode )
    {
        _strCode = strCode;
    }  
    
    /**
     * @return the _strActionName
     */
    public String getActionName( )
    {
        return _strActionName;
    }

    /**
     * @param strActionName the _strActionName to set
     */
    public void setActionName( String strActionName )
    {
        this._strActionName = strActionName;
    }

    /**
     * @return the _dateCreatedDate
     */
    public Timestamp getCreatedDate( )
    {
        return _dateCreatedDate;
    }

    /**
     * @param _dateCreatedDate the _dateCreatedDate to set
     */
    public void setCreatedDate( Timestamp dateCreatedDate )
    {
        this._dateCreatedDate = dateCreatedDate;
    }

    /**
     * Returns the ValidityDate
     * @return The ValidityDate
     */
    public Timestamp getValidityDate( )
    {
        return _dateValidityDate;
    }

    /**
     * Sets the ValidityDate
     * @param dateValidityDate The ValidityDate
     */ 
    public void setValidityDate( Timestamp dateValidityDate )
    {
        _dateValidityDate = dateValidityDate;
    }
    
    
    /**
     * Returns the Used
     * @return The Used
     */
    public boolean getUsed( )
    {
        return _bUsed;
    }

    /**
     * Sets the Used
     * @param bUsed The Used
     */ 
    public void setUsed( boolean bUsed )
    {
        _bUsed = bUsed;
    }
    
}
