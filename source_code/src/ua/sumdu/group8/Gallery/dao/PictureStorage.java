package ua.sumdu.group8.Gallery.dao;

/**
 * Allows to get an instance of current picture storage object.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 26-Apr-2010
 */
public class PictureStorage {

    private static IPictureStorage instance = null;
    
    /**
     * Returns a new object, implementing IPictureStorage interface.
     */
    public static IPictureStorage getInstance() {
        if(instance == null) {
            instance = new FilePictureStorage();
        }
        return instance;
    }
}