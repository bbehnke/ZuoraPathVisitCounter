package app.model;

import app.model.error.FileLineParsingException;

/**
 * User key and navigation entry pair with extra information. Extra information includes the line number from the file,
 * the raw line data from the file, a boolean expressing if an error occurred on this line, and an exception that will
 * be set if an error occurred.
 *
 * @author Bradley Behnke
 * @since 1.0
 */
public class UserNavigationEntry {
    private String userKey;
    private String navigationEntry;
    private int lineNumber;
    private String rawLineData;
    private boolean error;
    private FileLineParsingException exception;

    /**
     * @return current userKey.
     */
    public String userKey() {
        return this.userKey;
    }

    /**
     * @return current navigationEntry.
     */
    public String navigationEntry() {
        return this.navigationEntry;
    }

    /**
     * @return current lineNumber.
     */
    public int lineNumber() {
        return this.lineNumber;
    }

    /**
     * @return current rawLineData.
     */
    public String rawLineData() {
        return this.rawLineData;
    }

    /**
     * @return current error.
     */
    public boolean error() {
        return this.error;
    }

    /**
     * @return current exception.
     */
    public FileLineParsingException exception() {
        return this.exception;
    }

    /**
     * @param userKey userKey to set.
     * @return this instance of UserNavigationEntry.
     */
    public UserNavigationEntry userKey(final String userKey) {
        this.userKey = userKey;
        return this;
    }

    /**
     * @param navigationEntry navigationEntry to set.
     * @return this instance of UserNavigationEntry.
     */
    public UserNavigationEntry navigationEntry(final String navigationEntry) {
        this.navigationEntry = navigationEntry;
        return this;
    }

    /**
     * @param lineNumber lineNumber to set.
     * @return this instance of UserNavigationEntry.
     */
    public UserNavigationEntry lineNumber(final int lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    /**
     * @param rawLineData rawLineData to set.
     * @return this instance of UserNavigationEntry.
     */
    public UserNavigationEntry rawLineData(final String rawLineData) {
        this.rawLineData = rawLineData;
        return this;
    }

    /**
     * @param error error to set.
     * @return this instance of UserNavigationEntry.
     */
    public UserNavigationEntry error(final boolean error) {
        this.error = error;
        return this;
    }

    /**
     * @param exception exception to set.
     * @return this instance of UserNavigationEntry.
     */
    public UserNavigationEntry exception(final FileLineParsingException exception) {
        this.exception = exception;
        return this;
    }
}
