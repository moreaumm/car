package com.example;

import java.io.IOException;
import java.io.InputStream;
//import java.util.logging.Logger;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;
import com.example.ftpclient.Authent;
import com.example.ftpclient.Cdup;
import com.example.ftpclient.Cwd;
import com.example.ftpclient.Delete;
import com.example.ftpclient.List;
import com.example.ftpclient.Mkdir;
import com.example.ftpclient.Rename;
import com.example.ftpclient.Retr;
import com.example.ftpclient.Store;
import com.example.ftpclient.TypeDirOrFile;
import com.example.ftpclient.TypeFile;

/**
 *
 * La classe pour MyResource
 *
 * @author Thomas Campistron et Sophie Danneels
 *
 */
@Path("ftp")
public class MyResource {
	//private Logger logger = Logger.getLogger("logger");

	/**
	 * Permet de vérifier si le mot de passe et le nom d'utilisateur permettent
	 * de s'enregistrer sur le serveur ftp.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return Response that will be returned as a APPLICATION/JSON response.
	 * @throws BadAuthentificationException
	 *             bad authentification when login fails
	 */
	@GET
	@Path("connect")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthentification(@NotNull @HeaderParam("username") String username,
			@HeaderParam("password") String password) throws BadAuthentificationException {
		System.out.println("In connect");
		Authent a = new Authent(username, password, "/", "Welcome to FTP/Passerelle");
		return a.execute();
	}

