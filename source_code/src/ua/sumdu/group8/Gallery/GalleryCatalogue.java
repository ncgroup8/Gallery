package ua.sumdu.group8.Gallery;

/**
 * This class represents a gallery catalogue with its specific properties.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 26-Apr-2010
 */
public class GalleryCatalogue implements IGalleryCatalogue {

    private String desc = "";
    private int id = 0;
    private String name = "";
    private int parentID = 0;

    /**
     * Creates an empty <code>GalleryCatalogue</code> object with no 
     * parameters specified.
     */
    public GalleryCatalogue() {
    }
    
    /**
     * Creates a new <code>GalleryCatalogue</code> object with specified 
     * parameters.
     *
     * @param id a catalogue ID.
     * @param parent a parent catalogue ID.
     * @param name a catalogue name.
     * @param desc a catalogue description.
     */
    public GalleryCatalogue(int id, int parent, String name, String desc) {
        this.desc = desc;
        this.name = name;
        this.parentID = id;
        this.id = id;
    }

    /**
     * Returns a description of this catalogue.
     */
    public String getDescription() {
        return this.desc;
    }

    /**
     * Returns an ID for this catalogue.
     */
    public int getID() {
        return this.id;
    }

    /**
     * Returns a name of this catalogue.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns an ID of parent catalogue.
     *
     * @return a parent catalogue ID, or <code>0</code> if is <code>root</code>.
     */
    public int getParent() {
        return this.parentID;
    }

    /**
     * Sets a specified description to current catalogue. 
     *
     * @param desc a new description.
     */
    public void setDescription(String desc) {
        this.desc = desc;
    }
    
    /**
     * Sets an ID of this catalogue.
     *
     * @param id an id to set.
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Sets a specified name to current catalogue.  
     *
     * @param name a new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets an ID of parent catalogue.
     *
     * @param id an id to set.
     */
    public void setParent(int id) {
        this.parentID = id;
    }
    
    /**
     * Returns hash code for this catalogue.
     * The result is hash code over summ of each
     * field hash code.
     * 
     * @return a hash code for this object
     */
    public int hashCode() {
        long res = new Integer( getID() ).hashCode();
        res += new Integer( getParent() ).hashCode();
        res += getName().hashCode();
        res += getDescription().hashCode();
        return new Long(res).hashCode();
    }
    
    /**
     * Returns a string representation of current catalogue. 
     * Returned result is next:
     * "ID=value. Parent=value. Name=value. 
     * Description=value."
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( "ID=" ).append( getID() ).append( ". " );
        sb.append( "Parent=" ).append( getParent() ).append( ". " );
        sb.append( "Name=" ).append( getName() ).append( ". " );
        sb.append( "Description=" ).append( getDescription() ).append( ". " );
        return sb.toString();
    }
    
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
    public boolean equals( Object obj ) {
        if( obj == null ) {
            return false;
        } else if (obj.getClass().equals(this.getClass())) {
            if (obj == this) {
                return true;
            }
            if(getID() != ((GalleryCatalogue) obj).getID()) {
                return false;
            } else if(getParent() != ((GalleryCatalogue) obj).getParent()) {
                return false;
            } else if(!getName().equals(((GalleryCatalogue) obj).getName())) {
                return false;
            } else if(!getDescription().equals(((GalleryCatalogue) obj).getDescription())) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}