package com.shodo.io.exceptions;

public class ScoresEngineInputException extends ScoresEngineException {
    
    public ScoresEngineInputException(String message) {
        super(message);
    }

    public ScoresEngineInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
