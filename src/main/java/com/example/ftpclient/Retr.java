package com.example.ftpclient;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.*;

import com.example.exception.BadAuthentificationException;

/**
 * Command Retr
 * 
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public class Retr extends FTPCommand {

	private String file;
	private TypeFile type;

	/**
	 * Constructeur de Retr
	 * 
	 * @param username
	 *            le login du client
	 * @param password
	 *            le mot de passe du client
	 * @param file
	 *            Le nom du fichier à télécharger avec le chemin.
	 * @param type
	 *            Le type a utiliser pour transférer les données "BINARY" ou
	 *            "ASCII"
	 */
	public Retr(String username, String password, String file, TypeFile type) {
		super(username, password, file);
		this.file = file;
		this.type = type;
	}

	/**
	 * execute la méthode Retr
	 * 
	 * @throws BadAuthentificationException
	 *             l'authentification a échoue.
	 * @throws IOException
	 *             Le fichier est introuvable ou une erreur est survenue sur le
	 *             réseau.
	 */
	public Response execute() throws BadAuthentificationException, IOException {
		System.out.println("retr called...");

		InputStream io = null;
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
			io = this.ftp.retrieveFileStream(this.file);
			if (io != null) {
				return Response.ok(io).build();
			}

			while (!this.ftp.completePendingCommand()) {
				io = this.ftp.retrieveFileStream(this.file);
			}
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (BadAuthentificationException e) {
			throw new BadAuthentificationException("bad login or bad password");
		}

	}

}
