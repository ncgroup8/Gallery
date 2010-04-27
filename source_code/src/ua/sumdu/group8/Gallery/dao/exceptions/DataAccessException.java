package ua.sumdu.group8.Gallery.dao.exceptions;

/**
 * This class represents exceptional situation while
 * accessing a data storage.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 26-Apr-2010
 */
public class DataAccessException extends Exception {

    /**
     * Creates new <code>DataAccessException</code> object.
     */
    public DataAccessException() {
    }

    /**
     * Creates a <code>DataAccessException</code> object
     * with the specified message
     *
     * @param msg a message to describe details
     */
    public DataAccessException( String msg ) {
        super( msg );
    }
    
    /**
     * Creates a <code>DataAccessException</code> object
     * with the specified message
     *
     * @param e a reason to describe details
     */
    public DataAccessException( Throwable e ) {
        super( e );
    }
    
    /**
     * Creates a <code>DataAccessException</code> object
     * with the specified message
     *
     * @param s a message to describe details
     * @param e a reason to describe details
     */
    public DataAccessException( String s, Throwable e ) {
        super( s, e );
    }
}