import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class CheckItTest {

    // Example method to capture output for assertion
    private String executeCheckIt(boolean a, boolean b, boolean c) {
        // Redirect System.out to capture the output
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call checkIt
        CheckIt.checkIt(a, b, c);

        // Restore standard output
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        return outContent.toString().trim();
    }

    @Test
    public void testPredicateTrue() {
        assertEquals("P is true", executeCheckIt(true, false, false));
    }

    @Test
    public void testPredicateFalse() {
        assertEquals("P isn't true", executeCheckIt(false, false, false));
    }

    @Test
    public void testClauseCoverageA() {
        assertEquals("P is true", executeCheckIt(true, false, false));
        assertEquals("P isn't true", executeCheckIt(false, false, false));
    }

    @Test
    public void testClauseCoverageB() {
        assertEquals("P is true", executeCheckIt(false, true, true));
        assertEquals("P isn't true", executeCheckIt(false, true, false));
    }

    @Test
    public void testClauseCoverageC() {
        assertEquals("P is true", executeCheckIt(false, true, true));
        assertEquals("P isn't true", executeCheckIt(false, false, true));
    }

    @Test
    public void testClauseCoverageBAndC() {
        assertEquals("P is true", executeCheckIt(false, true, true));
        assertEquals("P isn't true", executeCheckIt(false, true, false));
        assertEquals("P isn't true", executeCheckIt(false, false, true));
        assertEquals("P isn't true", executeCheckIt(false, false, false));
    }

    @Test
    public void testCACCCoverage() {
        assertEquals("P is true", executeCheckIt(true, false, true));
        assertEquals("P isn't true", executeCheckIt(false, false, true));
    }

    @Test
    public void testRACCCoverage() {
        // Test each pair with the third clause as tru
        assertEquals("P is true", executeCheckIt(true, false, true));
        assertEquals("P is true", executeCheckIt(false, true, true));
        assertEquals("P isn't true", executeCheckIt(false, false, true));

        // Test each pair with the third clause as false
        assertEquals("P is true", executeCheckIt(true, false, false));
        assertEquals("P isn't true", executeCheckIt(false, true, false));
        assertEquals("P isn't true", executeCheckIt(false, false, false));
    }
}

