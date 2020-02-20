
package com.mycompany.bluefood.application.service;

public class PagamentoException extends Exception{

    public PagamentoException() {
    }

    public PagamentoException(String message) {
        super(message);
    }

    public PagamentoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PagamentoException(Throwable cause) {
        super(cause);
    }

}
