package com.redhat.paas.quickstarts.quarkus;

import com.redhat.paas.quickstarts.quarkus.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class UserService {

    private Set<User> users = new HashSet<>();

    @PostConstruct
    public void init() throws Exception {
    }

    public void saveUsers(Set<User> users) {
        if (users!=null) {
            for(User user:users) {
                saveUser(user);
            }
        }
    }

    public void saveUser(User user) {
        users.add(user);
    }

    public void deleteUser(String userName) {
        users.removeIf(user -> user.getUserName().equals(userName));
    }

    public void updateUser(User user) {
        users.stream()
                .filter(u -> u.getUserName().equals(user.getUserName()))
                .findFirst()
                .ifPresent(u -> u=user);
    }

    public Optional<User> getUser(String userName) {
        return users.stream()
                .filter(u -> u.getUserName().equals(userName))
                .findFirst();
     }

    public Set<User> getAllUsers() {
        return users;
    }

    public long countUsers() {
        return users.size();
    }

    public void deleteAllUsers() {
        users = new HashSet<>();
    }
}
