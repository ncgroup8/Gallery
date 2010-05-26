package ua.sumdu.group8.Gallery;

import javax.servlet.http.*;  
import java.util.*;
import ua.sumdu.group8.Gallery.dao.*;
import ua.sumdu.group8.Gallery.dao.exceptions.*;


/**
 * This class process users request to show picture.
 *
 * @author Anufriev Alexey
 * @version 1.0
 * created 18-May-2010
 */
public class ShowPictureProcessor implements IActionProcessor {

    /**
     * Process users request to show specified picture
     *
     * @param request - users request
     * @throws DataAccessException
     */
    public void process( HttpServletRequest request ) throws DataAccessException {
    
        IQueryProcessor iqp = QueryProcessor.getInstance();
        request.getSession().setAttribute( "target", "pic" );
        if( request.getMethod().equals( "GET" ) ) {
            int id = -1;
            String idLine = request.getParameter( "id" );
            if( ( idLine ).matches( "[\\d]{1,10}" ) ) {
                 id = Integer.parseInt( idLine );
            }
            if( id != -1 ) {
                IGalleryPicture pic = iqp.getPictureByID( id );
                request.getSession().setAttribute( "pic", pic );
                List path = ShowCatalogueProcessor.getPath( pic.getCatalogue() );
                request.getSession().setAttribute( "path", path );
            } else {
                throw new DataAccessException( "Cant access specified picture." );
            }
        } else {
            // POST REQUEST
        }
    }
}