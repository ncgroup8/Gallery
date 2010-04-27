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
                result.AutoCommit(false);
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
        if(cat.getParent() != GallerySQLConstants.ROOT) {
            pst.setInt(1, cat.getParent());
        } else {
            pst.setNull(1, Types.INTEGER);
        }
        pst.setString(2, cat.getName());
        if (cat.getDescription() != null) {
            pst.setString(3, cat.getDescription());
        } else {
            pst.setNull(3, Types.VARCHAR);
        }
        try {
            pst.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            con.rollback();
            throw new DataAccessException("Catalogue adding error.", ex);
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
        if(pic.getCatalogue() != GallerySQLConstants.ROOT) {
            pst.setInt(1, pic.getCatalogue());
        } else {
            pst.setNull(1, Types.INTEGER);
        }
        pst.setString(2, pic.getName());
        pst.setString(3, pic.getURL());
        if (pic.getDescription() != null) {
            pst.setString(4, pic.getDescription());
        } else {
            pst.setNull(4, Types.VARCHAR);
        }
        try {
            pst.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            con.rollback();
            throw new DataAccessException("Picture adding error.", ex);
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
    public int delPictureByID(int id) throws DataAccessException {
        int res = 0;
        IGalleryPicture pic = null;
        pic = getPictureByID(id);
        if(pic == null) {
            return 0;
        }
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(
                GallerySQLConstants.ST_DEL_PIC);
        pst.setInt(1, pic.getID());
        try {
            res = pst.executeUpdate();
            con.commit();
            // physically files deleting logics
        } catch (SQLException ex) {
            con.rollback();
            throw new DataAccessException("Picture removing error.", ex);
        } finally {
            try {
                con.close();
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
     * @exception DataAccessException .
     */
    public IGalleryCatalogue getCatalogueByID(int id)
            throws DataAccessException {
        IGalleryCatalogue cat = null;
        ResultSet rs = null;
        Connection con = this.getConnection();
        PreparedStatement pst = con.prepareStatement(
                GallerySQLConstants.ST_DEL_PIC);
        pst.setInt(1, pic.getID());
        try {
            rs = pst.executeQuery();
            if(!rs.next()) {
                return null;
            } else {
                
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Picture removing error.", ex);
        } finally {
            try {
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
     * @exception DataAccessException .
     */
    public List getCataloguesByName(String name) throws DataAccessException {
        return null;
    }

    /**
     * Finds catalogues having their parent catalogue ID equals to specified.
     * 
     * @param sort sorting order.
     * @param id a parent ID to find.
     * @exception DataAccessException .
     */
    public List getCataloguesByParent(int sort, int id)
            throws DataAccessException{
        return null;
    }

    /**
     * Finds a picture with specified ID.
     * 
     * @param id an ID to find.
     * @exception DataAccessException .
     */
    public IGalleryPicture getPictureByID(int id) throws DataAccessException {
        return null;
    }

    /**
     * Finds pictures with specified name.
     * 
     * @param name a name to find.
     * @exception DataAccessException .
     */
    public List getPicturesByName(String name) throws DataAccessException {
        return null;
    }

    /**
     * Returns all pictures placed into catalogue with specified ID.
     * 
     * @param sort sorting order.
     * @param id a catalogue ID.
     * @exception DataAccessException .
     */
    public List getPicturesFromCat(int sort, int id)
            throws DataAccessException {
        return null;
    }

    /**
     * Returns a <code>root</code> catalogue.
     * @exception DataAccessException .
     */
    public IGalleryCatalogue getRoot() throws DataAccessException {
        return null;
    }

    /**
     * Opens needed connections and prepares data storage for work.
     * @exception DataAccessException .
     */
    public void open() throws DataAccessException {
        Connection con = null;
        try {
            Class.forName(options.getDriverClass());
            con = DriverManager.getConnection(options.getConnectionURL(), 
                options.getUserName(), options.getPasword());
            st_add_cat = 
            st_add_pic = con.prepareStatement(GallerySQLConstants.ST_ADD_PIC);
            st_del_cat = con.prepareStatement(GallerySQLConstants.ST_DEL_CAT);
            st_del_pic = con.prepareStatement(GallerySQLConstants.ST_DEL_PIC);
            st_upd_cat = con.prepareStatement(GallerySQLConstants.ST_UPD_CAT);
            st_upd_pic = con.prepareStatement(GallerySQLConstants.ST_UPD_PIC);
            st_get_cat_by_id = con.prepareStatement(
                    GallerySQLConstants.ST_GET_CAT_BY_ID);
            st_get_cat_by_name = con.prepareStatement(
                    GallerySQLConstants.ST_GET_CAT_BY_NAME);
            st_get_cat_by_parent = con.prepareStatement(
                    GallerySQLConstants.ST_GET_CAT_BY_PARENT);
            st_get_pic_by_id = con.prepareStatement(
                    GallerySQLConstants.ST_GET_PIC_BY_ID);
            st_get_pic_by_name = con.prepareStatement(
                    GallerySQLConstants.ST_GET_PIC_BY_NAME);
            st_get_pic_by_cat = con.prepareStatement(
                    GallerySQLConstants.ST_GET_PIC_BY_CAT);
            st_get_root = con.prepareStatement(GallerySQLConstants.ST_GET_ROOT);
        } catch (ExceptionInInitializerError ex) {
            throw new DataAccessException("Initialization failed.", ex);
        } catch (LinkageError ex) {
            throw new DataAccessException("Linkage failed.", ex);
        } catch (ClassNotFoundException ex) {
            throw new DataAccessException("No driver class found.", ex);
        } catch (SQLException ex) {
            throw new DataAccessException("Database using error.", ex);
        }
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