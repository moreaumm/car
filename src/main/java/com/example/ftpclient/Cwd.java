package com.example.ftpclient;

import java.io.IOException;
import javax.ws.rs.core.Response;
import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;

/**
 * La classe Cwd est la Command cwd (change working directory) Changer le
 * répertoire de travail.
 *
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public class Cwd extends FTPCommand {

	/**
	 * Constructor cwd
	 * 
	 * @param username
	 *            login for user
	 * @param password
	 *            password for user
	 * @param path
	 *            path for user changing
	 */
	public Cwd(String username, String password, String path) {
		super(username, password, path);
	}

	/**
	 * Method to execute CWD
	 * 
	 * @throws DirectoryOrFileNoFoundException
	 *             Directory n'existe pas
	 * @throws BadAuthentificationException
	 *             Authentification ratée
	 * 
	 */
	public Response execute() throws BadAuthentificationException, DirectoryOrFileNoFoundException {
		System.out.println("cwd called...");
		try {
			this.connect();
			System.out.println("after connection...");

			if (this.ftp.changeWorkingDirectory(this.path)) {
				this.disconnect();
				return Response.status(Response.Status.OK).entity(createMessageContenu("Ok.")).build();
			} else {
				this.disconnect();
				throw new DirectoryOrFileNoFoundException("Directory does not exist.");
			}
		} catch (IOException e) {

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(createMessageContenu("Can't connect"))
					.build();
		} catch (BadAuthentificationException e) {
			throw new BadAuthentificationException("bad login or bad password");
		}

	}
}
