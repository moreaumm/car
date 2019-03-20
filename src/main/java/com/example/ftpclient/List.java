package com.example.ftpclient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.core.Response;

import java.io.IOException;

import org.apache.commons.net.ftp.*;

import com.example.exception.BadAuthentificationException;

/**
 * La Commande List Affiche les informations d'un fichier ou d'un répertoire
 * spécifique, ou du répertoire courant.
 * 
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public class List extends FTPCommand {

	/**
	 * Constructeur List
	 * 
	 * @param username
	 *            le login du client
	 * @param password
	 *            le mot de passe du client
	 * @param path
	 *            l'endroit(chemin) ou on veut afficher l'ensemble des
	 *            repertoires
	 */
	public List(String username, String password, String path) {
		super(username, password, path);
	}

	/**
	 * execute la méthode List
	 * 
	 * @throws BadAuthentificationException
	 *             l'authentification a échoue.
	 * 
	 * @return Response http de la commande List
	 */
	public Response execute() throws BadAuthentificationException {
		System.out.println("list called...");
		try {
			this.connect();

			System.out.println("After connection...");

			String res = "";

			System.out.println("path is " + this.path);
			FTPFile[] files = this.ftp.listFiles(this.path);
			if (!FTPReply.isPositiveCompletion(this.ftp.getReplyCode())) {
				this.disconnect();
				return Response.status(Response.Status.BAD_REQUEST).entity(createMessageContenu("bad path")).build();

			}
			DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (FTPFile file : files) {
				String details = file.getName();
				if (file.isDirectory()) {
					details = "[" + details + "]";
				}
				details += "\t\t" + file.getSize();
				details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());
				res += details + "\n";
			}
			this.disconnect();
			return Response.status(Response.Status.OK).entity(createMessageContenu(res)).build();
		}

		catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(createMessageContenu("server is dead"))
					.build();
		} catch (BadAuthentificationException e) {
			throw new BadAuthentificationException("bad login or bad password");
		}

	}
}
