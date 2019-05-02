package controleur;
import java.io.File;
import java.io.IOException;

import model.Image;
import model.CompileImage;;

/**************************************************************
 * @CLASS_TITLE:	Concrete Factory
 * 
 * @Description: 	Classe presque identique a celle de ProxyFactory
 * 					Voir classe ProxyFactory.
 * 
 * @author:			Alexandre Laroche
 * 
 **************************************************************/
public class ConcreteFactory implements ImageFactory{

	/*****************************
	 * Class Instances (Singleton)
	 *****************************/
	private static ConcreteFactory instance = null;

	/******************************************************
	 * Build
	 * 
	 * @Resumer:	Initialise la construction de l'image
	 * 				importée (BMP).
	 * 
	 * @Return 		Image compilée
	 * 
	 ******************************************************/
	@Override
	public Image build(File file) throws IOException{
		return new CompileImage(file);
	}

	/******************************************************
	 * Get Instance	(Singleton)
	 * 
	 * @Resumer:	Creation d'une nouvelle instance de la 
	 * 				classe ConcreteFactory si elle est 
	 * 				inexistente.
	 * 
	 * @return 		Instance de la classe ConcreteFactory
	 * 
	 ******************************************************/
	public static ImageFactory getInstance(){
		if(instance == null)	
			instance = new ConcreteFactory();
		return instance;
	}
}