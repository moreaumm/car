package com.example.ftpclient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.ws.rs.core.Response;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;
/**
 * The RetrDir
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public class RetrDir extends FTPCommand {

	private String dir;

	/**
	 * The RetrDir
	 * @param username username
	 * @param password password
	 * @param path path
	 * @param dir dir
	 */
	protected RetrDir(String username, String password, String path, String dir) {
		super(username, password, path);
		this.dir = dir;
	}

	// Mettre des données dans un zip

	/**
	 * Faire une boucle pour lire dans un dossier et les stocker dans un ZipOutStream
	 */
	@Override
	public Response execute() throws BadAuthentificationException, DirectoryOrFileNoFoundException, IOException {

		return null;
	}

}
