package com.github.srang.datafactory;

public class ObjectFieldGenerationException extends Exception {
    public ObjectFieldGenerationException (String message) {
        super(message);
    }
    public ObjectFieldGenerationException (String message, Exception e) {
        super(message, e);
    }
}
