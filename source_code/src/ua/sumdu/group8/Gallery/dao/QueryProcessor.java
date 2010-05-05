package ua.sumdu.group8.Gallery.dao;

/**
 * Allows to get an instance of current storage query processor object.
 *
 * @author Buzov Andrey
 * @version 1.0
 * created 26-Apr-2010
 */
public class QueryProcessor {

    private static IQueryProcessor instance = null;
    
    /**
     * Returns a new object, implementing IQueryProcessor interface.
     */
    public static IQueryProcessor getInstance() {
        if(instance == null) {
            instance = new OracleQueryProcessor();
        }
        return instance;
    }
}