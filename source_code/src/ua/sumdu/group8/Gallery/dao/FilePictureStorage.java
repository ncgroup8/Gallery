package ua.sumdu.group8.Gallery.dao;

import ua.sumdu.group8.Gallery.dao.exceptions.*;
import ua.sumdu.group8.Gallery.*;
import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*;  
import java.util.*;

import org.apache.commons.fileupload.*; 
import org.apache.commons.fileupload.disk.*;  
import org.apache.commons.fileupload.util.*;
import org.apache.commons.fileupload.servlet.*;  


/**
 * Provides common methods to access picture storage.
 *
 * @author Anufriev Alexey
 * @version 1.0
 * created 05-May-2010
 */
public class FilePictureStorage implements IPictureStorage {

    private Random random = new Random(); 
    public final static String FILES_CAT = "/files/";

    /**
     * Stores a new picture.
     * 
     * @param pic a picture to store.
     * @param req a request containing picture.
     * @param sc servlet context.
     * @exception PictureStorageException.
     */
    public void store( IGalleryPicture pic, HttpServletRequest req, ServletContext sc ) 
            throws PictureStorageException {
                
		if ( !ServletFileUpload.isMultipartContent( req ) ) {
            throw new PictureStorageException( "Server error:" + 
                HttpServletResponse.SC_BAD_REQUEST );
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024 * 1024);
		File tempDir = ( File )sc.getAttribute( "javax.servlet.context.tempdir" ); 
		factory.setRepository( tempDir );
		ServletFileUpload upload = new ServletFileUpload( factory );
		upload.setSizeMax( 1024 * 1024 * 10 );
		try {
			List items = upload.parseRequest( req );
			Iterator iter = items.iterator();
			while ( iter.hasNext() ) {
			    FileItem item = ( FileItem )iter.next();
			    if ( !item.isFormField() ) {		    	
			        pic.setURL( processUploadedFile( item, sc ) );
			    }
			}			
		} catch ( Exception e ) {
			throw new PictureStorageException( e );
		}		    
    }

    /**
     * Stores uploaded picture to hdd.
     * 
     * @param item a picture to store.
     * @param sc servlet context.
     * @exception Exception.
     */
	private String processUploadedFile( FileItem item, ServletContext sc ) throws Exception {
    
		File uploadetFile = null;
        String path = null;
        String fileName = new File( item.getName() ).getName();
		do {
			path = sc.getRealPath( FILES_CAT + random.nextInt() + fileName );
			uploadetFile = new File( path );		
		} while( uploadetFile.exists() );
		uploadetFile.createNewFile();
		item.write( uploadetFile );
        return path;
	}

    /**
     * Removes a specified picture.
     * 
     * @param pic a picture to remove.
     * @exception PictureStorageException.
     */
    public void remove( IGalleryPicture pic ) throws PictureStorageException {
        
        File f = new File( pic.getURL() );
        if ( !f.exists() ) {
            throw new PictureStorageException( "No such file or directory: " + pic.getURL() );
        }
        if ( !f.canWrite() ) {
            throw new PictureStorageException( "Write protected: " + pic.getURL() );
        }
        if ( !f.delete() ) {
            throw new PictureStorageException( "Deletion failed." );
        }
    }
}