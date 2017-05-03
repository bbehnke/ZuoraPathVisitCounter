package app.model.error;


/**
 * File line parsing expection which will maintain the line data that was not able to be parsed.
 *
 * @author Bradley Behnke
 * @since 1.0
 */
public class FileLineParsingException extends Exception {
    private final String lineData;

    /**
     * Creates instance FileLineParsingException with lineData set and the superclass message set.
     *
     * @param message  message to set.
     * @param lineData linedata to set.
     */
    public FileLineParsingException(String message, String lineData) {
        super(message);
        this.lineData = lineData;
    }

    /**
     * @return current lineData.
     */
    public String lineData() {
        return lineData;
    }
}
