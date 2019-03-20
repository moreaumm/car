package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.example.exception.BadAuthentificationException;
import com.example.ftpclient.Authent;

public class QuitTest {
	@Test
	public void TestQuitInitialisation() {
		Authent q = new Authent("login", "password", "/","Good bye");
		assertNotNull(q);
	}

	@Test
	public void TestQuitGood() throws IOException, BadAuthentificationException {
		Authent q = new Authent("login", "password", "/","Good bye");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s = Response.status(Response.Status.OK).entity("Good Bye").build();
		q.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), q.execute().getStatus());
		verify(ftpMock).disconnect();
	}

	@Test(expected = BadAuthentificationException.class)
	public void TestQuitNoGood() throws IOException, BadAuthentificationException {
		Authent q = new Authent("login", "password", "/","Good bye");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		Response s = Response.status(Response.Status.UNAUTHORIZED).entity("bad login or password.").build();
		q.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), q.execute().getStatus());
	}

	@Test
	public void TestQuitExeceptionIO() throws IOException, BadAuthentificationException {
		Authent q = new Authent("login", "password", "/","Good bye");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		doThrow(new IOException()).when(ftpMock).connect(anyString(), anyInt());
		Response s = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("server is dead.").build();
		q.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), q.execute().getStatus());
	}
}
