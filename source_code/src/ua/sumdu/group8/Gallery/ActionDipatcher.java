package ua.sumdu.group8.Gallery;
  
import java.io.*;  
import java.util.*; 
import javax.servlet.*;  
import javax.servlet.http.*;   
import org.apache.commons.fileupload.servlet.*;
import ua.sumdu.group8.Gallery.*;
import ua.sumdu.group8.Gallery.dao.*;
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
                IActionProcessor sc = new ShowCatalogueProcessor();
                sc.process( request );
                response.sendRedirect( request.getContextPath() + "/index.jsp" );
            } else {
                if( request.getParameter( "act" ).equals( "addpic" ) ) {
                    request.getSession().setAttribute( "target", "pic" );
                    request.getSession().setAttribute( "cats", 
                        ShowCatalogueProcessor.getAllCatalogues() );
                    request.getSession().setAttribute( "object", null );
                    response.sendRedirect( request.getContextPath() + "/index.jsp?" 
                        + request.getQueryString() );
                } else if( request.getParameter( "act" ).equals( "addcat" ) ) {
                    request.getSession().setAttribute( "target", "cat" );
                    request.getSession().setAttribute( "cats", 
                        ShowCatalogueProcessor.getAllCatalogues() );
                    request.getSession().setAttribute( "object", null );
                    response.sendRedirect( request.getContextPath() + "/index.jsp?" 
                        + request.getQueryString() );
                } else {
                    if( request.getParameter( "id" ) == null ) {
                        request.getSession().setAttribute( "error", "Wrong or empty ID." );
                        response.sendRedirect( request.getContextPath() + "/index.jsp" );
                    } else {
                        if( request.getParameter( "act" ).equals( "showpic" ) ) {
                            IActionProcessor sp = new ShowPictureProcessor();
                            sp.process( request );
                            response.sendRedirect( request.getContextPath() + "/index.jsp?" 
                                + request.getQueryString() );
                        }
                        
                        if( request.getParameter( "act" ).equals( "showcat" ) ) {
                            IActionProcessor sc = new ShowCatalogueProcessor();
                            sc.process( request );
                            String cutParam = request.getParameter( "cut" );
                            if( cutParam != null && cutParam.equals( "y" ) ) {
                                request.getSession().setAttribute( "cut", "y" );
                                response.sendRedirect( request.getContextPath() + "/view.jsp" );
                            } else {
                                response.sendRedirect( request.getContextPath() + "/index.jsp?" 
                                    + request.getQueryString() );
                            }
                        }
                        
                        if( request.getParameter( "act" ).equals( "editpic" ) ) {
                            IActionProcessor sc = new EditPictureProcessor();
                            sc.process( request );
                            request.getSession().setAttribute( "cats", 
                                ShowCatalogueProcessor.getAllCatalogues() );
                            response.sendRedirect( request.getContextPath() + "/index.jsp?"
                                + request.getQueryString() );
                        }
                        
                        if( request.getParameter( "act" ).equals( "editcat" ) ) {
                            IActionProcessor sc = new EditCatalogueProcessor();
                            sc.process( request );
                            request.getSession().setAttribute( "cats", 
                                ShowCatalogueProcessor.getAllCatalogues() );
                            response.sendRedirect( request.getContextPath() + "/index.jsp?"
                                + request.getQueryString() );
                        }
                        
                        if( request.getParameter( "act" ).equals( "delpic" ) ) {
                            IActionProcessor sc = new EditPictureProcessor();
                            sc.process( request );
                            response.sendRedirect( request.getContextPath() + 
                                "/?act=showcat&id=" + request.getSession().
                                getAttribute( "returnID" ) );
                        }
                        
                        if( request.getParameter( "act" ).equals( "delcat" ) ) {
                            IActionProcessor sc = new EditCatalogueProcessor();
                            sc.process( request );
                            response.sendRedirect( request.getContextPath() + 
                                "/?act=showcat&id=" + request.getSession().
                                getAttribute( "returnID" ) );
                        }
                    }
                }
            }
        } catch ( DataAccessException ex ) {
            request.getSession().setAttribute( "error", "Data access error: " 
                + ex.toString() );
            //response.sendRedirect( request.getContextPath() + "/index.jsp?error" );
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher( "/index.jsp?error" );
            dispatcher.forward(request, response);

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
                if( request.getParameter( "target" ).equals( "cat" ) ) {
                    IActionProcessor sc = new EditCatalogueProcessor();
                    sc.process( request );
                }
                if( request.getParameter( "target" ).equals( "pic" ) ) {
                    IActionProcessor sc = new EditPictureProcessor();
                    sc.process( request );
                }
                response.sendRedirect( request.getContextPath() + "/" );
            } catch ( DataAccessException ex ) {
                request.getSession().setAttribute( "error", "Data access error: " + ex.toString() );
                response.sendRedirect( request.getContextPath() + "/index.jsp" );
            }
        } else {
            try {
                ServletContext sc = getServletContext();
                IGalleryPicture pic = new GalleryPicture();
                PictureStorage.getInstance().store( pic, request, sc );
                
                IQueryProcessor iqp = QueryProcessor.getInstance();
                pic.setID( -1 );
                pic.setCatalogue( iqp.getRoot().getID() );
                request.getSession().setAttribute( "uploadedpic", pic );
                request.getSession().setAttribute( "target", "pic" );
                response.sendRedirect( request.getContextPath() + "/upload.jsp" );
            } catch ( PictureStorageException ex ) {
                request.getSession().setAttribute( "error", "Data storing error: " + ex.toString() );
                response.sendRedirect( request.getContextPath() + "/index.jsp" );
            } catch( DataAccessException ex ) {
                request.getSession().setAttribute( "error", "Data base accessing error: " + ex.toString() );
                response.sendRedirect( request.getContextPath() + "/index.jsp" );
            }
        }
    }
}