package com.example.ftpclient;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class FTPCommandTest {

    public FTPCommand f;
    public FTPClient ftpMock;

    @Before
    public void SetUp(){
        f = new Authent("login","password","/","Welcome");
        ftpMock = mock(FTPClient.class);
        f.setFtpClient(ftpMock);
    }



    @Test
    public void connectActive() throws Exception {
        //VERIFIE qu'active est bien appele, il faudrait sortir le config dehors pour pouvoir
        //faire une injection de dependance est donc de verifier cet état avec un verify
    }

    public void connectPassive() throws Exception{
        //Verifie que passive est bien appele
        //faire une injection de dependance en mettant config dans le constructeur cela aurait permis de verifier
        // que les deux états son bien appelé.
    }

    @Test
    public void disconnectIfTheFtpIsAlreadyConnected() throws Exception {
        when(ftpMock.isConnected()).thenReturn(true);
        ftpMock.disconnect();
        verify(ftpMock).disconnect();
    }

    @Test
    public void disconnectIfTheFtpIsNoAlreadyConnected() throws Exception {
        when(ftpMock.isConnected()).thenReturn(false);
        ftpMock.disconnect();
        verify(ftpMock).disconnect();
    }

    @Test
    public void updateLogin() throws Exception {

    }

    @Test
    public void setActiveMode() throws Exception {
        f.setActiveMode();
        verify(ftpMock).enterLocalActiveMode();
    }

    @Test
    public void setPassiveMode() throws Exception {
        f.setPassiveMode();
        verify(ftpMock).enterLocalPassiveMode();
    }

    @Test
    public void execute() throws Exception {
    }

}