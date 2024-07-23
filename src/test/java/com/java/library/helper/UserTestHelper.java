package com.java.library.helper;

import com.java.library.model.User;
import com.java.library.model.Role;

import java.util.Arrays;
import java.util.List;

public class UserTestHelper {

    public static User createUser(Long id, String name, String username, String password, Role role) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    public static List<User> createMockUsers() {
        User user01 = createUser(3L, "User01", "user01@user.com", "password", Role.USER);
        User user02 = createUser(4L, "User02", "user02@user.com", "password", Role.USER);
        User user03 = createUser(5L, "User03", "user03@user.com", "password", Role.USER);
        User admin01 = createUser(6L, "Admin01", "admin01@user.com", "password", Role.ADMIN);
        User admin02 = createUser(7L, "Admin02", "admin02@user.com", "password", Role.ADMIN);

        return Arrays.asList(user01, user02, user03, admin01, admin02);
    }
}