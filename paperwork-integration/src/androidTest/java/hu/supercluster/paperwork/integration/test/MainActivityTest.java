package hu.supercluster.paperwork.integration.test;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import hu.supercluster.paperwork.integration.*;
import hu.supercluster.paperwork.integration.matcher.BuildTimeMatcher;
import hu.supercluster.paperwork.integration.matcher.GitShaMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    public static final long TIMESTAMP_THRESHOLD = TimeUnit.MINUTES.toMillis(10);
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMEZONE_ID_1 = "GMT";
    public static final String TIMEZONE_ID_2 = "Pacific/Honolulu";

    @Rule
    public TestRule rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldHaveCorrectValueForSimpleKey() {
        onView(ViewMatchers.withId(hu.supercluster.paperwork.integration.R.id.simpleKey)).check(matches(withText(is("simpleValue"))));
    }

    @Test
    public void shouldDisplayGitSha() {
        onView(ViewMatchers.withId(hu.supercluster.paperwork.integration.R.id.gitSha)).check(matches(withText(new GitShaMatcher())));
    }

    @Test
    public void shouldDisplayGitTag() {
        onView(ViewMatchers.withId(hu.supercluster.paperwork.integration.R.id.gitTag)).check(matches(not(withText(isEmptyString()))));
    }

    @Test
    public void shouldDisplayGitInfo() {
        onView(ViewMatchers.withId(hu.supercluster.paperwork.integration.R.id.gitInfo)).check(matches(not(withText(isEmptyString()))));
    }

    @Test
    public void shouldDisplayGitBranch() {
        onView(ViewMatchers.withId(hu.supercluster.paperwork.integration.R.id.gitBranch)).check(matches(not(withText(isEmptyString()))));
    }

    @Test
    public void shouldHaveRelevantBuildTime1() {
        onView(ViewMatchers.withId(hu.supercluster.paperwork.integration.R.id.buildTime1)).check(matches(withText(new BuildTimeMatcher(TIMESTAMP_THRESHOLD))));
    }

    @Test
    public void shouldDisplayBuildTime2() {
        onView(ViewMatchers.withId(hu.supercluster.paperwork.integration.R.id.buildTime2)).check(matches(withText("AD")));
    }

    @Test
    public void shouldHaveRelevantBuildTime3() {
        onView(ViewMatchers.withId(hu.supercluster.paperwork.integration.R.id.buildTime3)).check(matches(withText(new BuildTimeMatcher(TIMESTAMP_THRESHOLD, DATETIME_FORMAT, TIMEZONE_ID_1))));
    }

    @Test
    public void shouldHaveRelevantBuildTime4() {
        onView(ViewMatchers.withId(hu.supercluster.paperwork.integration.R.id.buildTime4)).check(matches(withText(new BuildTimeMatcher(TIMESTAMP_THRESHOLD, DATETIME_FORMAT, TIMEZONE_ID_2))));
    }

    @Test
    public void shouldHaveCorrectValueForShell() {
        onView(ViewMatchers.withId(hu.supercluster.paperwork.integration.R.id.shell)).check(matches(withText("String output of test.sh")));
    }

    @Test
    public void shouldDisplayEnv() {
        onView(ViewMatchers.withId(hu.supercluster.paperwork.integration.R.id.env)).check(matches(not(withText(isEmptyString()))));
    }
}
