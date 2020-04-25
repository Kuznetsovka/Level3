package lesson7;

public class Calc {
    int a;
    int b;

    public int getA() {
        return a;
    }

    public Calc(int a, int b) {
        this.a = a;
        this.b = b;
    }


    public int getB() {
        return b;
    }

    @BeforeSuite
    public void infoBefore(){
        System.out.println("Start before");
    }

    @Test
    public int sum(int a, int b){
        return a+b;
    }

//    @Test(priority = 2)
//    public int dev(int a, int b){
//        return a/b;
//    }

    @Test(priority = 3)
    public int subtraction(int a, int b){
        return a-b;
    }

    @Test(priority = 4)
    public int multiplication(int a, int b){
        return a*b;
    }

    @Test(priority = 5)
    public int degree(int a, int b){
        return (int) Math.pow(a,b);
    }

    @Test(priority = 6)
    public double root(int a, int b){
        return Math.pow(a,1/b);
    }

    @AfterSuite
    public void infoAfter(){
        System.out.println("End after");
    }
}
