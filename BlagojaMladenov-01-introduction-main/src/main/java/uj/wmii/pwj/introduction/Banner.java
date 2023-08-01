package uj.wmii.pwj.introduction;


public class Banner {

    public static String[] toBanner(String input) {
        String[] toEmpty = { };
        if (input == null)
            return toEmpty;
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
        return toReturn;
    }
    public static void main(String[] args) {
//        String input = "Ala ma kota ";
        String input = null;
        if (input == null) {
            System.out.println(toBanner(null));
        }
        else {
            for (int i = 0; i < 7; i++) {
                System.out.println(toBanner(input)[i]);
            }
        }
    }
}
