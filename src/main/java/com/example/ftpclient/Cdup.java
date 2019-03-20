package com.example.ftpclient;

import java.io.IOException;

import javax.ws.rs.core.Response;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;

/**
 * La classe Cdup est la commande Transformer en répertoire parent.
 * 
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public class Cdup extends FTPCommand {

	/**
	 * Constructor for Cdup
	 * 
	 * @param username
	 *            login for user
	 * @param password
	 *            password for user
	 * @param path
	 *            path for user changing
	 */
	public Cdup(String username, String password, String path) {
		super(username, password, path);
	}

	/**
	 * Method to execute Cdup
	 * 
	 * @return Response la reponse de cdup
	 * @throws DirectoryOrFileNoFoundException
	 *             Directory n'existe pas
	 * @throws BadAuthentificationException
	 *             Authentification ratée
	 * 
	 */
	public Response execute() throws BadAuthentificationException, DirectoryOrFileNoFoundException {
		System.out.println("cdup called...");
		try {
			this.connect();
			System.out.println("after connection...");

			if (this.ftp.changeWorkingDirectory(this.path)) {
				if (this.ftp.changeToParentDirectory()) {
					this.disconnect();
					return Response.status(Response.Status.OK).entity(createMessageContenu("Ok")).build();
				} else {
					this.disconnect();

					throw new DirectoryOrFileNoFoundException("Already at root.");
				}
			}

			else {
				this.disconnect();

				throw new DirectoryOrFileNoFoundException("It is a Directory does not exist.");
			}
		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(createMessageContenu("server is dead"))
					.build();
		}

		catch (BadAuthentificationException e) {
			throw new BadAuthentificationException("bad login or bad password");
		}
	}
}
