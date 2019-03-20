package com.example.ftpclient;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.FTP;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;

/**
 * Command Store
 * 
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public class Store extends FTPCommand {

	private InputStream file;
	private TypeFile type;

	/**
	 * Constructeur de Store
	 * 
	 * @param username
	 *            le login du client
	 * @param password
	 *            le mot de passe du client
	 * @param path
	 *            l'endroit(chemin) ou se trouve le fichier que l'ont veut
	 *            envoyer
	 * @param file
	 *            L'input stream "contenant" le fichier.
	 * @param type
	 *            Le type a utiliser pour transférer les données "BINARY" ou
	 *            "ASCII"
	 */
	public Store(String username, String password, String path, InputStream file, TypeFile type) {
		super(username, password, path);
		this.file = file;
		this.type = type;
		this.path = path;
	}

	/**
	 * execute la méthode Store
	 * 
	 * @throws BadAuthentificationException
	 *             l'authentification a échoue.
	 * @throws IOException
	 *             Le fichier est introuvable ou une erreur est survenue sur le
	 *             réseau.
	 * @throws DirectoryOrFileNoFoundException
	 *             Le repertoire ou le fichier n'existe pas
	 */
	@Override
	public Response execute() throws BadAuthentificationException, IOException, DirectoryOrFileNoFoundException {
		System.out.println("store called...");
		try {
			this.connect();
			if (TypeFile.BINARY == type) {
				this.ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
				this.ftp.setFileType(FTP.BINARY_FILE_TYPE);
			}

			else {
				this.ftp.setFileTransferMode(FTP.ASCII_FILE_TYPE);
				this.ftp.setFileType(FTP.BINARY_FILE_TYPE);
			}

			// if (this.ftp.changeWorkingDirectory(this.path)) {
			boolean success = this.ftp.storeFile(this.path, this.file);
			if (!success) {
				return Response.status(Response.Status.BAD_REQUEST).entity(createMessageContenu("Echoue")).build();

			}
			file.close();
			return Response.status(Response.Status.OK).entity(createMessageContenu("store sucess")).build();
		}
		/*
		 * else{ throw new
		 * DirectoryOrFileNoFoundException("Directory does not exist./or Root");
		 * }
		 * 
		 * 
		 * }
		 */
		catch (BadAuthentificationException e) {
			throw new BadAuthentificationException("bad login or bad password");
		}

	}
}
