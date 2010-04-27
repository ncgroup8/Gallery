package ua.sumdu.group8.Gallery;

/**
 * This interface provides common features for pictures in gallery.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 26-Apr-2010
 */
public interface IGalleryPicture {

    /**
     * Returns a catalogue ID picture placed in.
     *
     * @return a parent catalogue ID or <code>0</code> if placed in 
     * <code>root</code>
     */
    public int getCatalogue();

    /**
     * Returns a picture description.
     */
    public String getDescription();

    /**
     * Returns a picture ID.
     */
    public int getID();

    /**
     * Returns a picture name.
     */
    public String getName();

    /**
     * Returns a URL for this picture.
     */
    public URL getURL();

    /**
     * Sets a parent catalogue for this picture. 
     *
     * @param id a parent catalogue ID.
     */
    public void setCatalogue(int id);

    /**
     * Sets the picture description. 
     *
     * @param desc a new description.
     */
    public void setDescription(String desc);
    
    /**
     * Sets a picture ID. 
     *
     * @param id a picture ID.
     */
    public void setID(int id);

    /**
     * Sets a picture name.
     * 
     * @param name a new name.
     */
    public void setName(String name);

    /**
     * Sets a picture URL. 
     *
     * @param url a new URL.
     */
    public void setURL(URL url);
    
    /**
     * Returns hash code for this picture.
     * The result is hash code over summ of each
     * field hash code.
     * 
     * @return a hash code for this object
     */
    public int hashCode();
    
    /**
     * Returns a string representation of current picture. 
     * Returned result is next:
     * "ID=value. Catalogue=value. Name=value. URL=value.
     * Description=value."
     */
    public String toString();
    
    /**
     * Compares this object to the specified object.
     * The result is <code>true</code> if and only if the argument is not null 
     * and is a <code>GalleryPicture</code> object that contains same 
     * task information over each field.
     *
     * @param obj - the object to compare with.
     * @return <code>true</code> if the objects represent the same values; 
     * <code>false</code> otherwise.
     */
    public boolean equals( Object obj );

}