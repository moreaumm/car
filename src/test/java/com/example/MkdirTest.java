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
import com.example.ftpclient.Mkdir;

public class MkdirTest {
	@Test
	public void TestMkdirInitialisation() {
		Mkdir mk = new Mkdir("login", "password", "/", "directory");
		assertNotNull(mk);
	}

	@Test
	public void TestMkdirNoGood() throws IOException, BadAuthentificationException {
		Mkdir mk = new Mkdir("login", "password", "/", null);
		Response s = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("[Error] Need a directory name")
				.build();
		;
		assertEquals(s.getStatus(), mk.execute().getStatus());
	}

	@Test(expected = BadAuthentificationException.class)
	public void TestMkdirNoConnectionGood() throws IOException, BadAuthentificationException {
		Mkdir mk = new Mkdir("login", "password", "/", "directory");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		Response s = Response.status(Response.Status.UNAUTHORIZED).entity("bad login or password.").build();
		mk.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), mk.execute().getStatus());
	}

	@Test
	public void TestMkdirExeceptionIO() throws IOException, BadAuthentificationException {
		Mkdir mk = new Mkdir("login", "password", "/", "directory");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		doThrow(new IOException()).when(ftpMock).connect(anyString(), anyInt());
		Response s = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("server is dead.").build();
		mk.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), mk.execute().getStatus());
	}

	@Test
	public void TestMkdirConnectionGoodAndGoodDirectory() throws IOException, BadAuthentificationException {
		Mkdir mk = new Mkdir("login", "password", "/", "directory");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s = Response.status(Response.Status.CREATED).entity("Success").build();
		mk.setFtpClient(ftpMock);
		when(ftpMock.makeDirectory("//directory")).thenReturn(true);
		assertEquals(s.getStatus(), mk.execute().getStatus());
		verify(ftpMock).disconnect();
		verify(ftpMock).makeDirectory("//directory");

	}

	@Test
	public void TestMkdirConnectionGoodandBadDirectory() throws IOException, BadAuthentificationException {
		Mkdir mk = new Mkdir("login", "password", "/", "directory");
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s = Response.status(Response.Status.NOT_MODIFIED).entity("Echoue").build();
		mk.setFtpClient(ftpMock);
		when(ftpMock.makeDirectory("//directory")).thenReturn(false);
		assertEquals(s.getStatus(), mk.execute().getStatus());
		verify(ftpMock).disconnect();
		verify(ftpMock).makeDirectory("//directory");

	}




}
