package controleur;

import java.io.File;

import model.Image;
import model.ProxyImage;

/**************************************************************
 * @CLASS_TITLE:	Proxy Factory
 * 
 * @Description: 	Classe presque identique a celle de ConcreteFactory
 * 					Voir classe COncreteFactory.
 * 
 * @author:			Alexandre Laroche
 * 
 **************************************************************/
public class ProxyFactory implements ImageFactory {
	
	/*****************************
	 * Interface Instance (Singleton)
	 *****************************/
	private static ImageFactory instance = null;
	
	/******************************************************
	 * Constructeur - ProxyFactory
	 * 
	 * @Resumer:	Cache le constructeur en dehors de 
	 * 				la classe.
	 * 
	 ******************************************************/
	private ProxyFactory() {};
	
	/******************************************************
	 * Build
	 * 
	 * @Resumer:	Initialise la construction Proxy de 
	 * 				l'image importée (BMP).
	 * 
	 * @Return 		Image compilée
	 * 
	 ******************************************************/
	public Image build(File file) {
		return new ProxyImage(file);
	}
	
	/******************************************************
	 * Get Instance	(Singleton)
	 * 
	 * @Resumer:	Creation d'une nouvelle instance de la 
	 * 				classe ProxyFactory si elle est 
	 * 				inexistente.
	 * 
	 * @return 		Instance de la classe ProxyFactory
	 * 
	 ******************************************************/
	public static ImageFactory getInstance() {
		if(instance == null)
			instance = new ProxyFactory();
		return instance;
	}
}
