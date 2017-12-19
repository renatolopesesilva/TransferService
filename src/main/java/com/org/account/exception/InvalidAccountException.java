package com.org.account.exception;

import javax.xml.ws.WebFault;

@WebFault(name="InvalidAccountFault", messageName="InvalidAccountFault")
public class InvalidAccountException extends AbstractAccountException {

    private static final long serialVersionUID = 1L;

    public InvalidAccountException(String msg) {
        super(msg);
    }
}