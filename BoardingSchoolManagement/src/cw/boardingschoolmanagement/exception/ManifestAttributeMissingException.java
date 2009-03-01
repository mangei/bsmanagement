package cw.boardingschoolmanagement.exception;

/**
 * Exception when a manifest attribute in the 'META-INF/manifest.mf' is missing.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class ManifestAttributeMissingException
extends RuntimeException
{

    public ManifestAttributeMissingException() {
    }

    public ManifestAttributeMissingException(String message) {
        super(message);
    }

}
