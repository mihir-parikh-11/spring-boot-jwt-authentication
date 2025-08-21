package com.auth.app.utility;

import com.auth.app.entity.User;
import com.auth.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserUtility {

    private static UserService userService;

    @Autowired
    public UserUtility(UserService userService) {
        UserUtility.userService = userService;
    }

    public static String getCurrentLoginUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                return userDetails.getUsername();
            } else {
                return null;
            }
        }
        return null;
    }

    public static User getCurrentLoginUser() {
        return userService.findByUsername(getCurrentLoginUsername());
    }
}
