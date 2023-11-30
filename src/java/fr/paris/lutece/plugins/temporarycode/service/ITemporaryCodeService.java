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

}