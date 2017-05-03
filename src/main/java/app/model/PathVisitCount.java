package app.model;

/**
 * Representation of the number of times a navigation path was visited.
 *
 * @author Bradley Behnke
 * @since 1.0
 */
public class PathVisitCount {
    private int visitCount;
    private String path;

    /**
     * @return the current navigation path visit count.
     */
    public int visitCount() {
        return this.visitCount;
    }

    /**
     * @return the current navigation path.
     */
    public String path() {
        return this.path;
    }

    /**
     * @param visitCount visitCount to set.
     * @return this instance of PathVisitCount.
     */
    public PathVisitCount visitCount(final int visitCount) {
        this.visitCount = visitCount;
        return this;
    }

    /**
     * @param path path to set.
     * @return this instance of PathVisitCount.
     */
    public PathVisitCount path(final String path) {
        this.path = path;
        return this;
    }

    /**
     * Increments current visit count by 1.
     */
    public void incrementVisitCount() {
        visitCount++;
    }
}
