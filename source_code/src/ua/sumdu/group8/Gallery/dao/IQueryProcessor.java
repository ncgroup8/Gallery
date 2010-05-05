package ua.sumdu.group8.Gallery.dao;

import ua.sumdu.group8.Gallery.dao.exceptions.*;
import ua.sumdu.group8.Gallery.*;
import java.util.List;

/**
 * Provides common methods to access gallery data storage.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 26-Apr-2010
 */
public interface IQueryProcessor {

    /**
     * Adds a new catalogue.
     * 
     * @param cat a catalogue to add.
     * @exception DataAccessException.
     */
    public void addCatalogue(IGalleryCatalogue cat) throws DataAccessException;

    /**
     * Adds a new picture.
     * 
     * @param pic a picture to add.
     * @exception DataAccessException.
     */
    public void addPicture(IGalleryPicture pic) throws DataAccessException;

    /**
     * Removes a catalogue with specified ID.
     * All subcatalogues and pictures within specified catalogue are deleted
     * too.
     * 
     * @param id an ID to find.
     * @return number of matching objects actually deleted.
     * @exception DataAccessException .
     */
    public int delCatalogueByID(int id) throws DataAccessException;

    /**
     * Removes a picture with specified ID.
     * 
     * @param id an ID to find.
     * @return number of matching objects actually deleted.
     * @exception DataAccessException .
     */
    public int delPictureByID(int id) throws DataAccessException;

    /**
     * Finds a catalogue with specified ID.
     * 
     * @param id an ID to find.
     * @return matching catalogue object or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public IGalleryCatalogue getCatalogueByID(int id) throws DataAccessException;

    /**
     * Finds catalogues with specified name.
     * 
     * @param name a name to find.
     * @param sort a sorting order by name.
     * @return matching catalogues list or <code>null</code> if nothing found.
     * @exception DataAccessException.
     */
    public List getCataloguesByName(String name, int sort) 
            throws DataAccessException;

    /**
     * Finds catalogues having their parent catalogue ID equals to specified.
     * 
     * @param id a parent ID to find.
     * @param sort a sorting order by name.
     * @return matching catalogues list or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public List getCataloguesByParent(int id, int sort) 
            throws DataAccessException;

    /**
     * Finds a picture with specified ID.
     * 
     * @param id an ID to find.
     * @return matching picture object or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public IGalleryPicture getPictureByID(int id) throws DataAccessException;

    /**
     * Finds pictures with specified name.
     * 
     * @param name a name to find.
     * @param sort a sorting order by name.
     * @return matching pictures list or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public List getPicturesByName(String name, int sort) 
            throws DataAccessException;

    /**
     * Returns all pictures placed into catalogue with specified ID.
     * 
     * @param id a catalogue ID.
     * @param sort a sorting order by name.
     * @return matching pictures list or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public List getPicturesFromCat(int id, int sort) throws DataAccessException;

    /**
     * Returns a <code>root</code> catalogue.
     *
     * @return root catalogue object or <code>null</code> if nothing found.
     * @exception DataAccessException .
     */
    public IGalleryCatalogue getRoot() throws DataAccessException;

    /**
     * Updates specified catalogue.
     * 
     * @param cat a catalogue to update.
     * @return count of actually updated objects.
     * @exception DataAccessException.
     */
    public int updateCatalogue(IGalleryCatalogue cat) throws DataAccessException;

    /**
     * Updates specified picture.
     * 
     * @param pic a picture to update.
     * @return count of actually updated objects.
     * @exception DataAccessException .
     */
    public int updatePicture(IGalleryPicture pic) throws DataAccessException;

}