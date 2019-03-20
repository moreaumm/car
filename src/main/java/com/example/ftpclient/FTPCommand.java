package com.example.ftpclient;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;

import configuration.ConfigServeur;

import java.io.IOException;

import org.apache.commons.net.ftp.*;
import org.json.simple.JSONObject;

import javax.ws.rs.core.Response;

/**
 * La classe abstraite FTPCommand
 * 
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public abstract class FTPCommand {

	/**
	 * FTPClient ftp
	 */
	protected FTPClient ftp;

	/**
	 * Le username d'un client
	 */
	protected String username;

	/**
	 * Le password d'un client
	 */
	protected String password;

	/**
	 * Le path d'un chemin
	 */
	protected String path;

	/**
	 * Constructeur de FTPCommand
	 * 
	 * @param username
	 *            username d'un client
	 * @param password
	 *            le mot de passe d'un client
	 * @param path
	 *            le chemin d'un path
	 */
	protected FTPCommand(String username, String password, String path) {
		this.ftp = new FTPClient();
		this.username = username;
		this.password = password;
		this.path = path;
	}

	/**
	 * Setteur du FtpClient
	 * 
	 * @param ftp
	 *            ftp
	 */
	public void setFtpClient(FTPClient ftp) {
		this.ftp = ftp;

	}

	/**
	 * Try to connect to the server.
	 *
	 * @throws FTPConnectionClosedException
	 *             if the FTP server prematurely closes the connection as a
	 *             result of the client being idle or some other reason causing
	 *             the server to send FTP reply code 421.
	 * @throws IOException
	 *             for unknow reaons.
	 * @throws BadAuthentificationException
	 *             if the authentification is failed the exeception is throwed
	 *
	 * @return true if you can connect or are already connected, false either
	 */
	protected boolean connect() throws IOException, BadAuthentificationException {
		if (this.ftp.isConnected())
			return true;

		System.out.println("In connect...");
		try {
			ConfigServeur c = new ConfigServeur();

			this.ftp.connect(c.getServeur(), c.getPort());
			if (c.getModeActive())
				this.setActiveMode();
			else
				this.setPassiveMode();
			System.out.println("After connection to server...");

			System.out.println("Going to connect with " + this.username + " " + this.password + "...");
			if (this.ftp.login(this.username, this.password))
				return true;
			else
				throw new BadAuthentificationException("bad login or bad password");

		} catch (IOException e) {
			System.out.println("Failed, before disconnect...");
			this.ftp.disconnect(); /* can throw IOException */
			throw e;
		}
	}

	/**
	 * Fonction qui permet de deconnecter le FtpClient
	 */
	protected void disconnect() {
		if (!this.ftp.isConnected())
			return;
		try {
			this.ftp.disconnect();
		} catch (IOException e) {
		}
	}

	/**
	 * Update your login and password and disconnect you.
	 *
	 * @param username
	 *            String
	 * @param password
	 *            String or null
	 */
	public void updateLogin(String username, String password) {
		this.username = username;
		this.password = password;
		this.disconnect();
	}

	/**
	 * ActiveMode FtpClient
	 */
	public void setActiveMode() {
		this.ftp.enterLocalActiveMode();
	}

	/**
	 * getteur
	 * 
	 * @return boolean
	 */
	public boolean getActiveMode() {
		ConfigServeur c = new ConfigServeur();
		return c.getModeActive();
	}

	/**
	 * PassiveMode FtpClient
	 */
	public void setPassiveMode() {
		this.ftp.enterLocalPassiveMode();
	}

	/**
	 * Qui execute la commande effectuée
	 * 
	 * @return reponse http de la commande effectuée
	 * @throws BadAuthentificationException
	 *             si l'authentification a raté
	 * @throws DirectoryOrFileNoFoundException
	 *             si le repertoire ou fichier n'est pas trouvé
	 * @throws IOException
	 *             error with read/write
	 */
	public abstract Response execute()
			throws BadAuthentificationException, DirectoryOrFileNoFoundException, IOException;

	@SuppressWarnings("unchecked")
	public JSONObject createMessageContenu(Object message) {
		JSONObject obj = new JSONObject();
		obj.put("contenu", message);
		return obj;
	}
}
