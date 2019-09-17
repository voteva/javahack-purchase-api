package com.purchase.admin.security;

import org.springframework.security.core.AuthenticationException;

class WrongClientDataException
        extends AuthenticationException {
    WrongClientDataException(String msg) {
        super(msg);
    }
}
