import org.junit.Test;
import lesson6.WorkWithArr;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestModifyArr {
    @Parameterized.Parameters
    public static Collection<int[][]> data() {
        return Arrays.asList(new int[][][] {
                {{1,7}, {1,2,4,4,2,3,4,1,7}},
                {null, {1,2,4,4,2,3,4,1,4}},
                {{7}, {1,2,4,4,2,3,4,4,7}},
                {{2,1,2,2,5,0,0,7}, {4,2,1,2,2,5,0,0,7}},
                {null, {1,2,1,2,2,5,0,0,7}},
        });
    }

    private int[] in;
    private int[] out;

    public TestModifyArr(int[] out, int[] in) {
        this.out = out;
        this.in = in;
    }

    WorkWithArr methodModify;

    @Before
    public void init() {
        methodModify = new WorkWithArr();
    }

    @Test (expected = RuntimeException.class)
    public void massTestMod() {
        Assert.assertArrayEquals(out, methodModify.modifyArr(in));
    }
}

