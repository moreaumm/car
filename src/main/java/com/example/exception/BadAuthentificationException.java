package com.example.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.json.simple.JSONObject;

/**
 * Classe Exception BadAuthentificationException: erreur en cas de mauvaise
 * authentification
 *
 * @author Thomas Campistron et Sophie Danneels
 *
 */
@Provider
public class BadAuthentificationException extends Exception implements ExceptionMapper<BadAuthentificationException> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructeur BadAuthentificationException
	 */
	public BadAuthentificationException() {
		super("bad login or bad password");
	}



	/**
	 * Constructeur BadAuthentificationException
	 *
	 * @param msg
	 *            message d'erreur
	 */
	public BadAuthentificationException(String msg) {
		super(msg);
	}

	/**
	 * transforme l'erreur en une reponse HTTP
	 *
	 * @param exception
	 *            lancé
	 * @return Response retourne l'erreur
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Response toResponse(BadAuthentificationException exception) {
		JSONObject obj = new JSONObject();
		obj.put("contenu", exception.getMessage());
		return Response.status(Status.UNAUTHORIZED).entity(obj).build();
	}
}
