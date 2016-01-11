package hu.supercluster.paperwork.integration;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import hu.supercluster.paperwork.integration.matcher.GitShaMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public TestRule rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldHaveCorrectValueForSimpleKey() {
        onView(withId(R.id.simpleKey)).check(matches(withText(is("simpleValue"))));
    }

    @Test
    public void shouldDisplayGitSha() {
        onView(withId(R.id.gitSha)).check(matches(withText(new GitShaMatcher())));
    }

    @Test
    public void shouldDisplayGitTag() {
        onView(withId(R.id.gitTag)).check(matches(withText(any(String.class))));
    }

    @Test
    public void shouldDisplayGitInfo() {
        onView(withId(R.id.gitInfo)).check(matches(withText(any(String.class))));
    }
}
