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
package fr.paris.lutece.plugins.temporarycode.utils;


/**
 * 
 * Constants
 *
 */
public final class Constants
{
    /**
     * Private constructor
     */
    private Constants ( )
    {
        //Do nothing
    }
    
    //PATH
    public static final String PATH_TEMPORARYCODE = "temporarycode/";
    public static final String PATH_API = "api/";
    public static final String PATH_GENERATE = "generate";
    public static final String PATH_VERIFY = "verify";
    public static final String PATH_CONSUME = "consume";
    
    //HEADER PARAMETERS
    public static final String HEADER_PARAM_CONFIGURATION_ID = "configuration_id";
    public static final String HEADER_PARAM_USER_ID = "user_id";
    public static final String HEADER_PARAM_ACTION_NAME = "action_name";
    public static final String HEADER_PARAM_TEMPORARY_CODE = "temporary_code";
    
    //MESSAGE_ERROR
    public static final String CODE_ERROR_MANDADORY = "MANDATORY_HEADER_PARAMETER";
    public static final String MESSAGE_ERROR_USER = "The header parameter user_id is mandatory";
    public static final String MESSAGE_ERROR_TEMPORARY_CODE = "The header parameter temporary_code is mandatory";
    public static final String MESSAGE_ERROR_ACTION_NAME = "The header parameter action_name is mandatory";
    public static final String MESSAGE_ERROR_CONFIG_NOTFOUND = "Temporary code configuration not found";
    public static final String MESSAGE_ERROR_CONFIG_DEFAULT = "The default configuration not found please add to the header an configuration_id";
   
    
}
