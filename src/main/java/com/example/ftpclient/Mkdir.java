package com.example.ftpclient;

import java.io.IOException;

import javax.ws.rs.core.Response;

import com.example.exception.BadAuthentificationException;

/**
 * La Commande Mkdir sert à créer un dossier
 * 
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public class Mkdir extends FTPCommand {

	/**
	 * Directory est un argument representant le dossier crée
	 */
	private String directory;

	/**
	 * Constructeur de Mkdir
	 * 
	 * @param username
	 *            le login du client
	 * @param password
	 *            le mot de passe du client
	 * @param path
	 *            l'endroit(chemin) ou on veut le nouveau dossier
	 * @param directory
	 *            le dossier qu'on veut créer
	 */
	public Mkdir(String username, String password, String path, String directory) {
		super(username, password, path);
		this.directory = directory;
	}

	/**
	 * execute la méthode Mkdir
	 * 
	 * @throws BadAuthentificationException
	 *             l'authentification a échoue.
	 */
	public Response execute() throws BadAuthentificationException {

		System.out.println("Mkdir called...");
		if (this.directory == null)
			return Response.status(Response.Status.METHOD_NOT_ALLOWED)
					.entity(createMessageContenu("[Error] Need a directory name")).build();

		try {
			this.connect();
			boolean created = this.ftp.makeDirectory(this.path + "/" + this.directory);
			this.disconnect();

			if (created)
				return Response.status(Response.Status.CREATED).entity(createMessageContenu("Success")).build();

			else
				return Response.status(Response.Status.NOT_MODIFIED).entity(createMessageContenu("Echoue")).build();

		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(createMessageContenu("server is dead"))
					.build();

		} catch (BadAuthentificationException e) {
			throw new BadAuthentificationException("bad login or bad password");
		}
	}
}
