package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import controleur.ConcreteFactory;


/**************************************************************
 * @CLASS_TITLE:	Proxy Image
 * 
 * @Description: 	The ProxyImage object acts as an intermediate 
 * 					between the actual Image and the object using 
 * 					the Image. As long as the Image doesn't need 
 * 					to be drawn, the ProxyImage will sent information 
 * 					to the caller (false one in most cases, but it 
 * 					is better than a NullPointerException).
 * 
 * @author:			Alexandre Laroche
 * 
 **************************************************************/
public class ProxyImage extends Image {

	/*****************************
	 * Class Instances
	 *****************************/
	private Image concrete = null;	//Objet r�el remplac� par le proxy
	

	/******************************************************
	 * Constructeur - Proxy Image
	 * 
	 * @Resumer:	Cr�ez un proxy pour couvrir l'image r�elle.
	 * 
	 * @param 		Fichier image import�e
	 * 
	 ******************************************************/
	public ProxyImage(File file) {
		super._file = file;		
	}
	
	/******************************************************
	 * Draw
	 * 
	 * @Resumer:	Fonction qui permet de dessiner l'image
	 * 				sur un panneau.
	 * 
	 ******************************************************/
	@Override
	public BufferedImage draw() throws IOException {
		
		//Va chercher l'instance de la classe ConcreteFactory
		concrete = ConcreteFactory.getInstance().build(super._file);
		
		//Si l'image n'est pas cr��e, on en instancie une.
		if (concrete != null)	return concrete.draw();
		
		//Si l'image n'a pas pu �tre cr��e , retourne null
		return null;
	}

	/******************************************************
	 * Get Height
	 * 
	 * @Resumer:	Gestion d'erreur de r�solution.
	 * 				Si l'image n'est pas cr��e, on envoi de 
	 * 				fausses informations, sinon on retourne
	 * 				la bonne d'imension de l'image.
	 * 
	 * @return		Hauteur de l'image
	 * 
	 ******************************************************/
	@Override
	public int getHeight() {
		if(concrete == null)	return 100;
		else					return concrete.getHeight();
	}

	/******************************************************
	 * Get Height
	 * 
	 * @Resumer:	Gestion d'erreur de r�solution.
	 * 				Si l'image n'est pas cr��e, on envoi de 
	 * 				fausses informations, sinon on retourne
	 * 				la bonne d'imension de l'image.
	 * 
	 * @return		Largeur de l'image
	 * 
	 ******************************************************/
	@Override
	public int getWidth() {
		if(concrete == null)	return 100;
		else					return concrete.getWidth();
	}
}
