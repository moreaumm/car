package configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigServeur classe qui permet de lire un fichier de configuration pour une
 * PasserelleRest
 *
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public class ConfigServeur {

	/**
	 * Properties qui permet de lire un fichier
	 */
	Properties prop = new Properties();

	/**
	 * InputStream lis un fichier
	 */
	InputStream input = null;

	/**
	 * Numéro de port
	 */
	public Integer port;

	/**
	 * Nom du Serveur
	 */
	public String serveur;
	
	/**
	 * Mode Active
	 */
	public boolean mode_actif;

	/**
	 * Constructeur du ConfigServeur
	 */
	public ConfigServeur() {
		try {

			input = new FileInputStream("config.properties");
			prop.load(input);
			this.port = Integer.parseInt(prop.getProperty("port"));
			this.serveur = prop.getProperty("serveur");
			if (prop.getProperty("mode_actif").equals("true")){
				System.out.println("mode Active");
				mode_actif = true;
			}
			else{
				System.out.println("mode Passive");
				mode_actif = false;
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	/**
	 * get mode actif if true else passif
	 * @return boolean 
	 */
	public  boolean getModeActive(){
		return this.mode_actif;
	}

	/**
	 * GetPort
	 * 
	 * @return port
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * GetString
	 * 
	 * @return String
	 */
	public String getServeur() {
		return this.serveur;
	}
}
