package uj.wmii.pwj.introduction;

public class HelloWorld {
    public static void main(String[] args) {
        
         if (args.length == 0) {
            System.out.println("No input parameters provided");
            return;
        }
        
        for (String toPrint: args) {
            System.out.print(toPrint + "\n");
        }
        
    }

}
