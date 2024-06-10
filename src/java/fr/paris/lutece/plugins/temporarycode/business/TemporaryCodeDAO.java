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
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class provides Data Access methods for TemporaryCode objects
 */
public final class TemporaryCodeDAO implements ITemporaryCodeDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_temporary_code, user_id, code, action_name, created_date, validity_date, used,complementary_info  FROM temporary_code_generated WHERE id_temporary_code = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO temporary_code_generated ( user_id, code, action_name, created_date, validity_date, used,complementary_info ) VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM temporary_code_generated WHERE id_temporary_code = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE temporary_code_generated SET user_id = ?, code = ?, action_name = ?, created_date = ?, validity_date = ?, used = ?,complementary_info= ? WHERE id_temporary_code = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_temporary_code, user_id, code, action_name, created_date, validity_date, used,complementary_info FROM temporary_code_generated";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_temporary_code FROM temporary_code_generated";
    private static final String SQL_QUERY_SELECTALL_BY_IDS = "SELECT id_temporary_code, user_id, code, action_name, created_date, validity_date, used,complementary_info FROM temporary_code_generated WHERE id_temporary_code IN (  ";
    private static final String SQL_QUERY_SELECT_BY_USER_AND_CODE_AND_ACTON = "SELECT id_temporary_code, user_id, code, action_name, created_date, validity_date, used,complementary_info FROM temporary_code_generated WHERE user_id = ? AND code = ?  AND action_name = ?";
    private static final String SQL_QUERY_SELECT_BY_CODE_AND_ACTON = "SELECT id_temporary_code, user_id, code, action_name, created_date, validity_date, used,complementary_info FROM temporary_code_generated WHERE code = ?  AND action_name = ?";
    private static final String SQL_QUERY_SELECT_BY_USER_AND_ACTION_NAME = "SELECT id_temporary_code, user_id, code, action_name, created_date, validity_date, used,complementary_info FROM temporary_code_generated WHERE user_id = ? AND action_name = ?";

    
    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( TemporaryCode temporaryCode, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , temporaryCode.getUserId( ) );
            daoUtil.setString( nIndex++ , temporaryCode.getCode( ) );
            daoUtil.setString( nIndex++ , temporaryCode.getActionName( ) );           
            daoUtil.setTimestamp( nIndex++ , temporaryCode.getCreatedDate( ) );
            daoUtil.setTimestamp( nIndex++ , temporaryCode.getValidityDate( ) );
            daoUtil.setBoolean( nIndex++ , temporaryCode.getUsed( ) );
            daoUtil.setString( nIndex++ , temporaryCode.getComplementaryInfo() );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                temporaryCode.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<TemporaryCode> load( int nKey, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeQuery( );
	        TemporaryCode temporaryCode = null;
	
	        if ( daoUtil.next( ) )
	        {
	            temporaryCode = new TemporaryCode();
	            int nIndex = 1;
	            
	            temporaryCode.setId( daoUtil.getInt( nIndex++ ) );
			    temporaryCode.setUserId( daoUtil.getString( nIndex++ ) );
			    temporaryCode.setCode( daoUtil.getString( nIndex++ ) );
	            temporaryCode.setActionName( daoUtil.getString( nIndex++ ) );
			    temporaryCode.setCreatedDate( daoUtil.getTimestamp( nIndex++  ) );
			    temporaryCode.setValidityDate( daoUtil.getTimestamp( nIndex++ ) );
			    temporaryCode.setUsed( daoUtil.getBoolean( nIndex++ ) );
			    temporaryCode.setComplementaryInfo( daoUtil.getString( nIndex ) );
	        }
	
	        return Optional.ofNullable( temporaryCode );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( TemporaryCode temporaryCode, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
	        int nIndex = 1;
	        
        	daoUtil.setString( nIndex++ , temporaryCode.getUserId( ) );
        	daoUtil.setString( nIndex++ , temporaryCode.getCode( ) );
        	daoUtil.setString( nIndex++ , temporaryCode.getActionName( ) );
            daoUtil.setTimestamp( nIndex++ , temporaryCode.getCreatedDate( ) );
        	daoUtil.setTimestamp( nIndex++ , temporaryCode.getValidityDate( ) );
        	daoUtil.setBoolean( nIndex++ , temporaryCode.getUsed( ) );
        	daoUtil.setString( nIndex++ , temporaryCode.getComplementaryInfo() );
	        daoUtil.setInt( nIndex , temporaryCode.getId( ) );
	
	        daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<TemporaryCode> selectTemporaryCodesList( Plugin plugin )
    {
        List<TemporaryCode> temporaryCodeList = new ArrayList<>(  );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            TemporaryCode temporaryCode = new TemporaryCode(  );
	            int nIndex = 1;
	            
	            temporaryCode.setId( daoUtil.getInt( nIndex++ ) );
			    temporaryCode.setUserId( daoUtil.getString( nIndex++ ) );
			    temporaryCode.setCode( daoUtil.getString( nIndex++ ) );
			    temporaryCode.setActionName( daoUtil.getString( nIndex++ ) );
                temporaryCode.setCreatedDate( daoUtil.getTimestamp( nIndex++ ) );
			    temporaryCode.setValidityDate( daoUtil.getTimestamp( nIndex++ ) );
			    temporaryCode.setUsed( daoUtil.getBoolean( nIndex++ ) );
			    temporaryCode.setComplementaryInfo( daoUtil.getString( nIndex ) );
	
	            temporaryCodeList.add( temporaryCode );
	        }
	
	        return temporaryCodeList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdTemporaryCodesList( Plugin plugin )
    {
        List<Integer> temporaryCodeList = new ArrayList<>( );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            temporaryCodeList.add( daoUtil.getInt( 1 ) );
	        }
	
	        return temporaryCodeList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectTemporaryCodesReferenceList( Plugin plugin )
    {
        ReferenceList temporaryCodeList = new ReferenceList();
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            temporaryCodeList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
	        }
	
	        return temporaryCodeList;
    	}
    }
    
    /**
     * {@inheritDoc }
     */
	@Override
	public List<TemporaryCode> selectTemporaryCodesListByIds( Plugin plugin, List<Integer> listIds ) {
		List<TemporaryCode> temporaryCodeList = new ArrayList<>(  );
		
		StringBuilder builder = new StringBuilder( );

		if ( !listIds.isEmpty( ) )
		{
			for( int i = 0 ; i < listIds.size(); i++ ) {
			    builder.append( "?," );
			}
	
			String placeHolders =  builder.deleteCharAt( builder.length( ) -1 ).toString( );
			String stmt = SQL_QUERY_SELECTALL_BY_IDS + placeHolders + ")";
			
			
	        try ( DAOUtil daoUtil = new DAOUtil( stmt, plugin ) )
	        {
	        	int index = 1;
				for( Integer n : listIds ) {
					daoUtil.setInt(  index++, n ); 
				}
	        	
	        	daoUtil.executeQuery(  );
	        	while ( daoUtil.next(  ) )
		        {
		        	TemporaryCode temporaryCode = new TemporaryCode(  );
		            int nIndex = 1;
		            
		            temporaryCode.setId( daoUtil.getInt( nIndex++ ) );
				    temporaryCode.setUserId( daoUtil.getString( nIndex++ ) );
				    temporaryCode.setCode( daoUtil.getString( nIndex++ ) );
				    temporaryCode.setActionName( daoUtil.getString( nIndex++ ) );
				    temporaryCode.setCreatedDate( daoUtil.getTimestamp( nIndex++ ) );
				    temporaryCode.setValidityDate( daoUtil.getTimestamp( nIndex++ ) );
				    temporaryCode.setUsed( daoUtil.getBoolean( nIndex++ ) );
				    temporaryCode.setComplementaryInfo( daoUtil.getString( nIndex ) );
		            
		            temporaryCodeList.add( temporaryCode );
		        }
		
		        daoUtil.free( );
		        
	        }
	    }
		return temporaryCodeList;
		
	}

    @Override
    public Optional<TemporaryCode> loadByUserIdAndCode( String strUserId, String strCode, String strActionName, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_USER_AND_CODE_AND_ACTON, plugin ) )
        {
            daoUtil.setString( 1 , strUserId );
            daoUtil.setString( 2 , strCode );
            daoUtil.setString( 3 , strActionName );
            
            daoUtil.executeQuery( );
            TemporaryCode temporaryCode = null;
    
            if ( daoUtil.next( ) )
            {
                temporaryCode = new TemporaryCode();
                int nIndex = 1;
                
                temporaryCode.setId( daoUtil.getInt( nIndex++ ) );
                temporaryCode.setUserId( daoUtil.getString( nIndex++ ) );
                temporaryCode.setCode( daoUtil.getString( nIndex++ ) );
                temporaryCode.setActionName( daoUtil.getString( nIndex++ ) );
                temporaryCode.setCreatedDate( daoUtil.getTimestamp( nIndex++ ) );
                temporaryCode.setValidityDate( daoUtil.getTimestamp( nIndex++ ) );
                temporaryCode.setUsed( daoUtil.getBoolean( nIndex++ ) );
                temporaryCode.setComplementaryInfo( daoUtil.getString( nIndex ) );
            }
    
            return Optional.ofNullable( temporaryCode );
        }
    }
    
    @Override
    public Optional<TemporaryCode> loadByCodeAndActionName( String strCode, String strActionName, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_CODE_AND_ACTON, plugin ) )
        {
            daoUtil.setString( 1 , strCode );
            daoUtil.setString( 2 , strActionName );
            
            daoUtil.executeQuery( );
            TemporaryCode temporaryCode = null;
    
            if ( daoUtil.next( ) )
            {
                temporaryCode = new TemporaryCode();
                int nIndex = 1;
                
                temporaryCode.setId( daoUtil.getInt( nIndex++ ) );
                temporaryCode.setUserId( daoUtil.getString( nIndex++ ) );
                temporaryCode.setCode( daoUtil.getString( nIndex++ ) );
                temporaryCode.setActionName( daoUtil.getString( nIndex++ ) );
                temporaryCode.setCreatedDate( daoUtil.getTimestamp( nIndex++ ) );
                temporaryCode.setValidityDate( daoUtil.getTimestamp( nIndex++ ) );
                temporaryCode.setUsed( daoUtil.getBoolean( nIndex++ ) );
                temporaryCode.setComplementaryInfo( daoUtil.getString( nIndex ) );
            }
    
            return Optional.ofNullable( temporaryCode );
        }
    }

    @Override
    public Optional<TemporaryCode> loadByUserAndActionName( String strUserId, String strActionName, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_USER_AND_ACTION_NAME, plugin ) )
        {
            daoUtil.setString( 1 , strUserId );
            daoUtil.setString( 2 , strActionName );
            
            daoUtil.executeQuery( );
            TemporaryCode temporaryCode = null;
    
            if ( daoUtil.next( ) )
            {
                temporaryCode = new TemporaryCode();
                int nIndex = 1;
                
                temporaryCode.setId( daoUtil.getInt( nIndex++ ) );
                temporaryCode.setUserId( daoUtil.getString( nIndex++ ) );
                temporaryCode.setCode( daoUtil.getString( nIndex++ ) );
                temporaryCode.setActionName( daoUtil.getString( nIndex++ ) );
                temporaryCode.setCreatedDate( daoUtil.getTimestamp( nIndex++ ) );
                temporaryCode.setValidityDate( daoUtil.getTimestamp( nIndex++ ) );
                temporaryCode.setUsed( daoUtil.getBoolean( nIndex++ ) );
                temporaryCode.setComplementaryInfo( daoUtil.getString( nIndex ) );
            }
    
            return Optional.ofNullable( temporaryCode );
        }
    }
}
