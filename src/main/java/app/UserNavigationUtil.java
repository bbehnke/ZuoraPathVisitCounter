package app;

import app.model.PathVisitCount;
import app.model.UserNavigationEntry;
import app.model.UserNavigationList;
import app.model.error.FileLineParsingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User navigation utility.
 * Includes the ability to parse a user navigation data file and the ability to find the number of times unique
 * navigation paths were visited by users.
 *
 * @author Bradley Behnke
 * @since 1.0
 */
public class UserNavigationUtil {

    /**
     * Given user navigation entries and the size of paths to process this method will return a list of path visit counts
     * in descending order from most visited to least visited.
     *
     * @param userNavigationEntries the list of user navigation entries to process.
     * @param pathSize              the size of navigation paths to include in processing.
     * @param resultSize            the max size of the results to return.
     * @return the list of path visit counts
     * @see List
     * in descending order from most visited to least visited.
     */
    public static List<PathVisitCount> getPathVisitCountsFromEntries(List<UserNavigationEntry> userNavigationEntries, int pathSize, int resultSize) {
        return getPathVisitCountsFromLists(getUserNavigationLists(userNavigationEntries), pathSize, resultSize);
    }

    /**
     * Given user navigation lists and the size of paths to process this method will return a list of path visit counts
     * in descending order from most visited to least visited.
     *
     * @param userNavigationLists the list of user navigation lists to process.
     * @param pathSize            the size of navigation paths to include in processing.
     * @param resultSize          the max size of the results to return.
     * @return the list of path visit counts
     * @see List
     * in descending order from most visited to least visited.
     */
    public static List<PathVisitCount> getPathVisitCountsFromLists(List<UserNavigationList> userNavigationLists, int pathSize, int resultSize) {
        final Map<String, PathVisitCount> pathVisitCountMap = new HashMap<>();
        userNavigationLists.stream()
                .map(userNavigationList -> userNavigationList.getSequentialNavigationPaths(pathSize))
                .flatMap(Set::stream)
                .forEach(path -> {
                    PathVisitCount pathVisitCount = pathVisitCountMap.get(path);
                    if (pathVisitCount == null) {
                        pathVisitCount = new PathVisitCount().path(path);
                        pathVisitCountMap.put(pathVisitCount.path(), pathVisitCount);
                    }
                    pathVisitCount.incrementVisitCount();
                });
        return pathVisitCountMap.values().stream()
                .sorted((o1, o2) -> o2.visitCount() - o1.visitCount())
                .limit(resultSize)
                .collect(Collectors.toList());
    }

    /**
     * Given user navigation entries this method will group the entries by user into user navigation lists.
     *
     * @param userNavigationEntries the list of user navigation entries to process.
     * @return list of user navigation lists.
     * @see List
     */
    private static List<UserNavigationList> getUserNavigationLists(List<UserNavigationEntry> userNavigationEntries) {
        Map<String, UserNavigationList> userNavigationListMap = new HashMap<>();
        userNavigationEntries.forEach(userNavigationEntry -> {
            UserNavigationList userNavigationList = userNavigationListMap.get(userNavigationEntry.userKey());
            if (userNavigationList == null) {
                userNavigationList = new UserNavigationList(userNavigationEntry.userKey());
                userNavigationListMap.put(userNavigationList.userKey(), userNavigationList);
            }
            userNavigationList.addNavigationEntry(userNavigationEntry.navigationEntry());
        });
        return new ArrayList<>(userNavigationListMap.values());
    }

    /**
     * Given a path to a data file and a data delimiter this method will parse the data file into a list of user
     * navigation entries. If there is an error parsing a particular line of the file it will continue processing the
     * file and add a FileLineParsingException to the UserNavigationEntry object for the line of data.
     *
     * @param path      the path of the data file to process.
     * @param delimiter the delimiter to parse each data line with.
     * @return list of user navigation entries.
     * @throws IOException if there is an error accessing or reading the data file.
     * @see List
     */
    public static List<UserNavigationEntry> parseUserNavigationFile(String path, String delimiter)
            throws IOException {
        String defaultDelimiter = " ";
        // Validate parameters.
        if (path == null || path.isEmpty()) {
            throw new InvalidParameterException("Invalid file path.");
        }
        if (delimiter == null || delimiter.isEmpty()) {
            delimiter = defaultDelimiter;
        }

        // Load data lines from file path.
        Stream<String> lines = Files.lines(Paths.get(path));

        // Parse data lines into list of entries.
        final String finalDelimiter = delimiter;
        final AtomicInteger lineNumberCounter = new AtomicInteger(1);
        return lines.map(line -> {
            UserNavigationEntry entry = new UserNavigationEntry()
                    .lineNumber(lineNumberCounter.getAndIncrement())
                    .rawLineData(line);
            String[] values = line.split(finalDelimiter);
            if (values.length != 2) {
                return entry.exception(new FileLineParsingException("Invalid line format.", line)).error(true);
            }
            return entry.userKey(values[0].trim()).navigationEntry(values[1].trim());
        }).collect(Collectors.toList());
    }

}
