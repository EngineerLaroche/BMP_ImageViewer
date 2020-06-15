package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import controleur.ConcreteFactory;

/**
 * The ProxyImage object acts as an intermediate between the actual Image and
 * the object using the Image. As long as the Image doesn't need to be drawn,
 * the ProxyImage will sent information to the caller (false one in most cases,
 * but it is better than a NullPointerException).
 * 
 * @author Alexandre Laroche
 */
public class ProxyImage extends Image {

	/** The actual subject the proxy is replacing */
	private Image _concrete = null;
	
	/**
	 * Create a proxy to cover for the actual Image.
	 * @param file The handle to the image file.
	 */
	public ProxyImage(File file) {
		super._file = file;		
	}
	
	@Override
	public BufferedImage draw() throws IOException {
		
		//Va chercher l'instance de la classe ConcreteFactory
		_concrete = ConcreteFactory.getInstance().build(super._file);
		
		/* if the Image is not created, instanciate it */
		if (_concrete != null)
			return _concrete.draw();
		
		/* if the Image could not be created for some reason, return null */
		return null;
	}

	@Override
	public int getHeight() {
		/* if the Image is not created, send false information */
		if(_concrete == null)
			return 100;
		else
			return _concrete.getHeight();
	}

	@Override
	public int getWidth() {
		/* if the Image is not created, send false information */
		if(_concrete == null)
			return 100;
		else
			return _concrete.getWidth();
	}
}
