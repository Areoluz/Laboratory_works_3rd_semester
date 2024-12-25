package ui.services;

import jpa.entities.User;
import org.springframework.security.core.context.SecurityContext;
import ui.exeptions.UnauthenticatedException;

public abstract class AbstractSecurityService {
    protected Long getId(SecurityContext context) throws UnauthenticatedException {
        if (context.getAuthentication().getPrincipal() instanceof User user) {
            return user.getId();
        } else throw new UnauthenticatedException("Ошибка аунтефикации");
    }

    protected User getUser(SecurityContext context) throws UnauthenticatedException {
        if (context.getAuthentication().getPrincipal() instanceof User user) {
            return user;
        } else throw new UnauthenticatedException("Ошибка аунтефикации");
    }
}