	/**
	 * Vérifie si le mot de passe et le nom d'utilisateur permettent de
	 * s'enregistrer sur le serveur ftp.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return Response that will be returned as a APPLICATION/JSON response.
	 * @throws BadAuthentificationException
	 *             bad authentification when login fails
	 */
	@GET
	@Path("quit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuit(@NotNull @HeaderParam("username") String username, @HeaderParam("password") String password)
			throws BadAuthentificationException {
		System.out.println("in list");
		Authent a = new Authent(username, password, "/", "Good bye");
		return a.execute();
	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "APPLICATION/JSON" media type.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return Response that will be returned as a APPLICATION/JSON response.
	 * @throws BadAuthentificationException
	 *             bad authentification when login fails
	 */
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList(@NotNull @HeaderParam("username") String username, @HeaderParam("password") String password)
			throws BadAuthentificationException {
		System.out.println("in list");

		List l = new List(username, password, "/");

		return l.execute();
	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "APPLICATION/JSON" media type.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param path
	 *            the path of user
	 * @return Response that will be returned as a APPLICATION/JSON response.
	 * @throws BadAuthentificationException
	 *             bad authentification when login fails
	 */
	@GET
	@Path("{path:.*}/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList(@NotNull @HeaderParam("username") String username, @HeaderParam("password") String password,
			@PathParam("path") String path) throws BadAuthentificationException {
		System.out.println("in list");
		String path_inter = "/" + path;
		List l = new List(username, password, path_inter);

		return l.execute();
	}

	/**
	 * Permet de créer un répertoire sur le serveur ftp.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param path
	 *            the path of user to create a repertory
	 * @return Response that will be returned as a APPLICATION/JSON response.
	 * @throws BadAuthentificationException
	 *             bad authentification when login fails
	 */
	@POST
	@Path("{path:.*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postMkdir(@NotNull @HeaderParam("username") String username,
			@HeaderParam("password") String password, @PathParam("path") String path)
			throws BadAuthentificationException {
		System.out.println("in mkdir");

		String path_inter = "";
		int taille = path.split("/").length;
		int taille_boucle = 0;
		String dir = "";
		for (String retval : path.split("/")) {
			if (taille_boucle == taille - 1) {
				dir = retval;
				break;
			}
			path_inter += "/" + retval;
			taille_boucle += 1;
		}
		Mkdir p = new Mkdir(username, password, path_inter, dir);
		return p.execute();
	}

	/**
	 * Indique s'il est possible de se déplacer au chemin indiqué.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param path
	 *            where the user wants to go
	 * @return Response that will be returned as a APPLICATION/JSON response.
	 * @throws BadAuthentificationException
	 *             BadAuthentificationException bad authentification when login
	 *             fails
	 * @throws DirectoryOrFileNoFoundException
	 *             no found the path where the user wants to go
	 */
	@GET
	@Path("{path:.*}/cwd")
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCwd(@NotNull @HeaderParam("username") String username, @HeaderParam("password") String password,
			@PathParam("path") String path) throws BadAuthentificationException, DirectoryOrFileNoFoundException {
		System.out.println("in cwd");
		Cwd p = new Cwd(username, password, path);
		return p.execute();
	}

	/**
	 * Indique s'il est possible de se déplacer à la racine.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return Response that will be returned as a APPLICATION/JSON response.
	 * @throws BadAuthentificationException
	 *             BadAuthentificationException bad authentification when login
	 *             fails
	 * @throws DirectoryOrFileNoFoundException
	 *             no found the path where the user wants to go
	 */
	@GET
	@Path("cwd")
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCwd(@NotNull @HeaderParam("username") String username, @HeaderParam("password") String password)
			throws BadAuthentificationException, DirectoryOrFileNoFoundException {
		System.out.println("in cwd /");
		Cwd p = new Cwd(username, password, "/");
		return p.execute();
	}

	/**
	 * Indique s'il est possible de se déplacer au chemin parent indiqué.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param path
	 *            where the user wants to go parent found
	 * @return Response that will be returned as a APPLICATION/JSON response.
	 * @throws BadAuthentificationException
	 *             BadAuthentificationException bad authentification when login
	 *             fails
	 * @throws DirectoryOrFileNoFoundException
	 *             no found the path where the user wants to go
	 */
	@GET
	@Path("{path:.*}/cdup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCdup(@NotNull @HeaderParam("username") String username, @HeaderParam("password") String password,
			@PathParam("path") String path) throws BadAuthentificationException, DirectoryOrFileNoFoundException {
		System.out.println("in cdup");

		Cdup p = new Cdup(username, password, path);
		return p.execute();
	}

	/**
	 * Indique s'il est possible de se déplacer au chemin parent indiqué au
	 * niveau de la racine.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return Response that will be returned as a APPLICATION/JSON response.
	 * @throws BadAuthentificationException
	 *             BadAuthentificationException bad authentification when login
	 *             fails
	 * @throws DirectoryOrFileNoFoundException
	 *             no found the path where the user wants to go
	 */
	@GET
	@Path("/cdup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCdup(@NotNull @HeaderParam("username") String username, @HeaderParam("password") String password)
			throws BadAuthentificationException, DirectoryOrFileNoFoundException {
		System.out.println("in cdup");

		Cdup p = new Cdup(username, password, "/");
		return p.execute();
	}

	/**
	 * Save a file on the server ftp
	 *
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @param path
	 *            path where user wanted
	 * @param isp
	 *            the inputstream
	 * @param file
	 *            file
	 * @param type
	 *            ascii/binary
	 * @return Response that will be returned as a APPLICATION/JSON response
	 * @throws IOException
	 *             exception read/write
	 * @throws BadAuthentificationException
	 *             BadAuthentificationException bad authentification when login
	 *             fails
	 * @throws DirectoryOrFileNoFoundException
	 *             no found the path where the user wants to go
	 */
	@POST
	@Path("{path:.*}/file")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postStore(@NotNull @HeaderParam("username") String username,
			@HeaderParam("password") String password, @PathParam("path") String path,
			@FormDataParam("file") InputStream isp, @FormDataParam("file") FormDataContentDisposition file,
			@QueryParam("type") String type)
			throws IOException, BadAuthentificationException, DirectoryOrFileNoFoundException {
		//String dl = file.getFileName();
		Store p;
		if (type.equals("ascii"))
			p = new Store(username, password, path, isp, TypeFile.ASCII);
		else
			p = new Store(username, password, path, isp, TypeFile.BINARY);
		return p.execute();
	}

	/**
	 * update a file on the server ftp
	 *
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @param file
	 *            the path of file
	 * @param type
	 *            ascii/binary
	 * @return Response that will be returned as a APPLICATION/JSON response
	 * @throws BadAuthentificationException
	 *             BadAuthentificationException bad authentification when login
	 *             fails
	 * @throws IOException
	 *             exception write/read
	 */
	@GET
	@Path("{file:.*}/file")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getRetr(@NotNull @HeaderParam("username") String username, @HeaderParam("password") String password,
			@PathParam("file") String file, @QueryParam("type") String type)
			throws BadAuthentificationException, IOException {
		System.out.println("in retr");
		Retr p;

		if (type.equals("ascii"))
			p = new Retr(username, password, file, TypeFile.ASCII);
		else
			p = new Retr(username, password, file, TypeFile.BINARY);
		System.out.println("before return [RETR]");
		return p.execute();
	}

	/**
	 * rename a file or directory.
	 *
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @param path
	 *            path where the user want to rename
	 * @param newfile
	 *            the new name
	 * @return Response that will be returned as a APPLICATION/JSON response
	 * @throws BadAuthentificationException
	 *             bad login (error username or password)
	 * @throws DirectoryOrFileNoFoundException
	 *             no found the file/dir to rename
	 */
	@PUT
	@Path("{path:.*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response putRename(@NotNull @HeaderParam("username") String username,
			@HeaderParam("password") String password, @NotNull @PathParam("path") String path,
			@NotNull @QueryParam("newfile") String newfile)
			throws BadAuthentificationException, DirectoryOrFileNoFoundException {

		System.out.println("in rename");
		String path_inter = "";
		int taille = path.split("/").length;
		int taille_boucle = 0;
		String file;
		file = "";
		for (String retval : path.split("/")) {
			if (taille_boucle == taille - 1) {
				file = retval;
				break;
			}
			path_inter += "/" + retval;
			taille_boucle += 1;
		}
		Rename p = new Rename(username, password, path_inter, file, newfile);
		return p.execute();
	}

	/**
	 * delete a directory and file( un dossier et tous ses sous dossiers /
	 * fichiers)
	 *
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @param path
	 *            path
	 * @param type
	 *            type(dir or file)
	 * @return Response that will be returned as a APPLICATION/JSON response
	 * @throws BadAuthentificationException
	 *             bad login (error username or password)
	 * @throws DirectoryOrFileNoFoundException
	 *             no found the file/dir to delete
	 */
	@DELETE
	@Path("{path:.*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@NotNull @HeaderParam("username") String username, @HeaderParam("password") String password,
			@PathParam("path") String path, @QueryParam("type") String type)
			throws BadAuthentificationException, DirectoryOrFileNoFoundException {
		System.out.println("in deletedir");

		int taille = path.split("/").length;
		int taille_boucle = 0;
		String dir;
		dir = "";
		String path_inter = "";
		for (String retval : path.split("/")) {
			if (taille_boucle == taille) {
				dir = retval;
			}
			path_inter += "/" + retval;
			taille += 1;
		}
		Delete p;
		if (type.equals("dir"))
			p = new Delete(username, password, path, dir, TypeDirOrFile.DIR);
		else
			p = new Delete(username, password, path, dir, TypeDirOrFile.FILE);
		return p.execute();
	}
}
