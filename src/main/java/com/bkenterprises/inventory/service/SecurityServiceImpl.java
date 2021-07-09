package com.bkenterprises.inventory.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }

}
