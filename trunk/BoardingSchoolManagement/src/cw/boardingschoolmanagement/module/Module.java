package cw.boardingschoolmanagement.module;


/**
 * Every Module has to implement this Module-Interface.
 * 
 * @author Manuel Geier
 */
public interface Module {
    
    /**
     * Initializes the module. 
     * The module can register extentions, mappings, cascadings etc.
     */
    public void init();

}