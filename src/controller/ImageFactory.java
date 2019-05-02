package controller;

import java.io.File;
import java.io.IOException;

import model.Image;

/**************************************************************
 * @CLASS_TITLE:	Image Factory	(Interface)
 * 
 * @Description: 	L'interface ImageFactory pr�sente la d�finition 
 * 					de la m�thode utilis�e pour cr�er des objets Image. 
 * 					Une classe red�finissant l�interface ImageFactory 
 * 					ne doit �tre utilis�e que pour cr�er un type d�image 
 * 					(par exemple, GIF, JPEG, format de fichier, etc.).
 * 					Voir classe ProxyFactory.
 * 
 * @author:			Alexandre Laroche
 * 
 **************************************************************/
public interface ImageFactory {

	/******************************************************
	 * Build	(abstract)
	 * 
	 * @Resumer:	Cr�� une nouvelle instance d'image concr�te 
	 * 				� l'aide du descripteur de fichier.
	 * 
	 * @param 		Le fichier image import�e
	 * @return 		Une nouvelle instance d'image concr�te.
	 * @throws 		IOException 
	 * 
	 ******************************************************/
	public abstract Image build(File file) throws IOException;
}
