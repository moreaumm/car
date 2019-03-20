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
import com.example.ftpclient.Cwd;
import com.example.ftpclient.List;

public class ListTest {
	
	
	@Test
	public void TestCdupInitialisation() {
		List c = new List("login", "password", "/");
		assertNotNull(c);
	}
	
	
	@Test(expected = BadAuthentificationException.class)
	public void TestListNoGood()
			throws IOException, BadAuthentificationException{
		List l = new List("login", "password", "/");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		Response s = Response.status(Response.Status.UNAUTHORIZED).entity("bad login or password.").build();
		l.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), l.execute().getStatus());
		verify(ftpMock, never()).disconnect();
	}
	
	
	@Test
	public void TestListExeceptionIO()
			throws IOException, BadAuthentificationException {
		List l = new List("login", "password", "/");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		doThrow(new IOException()).when(ftpMock).connect(anyString(), anyInt());
		Response s = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("server is dead.").build();
		l.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), l.execute().getStatus());
	}
	
	
	@Test
	public void TestListCodeReplyNoPositive()
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		List l = new List("login", "password", "/");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		when(ftpMock.getReplyCode()).thenReturn(450);
		Response s =  Response.status(Response.Status.BAD_REQUEST).entity("bad path").build();
		l.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), l.execute().getStatus());
		verify(ftpMock).disconnect();
	}

	
	/***
	@Test(expected = BadAuthentificationException.class)
	public void TestListCodeReplyPositive()
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		List l = new List("login", "password", "/");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		when(ftpMock.getReplyCode()).thenReturn(200);
		Response s =  Response.status(Response.Status.OK).entity("").build();
		l.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), l.execute().getStatus());
		verify(ftpMock).disconnect();
	}
	**/


}
