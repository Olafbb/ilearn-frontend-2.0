package com.ilearn.user;

import lombok.Getter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
public class ActualUser {
    private String username;
    private SecurityContext securityContext;

    public ActualUser() {
        this.securityContext = SecurityContextHolder.getContext();
        this.username = securityContext.getAuthentication().getName();
    }
}
