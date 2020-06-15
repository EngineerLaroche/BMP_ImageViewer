package controleur;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Strict file filter for a JFileChooser that only accepts specific files.
 * 
 * @author Alexandre Laroche
 */
public class ImageFileFilter extends FileFilter {
	/* unique file extension allowed */
	private static final String EXTENSION = "bmp";
	private static final String DESCRIPTION = "Fichier Bitmap (*.bmp)";
	
	@Override
	public boolean accept(File arg0) {
		/* accept directories */
		if(arg0.isDirectory()) 
			return true;
		
		/* check file extensions */
		String name = arg0.getName();
		int pos = name.lastIndexOf(".") + 1;
		
		/* return TRUE if the file's extension matches the awaited extension */
		return name.substring(pos).equals(EXTENSION);
	}

	@Override
	public String getDescription() {
		/* This String will be displayed in the file type combobox */
		return DESCRIPTION;
	}

}
