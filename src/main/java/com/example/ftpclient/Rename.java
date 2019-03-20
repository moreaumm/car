package com.example.ftpclient;

import java.io.IOException;

import javax.ws.rs.core.Response;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;

/**
 * Command Rename
 * 
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public class Rename extends FTPCommand {

	private String directoryNameOld;
	private String directroyNameNew;

	/**
	 * Constructeur de Rename
	 * 
	 * @param username
	 *            le login du client
	 * @param password
	 *            le mot de passe du client
	 * @param path
	 *            l'endroit(chemin) ou on veut renommer le dossier
	 * @param directoryNameOld
	 *            le dossier que l'on veut renommer
	 * @param directroyNameNew
	 *            Le nom qu'on veut lui donner
	 */
	public Rename(String username, String password, String path, String directoryNameOld, String directroyNameNew) {
		super(username, password, path);
		this.directoryNameOld = path + "/" + directoryNameOld;
		this.directroyNameNew = path + "/" + directroyNameNew;
	}

	/**
	 * execute la méthode Rename
	 * 
	 * @throws BadAuthentificationException
	 *             l'authentification a échoue.
	 * @throws DirectoryOrFileNoFoundException
	 *             Le fichier / dossier est introuvable.
	 */
	@Override
	public Response execute() throws BadAuthentificationException, DirectoryOrFileNoFoundException {
		System.out.println("rename called...");
		try {
			this.connect();
		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(createMessageContenu("server is dead"))
					.build();
		} catch (BadAuthentificationException e) {
			throw new BadAuthentificationException("bad login or bad password");
		}

		System.out.println("After connection...");

		boolean res;

		try {
			res = this.ftp.rename(this.directoryNameOld, this.directroyNameNew);
			this.disconnect();
			System.out.println(res);
			if (res)
				return Response.status(Response.Status.OK).entity(createMessageContenu("Rename Sucess")).build();
			else
				throw new DirectoryOrFileNoFoundException("No file/directory found, Rename no sucess");

		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(createMessageContenu("server is dead"))
					.build();
		}
	}
}
