package com.example;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.example.exception.BadAuthentificationException;
import com.example.ftpclient.Authent;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.anyInt;
import java.io.IOException;

import javax.ws.rs.core.Response;

public class AuthentTest {

	@Test
	public void TestAuthentificationInitialisation() {
		Authent a = new Authent("login", "password", "/","Welcome to FTP/Passerelle");
		assertNotNull(a);
	}

	@Test
	public void TestAuthentificationConnectionGood() throws IOException, BadAuthentificationException {
		Authent a = new Authent("login", "password", "/","Welcome to FTP/Passerelle");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s = Response.status(Response.Status.OK).entity("Welcome to FTP/Passerelle").build();
		a.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), a.execute().getStatus());
		verify(ftpMock).disconnect();
	}

	@Test(expected = BadAuthentificationException.class)
	public void TestAuthentificationConnectionNoGood() throws IOException, BadAuthentificationException {
		Authent a = new Authent("login", "password", "/","Welcome to FTP/Passerelle");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		Response s = Response.status(Response.Status.UNAUTHORIZED).entity("bad login or password.").build();
		a.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), a.execute().getStatus());
	}

	@Test
	public void TestAuthentificationExeceptionIO() throws IOException, BadAuthentificationException {
		Authent a = new Authent("login", "password", "/","Welcome to FTP/Passerelle");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		doThrow(new IOException()).when(ftpMock).connect(anyString(), anyInt());
		Response s = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("server is dead.").build();
		a.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), a.execute().getStatus());
	}
}
