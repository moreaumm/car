package com.example.ftpclient;

import java.io.IOException;

import javax.ws.rs.core.Response;

import com.example.exception.BadAuthentificationException;

/**
 * La commande Authent qui permet à un utilisateur de se connecter et se
 * deconnecter
 * 
 * @author Campistron Thomas et Danneels Sophie
 *
 */
public class Authent extends FTPCommand {

	private String sentence;

	/**
	 * Constructeur Authent
	 * 
	 * @param username
	 *            le login du client
	 * @param password
	 *            le password du client
	 * @param path
	 *            le chemin de l'utilisateur
	 * @param sentence
	 *            la phrase pour deconnecter/connecter
	 */
	public Authent(String username, String password, String path, String sentence) {
		super(username, password, path);
		this.sentence = sentence;

	}

	/**
	 * execute la commande
	 * 
	 * @throws BadAuthentificationException
	 *             badException
	 * @return Response la reponse de la commande
	 */
	public Response execute() throws BadAuthentificationException {
		System.out.println("Authent called...");
		try {
			this.connect();
			this.disconnect();
			return Response.status(Response.Status.OK).entity(createMessageContenu(this.sentence)).build();
		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(createMessageContenu("server is dead.")).build();

		} catch (BadAuthentificationException e) {
			throw new BadAuthentificationException("bad login or bad password");
		}

	}

}
