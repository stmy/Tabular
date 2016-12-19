import static org.junit.Assert.*;

import org.junit.Test;
import com.github.stmy.util.tabular.*;

public class Tests {

    @Test
    public void test() {
        Tabular t = new Tabular();
        t.setHeader(new Object[] { "Name", "Age", "Gender", "Score" });
        t.addRow(new Object[] { "Alice", 23, "Female", 15 });
        t.addRow(new Object[] { "Bob",   30, "Male",   892 });
        t.addRow(new Object[] { "Carol", 25, "Female", 1059 });

        assertEquals(t.toString(), 
            "Name  Age Gender Score\n" +
            "Alice  23 Female    15\n" +
            "Bob    30 Male     892\n" +
            "Carol  25 Female  1059");
    }

    @Test
    public void nullHandlingTest() {
        Tabular t = new Tabular();
        t.setHeader(new Object[] { null, null, null });
        t.addRow(new Object[] { null, null, null });
        assertEquals(t.toString(),
            "  \n" +
            "  ");
    }

    @Test
    public void emptyTableTest() {
        Tabular t = new Tabular();
        assertEquals(t.toString(), "");
    }
}