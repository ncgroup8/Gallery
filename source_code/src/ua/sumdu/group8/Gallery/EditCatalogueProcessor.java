package ua.sumdu.group8.Gallery;

import javax.servlet.http.*;  
import java.util.*;
import ua.sumdu.group8.Gallery.dao.*;
import ua.sumdu.group8.Gallery.dao.exceptions.*;


/**
 * This class process users request to edit catalogue.
 *
 * @author Anufriev Alexey
 * @version 1.0
 * created 19-May-2010
 */
public class EditCatalogueProcessor implements IActionProcessor {

    /**
     * Process users request to edit specified catalogue
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
            if( request.getParameter( "act" ).equals( "editcat" ) ) {
                if( id != -1 ) {
                    IGalleryCatalogue cat = iqp.getCatalogueByID( id );
                    request.getSession().setAttribute( "object", cat );
                    request.getSession().setAttribute( "target", "cat" );
                } else {
                    throw new DataAccessException( "Cant edit specified picture." );
                }
            }
            if( request.getParameter( "act" ).equals( "delcat" ) ) {
                if( id != -1 ) {
                    int parent = iqp.getCatalogueByID( id ).getParent();
                    iqp.delCatalogueByID( id );
                    request.getSession().setAttribute( "returnID", 
                        new Integer( parent ) );
                } else {
                    throw new DataAccessException( "Cant del specified catalogue." );
                }
            }
        } else {
            int id = -1;
            int parent = -1;
            String name = null;
            String description = null;
            try {
                id = Integer.parseInt( request.getParameter( "id" ) );
                parent = Integer.parseInt( request.getParameter( "parent" ) );
            } catch ( NumberFormatException ex ) {
                throw new DataAccessException( "Wrong requested id." );
            }
            name = request.getParameter( "name" );
            description = request.getParameter( "desc" );
            if( parent != -1 ) {
                if( request.getParameter( "mode" ).equals( "edit" ) ) {
                    IGalleryCatalogue cat = new GalleryCatalogue( id, parent, name, description );
                    iqp.updateCatalogue( cat );
                }
                if( request.getParameter( "mode" ).equals( "add" ) ) {
                    IGalleryCatalogue cat = new GalleryCatalogue( 0, parent, name, description );
                    iqp.addCatalogue( cat );
                }
            }
            request.getSession().setAttribute( "returnID", new Integer( parent ) );
        }
    }
}