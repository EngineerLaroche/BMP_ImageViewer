package vue;

/**
 * Application launcher.
 * 
 * @author Alexandre Laroche
 */
public class Application {

	/**
	 * Application's entry point.
	 * The command line arguments are ignored.
	 * 
	 * @param args 
	 */
	public static void main(String args[]) {
		(new ImageViewer()).init();
	}
}
