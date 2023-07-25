package edu.school21.game.app;

public class IllegalParametersException extends RuntimeException {
    public IllegalParametersException () {
        super();
    }
    public IllegalParametersException (String message) {
        super(message);
    }
}
