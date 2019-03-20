package com.example;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.FTPClient;


import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;

import com.example.ftpclient.Retr;
import com.example.ftpclient.TypeFile;

public class RetrTest {
	@Test
	public void TestRetrInitialisation() {
		Retr c = new Retr("login", "password", "/file", TypeFile.BINARY);
		assertNotNull(c);
	}

	@Test(expected=BadAuthentificationException.class)
	public void TestRetrConnectionNoGood() throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		Retr c = new Retr("login", "password", "/file", TypeFile.BINARY);
		FTPClient ftpMock = mock(FTPClient.class);
		when(ftpMock.isConnected()).thenReturn(false);
		Response s = Response.status(Response.Status.UNAUTHORIZED).entity("bad login or password.").build();
		c.setFtpClient(ftpMock);
		assertEquals(s.getStatus(), c.execute().getStatus());
	}

	@Test
    public void TestRetrOkASCII(){
        InputStream a = null;
        Retr c = new Retr("login", "password", "/file", TypeFile.ASCII);
        Response s = Response.ok("").build();
        FTPClient ftpMock= mock(FTPClient.class);
        when(ftpMock.isConnected()).thenReturn(true);
    }

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

    @Test
    public void TestRetrFichierNull(){
        InputStream a = null;
        Retr c = new Retr("login", "password", "/", "file", TypeFile.BINARY);
        Response s = Response.status(Response.Status.BAD_REQUEST).build();
        FTPClient ftpMock= mock(FTPClient.class);
        when(ftpMock.isConnected()).thenReturn(true);
    }

    **/

}
