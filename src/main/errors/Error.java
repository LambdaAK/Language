package main.errors;

public abstract class Error {
    public void execute () {
        displayMessage();
        System.exit(1);
    }

    abstract void displayMessage();

    public static void throwError(Error error) {
        error.execute();
    }
}
