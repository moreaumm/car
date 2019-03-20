package com.example.ftpclient;

import java.io.IOException;

import javax.ws.rs.core.Response;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;

/**
 * La Commande DeleteDir supprime un directoire ou un repertoire
 * 
 * @author Thomas Campistron et Sophie Danneels
 *
 **/

public class Delete extends FTPCommand {

	/**
	 * Le chemin complet du repertoire qui doit être supprimé
	 */

	private String completePath;
	private TypeDirOrFile typefile;

	/**
	 * Constructeur de la commande DeleteDir
	 * 
	 * @param username
	 *            le login du client
	 * @param password
	 *            le mot de passe du client
	 * @param path
	 *            le chemin ou est le repertoire/file
	 * @param f
	 *            le chemin du repertoire/file doit supprimer
	 * @param d
	 *            le type si c'est une repertoire/ou un file
	 */
	public Delete(String username, String password, String path, String f, TypeDirOrFile d) {
		super(username, password, path);
		this.completePath = path + "/" + f;
		this.typefile = d;
	}

	/**
	 * Execute la commande Delete
	 *
	 * @throws BadAuthentificationException
	 *             l'authentification a échoue.
	 * @throws DirectoryOrFileNoFoundException
	 *             DirectoryOrFileNoFoundException
	 * 
	 * @return Response Http de la commande Delete
	 * 
	 */
	public Response execute() throws BadAuthentificationException, DirectoryOrFileNoFoundException {
		if (TypeDirOrFile.DIR == this.typefile) {
			System.out.println("delete dir called...");
			try {
				this.connect();
			} catch (IOException e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(createMessageContenu("server is dead")).build();
			} catch (BadAuthentificationException e) {
				throw new BadAuthentificationException("bad login or bad password");
			}

			try {
				if (this.ftp.removeDirectory(this.completePath)) {
					this.disconnect();
					return Response.status(Response.Status.OK)
							.entity(createMessageContenu("The file was deleted successfully.")).build();

				}
				this.disconnect();
				throw new DirectoryOrFileNoFoundException("Could not delete the  file, it may not exist.");

			} catch (IOException e) {
				return Response.status(Response.Status.NOT_FOUND).entity(createMessageContenu("server is dead"))
						.build();
			}
		}
		if (TypeDirOrFile.FILE == this.typefile) {
			System.out.println("delete called...");
			try {
				this.connect();
			} catch (IOException e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(createMessageContenu("server is dead")).build();
			}

			catch (BadAuthentificationException e) {
				throw new BadAuthentificationException("bad login or bad password");
			}
			try {
				if (this.ftp.deleteFile(this.completePath)) {
					this.disconnect();
					return Response.status(Response.Status.OK)
							.entity(createMessageContenu("The file was deleted successfully.")).build();
				} else {
					this.disconnect();
					throw new DirectoryOrFileNoFoundException("Could not delete the  file, it may not exist.");
				}
			} catch (IOException e) {
				return Response.status(Response.Status.NOT_FOUND).entity(createMessageContenu("server is dead"))
						.build();
			}

		} else {
			return Response.status(Response.Status.NOT_FOUND)
					.entity(createMessageContenu("No implemented nofile norepertoire")).build();
		}

	}

}
