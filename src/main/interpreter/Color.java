package main.interpreter;

import java.util.Stack;

public class Color {
    public static final String BLACK = "\u001B[30m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String RED = "\u001B[35m";

    public static final String WHITE = "\u001B[37m";

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE


    private static final Stack<String> stack = new Stack<String>();


    public static String addColor(String color) {
        stack.add(color);
        return color;
    }

    public static String removeColor() {
        if (stack.empty()) return WHITE; // white is the default color
        else {
            stack.pop(); // remove the top color from the stack

            if (stack.empty()) return WHITE; // white is the default color
            else return stack.peek(); // otherwise, return the next color
        }
    }



}
