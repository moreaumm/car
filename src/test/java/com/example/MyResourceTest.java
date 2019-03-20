package com.example;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.example.ftpclient.Authent;
import org.apache.commons.net.ftp.FTPClient;
import org.glassfish.grizzly.http.server.HttpServer;

import org.glassfish.grizzly.http.util.Header;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.exception.BadAuthentificationException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MyResourceTest {

	private HttpServer server;
	private WebTarget target;

	
	@Before
	public void setUp() throws Exception {
		// start the server
		server = Main.startServer();
		// create the client
		Client c = ClientBuilder.newClient();

		// uncomment the following line if you want to enable
		// support for JSON in the client (you also have to uncomment
		// dependency on jersey-media-json module in pom.xml and
		// Main.startServer())
		// --
		// c.configuration().enable(new
		// org.glassfish.jersey.media.json.JsonJaxbFeature());

		target = c.target(Main.BASE_URI);
	}

	
	@After
	public void tearDown() throws Exception {
		server.stop();
	}
	
	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */

	@Test
	public void TestgetAuthentification() throws BadAuthentificationException {
		assertTrue(true);
		  Authent AutentMock = mock(Authent.class);
		  Response s = Response.status(Response.Status.OK).entity("Welcome to FTP/Passerelle").build();
		  when(AutentMock.execute()).thenReturn(s);
		  // ici il faut mettre un header et verifie que le code du mock et le bon comme ça on a pu verifier
		//la redirection le path (ici c'est l'essentiel des tests)
		/**
		 * Header h1= new Header("username", ");
		* Header h2 = new Header("Accept-Language", "en-US");
		Header h3 = new Header("User-Agent", "Mozilla/5.0");
		List<Header> list = new ArrayList<Header>();
		list.add(h1);
		list.add(h2);
		list.add(h3);
		 */




		  /**get("ftp/connection\")
		  //target.path("ftp/connection").setHeader()**/


	}

}
