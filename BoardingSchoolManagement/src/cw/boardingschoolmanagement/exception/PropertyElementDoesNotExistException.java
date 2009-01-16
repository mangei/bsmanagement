package cw.boardingschoolmanagement.exception;

/**
 * Exception when a property does not exist.
 * 
 * @author Manuel Geier (CreativeWorkers)
 */
public class PropertyElementDoesNotExistException
extends RuntimeException
{

    public PropertyElementDoesNotExistException(String message) {
        super(message);
    }

    public PropertyElementDoesNotExistException() {
    }
    
}
