package ua.sumdu.group8.Gallery.dao.exceptions;

/**
 * This class represents exceptional situation while
 * accessing a picture storage.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 30-Apr-2010
 */
public class PictureStorageException extends Exception {

    /**
     * Creates new <code>PictureStorageException</code> object.
     */
    public PictureStorageException() {
    }

    /**
     * Creates a <code>PictureStorageException</code> object
     * with the specified message
     *
     * @param msg a message to describe details
     */
    public PictureStorageException( String msg ) {
        super( msg );
    }
    
    /**
     * Creates a <code>PictureStorageException</code> object
     * with the specified message
     *
     * @param e a reason to describe details
     */
    public PictureStorageException( Throwable e ) {
        super( e );
    }
    
    /**
     * Creates a <code>PictureStorageException</code> object
     * with the specified message
     *
     * @param s a message to describe details
     * @param e a reason to describe details
     */
    public PictureStorageException( String s, Throwable e ) {
        super( s, e );
    }
}