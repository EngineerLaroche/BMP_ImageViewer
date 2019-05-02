package controller;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**************************************************************
 * @CLASS_TITLE:	Image File Filter
 * 
 * @Description: 	Filtre de fichier strict pour un JFileChooser 
 * 					n'acceptant que des fichiers sp�cifiques.
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
	 * 				l'extension du fichier import�e.
	 * 
	 * @param 		Fichier image import�
	 * @return		True or False (image valide)
	 * 
	 ******************************************************/
	@Override
	public boolean accept(File arg0) {
		
		//Accept directories
		if(arg0.isDirectory()) 		
			return true;
		
		//V�rifie l'extensiondu fichier import�e
		String name = arg0.getName();
		int pos = name.lastIndexOf(".") + 1;
		
		//Retourne "true" si l'extension du fichier correspond � l'extension attendue
		return name.substring(pos).equals(EXTENSION);
	}

	/******************************************************
	 * Get Description
	 * 
	 * @Resumer:	Cette cha�ne sera affich�e dans la liste 
	 * 				d�roulante des types de fichiers.
	 * 
	 * @return		Description de l'extension du fichier 
	 * 				� importer. 
	 * 
	 ******************************************************/
	@Override
	public String getDescription() { return DESCRIPTION; }
}
