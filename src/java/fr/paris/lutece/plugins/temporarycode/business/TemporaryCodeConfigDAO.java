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
 * This class provides Data Access methods for TemporaryCodeConfig objects
 */
public final class TemporaryCodeConfigDAO implements ITemporaryCodeConfigDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_temporary_code_config, code_length, validity_time, character_type, default_temporary_code FROM temporary_code_config WHERE id_temporary_code_config = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO temporary_code_config ( code_length, validity_time, character_type, default_temporary_code ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM temporary_code_config WHERE id_temporary_code_config = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE temporary_code_config SET code_length = ?, validity_time = ?, character_type = ?, default_temporary_code = ? WHERE id_temporary_code_config = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_temporary_code_config, code_length, validity_time, character_type, default_temporary_code FROM temporary_code_config";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_temporary_code_config FROM temporary_code_config";
    private static final String SQL_QUERY_SELECTALL_BY_IDS = "SELECT id_temporary_code_config, code_length, validity_time, character_type, default_temporary_code FROM temporary_code_config WHERE id_temporary_code_config IN (  ";
    private static final String SQL_QUERY_SELECT_DEFAULT = "SELECT id_temporary_code_config, code_length, validity_time, character_type, default_temporary_code FROM temporary_code_config WHERE default_temporary_code = 1";    
    private static final String SQL_QUERY_CLEAR_DEFAULT_FLAG = "UPDATE temporary_code_config SET default_temporary_code=0";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( TemporaryCodeConfig temporaryCodeConfig, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setInt( nIndex++ , temporaryCodeConfig.getCodeLength( ) );
            daoUtil.setInt( nIndex++ , temporaryCodeConfig.getValidityTime( ) );
            daoUtil.setString( nIndex++ , temporaryCodeConfig.getCharacterType( ) );
            daoUtil.setBoolean( nIndex++, temporaryCodeConfig.isDefault( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                temporaryCodeConfig.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<TemporaryCodeConfig> load( int nKey, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeQuery( );
	        TemporaryCodeConfig temporaryCodeConfig = null;
	
	        if ( daoUtil.next( ) )
	        {
	            temporaryCodeConfig = new TemporaryCodeConfig();
	            int nIndex = 1;
	            
	            temporaryCodeConfig.setId( daoUtil.getInt( nIndex++ ) );
			    temporaryCodeConfig.setCodeLength( daoUtil.getInt( nIndex++ ) );
			    temporaryCodeConfig.setValidityTime( daoUtil.getInt( nIndex++ ) );
			    temporaryCodeConfig.setCharacterType( daoUtil.getString( nIndex++ ) );
			    temporaryCodeConfig.setDefault( daoUtil.getBoolean( nIndex++ ) );
	        }
	
	        return Optional.ofNullable( temporaryCodeConfig );
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
    public void store( TemporaryCodeConfig temporaryCodeConfig, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
	        int nIndex = 1;
	        
        	daoUtil.setInt( nIndex++ , temporaryCodeConfig.getCodeLength( ) );
        	daoUtil.setInt( nIndex++ , temporaryCodeConfig.getValidityTime( ) );
        	daoUtil.setString( nIndex++ , temporaryCodeConfig.getCharacterType( ) );
        	daoUtil.setBoolean( nIndex++, temporaryCodeConfig.isDefault( ) );
	        daoUtil.setInt( nIndex , temporaryCodeConfig.getId( ) );
	
	        daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<TemporaryCodeConfig> selectTemporaryCodeConfigsList( Plugin plugin )
    {
        List<TemporaryCodeConfig> temporaryCodeConfigList = new ArrayList<>(  );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            TemporaryCodeConfig temporaryCodeConfig = new TemporaryCodeConfig(  );
	            int nIndex = 1;
	            
	            temporaryCodeConfig.setId( daoUtil.getInt( nIndex++ ) );
			    temporaryCodeConfig.setCodeLength( daoUtil.getInt( nIndex++ ) );
			    temporaryCodeConfig.setValidityTime( daoUtil.getInt( nIndex++ ) );
			    temporaryCodeConfig.setCharacterType( daoUtil.getString( nIndex++ ) );
			    temporaryCodeConfig.setDefault( daoUtil.getBoolean( nIndex ) );
			    
	            temporaryCodeConfigList.add( temporaryCodeConfig );
	        }
	
	        return temporaryCodeConfigList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdTemporaryCodeConfigsList( Plugin plugin )
    {
        List<Integer> temporaryCodeConfigList = new ArrayList<>( );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            temporaryCodeConfigList.add( daoUtil.getInt( 1 ) );
	        }
	
	        return temporaryCodeConfigList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectTemporaryCodeConfigsReferenceList( Plugin plugin )
    {
        ReferenceList temporaryCodeConfigList = new ReferenceList();
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            temporaryCodeConfigList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
	        }
	
	        return temporaryCodeConfigList;
    	}
    }
    
    /**
     * {@inheritDoc }
     */
	@Override
	public List<TemporaryCodeConfig> selectTemporaryCodeConfigsListByIds( Plugin plugin, List<Integer> listIds ) {
		List<TemporaryCodeConfig> temporaryCodeConfigList = new ArrayList<>(  );
		
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
		        	TemporaryCodeConfig temporaryCodeConfig = new TemporaryCodeConfig(  );
		            int nIndex = 1;
		            
		            temporaryCodeConfig.setId( daoUtil.getInt( nIndex++ ) );
				    temporaryCodeConfig.setCodeLength( daoUtil.getInt( nIndex++ ) );
				    temporaryCodeConfig.setValidityTime( daoUtil.getInt( nIndex++ ) );
				    temporaryCodeConfig.setCharacterType( daoUtil.getString( nIndex++ ) );
				    temporaryCodeConfig.setDefault( daoUtil.getBoolean( nIndex ) );
				    
		            temporaryCodeConfigList.add( temporaryCodeConfig );
		        }
				        
	        }
	    }
		return temporaryCodeConfigList;
		
	}

    @Override
    public void clearDefaultFlag( Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CLEAR_DEFAULT_FLAG, plugin ) )
        {
            daoUtil.executeUpdate( );
        }
    }

    @Override
    public Optional<TemporaryCodeConfig> loadDefaultConfig( Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_DEFAULT, plugin ) )
        {
            daoUtil.executeQuery( );
            TemporaryCodeConfig temporaryCodeConfig = null;
    
            if ( daoUtil.next( ) )
            {
                temporaryCodeConfig = new TemporaryCodeConfig();
                int nIndex = 1;
                
                temporaryCodeConfig.setId( daoUtil.getInt( nIndex++ ) );
                temporaryCodeConfig.setCodeLength( daoUtil.getInt( nIndex++ ) );
                temporaryCodeConfig.setValidityTime( daoUtil.getInt( nIndex++ ) );
                temporaryCodeConfig.setCharacterType( daoUtil.getString( nIndex++ ) );
                temporaryCodeConfig.setDefault( daoUtil.getBoolean( nIndex++ ) );
            }
    
            return Optional.ofNullable( temporaryCodeConfig );
        }
    }
}
