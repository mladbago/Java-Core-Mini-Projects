package uj.wmii.pwj.spreadsheet;
public class Spreadsheet {

    public String[][] calculate(String[][] input) {

        for(int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (tryToParseNumber(input[i][j]) == false) {
                    String takeString = input[i][j];
                    input[i][j] = String.valueOf(convertToInteger(takeString, input));
                }
            }
        }
        return input;
    }
    public static boolean tryToParseNumber(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public int doReference (String takeString, String[][] input, int referencedRow, int referencedColumn) {
        referencedRow = Integer.parseInt(takeString.substring(2))-1;
        referencedColumn = takeString.charAt(1) - 'A';

        String importString = input[referencedRow][referencedColumn];

        return convertToInteger(importString,input);
    }
    public int convertToInteger(String takeString, String[][] input)
    {
        if (tryToParseNumber(takeString))
            return Integer.parseInt(takeString);

        if(takeString.charAt(0) == '$')
           return doReference(takeString, input, 0, 0);
        if (takeString.charAt(0) == '=')
            return operation(takeString,input);

        return 0;

    }
    public int finalReturn(String checkString, int firstOperand, int secondOperand) {
        return switch (checkString) {
            case "ADD" -> firstOperand + secondOperand;
            case "SUB" -> firstOperand - secondOperand;
            case "MUL" -> firstOperand * secondOperand;
            case "DIV" -> firstOperand / secondOperand;
            case "MOD" -> firstOperand % secondOperand;
            default -> 0;
        };
    }
    public int operation(String toFactor, String[][] input)
    {

        String firstOperandInString;
        StringBuilder doString = new StringBuilder();
        int rememberComa = 0;
        for (int i = 5; i < toFactor.length(); i ++) {
            if (toFactor.charAt(i) == ',') {
                rememberComa = i;
                break;
            }
            doString.append(toFactor.charAt(i));
        }
        firstOperandInString = doString.toString();
        doString = new StringBuilder();
        for (int i = rememberComa + 1; i < toFactor.length(); i ++) {
            if (toFactor.charAt(i) == ')') {
                break;
            }
            doString.append(toFactor.charAt(i));
        }
        int firstOperand = convertToInteger(firstOperandInString, input);
        String secondOperandInString = doString.toString();
        int secondOperand = convertToInteger(secondOperandInString, input);

        String checkString = toFactor.substring(1, 4);

        return finalReturn(checkString, firstOperand, secondOperand);
    }

}
