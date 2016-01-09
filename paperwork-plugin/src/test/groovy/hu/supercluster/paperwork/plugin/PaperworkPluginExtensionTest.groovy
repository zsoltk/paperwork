package hu.supercluster.paperwork.plugin

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class PaperworkPluginExtensionTest {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private def extension;

    @Before
    public void setUp() {
        def project = ProjectBuilder.builder().build()
        extension = new PaperworkPluginExtension(project)
    }

    @Test
    public void testDefaultFileName() {
        assert extension.filename == "src/main/assets/paperwork.json"
    }

    @Test
    public void testSetShouldBeEmptyByDefault() {
        assert extension.set.size() == 0
    }

    @Test
    public void testBuildTime() {
        def buildTime = extension.buildTime() as long
        def currentTime = new Date().getTime()

        assert fallsWithinASecond(buildTime, currentTime);
    }

    @Test
    public void testBuildTimeWithFormat() {
        def buildTime = extension.buildTime(DATE_FORMAT)
        def parseBack = new Date().parse(DATE_FORMAT, buildTime).getTime();
        def currentTime = new Date().getTime()

        assert fallsWithinASecond(parseBack, currentTime);
    }

    @Test
    public void testBuildTimeWithFormatAndTimeZone() {
        def timeZoneId = "UTC"
        def timeZone = TimeZone.getTimeZone(timeZoneId)

        def buildTime = extension.buildTime(DATE_FORMAT, timeZoneId)
        def parseBack = new Date().parse(DATE_FORMAT, buildTime, timeZone).getTime();
        def currentTime = new Date().getTime()

        assert fallsWithinASecond(parseBack, currentTime);
    }

    private boolean fallsWithinASecond(long time1, long time2) {
        Math.abs(time1 - time2) < 1000
    }
}