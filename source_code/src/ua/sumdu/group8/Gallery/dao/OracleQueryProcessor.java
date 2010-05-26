package ua.sumdu.group8.Gallery.dao;

import ua.sumdu.group8.Gallery.dao.exceptions.*;
import ua.sumdu.group8.Gallery.*;
import javax.naming.*;
import java.util.*;
import java.sql.*;
import javax.sql.*; 

/**
 * Provides common methods to access gallery data storage based on
 * Oracle database.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 26-Apr-2010
 */
public class OracleQueryProcessor implements IQueryProcessor {

    private final static String DATASOURCE = "java:comp/env/jdbc/gallery_oracle";
    
    private final static int ACT_GET_PIC_BY_ID = 1;
    private final static int ACT_GET_PIC_BY_NAME = 2;
    private final static int ACT_GET_PIC_BY_CAT = 3;
    private final static int ACT_GET_CAT_BY_ID = 10;
    private final static int ACT_GET_CAT_BY_NAME = 11;
    private final static int ACT_GET_CAT_BY_PARENT = 12;
    private final static int ACT_GET_ROOT = 13;
    private final static int ACT_ADD_CAT = 21;
    private final static int ACT_UPD_CAT = 22;
    private final static int ACT_ADD_PIC = 31;
    private final static int ACT_UPD_PIC = 32;
    
    /**
     * Returns a connection to specified datasource.
     */
    private Connection getConnection() throws DataAccessException {
        Connection result = null;
        try {
            Context ctx = new InitialContext();
            if (ctx == null) {
                throw new DataAccessException("Cannot get InitialContext.");
            }
            DataSource ds = (DataSource) ctx.lookup(DATASOURCE);
            if (ds != null) {
                result = ds.getConnection();
            } else {
                throw new DataAccessException("Failed to lookup datasource.");
            }
        }
        catch ( NamingException ex ) {
            throw new DataAccessException("Cannot get connection: ", ex);
        }
        catch(SQLException ex){
            throw new DataAccessException("Database connection error: ", ex);
        }
        return result;
    }
    
    /**
     * Returns results of query execution.
     *
     * @param id an id to specify.
     * @param name a name to specify.
     * @param action an action to perform.
     * @param query a wuery to execte.
     * @param sort a sorting order if needed. set <code>0</code> 
     * if no sorting needed at all.
     * @exception DataAccessException
     */
    private List processSelection(int id, String name, int action, String query,
            int sort) throws DataAccessException {
        List res = null;
        Connection con = this.getConnection();
        IGalleryCatalogue cat = null;
        IGalleryPicture pic = null;
        ResultSet rs = null;
        if(sort == GallerySQLConstants.SORT_ASC) {
            query += GallerySQLConstants.ORDER_NAME_ASC;
        } else if(sort == GallerySQLConstants.SORT_DESC) {
            query += GallerySQLConstants.ORDER_NAME_DESC;
        }
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(query);
            switch(action) {
                case ACT_GET_CAT_BY_ID: 
                case ACT_GET_CAT_BY_PARENT:
                case ACT_GET_PIC_BY_CAT:
                case ACT_GET_PIC_BY_ID:
                    pst.setInt(1,id);
                    break;
                    
                case ACT_GET_CAT_BY_NAME:
                case ACT_GET_PIC_BY_NAME:
                    pst.setString(1, name);
                    break;
            }
            rs = pst.executeQuery();
            if(!rs.next()) {
                return null;
            } else {
                res = new LinkedList();
                do {
                    switch(action) {
                        case ACT_GET_CAT_BY_ID:
                        case ACT_GET_CAT_BY_NAME:
                        case ACT_GET_CAT_BY_PARENT:
                        case ACT_GET_ROOT:
                            cat = new GalleryCatalogue();
                            cat.setID(rs.getInt(GallerySQLConstants.COL_ID));
                            cat.setParent(rs.getInt(GallerySQLConstants.COL_PARENT));
                            cat.setName(rs.getString(GallerySQLConstants.COL_NAME));
                            cat.setDescription(rs.getString(
                                    GallerySQLConstants.COL_DESC));
                            res.add(cat);
                            break;
                        
                        case ACT_GET_PIC_BY_CAT:
                        case ACT_GET_PIC_BY_ID:
                        case ACT_GET_PIC_BY_NAME:
                            pic = new GalleryPicture();
                            pic.setID(rs.getInt(GallerySQLConstants.COL_ID));
                            pic.setCatalogue(rs.getInt(GallerySQLConstants.COL_CAT));
                            pic.setName(rs.getString(GallerySQLConstants.COL_NAME));
                            pic.setURL(rs.getString(GallerySQLConstants.COL_URL));
                            pic.setDescription(rs.getString(
                                    GallerySQLConstants.COL_DESC));
                            res.add(pic);
                            break;
                    } 
                } while(rs.next());
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pst != null) pst.close();
                if(con != null) con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return res;
    }
    
