package ua.sumdu.group8.Gallery;

/**
 * This interface provides common features for gallery catalogues.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 26-Apr-2010
 */
public interface IGalleryCatalogue {

    /**
     * Returns a description of this catalogue.
     */
    public String getDescription();

    /**
     * Returns an ID for this catalogue.
     */
    public int getID();

    /**
     * Returns a name of this catalogue.
     */
    public String getName();

    /**
     * Returns an ID of parent catalogue.
     *
     * @return a parent catalogue ID, or <code>0</code> if is <code>root</code>.
     */
    public int getParent();

    /**
     * Sets a specified description to current catalogue. 
     *
     * @param desc a new description.
     */
    public void setDescription(String desc);

    /**
     * Sets an ID of this catalogue.
     *
     * @param id an id to set.
     */
    public void setID(int id);
    
    /**
     * Sets a specified name to current catalogue.  
     *
     * @param name a new name.
     */
    public void setName(String name);

    /**
     * Sets an ID of parent catalogue.
     *
     * @param id an id to set.
     */
    public void setParent(int id);
    
    /**
     * Returns hash code for this catalogue.
     * The result is hash code over summ of each
     * field hash code.
     * 
     * @return a hash code for this object
     */
    public int hashCode();
    
    /**
     * Returns a string representation of current catalogue. 
     * Returned result is next:
     * "ID=value. Parent=value. Name=value. 
     * Description=value."
     */
    public String toString();
    
    /**
     * Compares this object to the specified object.
     * The result is <code>true</code> if and only if the argument is not null 
     * and is a <code>GalleryCatalogue</code> object that contains same 
     * task information over each field.
     *
     * @param obj - the object to compare with.
     * @return <code>true</code> if the objects represent the same values; 
     * <code>false</code> otherwise.
     */
    public boolean equals( Object obj );

}