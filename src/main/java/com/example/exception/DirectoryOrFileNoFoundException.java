package com.example.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.json.simple.JSONObject;

/**
 * Classe Exception DirectoryOrFileNoFoundException: erreur si le fichier ou
 * dossier n'est pas trouvé
 * 
 * @author Thomas Campistron et Sophie Danneels
 *
 */
@Provider
public class DirectoryOrFileNoFoundException extends Exception
		implements ExceptionMapper<DirectoryOrFileNoFoundException> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur DirectoryOrFileNoFoundException
	 */
	public DirectoryOrFileNoFoundException() {
		super("Directory No Found");
	}

	/**
	 * Constructeur DirectoryOrFileNoFoundException
	 * 
	 * @param msg
	 *            message d'erreurs
	 */
	public DirectoryOrFileNoFoundException(String msg) {
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
	public Response toResponse(DirectoryOrFileNoFoundException exception) {
		JSONObject obj = new JSONObject();
		obj.put("contenu", exception.getMessage());
		return Response.status(Response.Status.BAD_REQUEST).entity(obj).build();

	}

}
