package ua.sumdu.group8.Gallery.dao;

import ua.sumdu.group8.Gallery.dao.exceptions.*;
import ua.sumdu.group8.Gallery.*;
import javax.servlet.http.*; 
import javax.servlet.*; 

/**
 * Provides common methods to access picture storage.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 30-Apr-2010
 */
public interface IPictureStorage {

    /**
     * Stores a new picture.
     * 
     * @param pic a picture to store.
     * @param req a request containing picture.
     * @exception PictureStorageException.
     */
    public void store(IGalleryPicture pic, HttpServletRequest req, ServletContext sc) 
            throws PictureStorageException;
            
    /**
     * Removes a specified picture.
     * 
     * @param pic a picture to remove.
     * @exception PictureStorageException.
     */
    public void remove(IGalleryPicture pic) throws PictureStorageException;

}