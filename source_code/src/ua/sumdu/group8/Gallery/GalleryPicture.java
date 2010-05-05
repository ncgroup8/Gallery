package ua.sumdu.group8.Gallery;

/**
 * This class represents a gallery picture with its specific properties.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 26-Apr-2010
 */
public class GalleryPicture implements IGalleryPicture {

    private int cat = 0;
    private String desc = "";
    private int id = 0;
    private String name = "";
    private String url = "";

    /**
     * Creates an empty <code>GalleryPicture</code> object with no 
     * parameters specified.
     */
    public GalleryPicture() {
    }
    
    /**
     * Creates a new <code>GalleryPicture</code> object with specified 
     * parameters.
     *
     * @param id a picture ID.
     * @param catID a parent catalogue ID.
     * @param url a picture url.
     * @param name a picture name.
     * @param desc a picture description.
     */
    public GalleryPicture(int id, int catID, String url, String name, String desc) {
        this.id = id;
        this.cat = catID;
        this.name = name;
        this.desc = desc;
        this.url = url;
    }

    /**
     * Returns a catalogue ID picture placed in.
     *
     * @return a parent catalogue ID or <code>0</code> if placed in 
     * <code>root</code>
     */
    public int getCatalogue() {
        return cat;
    }

    /**
     * Returns a picture description.
     */
    public String getDescription() {
        return desc;
    }

    /**
     * Returns a picture ID.
     */
    public int getID() {
        return id;
    }

    /**
     * Returns a picture name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a URL for this picture.
     */
    public String getURL() {
        return url;
    }

    /**
     * Sets a parent catalogue for this picture. 
     *
     * @param id a parent catalogue ID.
     */
    public void setCatalogue(int id) {
        this.cat = id;
    }

    /**
     * Sets the picture description. 
     *
     * @param desc a new description.
     */
    public void setDescription(String desc) {
        this.desc = desc;
    }
    
    /**
     * Sets a picture ID. 
     *
     * @param id a picture ID.
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Sets a picture name.
     * 
     * @param name a new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a picture URL. 
     *
     * @param url a new URL.
     */
    public void setURL(String url) {
        this.url = url;
    }
    
    /**
     * Returns hash code for this picture.
     * The result is hash code over summ of each
     * field hash code.
     * 
     * @return a hash code for this object
     */
    public int hashCode() {
        long res = new Integer( getID() ).hashCode();
        res += new Integer( getCatalogue() ).hashCode();
        res += getName().hashCode();
        res += getURL().hashCode();
        res += getDescription().hashCode();
        return new Long(res).hashCode();
    }
    
    /**
     * Returns a string representation of current picture. 
     * Returned result is next:
     * "ID=value. Catalogue=value. Name=value. URL=value.
     * Description=value."
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( "ID=" ).append( getID() ).append( ". " );
        sb.append( "Catalogue=" ).append( getCatalogue() ).append( ". " );
        sb.append( "Name=" ).append( getName() ).append( ". " );
        sb.append( "URL=" ).append( getURL() ).append( ". " );
        sb.append( "Description=" ).append( getDescription() ).append( ". " );
        return sb.toString();
    }
    
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
    public boolean equals( Object obj ) {
        if( obj == null ) {
            return false;
        } else if (obj.getClass().equals(this.getClass())) {
            if (obj == this) {
                return true;
            }
            if(getID() != ((GalleryPicture) obj).getID()) {
                return false;
            } else if(getCatalogue() != ((GalleryPicture) obj).getCatalogue()) {
                return false;
            } else if(!getName().equals(((GalleryPicture) obj).getName())) {
                return false;
            } else if(!getURL().equals(((GalleryPicture) obj).getURL())) {
                return false;
            } else if(!getDescription().equals(((GalleryPicture) obj).getDescription())) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

}