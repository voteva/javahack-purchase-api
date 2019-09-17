package com.purchase.admin.service;

import com.purchase.admin.domain.Client;
import com.purchase.admin.domain.Session;

public interface SessionService {

    Session createSession(Client client);
}
