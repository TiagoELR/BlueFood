
package com.mycompany.bluefood.application.service;

public class ApplicationServiceExceptions extends RuntimeException{

    public ApplicationServiceExceptions(String message) {
        super(message);
    }

    public ApplicationServiceExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationServiceExceptions(Throwable cause) {
        super(cause);
    }
    
}
