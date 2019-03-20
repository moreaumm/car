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
import com.example.exception.DirectoryOrFileNoFoundException;
import com.example.ftpclient.Rename;

public class RenameTest {

	@Test
	public void TestRenameInitialisation(){
		Rename re = new Rename("login","password","/","directoryNameOld","directoryNameNew");
		assertNotNull(re);
	}
	
	@Test(expected=BadAuthentificationException.class)
	public void TestRenameConnectionNoGood() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException{
		Rename re = new Rename("login","password","/","directoryNameOld","directoryNameNew");
		FTPClient ftpMock= mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		Response s = Response.status(Response.Status.UNAUTHORIZED).entity("bad login or password.").build();
		re.setFtpClient(ftpMock);
		assertEquals(s.getStatus(),re.execute().getStatus());
	}
	
	
	@Test
	public void TestRenameExeceptionIO() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException{
		Rename re = new Rename("login","password","/","directoryNameOld","directoryNameNew");
		FTPClient ftpMock= mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		doThrow(new IOException()).when(ftpMock).connect(anyString(), anyInt());
		Response s = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("server is dead.").build();
		re.setFtpClient(ftpMock);
		assertEquals(s.getStatus(),re.execute().getStatus());
	}

	@Test
	public void TestRenameConnectionGoodAndGoodDirectory() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException{
		Rename re = new Rename("login","password","/","directoryNameOld","directoryNameNew");
		FTPClient ftpMock= mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s = Response.status(Response.Status.OK).entity("Rename Sucess").build();
		re.setFtpClient(ftpMock);
		when(ftpMock.rename("//directoryNameOld","//directoryNameNew")).thenReturn(true);
		assertEquals(s.getStatus(),re.execute().getStatus());
		verify(ftpMock).disconnect();
		verify(ftpMock).rename("//directoryNameOld","//directoryNameNew");

	}
	
	@Test(expected=DirectoryOrFileNoFoundException.class)
	public void TestRenameConnectionGoodandBadDirectory() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException{
		Rename re = new Rename("login","password","/","directoryNameOld","directoryNameNew");
		FTPClient ftpMock= mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s = Response.status(Response.Status.NOT_MODIFIED).entity("Echoue").build();
		re.setFtpClient(ftpMock);
		when(ftpMock.rename("//directoryNameOld","//directoryNameNew")).thenReturn(false);
		assertEquals(s.getStatus(),re.execute().getStatus());
		verify(ftpMock).disconnect();
		verify(ftpMock).rename("//directoryNameOld","//directoryNameNew");

	}
	
	
	@Test
	public void TestRenameExeceptionIOFordDsconnect() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException{
		Rename re = new Rename("login","password","/","directoryNameOld","directoryNameNew");
		FTPClient ftpMock= mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		when(ftpMock.isConnected()).thenReturn(true);
		Response s = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error printWorkingDirectory").build();
		re.setFtpClient(ftpMock);
		doThrow(new IOException()).when(ftpMock).rename("//directoryNameOld","//directoryNameNew");
		assertEquals(s.getStatus(),re.execute().getStatus());
		verify(ftpMock).rename("//directoryNameOld","//directoryNameNew");
		re.setFtpClient(ftpMock);
		assertEquals(s.getStatus(),re.execute().getStatus());
	}
}