    /**
     * Returns results of query execution (update and insert queries).
     *
     * @param pic an object to add / modify.
     * @param cat an object to add / modify.
     * @param action an action to perform.
     * @param query a wuery to execte.
     * @exception DataAccessException
     */
    private int processUpdate(IGalleryPicture pic, IGalleryCatalogue cat, 
            String query, int action) throws DataAccessException {
        Connection con = this.getConnection();
        PreparedStatement pst = null;
        int res = 0;
        try {
            pst = con.prepareStatement(query);
            switch(action) {
                case ACT_UPD_CAT:
                case ACT_ADD_CAT:
                    pst.setInt(1, cat.getParent());
                    pst.setString(2, cat.getName());
                    pst.setString(3, cat.getDescription());
                    if(action == ACT_UPD_CAT) {
                        pst.setInt(4, cat.getID());
                    }
                    break;
                    
                case ACT_UPD_PIC:
                case ACT_ADD_PIC:
                    pst.setInt(1, pic.getCatalogue());
                    pst.setString(2, pic.getName());
                    pst.setString(3, pic.getURL());
                    pst.setString(4, pic.getDescription());
                    if(action == ACT_UPD_PIC) {
                        pst.setInt(5, pic.getID());
                    }
                    break;
            }
            res = pst.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                if(pst != null) pst.close();
                if(con != null) con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return res;
    }

    /**
     * Creates a new <code>OracleQueryProcessor</code> with the specified 
     * DataSource to connect to.
     *
     * @param source a DataSource to connect to.
     */
    public OracleQueryProcessor() {
    }

    /**
     * Adds a new catalogue.
     * 
     * @param cat a catalogue to add.
     * @exception DataAccessException.
     */
    public void addCatalogue(IGalleryCatalogue cat)
            throws DataAccessException {
        processUpdate(null, cat, GallerySQLConstants.ST_ADD_CAT, ACT_ADD_CAT);
     }

    /**
     * Adds a new picture.
     * 
     * @param pic a picture to add.
     * @exception DataAccessException .
     */
    public void addPicture(IGalleryPicture pic) 
            throws DataAccessException {
        processUpdate(pic, null, GallerySQLConstants.ST_ADD_PIC, ACT_ADD_PIC);
    }

    /**
     * Removes a catalogue with specified ID.
     * All subcatalogues and pictures within specified catalogue are deleted
     * too.
     * 
     * @param id an ID to find.
     * @return number of matching objects actually deleted.
     * @exception DataAccessException .
     */
    public int delCatalogueByID(int id) throws DataAccessException {
        int res = 0;
        List subcats = getCataloguesByParent(id, GallerySQLConstants.SORT_ASC);
        IGalleryCatalogue cat = null;
        if(subcats != null) {
            for(Iterator it=subcats.iterator();it.hasNext();) {
                cat = (IGalleryCatalogue) it.next();
                delCatalogueByID(cat.getID());
            }
        }
        List pictures = getPicturesFromCat(id, GallerySQLConstants.SORT_ASC);
        if(pictures != null) {
            IGalleryPicture pic = null;
            for(Iterator it=pictures.iterator();it.hasNext();) {
                pic = (IGalleryPicture) it.next();
                delPictureByID(pic.getID());
            }
        }
        Connection con = this.getConnection();
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(
                GallerySQLConstants.ST_DEL_CAT);
            pst.setInt(1, id);
            res = pst.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                if(pst != null) pst.close();
                if(con != null) con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return res;
    }

    /**
     * Removes a picture with specified ID.
     * 
     * @param id an ID to find.
     * @return number of matching objects actually deleted.
     * @exception DataAccessException .
     */
    public int delPictureByID(int id) throws DataAccessException {
        IGalleryPicture pic = null;
        int res = 0;
        pic = getPictureByID(id);
        if(pic == null) {
            return 0;
        }
        Connection con = this.getConnection();
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(
                GallerySQLConstants.ST_DEL_PIC);
            pst.setInt(1, pic.getID());
            res = pst.executeUpdate();
            PictureStorage.getInstance().remove(pic);
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } catch (PictureStorageException ex) {
            throw new DataAccessException("Picture removing error.", ex);
        } finally {
            try {
                if(pst != null) pst.close();
                if(con != null) con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return res;
    }

    /**
     * Finds a catalogue with specified ID.
     * 
     * @param id an ID to find.
     * @return matching catalogue object or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public IGalleryCatalogue getCatalogueByID(int id)
            throws DataAccessException {
        List res = processSelection(id, null, ACT_GET_CAT_BY_ID, 
                GallerySQLConstants.ST_GET_CAT_BY_ID, 0);
        IGalleryCatalogue cat = null;
        if(res != null && !res.isEmpty()) {
            cat = (IGalleryCatalogue) res.get(0);
        }
        return cat;
    }

    /**
     * Finds catalogues with specified name.
     * 
     * @param name a name to find.
     * @param sort a sorting order by name.
     * @return matching catalogues list or <code>null</code> if nothing found.
     * @exception DataAccessException.
     */
    public List getCataloguesByName(String name, int sort) 
            throws DataAccessException {
        return processSelection(0, name, ACT_GET_CAT_BY_NAME, 
                GallerySQLConstants.ST_GET_CAT_BY_NAME, sort);
    }

    /**
     * Finds catalogues having their parent catalogue ID equals to specified.
     * 
     * @param id a parent ID to find.
     * @param sort a sorting order by name.
     * @return matching catalogues list or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public List getCataloguesByParent(int id, int sort)
            throws DataAccessException{
        return processSelection(id, null, ACT_GET_CAT_BY_PARENT, 
                GallerySQLConstants.ST_GET_CAT_BY_PARENT, sort);
    }

    /**
     * Finds a picture with specified ID.
     * 
     * @param id an ID to find.
     * @return matching picture object or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public IGalleryPicture getPictureByID(int id) throws DataAccessException {
        List res = processSelection(id, null, ACT_GET_PIC_BY_ID, 
                GallerySQLConstants.ST_GET_PIC_BY_ID, 0);
        IGalleryPicture pic = null;
        if(res != null && !res.isEmpty()) {
            pic = (IGalleryPicture) res.get(0);
        }
        return pic;
    }

    /**
     * Finds pictures with specified name.
     * 
     * @param name a name to find.
     * @param sort a sorting order by name.
     * @return matching pictures list or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public List getPicturesByName(String name, int sort) 
            throws DataAccessException {
        return processSelection(0, name, ACT_GET_PIC_BY_NAME, 
                GallerySQLConstants.ST_GET_PIC_BY_NAME, sort);
    }

    /**
     * Returns all pictures placed into catalogue with specified ID.
     * 
     * @param id a catalogue ID.
     * @param sort a sorting order by name.
     * @return matching pictures list or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public List getPicturesFromCat(int id, int sort)
            throws DataAccessException {
        return processSelection(id, null, ACT_GET_PIC_BY_CAT, 
                GallerySQLConstants.ST_GET_PIC_BY_CAT, sort);
    }

    /**
     * Returns a <code>root</code> catalogue.
     *
     * @return root catalogue object or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public IGalleryCatalogue getRoot() throws DataAccessException {
        List res = processSelection(0, null, ACT_GET_ROOT, 
                GallerySQLConstants.ST_GET_ROOT, 0);
        IGalleryCatalogue cat = null;
        if(res != null && !res.isEmpty()) {
            cat = (IGalleryCatalogue) res.get(0);
        }
        return cat;
    }

    /**
     * Updates specified catalogue.
     * 
     * @param cat a catalogue to update.
     * @return count of actually updated objects.
     * @exception DataAccessException.
     */
    public int updateCatalogue(IGalleryCatalogue cat)
            throws DataAccessException {
        return processUpdate(null, cat, GallerySQLConstants.ST_UPD_CAT, 
                ACT_UPD_CAT);
    }

    /**
     * Updates specified picture.
     * 
     * @param pic a picture to update.
     * @return count of actually updated objects.
     * @exception DataAccessException .
     */
    public int updatePicture(IGalleryPicture pic)
            throws DataAccessException {
        return processUpdate(pic, null, GallerySQLConstants.ST_UPD_PIC, 
                ACT_UPD_PIC);
    }

}