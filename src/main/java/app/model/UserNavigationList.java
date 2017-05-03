package app.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Representation of a user and each of their navigation entries.
 *
 * @author Bradley Behnke
 * @since 1.0
 */
public class UserNavigationList {
    private final String userKey;
    private final List<String> navigationEntries = new ArrayList<>();

    /**
     * Creates new instance of UserNavigationList with userKey set from the given param.
     *
     * @param userKey userKey to set.
     */
    public UserNavigationList(String userKey) {
        this.userKey = userKey;
    }

    /**
     * @return current userKey.
     */
    public String userKey() {
        return userKey;
    }

    /**
     * Adds a navigation entry to the user's navigation list.
     *
     * @param navigationEntry to add to user navigation list.
     */
    public void addNavigationEntry(String navigationEntry) {
        navigationEntries.add(navigationEntry);
    }

    /**
     * Builds and returns a set of path strings of the given path size from the user's navigation entries.
     *
     * @param pathSize size of paths to include in returned set of paths.
     * @return set of path strings of the given path size from the user's navigation entries.
     * @see Set
     */
    public Set<String> getSequentialNavigationPaths(int pathSize) {
        if (pathSize < 1) {
            throw new InvalidParameterException("Invalid path size: " + pathSize + ".");
        }
        Set<String> paths = new HashSet<>();
        for (int startIdx = 0; startIdx < navigationEntries.size(); startIdx++) {
            int endIdx = startIdx + pathSize;
            if (endIdx > navigationEntries.size()) {
                break;
            }
            List<String> entries = navigationEntries.subList(startIdx, endIdx);
            StringBuilder path = new StringBuilder();
            entries.forEach(entry -> {
                if (path.length() != 0) {
                    path.append(" -> ");
                }
                path.append(entry);
            });
            paths.add(path.toString());
        }
        return paths;
    }

}
