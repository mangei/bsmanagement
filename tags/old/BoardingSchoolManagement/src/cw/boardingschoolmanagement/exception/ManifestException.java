package cw.boardingschoolmanagement.exception;

/**
 * Exception when a manifest attribute in the 'META-INF/manifest.mf' is missing.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class ManifestException
extends RuntimeException
{

    public ManifestException() {
    }

    public ManifestException(String message) {
        super(message);
    }

}
