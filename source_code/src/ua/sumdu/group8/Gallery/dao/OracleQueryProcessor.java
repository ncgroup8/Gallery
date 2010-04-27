package ua.sumdu.group8.Gallery.dao;

import ua.sumdu.group8.Gallery.dao.exceptions.*;
import ua.sumdu.group8.Gallery.*;
import java.util.*;
import java.sql.*;

/**
 * Provides common methods to access gallery data storage based on
 * Oracle database.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 26-Apr-2010
 */
public class OracleQueryProcessor implements IQueryProcessor {

    private String dataSourceName = null;
    
    private Connection getConnection() throws DataAccessException {
        Connection result = null;
        try {
            Context ctx = new InitialContext();
            if (ctx == null) {
                throw new DataAccessException("Cannot get InitialContext.");
            }
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/" 
                    + dataSourceName);
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
            throw new DataAccessException("Database usage error: ", ex);
        }
        return result;
    }

    public OracleQueryProcessor(String source) {
        dataSourceName = source;
        //"jdbc/gallery_oracle"
    }

    /**
     * Adds a new catalogue.
     * 
     * @param cat a catalogue to add.
     * @exception DataAccessException.
     */
    public void addCatalogue(IGalleryCatalogue cat)
            throws DataAccessException {
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(
                GallerySQLConstants.ST_ADD_CAT);
        pst.setInt(1, cat.getParent());
        pst.setString(2, cat.getName());
        pst.setString(3, cat.getDescription());
        try {
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
    }

    /**
     * Adds a new picture.
     * 
     * @param pic a picture to add.
     * @exception DataAccessException .
     */
    public void addPicture(IGalleryPicture pic) 
            throws DataAccessException {
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(
                GallerySQLConstants.ST_ADD_PIC);
        pst.setInt(1, pic.getCatalogue());
        pst.setString(2, pic.getName());
        pst.setString(3, pic.getURL());
        pst.setString(4, pic.getDescription());
        try {
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
    }

    /**
     * Removes a catalogue with specified ID.
     * 
     * @param id an ID to find.
     * @exception DataAccessException .
     */
    public int delCatalogueByID(int id) throws DataAccessException {
        
        return res;
    }

    /**
     * Removes a picture with specified ID.
     * 
     * @param id an ID to find.
     * @exception DataAccessException .
     */
    public IGalleryPicture delPictureByID(int id) throws DataAccessException {
        IGalleryPicture pic = null;
        pic = getPictureByID(id);
        if(pic == null) {
            return pic;
        }
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(
                GallerySQLConstants.ST_DEL_PIC);
        pst.setInt(1, pic.getID());
        try {
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return pic;
    }

    /**
     * Finds a catalogue with specified ID.
     * 
     * @param id an ID to find.
     * @exception DataAccessException .
     */
    public IGalleryCatalogue getCatalogueByID(int id)
            throws DataAccessException {
        IGalleryCatalogue cat = null;
        ResultSet rs = null;
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(
                GallerySQLConstants.ST_GET_CAT_BY_ID);
        pst.setInt(1, id);
        try {
            rs = pst.executeQuery();
            if(!rs.next()) {
                return null;
            } else {
                cat = new GalleryCatalogue();
                cat.setID(rs.getInt(GallerySQLConstants.COL_ID));
                cat.setParent(rs.getInt(GallerySQLConstants.COL_PARENT));
                cat.setName(rs.getString(GallerySQLConstants.COL_NAME));
                cat.setDescription(rs.getString(GallerySQLConstants.COL_DESC));
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return cat;
    }

    /**
     * Finds catalogues with specified name.
     * 
     * @param name a name to find.
     * @param sort a sorting order by name.
     * @exception DataAccessException .
     */
    public List getCataloguesByName(String name, int sort) 
            throws DataAccessException {
        IGalleryCatalogue cat = null;
        ResultSet rs = null;
        List res = null;
        String query = GallerySQLConstants.ST_GET_CAT_BY_NAME;
        if(sort == GallerySQLConstants.SORT_ASC) {
            query += GallerySQLConstants.ORDER_NAME_ASC;
        } else if(sort == GallerySQLConstants.SORT_DESC) {
            query += GallerySQLConstants.ORDER_NAME_DESC;
        }
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, name);
        try {
            rs = pst.executeQuery();
            if(!rs.next()) {
                return null;
            } else {
                res = new LinkedList();
                do {
                    cat = new GalleryCatalogue();
                    cat.setID(rs.getInt(GallerySQLConstants.COL_ID));
                    cat.setParent(rs.getInt(GallerySQLConstants.COL_PARENT));
                    cat.setName(rs.getString(GallerySQLConstants.COL_NAME));
                    cat.setDescription(rs.getString(GallerySQLConstants.COL_DESC));
                    res.add(cat);
                } while(rs.next());
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return res;
    }

    /**
     * Finds catalogues having their parent catalogue ID equals to specified.
     * 
     * @param id a parent ID to find.
     * @param sort a sorting order by name.
     * @exception DataAccessException .
     */
    public List getCataloguesByParent(int id, int sort)
            throws DataAccessException{
        IGalleryCatalogue cat = null;
        ResultSet rs = null;
        List res = null;
        String query = GallerySQLConstants.ST_GET_CAT_BY_PARENT;
        if(sort == GallerySQLConstants.SORT_ASC) {
            query += GallerySQLConstants.ORDER_NAME_ASC;
        } else if(sort == GallerySQLConstants.SORT_DESC) {
            query += GallerySQLConstants.ORDER_NAME_DESC;
        }
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        try {
            rs = pst.executeQuery();
            if(!rs.next()) {
                return null;
            } else {
                res = new LinkedList();
                do {
                    cat = new GalleryCatalogue();
                    cat.setID(rs.getInt(GallerySQLConstants.COL_ID));
                    cat.setParent(rs.getInt(GallerySQLConstants.COL_PARENT));
                    cat.setName(rs.getString(GallerySQLConstants.COL_NAME));
                    cat.setDescription(rs.getString(GallerySQLConstants.COL_DESC));
                    res.add(cat);
                } while(rs.next());
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return res;
    }

    /**
     * Finds a picture with specified ID.
     * 
     * @param id an ID to find.
     * @exception DataAccessException .
     */
    public IGalleryPicture getPictureByID(int id) throws DataAccessException {
        IGalleryPicture pic = null;
        ResultSet rs = null;
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(
                GallerySQLConstants.ST_GET_PIC_BY_ID);
        pst.setInt(1, id);
        try {
            rs = pst.executeQuery();
            if(!rs.next()) {
                return null;
            } else {
                pic = new GalleryPicture();
                pic.setID(rs.getInt(GallerySQLConstants.COL_ID));
                pic.setCatalogue(rs.getInt(GallerySQLConstants.COL_CAT));
                pic.setName(rs.getString(GallerySQLConstants.COL_NAME));
                pic.setURL(rs.getString(GallerySQLConstants.COL_URL));
                pic.setDescription(rs.getString(GallerySQLConstants.COL_DESC));
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return pic;
    }

    /**
     * Finds pictures with specified name.
     * 
     * @param name a name to find.
     * @param sort a sorting order by name.
     * @exception DataAccessException .
     */
    public List getPicturesByName(String name, int sort) 
            throws DataAccessException {
        IGalleryPicture pic = null;
        ResultSet rs = null;
        List res = null;
        String query = GallerySQLConstants.ST_GET_PIC_BY_NAME;
        if(sort == GallerySQLConstants.SORT_ASC) {
            query += GallerySQLConstants.ORDER_NAME_ASC;
        } else if(sort == GallerySQLConstants.SORT_DESC) {
            query += GallerySQLConstants.ORDER_NAME_DESC;
        }
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, name);
        try {
            rs = pst.executeQuery();
            if(!rs.next()) {
                return null;
            } else {
                res = new LinkedList();
                do {
                    pic = new GalleryPicture();
                    pic.setID(rs.getInt(GallerySQLConstants.COL_ID));
                    pic.setCatalogue(rs.getInt(GallerySQLConstants.COL_CAT));
                    pic.setName(rs.getString(GallerySQLConstants.COL_NAME));
                    pic.setURL(rs.getString(GallerySQLConstants.COL_URL));
                    pic.setDescription(rs.getString(GallerySQLConstants.COL_DESC));
                    res.add(pic);
                } while(rs.next());
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return res;
    }

    /**
     * Returns all pictures placed into catalogue with specified ID.
     * 
     * @param id a catalogue ID.
     * @param sort a sorting order by name.
     * @exception DataAccessException .
     */
    public List getPicturesFromCat(int id, int sort)
            throws DataAccessException {
        IGalleryPicture pic = null;
        ResultSet rs = null;
        List res = null;
        String query = GallerySQLConstants.ST_GET_PIC_BY_CAT;
        if(sort == GallerySQLConstants.SORT_ASC) {
            query += GallerySQLConstants.ORDER_NAME_ASC;
        } else if(sort == GallerySQLConstants.SORT_DESC) {
            query += GallerySQLConstants.ORDER_NAME_DESC;
        }
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        try {
            rs = pst.executeQuery();
            if(!rs.next()) {
                return null;
            } else {
                res = new LinkedList();
                do {
                    pic = new GalleryPicture();
                    pic.setID(rs.getInt(GallerySQLConstants.COL_ID));
                    pic.setCatalogue(rs.getInt(GallerySQLConstants.COL_CAT));
                    pic.setName(rs.getString(GallerySQLConstants.COL_NAME));
                    pic.setURL(rs.getString(GallerySQLConstants.COL_URL));
                    pic.setDescription(rs.getString(GallerySQLConstants.COL_DESC));
                    res.add(pic);
                } while(rs.next());
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return res;
    }

    /**
     * Returns a <code>root</code> catalogue.
     * @exception DataAccessException .
     */
    public IGalleryCatalogue getRoot() throws DataAccessException {
        IGalleryCatalogue cat = null;
        ResultSet rs = null;
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(
                GallerySQLConstants.ST_GET_ROOT);
        try {
            rs = pst.executeQuery();
            if(!rs.next()) {
                return null;
            } else {
                cat = new GalleryCatalogue();
                cat.setID(rs.getInt(GallerySQLConstants.COL_ID));
                cat.setParent(rs.getInt(GallerySQLConstants.COL_PARENT));
                cat.setName(rs.getString(GallerySQLConstants.COL_NAME));
                cat.setDescription(rs.getString(GallerySQLConstants.COL_DESC));
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Database usage error.", ex);
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                throw new DataAccessException("Connection closing error.", ex);
            }
        }
        return cat;
    }

    /**
     * Updates specified catalogue.
     * 
     * @param cat a catalogue to update.
     * @exception DataAccessException .
     */
    public int updateCatalogue(IGalleryCatalogue cat)
            throws DataAccessException {
        
    }

    /**
     * Updates specified picture.
     * 
     * @param pic a picture to update.
     * @exception DataAccessException .
     */
    public int updatePicture(IGalleryPicture pic)
            throws DataAccessException {

    }

}