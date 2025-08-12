package com.mktpc.marketPlace.security;

import jakarta.annotation.PreDestroy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextCleanup {

    @PreDestroy
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
        System.out.println("SecurityContextHolder cleared on shutdown.");
    }
}
