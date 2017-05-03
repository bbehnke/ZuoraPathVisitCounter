package app;

import app.model.PathVisitCount;
import app.model.UserNavigationEntry;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the UserNavigationUtil class.
 *
 * @author Bradley Behnke
 * @version 1.0
 */
public class UserNavigationUtilTest extends TestCase {
    private final List<UserNavigationEntry> userNavigationEntryList = new ArrayList<>();

    public void setUp() throws Exception {
        super.setUp();

        // Setup testing data
        String user1 = "U1";
        String user2 = "U2";
        String user3 = "U3";
        String root = "/";
        String subscribers = "subscribers";
        String filter = "filter";
        String export = "export";
        String catalog = "catalog";
        String edit = "edit";
        // Setup userNavigationEntryList
        userNavigationEntryList.add(new UserNavigationEntry().userKey(user1).navigationEntry(root));
        userNavigationEntryList.add(new UserNavigationEntry().userKey(user1).navigationEntry(subscribers));
        userNavigationEntryList.add(new UserNavigationEntry().userKey(user2).navigationEntry(root));
        userNavigationEntryList.add(new UserNavigationEntry().userKey(user2).navigationEntry(subscribers));
        userNavigationEntryList.add(new UserNavigationEntry().userKey(user1).navigationEntry(filter));
        userNavigationEntryList.add(new UserNavigationEntry().userKey(user1).navigationEntry(export));
        userNavigationEntryList.add(new UserNavigationEntry().userKey(user2).navigationEntry(filter));
        userNavigationEntryList.add(new UserNavigationEntry().userKey(user3).navigationEntry(root));
        userNavigationEntryList.add(new UserNavigationEntry().userKey(user3).navigationEntry(catalog));
        userNavigationEntryList.add(new UserNavigationEntry().userKey(user3).navigationEntry(edit));
    }

    /**
     * Tests UserNavigationUtil.getPathVisitCountsFromEntries for basic functionality.
     */
    public void testGetPathVisitCountsFromEntries() {
        System.out.println("Testing UserNavigationUtil.getPathVisitCountsFromEntries.");

        List<PathVisitCount> pathVisitCounts = UserNavigationUtil.getPathVisitCountsFromEntries(userNavigationEntryList, 3);
        assertNotNull(pathVisitCounts);
        assertEquals(3, pathVisitCounts.size());
        assertEquals(2, pathVisitCounts.get(0).visitCount());
        assertEquals("/ -> subscribers -> filter", pathVisitCounts.get(0).path());
        assertEquals(1, pathVisitCounts.get(1).visitCount());
        assertEquals("/ -> catalog -> edit", pathVisitCounts.get(1).path());
        assertEquals(1, pathVisitCounts.get(2).visitCount());
        assertEquals("subscribers -> filter -> export", pathVisitCounts.get(2).path());

        System.out.println("Completed testing UserNavigationUtil.getPathVisitCountsFromEntries.");
    }
}