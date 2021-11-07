package ru.otus.appcontainer;

public class NoBeanException extends RuntimeException{

    public NoBeanException(){

    }
    public NoBeanException(String exception){
        super(exception);
    }
}
