package ua.sumdu.group8.Gallery;

import javax.servlet.http.*;  
import java.util.*;
import ua.sumdu.group8.Gallery.dao.*;
import ua.sumdu.group8.Gallery.dao.exceptions.*;


/**
 * This class process users request to show catalogue.
 *
 * @author Anufriev Alexey
 * @version 1.0
 * created 18-May-2010
 */
public class ShowCatalogueProcessor implements IActionProcessor {

    /**
     * Process users request to show specified category
     *
     * @param request - users request
     * @throws DataAccessException
     */
    public void process( HttpServletRequest request ) throws DataAccessException {
    
        IQueryProcessor iqp = QueryProcessor.getInstance();
        request.getSession().setAttribute( "target", "cat" );
        if( request.getMethod().equals( "GET" ) ) {
            int id = -1;
            int sort = GallerySQLConstants.SORT_ASC;
            if( request.getQueryString() == null ) {
                id = iqp.getRoot().getID();
            } else {
                String idLine = request.getParameter( "id" );
                if( ( idLine ).matches( "[\\d]{1,10}" ) ) {
                    id = Integer.parseInt( idLine );
                }
            }
            if( id != -1 ) {
                if( iqp.getCatalogueByID( id ) == null ) {
                    throw new DataAccessException( "Directory you request not exist." );
                }
                List cats = iqp.getCataloguesByParent( id, sort );
                List pics = iqp.getPicturesFromCat( id, sort );
                request.getSession().setAttribute( "cats", cats );
                request.getSession().setAttribute( "pics", pics );
                List path = getPath( id );
                request.getSession().setAttribute( "path", path );
            } else {
                throw new DataAccessException( "Cant access specified directory." );
            }
        } else {
            // POST REQUEST
        }
    }
    
    /**
     * Returns list of elements that shows the path from root to current directory
     *
     * @param fromID - first element of the path
     * @throws DataAccessException
     */
    public static List getPath( int fromID ) throws DataAccessException {
    
        IQueryProcessor iqp = QueryProcessor.getInstance();
        List path = new LinkedList();
        IGalleryCatalogue current = iqp.getCatalogueByID( fromID );
        while( current.getID() != iqp.getRoot().getID() ) {
            path.add( current );
            current = iqp.getCatalogueByID( current.getParent() );
        }
        path.add( iqp.getCatalogueByID( iqp.getRoot().getID() ) );
        return path;
    }
    
    /**
     * Returns list of all categories in database
     *
     * @throws DataAccessException
     */
    public static List getAllCatalogues() throws DataAccessException {
        IQueryProcessor iqp = QueryProcessor.getInstance();
        List cats = getSubCatalogues( iqp.getRoot().getID() );
        cats.add( iqp.getRoot() );
        return cats;
    }
    
    /**
     * Returns list subcatalogues
     *
     * @param id - parent id for catalogues to search
     * @throws DataAccessException
     */
    private static List getSubCatalogues( int id ) throws DataAccessException {
        IQueryProcessor iqp = QueryProcessor.getInstance();
        List result = new LinkedList();
        List cats = iqp.getCataloguesByParent( id, GallerySQLConstants.SORT_ASC );
        if( cats != null ) {
            result.addAll( cats );
            Iterator it = cats.iterator();
            while( it.hasNext() ) {
                List sub = getSubCatalogues( ( ( IGalleryCatalogue )it.next() ).getID() );
                if( sub != null ) {
                    result.addAll( sub );
                }
            }
        }
        return result;
    }
}