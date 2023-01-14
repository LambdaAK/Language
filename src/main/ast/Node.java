package main.ast;

import main.main.Printer;

public abstract class Node {

    /**
     * This is the default print method for a Node subclass
     * @param printer
     */
    public void print(Printer printer) {
        printer.add(this);
    }


}
