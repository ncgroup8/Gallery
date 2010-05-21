package ua.sumdu.group8.Gallery;

import javax.servlet.http.*;  
import ua.sumdu.group8.Gallery.dao.exceptions.*;


/**
 * This interface provides common features for action processor.
 *
 * @author Anufriev Alexey
 * @version 1.0
 * created 18-May-2010
 */
public interface IActionProcessor {

    /**
     * Process users request.
     */
    public void process( HttpServletRequest request ) throws DataAccessException;
}