package hu.supercluster.paperwork.integration.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class BuildTimeMatcher extends TypeSafeMatcher<String> {
    private final long threshold;
    private final String format;
    private final String timeZoneId;

    public BuildTimeMatcher(long threshold) {
        this(threshold, "");
    }

    public BuildTimeMatcher(long threshold, String format) {
        this(threshold, format, "");
    }

    public BuildTimeMatcher(long threshold, String format, String timeZoneId) {
        this.threshold = threshold;
        this.format = format;
        this.timeZoneId = timeZoneId;
    }

    @Override
    protected boolean matchesSafely(String item) {
        try {
            long timestamp = convertToTimestamp(item);
            long diff = DateTime.now().minus(timestamp).getMillis();

            return Math.abs(diff) < threshold;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    private long convertToTimestamp(String item) {
        if (format.isEmpty()) {
            return Long.decode(item);

        } else {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
            DateTime parsed = formatter.withZone(getTimeZone()).parseDateTime(item);

            return parsed.getMillis();
        }
    }

    private DateTimeZone getTimeZone() {
        return timeZoneId.isEmpty() ? DateTimeZone.getDefault() : DateTimeZone.forID(timeZoneId);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" holds a past timestamp not older than: " + threshold + " millisecs");
    }
}
