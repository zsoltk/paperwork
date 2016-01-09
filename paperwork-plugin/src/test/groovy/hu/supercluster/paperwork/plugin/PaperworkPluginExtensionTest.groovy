package hu.supercluster.paperwork.plugin

import org.gradle.testfixtures.ProjectBuilder
import org.junit.After
import org.junit.Before
import org.junit.Test

class PaperworkPluginExtensionTest {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private static final String TEST_STRING = "Test string 123"
    private def extension
    private File baseDir

    @Before
    public void setUp() {
        baseDir = new File("build/" + UUID.randomUUID().toString())
        baseDir.mkdirs()

        def project = ProjectBuilder.builder().withProjectDir(baseDir).build()
        extension = new PaperworkPluginExtension(project)
    }

    @After
    public void tearDown() throws Exception {
        baseDir.deleteDir()
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

    @Test
    public void testShell() {
        def testScript = new File(baseDir, "test.sh")
        testScript.write(String.format('echo "%s"', TEST_STRING))
        testScript.setExecutable(true)

        def result = extension.shell(testScript.absolutePath)

        assert result == TEST_STRING
    }

    @Test
    public void testEnv() {
        def key = "PATH"

        assert extension.env(key) == System.getenv(key)
    }
}