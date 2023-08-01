package uj.wmii.pwj.w08;

@SuppressWarnings(value = "unused")
public class MyBeautifulTestSuite {

    @MyTest
    public void testSomething() {
        System.out.println("I'm testing something!");
    }

    @MyTest(params = {"a param", "b param", "c param. Long, long C param."})
    public void testWithParam(String param) {
        System.out.printf("I was invoked with parameter: %s\n", param);
    }

    public void notATest() {
        System.out.println("I'm not a test.");
    }

    @MyTest
    public void imFailue() {
        System.out.println("I AM EVIL.");
        throw new NullPointerException();
    }

    @RepeatedTest(parameters = {"Computer"}, expecteds = {"computer"})
    public String testFirst(String input) {
        return input;
    }

    @RepeatedTest(parameters = {"Computer","Laptop"}, expecteds = {"Computer", "laptop"}, times = 4)
    public String testSecond(String input) {
        return input;
    }

    @RepeatedTest(parameters = {"Computer","laptop"}, expecteds = {"Computer", "laptop"}, times = 100)
    public String testThird(String input) {
        return input;
    }
    //testing priorities

    @Prioritize
    @RepeatedTest(parameters = {"3"}, expecteds = {"3"})
    public String testFourth(String input) {
        return "3";
    }

    @Prioritize(priorityNumber = 2)
    @RepeatedTest(parameters = {"3"}, expecteds = {"3"})
    public String testFifth(String input) {
        return "3";
    }

    @Prioritize(priorityNumber = 9)
    @RepeatedTest(parameters = {"Blagoja"}, expecteds = {"Mladenov"})
    public String testSixth(String input) {
        return input;
    }

    @Prioritize(priorityNumber = 3)
    @RepeatedTest(parameters = {"Blagoja"}, expecteds = {"Blagoja"})
    public String testSeventh(String input) {
        return input;
    }

    @Prioritize(priorityNumber = 1)
    @RepeatedTest(parameters = {"Blagoja"}, expecteds = {"Blagoja"})
    public String testEighth(String input) {
        return input;
    }

    //end of testing priorities

    //testing errors

    @Prioritize(priorityNumber = 1)
    @RepeatedTest(parameters = {"Computer","Laptop"}, expecteds = {"Computer", "laptop"}, times = 4)
    public String testNinth(String input) {
        throw new IllegalArgumentException();
    }
    @Prioritize(priorityNumber = 2)
    @RepeatedTest(parameters = {"Computer","Laptop"}, expecteds = {"Computer", "laptop"}, times = 4)
    public String testTenth(String input) {
        throw new NullPointerException();
    }

    //end of testing errors

    //test passed

    @RepeatedTest(parameters = {"Shelf"}, expecteds = {"Shelf"}, times = 3)
    public String testEleventh(String input) {
        return input;
    }

    //end of test passed

    //test failed

    @RepeatedTest(parameters = {"Shelf"}, expecteds = {"Shelves"}, times = 2)
    public String testTwelfth(String input) {
        return input;
    }

    //end of test failed
}
