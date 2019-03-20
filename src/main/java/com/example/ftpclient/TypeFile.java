package com.example.ftpclient;

/**
 * Type de Fichier
 * 
 * @author Thomas Campistron et Sophie Danneels
 *
 */
public enum TypeFile {

	ASCII("A"), BINARY("I");

	/**
	 * Le Type est de base Ascii
	 */
	private String typeName = "A";

	/**
	 * Le constructeur de TYPE
	 * 
	 * @param typeName
	 */
	private TypeFile(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * Methode pour avoir le type en String
	 */
	public String toString() {
		return typeName;
	}
}
