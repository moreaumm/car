package com.example;

import static org.junit.Assert.assertNotNull;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;
import org.junit.Test;

import com.example.ftpclient.Delete;
import com.example.ftpclient.TypeDirOrFile;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

public class DeleteFileTest {

	@Test
	public void TestDeleteInitialisation() {
		Delete c = new Delete("login", "password", "/", "File",TypeDirOrFile.FILE);
		assertNotNull(c);
	}

	@Test(expected = BadAuthentificationException.class)
	public void TestDeleteDirExecuteErrorAuthentification() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Delete c = new Delete("login", "password", "/", "File",TypeDirOrFile.FILE);
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		Response s = Response.status(Response.Status.UNAUTHORIZED).entity("bad login or password.").build();
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
	}

	@Test
	public void TestDeleteFileItIsOk() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Delete c = new Delete("login", "password", "/", "File",TypeDirOrFile.FILE);		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		when(ftpMock.deleteFile("//File")).thenReturn(true);
		doThrow(new IOException()).when(ftpMock).connect(anyString(), anyInt());
		Response s = Response.status(Response.Status.OK).entity("The file was deleted successfully.").build();
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
		verify(ftpMock).disconnect();
		verify(ftpMock).deleteFile("//File");
	}

	@Test(expected = DirectoryOrFileNoFoundException.class)
	public void TestDeleteFileItIsNoOk() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Delete c = new Delete("login", "password", "/", "File",TypeDirOrFile.FILE);
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		when(ftpMock.deleteFile("//File")).thenReturn(false);
		doThrow(new IOException()).when(ftpMock).connect(anyString(), anyInt());
		Response s = Response.status(Response.Status.BAD_REQUEST).entity("").build();
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
		verify(ftpMock).disconnect();
		verify(ftpMock).removeDirectory("//dir");
	}

	/**
	 * @Test public void TestDeleteDirErrorException() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
	 * 		DeleteFile c = new DeleteFile("login", "password", "/", "File");
	 * FTPClient ftpMock = mock(FTPClient.class);
	 * when(ftpMock.isConnected()).thenReturn(true);
	 * doThrow(new IOException()).when(ftpMock).removeDirectory(anyString());
	 * Response s = Response.status(Response.Status.NOT_FOUND).entity("server is dead").build();
	 * c.setFtpClient(ftpMock);
	 * assertEquals(s.getStatus(), c.execute().getStatus());
	 * verify(ftpMock).disconnect();
	 * verify(ftpMock).removeDirectory("//dir");
	 * <p>
	 * }
	 **/


	@Test
	public void TestDeleteDireExeceptionIO() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Delete c = new Delete("login", "password", "/", "File",TypeDirOrFile.FILE);
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		doThrow(new IOException()).when(ftpMock).connect(anyString(), anyInt());
		Response s = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("server is dead.").build();
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
	}

}

