package view;

/**************************************************************
 * @CLASS_TITLE:	Application
 * 
 * @Description: 	Cette classe permet simplement de d�marrer 
 * 					l'application de visualisation d'images de
 * 					format BMP non-compress� de 24 bits par 
 * 					pixel seulement.
 * 
 * @author:			Alexandre Laroche
 * 
 **************************************************************/
public class Application {

	/******************************************************
	 * Main
	 * 
	 * @Resumer:	D�marrage de l'application en initialisant
	 * 				la classe qui cr��e la Frame visuel (GUI).
	 * 
	 * @param args 
	 * 
	 ******************************************************/
	public static void main(String args[]) { new ImageViewer(); }
}
