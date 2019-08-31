package com.trade.legalentities.exceptions;

public class ApplicationException extends Exception {
    private static final long serialVersionUID = 132432432432432L;

    public ApplicationException(String errorMessage) {
        super(errorMessage);
    }
}
