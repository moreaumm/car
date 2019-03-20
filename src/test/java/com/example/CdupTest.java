package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;
import com.example.ftpclient.Cdup;
import com.example.ftpclient.Cwd;

public class CdupTest {

	@Test
	public void TestCdupInitialisation() {
		Cdup c = new Cdup("login", "password", "/");
		assertNotNull(c);
	}
	

	@Test
	public void TestCdpExeceptionIO()
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Cdup c = new Cdup("login", "password", "/");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		doThrow(new IOException()).when(ftpMock).connect(anyString(), anyInt());
		Response s = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("server is dead.").build();
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
	}

	@Test(expected = DirectoryOrFileNoFoundException.class)
	public void TestCdupBadDirectory()
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Cwd c = new Cwd("login", "password", "/");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s = Response.status(Response.Status.BAD_REQUEST).entity("Directory does not exist.").build();
		when(ftpMock.changeWorkingDirectory("/")).thenReturn(false);
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
		verify(ftpMock).disconnect();
		verify(ftpMock).changeWorkingDirectory("/");

	}

	@Test(expected = BadAuthentificationException.class)
	public void TestCdupAuthentificationNoGood()
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Cdup c = new Cdup("login", "password", "/");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		Response s = Response.status(Response.Status.UNAUTHORIZED).entity("bad login or password.").build();
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
		verify(ftpMock, never()).disconnect();
	}

	
	@Test
	public void TestCdupGoodDirectory()
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Cdup c = new Cdup("login", "password", "/dossier");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s = Response.status(Response.Status.OK).entity("Ok.").build();
		when(ftpMock.changeWorkingDirectory("/dossier")).thenReturn(true);
		when(ftpMock.changeToParentDirectory()).thenReturn(true);
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
		verify(ftpMock).disconnect();
		verify(ftpMock).changeWorkingDirectory("/dossier");

	}
	

	@Test(expected = DirectoryOrFileNoFoundException.class)
	public void TestCdupGoodDirectoryButItisaroot()
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Cdup c = new Cdup("login", "password", "/dossier");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s =  Response.status(Response.Status.BAD_REQUEST).entity("It is a Root.").build();
		when(ftpMock.changeWorkingDirectory("/dossier")).thenReturn(true);
		when(ftpMock.changeToParentDirectory()).thenReturn(false);
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
		verify(ftpMock).disconnect();
		verify(ftpMock).changeWorkingDirectory("/dossier");

	}
	

	@Test(expected = DirectoryOrFileNoFoundException.class)
	public void TestCdupGoodDirectoryNoGood()
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Cdup c = new Cdup("login", "password", "/dossier");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s =  Response.status(Response.Status.BAD_REQUEST).entity("Directory does not exist.").build();
		when(ftpMock.changeWorkingDirectory("/dossier")).thenReturn(false);
		when(ftpMock.changeToParentDirectory()).thenReturn(true);
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
		verify(ftpMock).disconnect();
		verify(ftpMock).changeWorkingDirectory("/dossier");

	}

}
