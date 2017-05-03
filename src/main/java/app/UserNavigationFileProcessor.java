package app;

import app.model.PathVisitCount;
import app.model.UserNavigationEntry;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UserNavigationFileProcessor processes a given data file and prints the path visit counts that fit the given arguments.
 *
 * @author Bradley Behnke
 * @version 1.0
 */
public class UserNavigationFileProcessor {

    private final static Logger LOG = Logger.getLogger(UserNavigationFileProcessor.class.getName());

    /**
     * Main entry point for processing user data file. Validates arguments and prints path visit count results.
     *
     * @param args for processing. Arguments should come in order of: resultSize, pathSize, path, delimiter(optional).
     */
    public static void main(String[] args) {
        LOG.info("Starting UserNavigationFileProcessor");

        // Validate and extract arguments.
        ProcessorArguments arguments = validateAndExtractArguments(args);
        if (arguments == null) {
            System.exit(1);
            return;
        }

        // Parse file into user entry pairs.
        List<UserNavigationEntry> userNavigationEntries =
                parseUserNavigationFile(arguments.dataPath(), arguments.dataDelimiter());
        // Validate parse result.
        if (userNavigationEntries == null) {
            System.exit(1);
            return;
        } else if (userNavigationEntries.isEmpty()) {
            LOG.info("No user navigation entries found in file.");
            return;
        }

        // Load path visit counts.
        List<PathVisitCount> pathVisitCounts =
                UserNavigationUtil.getPathVisitCountsFromEntries(userNavigationEntries, arguments.pathSize(), arguments.resultSize());

        // Build output string and print.
        StringBuilder output = new StringBuilder();
        output.append("\nVisit Count Results\n");
        pathVisitCounts.forEach(pathVisitCount -> {
            output.append("Visited ");
            output.append(pathVisitCount.visitCount());
            output.append(" time(s)");
            output.append(" : ");
            output.append(pathVisitCount.path());
            output.append("\n");
        });
        LOG.info(output.toString());

        LOG.info("UserNavigationFileProcessor finished successfully.");
    }

    /**
     * Validates the given arguments and returns extracted arguments.
     *
     * @param args to validate and extract.
     * @return extracted argument wrapper object.
     */
    private static ProcessorArguments validateAndExtractArguments(String[] args) {
        // Validate and extract arguments
        if (args.length < 3 || args.length > 4) {
            LOG.log(Level.SEVERE, "Invalid program arguments.\n" +
                    "Expected: java -jar pathfinder.jar <resultSize> <pathSize> <path> <delimiter(optional)>");
            return null;
        }

        int resultSize;
        try {
            resultSize = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, "Invalid program arguments. Result size is not an integer. " +
                    "Expected: java -jar pathfinder.jar <resultSize> <pathSize> <path> <delimiter(optional)>");
            return null;
        }

        int pathSize;
        try {
            pathSize = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, "Invalid program arguments. Path size is not an integer. " +
                    "Expected: java -jar pathfinder.jar <resultSize> <pathSize> <path> <delimiter(optional)>");
            return null;
        }

        String path = args[2];

        String delimiter = null;
        if (args.length == 4) {
            delimiter = args[3];
        }

        return new ProcessorArguments().resultSize(resultSize).pathSize(pathSize).dataPath(path).dataDelimiter(delimiter);
    }

    /**
     * Parses given file with given delimiter and returns list of user navigation entries from file.
     *
     * @param path      the path of the data file to process.
     * @param delimiter the delimiter to parse each data line with.
     * @return list of user navigation entries.
     */
    private static List<UserNavigationEntry> parseUserNavigationFile(String path, String delimiter) {
        try {
            return UserNavigationUtil.parseUserNavigationFile(path, delimiter);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Failed to parse data file.", e);
            return null;
        }
    }

    /**
     * Simple wrapped for process arguemnts.
     *
     * @author Bradley Behnke
     * @since 1.0
     */
    private static class ProcessorArguments {
        private int resultSize;
        private int pathSize;
        private String dataPath;
        private String dataDelimiter;

        /**
         * @return current resultSize.
         */
        public int resultSize() {
            return this.resultSize;
        }

        /**
         * @return current pathSize.
         */
        public int pathSize() {
            return this.pathSize;
        }

        /**
         * @return current dataPath.
         */
        public String dataPath() {
            return this.dataPath;
        }

        /**
         * @return current dataDelimiter.
         */
        public String dataDelimiter() {
            return this.dataDelimiter;
        }

        /**
         * @param resultSize resultSize to set.
         * @return this instance of ProcessorArguments.
         */
        public ProcessorArguments resultSize(final int resultSize) {
            this.resultSize = resultSize;
            return this;
        }

        /**
         * @param pathSize pathSize to set.
         * @return this instance of ProcessorArguments.
         */
        public ProcessorArguments pathSize(final int pathSize) {
            this.pathSize = pathSize;
            return this;
        }

        /**
         * @param dataPath dataPath to set.
         * @return this instance of ProcessorArguments.
         */
        public ProcessorArguments dataPath(final String dataPath) {
            this.dataPath = dataPath;
            return this;
        }

        /**
         * @param dataDelimiter dataDelimiter to set.
         * @return this instance of ProcessorArguments.
         */
        public ProcessorArguments dataDelimiter(final String dataDelimiter) {
            this.dataDelimiter = dataDelimiter;
            return this;
        }
    }

}
