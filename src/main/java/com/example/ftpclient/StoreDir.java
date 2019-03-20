package com.example.ftpclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.FTP;

import com.example.exception.BadAuthentificationException;
import com.example.exception.DirectoryOrFileNoFoundException;

/**
 * La commande Store Dir permet de recuperer un dossier
 * 
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public class StoreDir extends FTPCommand {

	private String pathfile;
	private TypeFile type;
	private String pathDir;

	/**
	 * 
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @param path
	 *            the path of user
	 * @param pathDir
	 *            the path of file to store
	 * @param dir
	 *            the name of dir to store
	 * @param type
	 *            ascii,binary
	 */
	protected StoreDir(String username, String password, String path, String pathDir, String dir, TypeFile type) {
		super(username, password, path);
		this.pathfile = pathDir + "/" + dir;
		this.pathDir = pathDir;
		this.type = type;
	}

	/**
	 * 
	 * @param localFilePath
	 *            localfile
	 * @param remoteFilePath
	 *            remotefile
	 * @return true it is successful else no false
	 * @throws IOException
	 *             exception to write/read
	 */
	public boolean uploadSingleFile(String localFilePath, String remoteFilePath) throws IOException {
		File localFile = new File(localFilePath);

		InputStream inputStream = new FileInputStream(localFile);
		try {
			this.ftp.setFileType(FTP.BINARY_FILE_TYPE);
			return this.ftp.storeFile(remoteFilePath, inputStream);
		} finally {
			inputStream.close();
		}
	}

	/**
	 * Fonction recursive qui telecharge un dossier
	 * 
	 * @param remoteDirPath
	 *            remoteDirPath
	 * @param localParentDir
	 *            localparentdir
	 * @param remoteParentDir
	 *            remoteParentDir
	 * @throws IOException
	 *             IOException
	 * @throws DirectoryOrFileNoFoundException
	 *             No Found the dir,or a file
	 */
	public void uploadDirectory(String remoteDirPath, String localParentDir, String remoteParentDir)
			throws IOException, DirectoryOrFileNoFoundException {

		System.out.println("LISTING directory: " + localParentDir);

		/**File localDir = new File(localParentDir);
		File[] subFiles = localDir.listFiles();
		if (subFiles != null && subFiles.length > 0) {
			for (File item : subFiles) {
				String remoteFilePath = remoteDirPath + "/" + remoteParentDir + "/" + item.getName();
				if (remoteParentDir.equals("")) {
					remoteFilePath = remoteDirPath + "/" + item.getName();
				}

				if (item.isFile()) {
					String localFilePath = item.getAbsolutePath();
					System.out.println("About to upload the file: " + localFilePath);
					boolean uploaded = uploadSingleFile(localFilePath, remoteFilePath);
					if (uploaded) {
						System.out.println("UPLOADED a file to: " + remoteFilePath);
					} else
						throw new DirectoryOrFileNoFoundException("No found" + localFilePath);

				} else {
					boolean created = this.ftp.makeDirectory(remoteFilePath);
					if (created) {
						System.out.println("CREATED the directory: " + remoteFilePath);
					} else
						throw new DirectoryOrFileNoFoundException("No found" + remoteFilePath);

					String parent = remoteParentDir + "/" + item.getName();
					if (remoteParentDir.equals("")) {
						parent = item.getName();
					}

					localParentDir = item.getAbsolutePath();
					uploadDirectory(remoteDirPath, localParentDir, parent);
				}
			}
		}**/
	}

	/**
	 * Store Dir
	 */
	@Override
	public Response execute() throws BadAuthentificationException, DirectoryOrFileNoFoundException, IOException {
		/**try {
			this.connect();
			if (TypeFile.BINARY == type) {
				this.ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
				this.ftp.setFileType(FTP.BINARY_FILE_TYPE);
			}

			else {
				this.ftp.setFileTransferMode(FTP.ASCII_FILE_TYPE);
				this.ftp.setFileType(FTP.BINARY_FILE_TYPE);
			}

			this.uploadDirectory(this.path, this.pathfile, this.pathDir);
			return Response.ok(createMessageContenu("store Sucess")).build();
		} catch (BadAuthentificationException e) {
			throw new BadAuthentificationException("bad login or bad password");
		}

	}**/
		
		return Response.status(Response.Status.NOT_FOUND).entity(createMessageContenu("no impemented"))
				.build();
	}

}
