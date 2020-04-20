import lesson6.WorkWithArr;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOneFour {

    WorkWithArr methodModify;

    @Before
    public void init() {
        methodModify = new WorkWithArr();
    }

    @Test
    public void test1() {
        assertTrue(methodModify.isOneFour(new int[]{1, 1}));
    }
    @Test
    public void test2() {
        assertTrue(methodModify.isOneFour(new int[]{4, 4}));
    }
    @Test
    public void test3() {
        assertFalse(methodModify.isOneFour(new int[]{1, 4, 1}));
    }
    @Test
    public void test4() {
        assertFalse(methodModify.isOneFour(new int[]{1, 4, 4}));
    }
    @Test(expected = RuntimeException.class)
    public void test5() {
        assertTrue(methodModify.isOneFour(new int[]{0, 2, 5}));
    }

}

