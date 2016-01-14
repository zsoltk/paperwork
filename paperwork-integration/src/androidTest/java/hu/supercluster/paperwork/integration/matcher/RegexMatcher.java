package hu.supercluster.paperwork.integration.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RegexMatcher extends TypeSafeMatcher<String> {
    public final String pattern;

    public RegexMatcher(String pattern) {
        this.pattern = pattern;
    }

    @Override
    protected boolean matchesSafely(String item) {
        return item.matches(pattern);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" matches regex: "+ pattern +"");
    }

    @Override
    protected void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText("supplied text was: " + item);

        super.describeMismatchSafely(item, mismatchDescription);
    }
}
