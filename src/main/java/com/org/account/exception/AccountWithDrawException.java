package com.org.account.exception;

import javax.xml.ws.WebFault;

@WebFault(name="AccountWithDrawFault", messageName="AccountWithDrawFault")
public class AccountWithDrawException extends AbstractAccountException {

    private static final long serialVersionUID = 1L;

    public AccountWithDrawException(String msg) {
        super(msg);
    }
}