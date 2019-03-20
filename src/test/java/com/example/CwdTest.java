package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;
import com.example.ftpclient.Cwd;

public class CwdTest {

	@Test
	public void TestCwdInitialisation() {
		Cwd c = new Cwd("login", "password", "/");
		assertNotNull(c);
	}

	@Test(expected = BadAuthentificationException.class)
	public void TestCwdAuthentificationNoGood()
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Cwd c = new Cwd("login", "password", "/");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		Response s = Response.status(Response.Status.UNAUTHORIZED).entity("bad login or password.").build();
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
		verify(ftpMock, never()).disconnect();
	}

	@Test
	public void TestCwdExeceptionIO()
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Cwd c = new Cwd("login", "password", "/");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		doThrow(new IOException()).when(ftpMock).connect(anyString(), anyInt());
		Response s = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("server is dead.").build();
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
	}

	@Test(expected = DirectoryOrFileNoFoundException.class)
	public void TestCwdBadDirectory()
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

	@Test
	public void TestCwdGoodDirectory()
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Cwd c = new Cwd("login", "password", "/dossier");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s = Response.status(Response.Status.OK).entity("Ok.").build();
		when(ftpMock.changeWorkingDirectory("/dossier")).thenReturn(true);
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
		verify(ftpMock).disconnect();
		verify(ftpMock).changeWorkingDirectory("/dossier");

	}

}
