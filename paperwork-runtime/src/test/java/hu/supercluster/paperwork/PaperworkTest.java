package hu.supercluster.paperwork;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PaperworkTest {
    @Test
    public void testDefaultFileName() {
        assertEquals("paperwork.json", Paperwork.DEFAULT_FILENAME);
    }
}
