package com.shodo.io.exceptions;

class ScoresEngineException extends Exception {

    ScoresEngineException(String message) {
        super(message);
    }

    ScoresEngineException(String message, Throwable cause) {
        super(message, cause);
    }
}
