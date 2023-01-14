package main.interpreter;

import java.util.Stack;

public class Color {
    public static final String BLACK = "\u001B[30m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";

    public static final String WHITE = "\u001B[37m";


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
