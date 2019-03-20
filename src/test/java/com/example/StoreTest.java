package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;
import com.example.ftpclient.Retr;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.example.ftpclient.Store;
import com.example.ftpclient.TypeFile;

import javax.ws.rs.core.Response;

import java.io.IOException;


public class StoreTest {
	@Test
	public void TestStorInitialisation() {
		Store c = new Store("login", "password", "/file", null, TypeFile.BINARY);
		assertNotNull(c);
	}

	@Test(expected = BadAuthentificationException.class)
	public void TestStorConnectionNoGood() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Store c = new Store("login", "password", "/file", null, TypeFile.BINARY);
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		Response s = Response.status(Response.Status.UNAUTHORIZED).entity("bad login or password.").build();
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
	}}

	/**
	@Test
	public void TestStoreOkASCII() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		InputStream anyInputStream = new ByteArrayInputStream("test data".getBytes());
		Store c = new Store("login", "password", "/", "file", null, TypeFile.ASCII);
		Response s = Response.ok("").build();
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		when(ftpMock.storeFile("file",anyInputStream)).thenReturn(true);
		c.setFtpClient(ftpMock);
		c.execute();
		//assertEquals(200, c.execute().getStatus());
		verify(ftpMock).setFileTransferMode(FTP.ASCII_FILE_TYPE);
		//verify(ftpMock).setFileType(FTP.ASCII_FILE_TYPE);
	}


	@Test
	public void TestStoreOkBinary() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {

		InputStream anyInputStream = new ByteArrayInputStream("test data".getBytes());
		Store c = new Store("login", "password", "/", "file", null, TypeFile.BINARY);
		Response s = Response.ok("").build();
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(true);
		when(ftpMock.storeFile("file",anyInputStream)).thenReturn(true);
		c.setFtpClient(ftpMock);
		c.execute();
		//assertEquals(200, c.execute().getStatus());
		verify(ftpMock).setFileTransferMode(FTP.BINARY_FILE_TYPE);
		//verify(ftpMock).setFileType(FTP.BINARY_FILE_TYPE);
		}

}**/

/**
 @Test
 public void TestRetrOkBinary() throws IOException, BadAuthentificationException {
 InputStream anyInputStream = new ByteArrayInputStream("test data".getBytes());
 Retr c = new Retr("login", "password", "/", "file", TypeFile.BINARY);
 Response s = Response.ok("").build();
 FTPClient ftpMock= mock(FTPClient.class);
 when(ftpMock.isConnected()).thenReturn(true);
 when(ftpMock.retrieveFileStream(anyString())).thenReturn(anyInputStream);
 //verify disconnect
 assertEquals(s.getStatus(),c.execute().getStatus());

 }

}
 **/
