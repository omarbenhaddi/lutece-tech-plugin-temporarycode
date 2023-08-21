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
 * SUBSTITUTE GOODS OR SERVICES LOSS OF USE, DATA, OR PROFITS OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */

package fr.paris.lutece.plugins.temporarycode.web;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.admin.AdminAuthenticationService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import java.util.List;
import java.io.IOException;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.plugins.temporarycode.business.TemporaryCodeConfigHome;
/**
 * This is the business class test for the object TemporaryCodeConfig
 */
public class TemporaryCodeConfigJspBeanTest extends LuteceTestCase
{
    private static final int CODELENGTH1 = 1;
    private static final int CODELENGTH2 = 2;
    private static final int VALIDITYTIME1 = 1;
    private static final int VALIDITYTIME2 = 2;
    private static final String CHARACTER1 = "Character1";
    private static final String CHARACTER2 = "Character2";

public void testJspBeans(  ) throws AccessDeniedException, IOException
	{	
     	MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockServletConfig config = new MockServletConfig();

		//display admin TemporaryCodeConfig management JSP
		TemporaryCodeConfigJspBean jspbean = new TemporaryCodeConfigJspBean();
		String html = jspbean.getManageTemporaryCodeConfigs( request );
		assertNotNull(html);

		//display admin TemporaryCodeConfig creation JSP
		html = jspbean.getCreateTemporaryCodeConfig( request );
		assertNotNull(html);

		//action create TemporaryCodeConfig
		request = new MockHttpServletRequest();

		response = new MockHttpServletResponse( );
		AdminUser adminUser = new AdminUser( );
		adminUser.setAccessCode( "admin" );
		
        
        request.addParameter( "code_length" , String.valueOf( CODELENGTH1) );
        request.addParameter( "validity_time" , String.valueOf( VALIDITYTIME1) );
        request.addParameter( "character" , CHARACTER1 );
		request.addParameter("action","createTemporaryCodeConfig");
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "createTemporaryCodeConfig" ));
		request.setMethod( "POST" );
        
		
		try 
		{
			AdminAuthenticationService.getInstance( ).registerUser(request, adminUser);
			html = jspbean.processController( request, response ); 
			
			
			// MockResponse object does not redirect, result is always null
			assertNull( html );
		}
		catch (AccessDeniedException e)
		{
			fail("access denied");
		}
		catch (UserNotSignedException e) 
		{
			fail("user not signed in");
		}

		//display modify TemporaryCodeConfig JSP
		request = new MockHttpServletRequest();
        request.addParameter( "code_length" , String.valueOf( CODELENGTH1) );
        request.addParameter( "validity_time" , String.valueOf( VALIDITYTIME1) );
        request.addParameter( "character" , CHARACTER1 );
		List<Integer> listIds = TemporaryCodeConfigHome.getIdTemporaryCodeConfigsList();
        assertTrue( !listIds.isEmpty( ) );
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
		jspbean = new TemporaryCodeConfigJspBean();
		
		assertNotNull( jspbean.getModifyTemporaryCodeConfig( request ) );	

		//action modify TemporaryCodeConfig
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		
		adminUser = new AdminUser();
		adminUser.setAccessCode("admin");
		
        request.addParameter( "code_length" , String.valueOf( CODELENGTH2) );
        request.addParameter( "validity_time" , String.valueOf( VALIDITYTIME2) );
        request.addParameter( "character" , CHARACTER2 );
		request.setRequestURI("jsp/admin/plugins/example/ManageTemporaryCodeConfigs.jsp");
		//important pour que MVCController sache quelle action effectuer, sinon, il redirigera vers createTemporaryCodeConfig, qui est l'action par défaut
		request.addParameter("action","modifyTemporaryCodeConfig");
		request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "modifyTemporaryCodeConfig" ));

		try 
		{
			AdminAuthenticationService.getInstance( ).registerUser(request, adminUser);
			html = jspbean.processController( request, response );

			// MockResponse object does not redirect, result is always null
			assertNull( html );
		}
		catch (AccessDeniedException e)
		{
			fail("access denied");
		}
		catch (UserNotSignedException e) 
		{
			fail("user not signed in");
		}
		
		//get remove TemporaryCodeConfig
		request = new MockHttpServletRequest();
        //request.setRequestURI("jsp/admin/plugins/example/ManageTemporaryCodeConfigs.jsp");
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
		jspbean = new TemporaryCodeConfigJspBean();
		request.addParameter("action","confirmRemoveTemporaryCodeConfig");
		assertNotNull( jspbean.getModifyTemporaryCodeConfig( request ) );
				
		//do remove TemporaryCodeConfig
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		request.setRequestURI("jsp/admin/plugins/example/ManageTemporaryCodeConfigts.jsp");
		//important pour que MVCController sache quelle action effectuer, sinon, il redirigera vers createTemporaryCodeConfig, qui est l'action par défaut
		request.addParameter("action","removeTemporaryCodeConfig");
		request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "removeTemporaryCodeConfig" ));
		request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
		request.setMethod("POST");
		adminUser = new AdminUser();
		adminUser.setAccessCode("admin");

		try 
		{
			AdminAuthenticationService.getInstance( ).registerUser(request, adminUser);
			html = jspbean.processController( request, response ); 

			// MockResponse object does not redirect, result is always null
			assertNull( html );
		}
		catch (AccessDeniedException e)
		{
			fail("access denied");
		}
		catch (UserNotSignedException e) 
		{
			fail("user not signed in");
		}	
     
     }
}
