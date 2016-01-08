package hu.supercluster.paperwork.plugin

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class PaperworkPluginExtensionTest {
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
}