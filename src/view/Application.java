package view;

/**************************************************************
 * @CLASS_TITLE:	Application
 * 
 * @Description: 	Cette classe permet simplement de démarrer 
 * 					l'application de visualisation d'images de
 * 					format BMP non-compressé de 24 bits par 
 * 					pixel seulement.
 * 
 * @author:			Alexandre Laroche
 * 
 **************************************************************/
public class Application {

	/******************************************************
	 * Main
	 * 
	 * @Resumer:	Démarrage de l'application en initialisant
	 * 				la classe qui créée la Frame visuel (GUI).
	 * 
	 * @param args 
	 * 
	 ******************************************************/
	public static void main(String args[]) { new ImageViewer(); }
}
