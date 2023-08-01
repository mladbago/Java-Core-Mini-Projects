package uj.wmii.pwj.introduction;

public class Reverser {

    public String reverse(String input) {
        if (input == null)
            return null;
        String helMe = input.trim();
        StringBuilder newInput = new StringBuilder();
        for (int i = 0; i < helMe.length(); i ++) {
            char helpChar = helMe.charAt(i);
            newInput.insert(0, helpChar);
        }
        input = newInput.toString();
        return input;
    }

    public String reverseWords(String input) {
        if (input == null)
            return null;
        String helpMe = input.trim();
        String []words = helpMe.split("\\s");
        StringBuilder addReverse = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i --) {
            addReverse.append(words[i]).append(" ");
        }
        addReverse.deleteCharAt(addReverse.length() - 1);
        input = addReverse.toString();
        return input;
    }

}
