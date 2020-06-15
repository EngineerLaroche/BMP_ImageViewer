package controleur;
import java.io.File;
import java.io.IOException;

import model.Image;
import model.CompilationImage;;

/*******************************************************************
 * @Titre_Classe: Concrete Factory
 * 
 * @Resumer: 
 * Classe presque identique a celle de ProxyFactory
 * 
 * @author Alexandre Laroche
 *******************************************************************/
public class ConcreteFactory implements ImageFactory{

	/* Instance de la classe ConcreteFactory (singleton) */
	private static ConcreteFactory _instance = null;

	@Override
	public Image build(File file) throws IOException{
		return new CompilationImage(file);
	}

	/**
	 * Creation d'une nouvelle instance de la classe ConcreteFactory si elle est inexistente.
	 * @return instance de la classe ConcreteFactory
	 */
	public static ImageFactory getInstance(){
		if(_instance == null)
			_instance = new ConcreteFactory();
		return _instance;
	}
}