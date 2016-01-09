package hu.supercluster.paperwork;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@Config(sdk = 18, manifest = "src/test/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class PaperworkTest {
    @Test
    public void testDefaultFileName() {
        assertEquals("paperwork.json", Paperwork.DEFAULT_FILENAME);
    }

    @Test
    public void testPaperworkCustomFileName() {
        final Paperwork paperwork = new Paperwork(RuntimeEnvironment.application, "test.json");

        assertEquals("value", paperwork.get("key"));
    }

    @Test
    public void testPaperworkDefaultFileName() {
        final Paperwork paperwork = new Paperwork(RuntimeEnvironment.application);
        assertEquals("hell yeah", paperwork.get("testing rocks"));
    }

    @Test
    public void testPaperworkMultipleKeys() {
        final Paperwork paperwork = new Paperwork(RuntimeEnvironment.application, "multiple_keys.json");

        assertEquals("value 1", paperwork.get("key 1"));
        assertEquals("value 2", paperwork.get("key 2"));
        assertEquals("value 3", paperwork.get("key 3"));
    }
}
