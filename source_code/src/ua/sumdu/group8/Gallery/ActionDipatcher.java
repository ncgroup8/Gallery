package ua.sumdu.group8.Gallery;
  
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.*;  
import javax.servlet.http.*;   
import ua.sumdu.group8.Gallery.dao.exceptions.*;

   
/**
 * This class represents action dispatcher servlet.
 *
 * @author Anufriev Alexey
 * @version 1.0
 * created 18-May-2010
 */
public class ActionDipatcher extends HttpServlet {  
  
    /**
     * Catch GET request from user and dispatch it.
     *
     * @param request - user's request
     * @param response - response to user
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        
        request.getSession().setAttribute( "absolutePath", request.getContextPath() );
        try {
            if( request.getQueryString() == null ) {
                ShowCatalogueProcessor sc = new ShowCatalogueProcessor();
                sc.process( request );
                response.sendRedirect( request.getContextPath() + "/index.jsp" );
            } else {
                if( request.getParameter( "id" ) == null ) {
                    request.getSession().setAttribute( "error", "Wrong or empty ID." );
                    response.sendRedirect( request.getContextPath() + "/index.jsp" );
                } else {
                    if( request.getParameter( "act" ).equals( "showpic" ) ) {
                        ShowPictureProcessor sp = new ShowPictureProcessor();
                        sp.process( request );
                        response.sendRedirect( request.getContextPath() + "/index.jsp" );
                    }
                    
                    if( request.getParameter( "act" ).equals( "showcat" ) ) {
                        ShowCatalogueProcessor sc = new ShowCatalogueProcessor();
                        sc.process( request );
                        response.sendRedirect( request.getContextPath() + "/index.jsp" );
                    }
                    
                    if( request.getParameter( "act" ).equals( "editpic" ) ) {
                        EditPictureProcessor sc = new EditPictureProcessor();
                        sc.process( request );
                        response.sendRedirect( request.getContextPath() + "/edit.jsp" );
                    }
                    
                    if( request.getParameter( "act" ).equals( "editcat" ) ) {
                        EditCatalogueProcessor sc = new EditCatalogueProcessor();
                        sc.process( request );
                        response.sendRedirect( request.getContextPath() + "/edit.jsp" );
                    }
                    
                    if( request.getParameter( "act" ).equals( "delpic" ) ) {
                        EditPictureProcessor sc = new EditPictureProcessor();
                        sc.process( request );
                        response.sendRedirect( request.getContextPath() + "/index.jsp" );
                    }
                    
                    if( request.getParameter( "act" ).equals( "delcat" ) ) {
                        EditCatalogueProcessor sc = new EditCatalogueProcessor();
                        sc.process( request );
                        response.sendRedirect( request.getContextPath() + "/index.jsp" );
                    }
                }
            }
        } catch ( DataAccessException ex ) {
            request.getSession().setAttribute( "error", "Data access error: " + ex.toString() );
            response.sendRedirect( request.getContextPath() + "/index.jsp" );
        }
    }
    
    /**
     * Catch POST request from user and dispatch it.
     *
     * @param request - user's request
     * @param response - response to user
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        if( request.getParameter( "mode" ) != null ) {
            try {
                if( request.getSession().getAttribute( "target" ).equals( "cat" ) ) {
                    EditCatalogueProcessor sc = new EditCatalogueProcessor();
                    sc.process( request );
                }
                if( request.getSession().getAttribute( "target" ).equals( "pic" ) ) {
                    EditPictureProcessor sc = new EditPictureProcessor();
                    sc.process( request );
                }
                response.sendRedirect( request.getContextPath() + "/" );
            } catch (DataAccessException ex) {
                request.getSession().setAttribute( "error", "Data access error: " + ex.toString() );
                response.sendRedirect( request.getContextPath() + "/index.jsp" );
            }
        }
    }
}