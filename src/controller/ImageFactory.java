package controller;

import java.io.File;
import java.io.IOException;

import model.Image;

/**************************************************************
 * @CLASS_TITLE:	Image Factory	(Interface)
 * 
 * @Description: 	L'interface ImageFactory présente la définition 
 * 					de la méthode utilisée pour créer des objets Image. 
 * 					Une classe redéfinissant l’interface ImageFactory 
 * 					ne doit être utilisée que pour créer un type d’image 
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
	 * @Resumer:	Créé une nouvelle instance d'image concrète 
	 * 				à l'aide du descripteur de fichier.
	 * 
	 * @param 		Le fichier image importée
	 * @return 		Une nouvelle instance d'image concrète.
	 * @throws 		IOException 
	 * 
	 ******************************************************/
	public abstract Image build(File file) throws IOException;
}
