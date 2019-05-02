package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**************************************************************
 * @CLASS_TITLE:	Image	(Abstract class)
 * 
 * @Description: 	La classe abstraite Image définit des 
 * 					méthodes abstraites pures qui doivent être 
 * 					redéfinies par la classe spécialisée. 
 * 					Toutes les images partagent un attribut 
 * 					commun qui ne peut être stocké que de la 
 * 					même manière: le pointeur sur le descripteur 
 * 					de fichier. Tous les autres attributs peuvent 
 * 					être implémentés différemment. 
 * 					Voir classe ProxyImage.
 * 
 * @author:			Alexandre Laroche
 * 
 **************************************************************/
public abstract class Image {

	/*****************************
	 * Class Instances
	 *****************************/
	protected File _file;
	
	/******************************************************
	 * Paint
	 * 
	 * @Resumer:	Créez un objet BufferedImage pour qu'il 
	 * 				corresponde aux exigences RGB de l'écran.
	 * 
	 * @return		Le fichier image
	 * @throws 		IOException 
	 * 
	 ******************************************************/
	public abstract BufferedImage draw() throws IOException;
	
	/***************************
	 * Get Height
	 * 
	 * @return: Hauteur image
	 * 
	 ***************************/
	public abstract int getHeight();
	
	/***************************
	 * Get Width
	 * 
	 * @return: Largeur image
	 * 
	 ***************************/
	public abstract int getWidth();
	
	
}
