package uj.wmii.pwj.w08;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MyTestEngine {

    private final String className;
    @ASCIIArt
    private static String Test_Engine;
    @ASCIIArt
    private static String Blagoja_Mladenov;
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Please specify test class name");
            System.exit(-1);
        }
        String className = args[0].trim();
        MyTestEngine engine = new MyTestEngine(className);
        Class<?> c = engine.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {

            if(field.isAnnotationPresent(ASCIIArt.class)) {
                try {
                    field.set(engine, toBanner(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(Test_Engine);
        System.out.println(Blagoja_Mladenov);
        System.out.printf("Testing class: %s\n", className);
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        engine.runTests();
    }

    public MyTestEngine(String className) {
        this.className = className;
    }
    public int[] getWithPriority(List<Method> methods) {
        int []toReturn = new int[methods.size()];
        for (int i = 0; i < methods.size(); i ++) {
            if (methods.get(i).isAnnotationPresent(Prioritize.class)) {
                int a = methods.get(i).getAnnotation(Prioritize.class).priorityNumber();
                toReturn[i] = a;
            } else {
                toReturn[i] = Integer.MAX_VALUE;
            }
        }
        return toReturn;
    }
    private void bubbleSort(int[] arr, List<Method> methods)
    {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    Collections.swap(methods, j, j + 1);
                }
    }
    public void runTests() {
        final Object unit = getObject(className);
        List<Method> testMethods = getTestMethods(unit);
        List<Method> repeatedTestMethods = getRepeatedTestMethods(unit);
        int[] priorityNumbers = getWithPriority(repeatedTestMethods);
        bubbleSort(priorityNumbers, repeatedTestMethods);
        int successCount = 0;
        int failCount = 0;
        int errorCount = 0;
        for (Method m: testMethods) {
            TestResult result = launchSingleMethod(m, unit);
            if (result == TestResult.SUCCESS) successCount++;
            else if (result == TestResult.FAIL) failCount ++;
            else errorCount++;
        }
        for (Method m: repeatedTestMethods) {
            TestResult result = launchSingleMethodWithErrors(m, unit);
            if (result == TestResult.SUCCESS) successCount ++;
            else if (result == TestResult.FAIL) failCount ++;
            else errorCount ++;
        }
        System.out.printf("Engine launched %d tests.\n", repeatedTestMethods.size());
        System.out.printf("%d of them passed, %d failed.\n", successCount, failCount);
        System.out.printf("%d of them finished with an error.\n", errorCount);
    }


    private TestResult launchSingleMethod(Method m, Object unit) {
        try {
            String[] params = m.getAnnotation(MyTest.class).params();
            if (params.length == 0) {
                m.invoke(unit);
            } else {
                for (String param: params) {
                    m.invoke(unit, param);
                }
            }
            System.out.println("Tested method: " + m.getName() + " test successful.");
            return TestResult.SUCCESS;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return TestResult.ERROR;
        }
    }
    private TestResult launchSingleMethodWithErrors(Method m, Object unit) {
        try {
            String[] params = m.getAnnotation(RepeatedTest.class).parameters();
            String[] expecteds = m.getAnnotation(RepeatedTest.class).expecteds();
            int times = m.getAnnotation(RepeatedTest.class).times();
            if (times < 1)
                times = 1;
            System.out.println("Testing method: " + m.getName());
            System.out.println("Number of repeated tests: " + times);
            List<Integer> failed = new ArrayList<>();
            List<Integer> failedInside = new ArrayList<>();
            List<String> failingRes = new ArrayList<>();
            int countSuccesses = 0;
            for (int i = 0; i < times; i ++) {
                System.out.println("Repeated Test no. " + (i + 1));
                for (int j = 0; j < params.length; j ++) {
                    System.out.println("\tTesting with parameter: " + params[j] + " expected: " + expecteds[j]);
                    var returnedValue = m.invoke(unit, params[j]);
                    if (returnedValue.toString().equals(expecteds[j]))
                        countSuccesses ++;
                    else {
                        failed.add(i);
                        failedInside.add(j);
                        failingRes.add((String)returnedValue);
                    }
                }
            }
            System.out.println();
            if (countSuccesses == times * params.length) {
                System.out.println("Tested method: " + m.getName() + " Every test passed");
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                return TestResult.SUCCESS;
            }
            else {
                System.out.println("Tested method: " + m.getName() + " test failed");
                System.out.println("\tReport: ");
                for (int i = 0; i < failed.size(); i ++) {
                    System.out.println("\tFailed repetition no: " + (failed.get(i) + 1));
                    System.out.println("\tFailed parameter index no: " + failedInside.get(i));
                    System.out.println("\tExpected: " + expecteds[failedInside.get(i)] + " but was: " + failingRes.get(i));
                }
                System.out.println();
                System.out.println();
                System.out.println();
                return TestResult.FAIL;
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return TestResult.ERROR;
        }
    }

    private static List<Method> getTestMethods(Object unit) {
        Method[] methods = unit.getClass().getDeclaredMethods();
        return Arrays.stream(methods).filter(
                m -> m.getAnnotation(MyTest.class) != null).collect(Collectors.toList());
    }
    private List<Method> getRepeatedTestMethods(Object unit) {
        Method[] methods = unit.getClass().getDeclaredMethods();
        return Arrays.stream(methods).filter(
                m -> m.getAnnotation(RepeatedTest.class) != null).collect(Collectors.toList());
    }
    private static Object getObject(String className) {
        try {
            Class<?> unitClass = Class.forName(className);
            return unitClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return new Object();
        }
    }

    public static String toBanner(String input) {
        if (input == null)
            return "";
        String []toReturn = {"", "", "", "", "", "", ""};
        for (int i = 0; i < input.length(); i ++) {
            char at = input.charAt(i);
            switch (at) {
                case 'A', 'a' -> {
                    toReturn[0] += "   #   ";
                    toReturn[1] += "  # #  ";
                    toReturn[2] += " #   # ";
                    toReturn[3] += "#     #";
                    toReturn[4] += "#######";
                    toReturn[5] += "#     #";
                    toReturn[6] += "#     #";
                }
                case 'B', 'b' -> {
                    toReturn[0] += "###### ";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#     #";
                    toReturn[3] += "###### ";
                    toReturn[4] += "#     #";
                    toReturn[5] += "#     #";
                    toReturn[6] += "###### ";
                }
                case 'C', 'c' -> {
                    toReturn[0] += " ##### ";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#      ";
                    toReturn[3] += "#      ";
                    toReturn[4] += "#      ";
                    toReturn[5] += "#     #";
                    toReturn[6] += " ##### ";
                }
                case 'D', 'd' -> {
                    toReturn[0] += "###### ";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#     #";
                    toReturn[3] += "#     #";
                    toReturn[4] += "#     #";
                    toReturn[5] += "#     #";
                    toReturn[6] += "###### ";
                }
                case 'E', 'e' -> {
                    toReturn[0] += "#######";
                    toReturn[1] += "#      ";
                    toReturn[2] += "#      ";
                    toReturn[3] += "#####  ";
                    toReturn[4] += "#      ";
                    toReturn[5] += "#      ";
                    toReturn[6] += "#######";
                }
                case 'F', 'f' -> {
                    toReturn[0] += "#######";
                    toReturn[1] += "#      ";
                    toReturn[2] += "#      ";
                    toReturn[3] += "#####  ";
                    toReturn[4] += "#      ";
                    toReturn[5] += "#      ";
                    toReturn[6] += "#      ";
                }
                case 'G', 'g' -> {
                    toReturn[0] += " ##### ";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#      ";
                    toReturn[3] += "#  ####";
                    toReturn[4] += "#     #";
                    toReturn[5] += "#     #";
                    toReturn[6] += " ##### ";
                }
                case 'H', 'h' -> {
                    toReturn[0] += "#     #";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#     #";
                    toReturn[3] += "#######";
                    toReturn[4] += "#     #";
                    toReturn[5] += "#     #";
                    toReturn[6] += "#     #";
                }
                case 'I', 'i' -> {
                    toReturn[0] += "###";
                    toReturn[1] += " # ";
                    toReturn[2] += " # ";
                    toReturn[3] += " # ";
                    toReturn[4] += " # ";
                    toReturn[5] += " # ";
                    toReturn[6] += "###";
                }
                case 'J', 'j' -> {
                    toReturn[0] += "      #";
                    toReturn[1] += "      #";
                    toReturn[2] += "      #";
                    toReturn[3] += "      #";
                    toReturn[4] += "#     #";
                    toReturn[5] += "#     #";
                    toReturn[6] += " ##### ";
                }
                case 'K', 'k' -> {
                    toReturn[0] += "#    #";
                    toReturn[1] += "#   # ";
                    toReturn[2] += "#  #  ";
                    toReturn[3] += "###   ";
                    toReturn[4] += "#  #  ";
                    toReturn[5] += "#   # ";
                    toReturn[6] += "#    #";
                }
                case 'L', 'l' -> {
                    toReturn[0] += "#      ";
                    toReturn[1] += "#      ";
                    toReturn[2] += "#      ";
                    toReturn[3] += "#      ";
                    toReturn[4] += "#      ";
                    toReturn[5] += "#      ";
                    toReturn[6] += "#######";
                }
                case 'M', 'm' -> {
                    toReturn[0] += "#     #";
                    toReturn[1] += "##   ##";
                    toReturn[2] += "# # # #";
                    toReturn[3] += "#  #  #";
                    toReturn[4] += "#     #";
                    toReturn[5] += "#     #";
                    toReturn[6] += "#     #";
                }
                case 'N', 'n' -> {
                    toReturn[0] += "#     #";
                    toReturn[1] += "##    #";
                    toReturn[2] += "# #   #";
                    toReturn[3] += "#  #  #";
                    toReturn[4] += "#   # #";
                    toReturn[5] += "#    ##";
                    toReturn[6] += "#     #";
                }
                case 'O', 'o' -> {
                    toReturn[0] += "#######";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#     #";
                    toReturn[3] += "#     #";
                    toReturn[4] += "#     #";
                    toReturn[5] += "#     #";
                    toReturn[6] += "#######";
                }
                case 'P', 'p' -> {
                    toReturn[0] += "###### ";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#     #";
                    toReturn[3] += "###### ";
                    toReturn[4] += "#      ";
                    toReturn[5] += "#      ";
                    toReturn[6] += "#      ";
                }
                case 'Q', 'q' -> {
                    toReturn[0] += " ##### ";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#     #";
                    toReturn[3] += "#     #";
                    toReturn[4] += "#   # #";
                    toReturn[5] += "#    # ";
                    toReturn[6] += " #### #";
                }
                case 'R', 'r' -> {
                    toReturn[0] += "###### ";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#     #";
                    toReturn[3] += "###### ";
                    toReturn[4] += "#   #  ";
                    toReturn[5] += "#    # ";
                    toReturn[6] += "#     #";
                }
                case 'S', 's' -> {
                    toReturn[0] += " ##### ";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#      ";
                    toReturn[3] += " ##### ";
                    toReturn[4] += "      #";
                    toReturn[5] += "#     #";
                    toReturn[6] += " ##### ";
                }
                case 'T', 't' -> {
                    toReturn[0] += "#######";
                    toReturn[1] += "   #   ";
                    toReturn[2] += "   #   ";
                    toReturn[3] += "   #   ";
                    toReturn[4] += "   #   ";
                    toReturn[5] += "   #   ";
                    toReturn[6] += "   #   ";
                }
                case 'U', 'u' -> {
                    toReturn[0] += "#     #";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#     #";
                    toReturn[3] += "#     #";
                    toReturn[4] += "#     #";
                    toReturn[5] += "#     #";
                    toReturn[6] += " ##### ";
                }
                case 'V', 'v' -> {
                    toReturn[0] += "#     #";
                    toReturn[1] += "#     #";
                    toReturn[2] += "#     #";
                    toReturn[3] += "#     #";
                    toReturn[4] += " #   # ";
                    toReturn[5] += "  # #  ";
                    toReturn[6] += "   #   ";
                }
                case 'W', 'w' -> {
                    toReturn[0] += "#     #";
                    toReturn[1] += "#  #  #";
                    toReturn[2] += "#  #  #";
                    toReturn[3] += "#  #  #";
                    toReturn[4] += "#  #  #";
                    toReturn[5] += "#  #  #";
                    toReturn[6] += " ## ## ";
                }
                case 'X', 'x' -> {
                    toReturn[0] += "#     #";
                    toReturn[1] += " #   # ";
                    toReturn[2] += "  # #  ";
                    toReturn[3] += "   #   ";
                    toReturn[4] += "  # #  ";
                    toReturn[5] += " #   # ";
                    toReturn[6] += "#     #";
                }
                case 'Y', 'y' -> {
                    toReturn[0] += "#     #";
                    toReturn[1] += " #   # ";
                    toReturn[2] += "  # #  ";
                    toReturn[3] += "   #   ";
                    toReturn[4] += "   #   ";
                    toReturn[5] += "   #   ";
                    toReturn[6] += "   #   ";
                }
                case 'Z', 'z' -> {
                    toReturn[0] += "#######";
                    toReturn[1] += "     # ";
                    toReturn[2] += "    #  ";
                    toReturn[3] += "   #   ";
                    toReturn[4] += "  #    ";
                    toReturn[5] += " #     ";
                    toReturn[6] += "#######";
                }
                case ' ' -> {
                    toReturn[0] += "    ";
                    toReturn[1] += "    ";
                    toReturn[2] += "    ";
                    toReturn[3] += "    ";
                    toReturn[4] += "    ";
                    toReturn[5] += "    ";
                    toReturn[6] += "    ";
                }
            }
            if ((i != (input.length() - 1)) && (input.charAt(i + 1) != ' ') && (input.charAt(i) != ' ')) {
                toReturn[0] += " ";
                toReturn[1] += " ";
                toReturn[2] += " ";
                toReturn[3] += " ";
                toReturn[4] += " ";
                toReturn[5] += " ";
                toReturn[6] += " ";
            }
        }
        StringBuilder finalPrint = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            finalPrint.append(toReturn[i]).append("\n");
        }
        return finalPrint.toString();
    }
}
