package controller;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**************************************************************
 * @CLASS_TITLE:	Image File Filter
 * 
 * @Description: 	Filtre de fichier strict pour un JFileChooser 
 * 					n'acceptant que des fichiers spécifiques.
 * 
 * @author:			Alexandre Laroche
 * 
 **************************************************************/
public class ImageFileFilter extends FileFilter {

	/*****************************
	 * Constantes - File extension
	 *****************************/
	private static final String EXTENSION = "bmp";
	private static final String DESCRIPTION = "Fichier Bitmap (*.bmp)";
	
	
	/******************************************************
	 * Accept
	 * 
	 * @Resumer:	Cette fonction permet de valider 
	 * 				l'extension du fichier importée.
	 * 
	 * @param 		Fichier image importé
	 * @return		True or False (image valide)
	 * 
	 ******************************************************/
	@Override
	public boolean accept(File arg0) {
		
		//Accept directories
		if(arg0.isDirectory()) 		
			return true;
		
		//Vérifie l'extensiondu fichier importée
		String name = arg0.getName();
		int pos = name.lastIndexOf(".") + 1;
		
		//Retourne "true" si l'extension du fichier correspond à l'extension attendue
		return name.substring(pos).equals(EXTENSION);
	}

	/******************************************************
	 * Get Description
	 * 
	 * @Resumer:	Cette chaîne sera affichée dans la liste 
	 * 				déroulante des types de fichiers.
	 * 
	 * @return		Description de l'extension du fichier 
	 * 				à importer. 
	 * 
	 ******************************************************/
	@Override
	public String getDescription() { return DESCRIPTION; }
}
