package ua.sumdu.group8.Gallery;

import javax.servlet.http.*; 
import ua.sumdu.group8.Gallery.dao.*;
import ua.sumdu.group8.Gallery.dao.exceptions.*;


/**
 * This class process users request to edit picture.
 *
 * @author Anufriev Alexey
 * @version 1.0
 * created 19-May-2010
 */
public class EditPictureProcessor implements IActionProcessor {

    /**
     * Process users request to edit specified picture
     *
     * @param request - users request
     * @throws DataAccessException
     */
    public void process( HttpServletRequest request ) throws DataAccessException {
    
        IQueryProcessor iqp = QueryProcessor.getInstance();
        if( request.getMethod().equals( "GET" ) ) {
            int id = -1;
            String idLine = request.getParameter( "id" );
            if( ( idLine ).matches( "[\\d]{1,10}" ) ) {
                 id = Integer.parseInt( idLine );
            }
            
            if( request.getParameter( "act" ).equals( "editpic" ) ) {
                if( id != -1 ) {
                    IGalleryPicture pic = iqp.getPictureByID( id );
                    request.getSession().setAttribute( "object", pic );
                    request.getSession().setAttribute( "target", "pic" );
                } else {
                    throw new DataAccessException( "Cant edit specified picture." );
                }
            }
            if( request.getParameter( "act" ).equals( "delpic" ) ) {
                if( id != -1 ) {
                    IGalleryPicture pic = iqp.getPictureByID( id );
                    /*IPictureStorage ips = PictureStorage.getInstance();
                    try {
                        ips.remove( pic );
                    } catch ( PictureStorageException ex ) {
                        throw new DataAccessException( "Cant del specified picture." + ex );
                    }*/
                    iqp.delPictureByID( id );
                    request.getSession().setAttribute( "error", "Picture deleted." );
                } else {
                    throw new DataAccessException( "Cant del specified picture." );
                }
            }
        } else {
            int id = -1;
            int cat = -1;
            String name = null;
            String description = null;
            try {
                id = Integer.parseInt( request.getParameter( "id" ) );
                cat = Integer.parseInt( request.getParameter( "parent" ) );
            } catch ( NumberFormatException ex ) {
                throw new DataAccessException( "Wrong requested id." );
            }
            name = request.getParameter( "name" );
            description = request.getParameter( "desc" );
            String url = iqp.getPictureByID( id ).getURL();
            IGalleryPicture pic = new GalleryPicture( id, cat, url, name, description );
            iqp.updatePicture( pic );
        }
    }
}