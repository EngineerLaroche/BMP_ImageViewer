package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**************************************************************
 * @CLASS_TITLE:	Image	(Abstract class)
 * 
 * @Description: 	La classe abstraite Image d�finit des 
 * 					m�thodes abstraites pures qui doivent �tre 
 * 					red�finies par la classe sp�cialis�e. 
 * 					Toutes les images partagent un attribut 
 * 					commun qui ne peut �tre stock� que de la 
 * 					m�me mani�re: le pointeur sur le descripteur 
 * 					de fichier. Tous les autres attributs peuvent 
 * 					�tre impl�ment�s diff�remment. 
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
	 * @Resumer:	Cr�ez un objet BufferedImage pour qu'il 
	 * 				corresponde aux exigences RGB de l'�cran.
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
