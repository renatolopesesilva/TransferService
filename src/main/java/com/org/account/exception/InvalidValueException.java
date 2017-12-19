package com.org.account.exception;

import javax.xml.ws.WebFault;

@WebFault(name="InvalidValueFault", messageName="InvalidValueFault")
public class InvalidValueException extends AbstractAccountException {

    private static final long serialVersionUID = 1L;

    public InvalidValueException(String msg) {
        super(msg);
    }
}