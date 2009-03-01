package cw.boardingschoolmanagement.exception;

/**
 * Exception when something isn't initialized yet.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class NotInitializedException
extends RuntimeException
{

    public NotInitializedException() {
    }

    public NotInitializedException(String message) {
        super(message);
    }

}
