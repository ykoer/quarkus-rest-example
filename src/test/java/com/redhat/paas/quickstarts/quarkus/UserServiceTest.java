package com.redhat.paas.quickstarts.quarkus;

import com.redhat.paas.quickstarts.quarkus.model.User;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class UserServiceTest {

    @Inject
    UserService userService;

    @BeforeEach
    void initializeDatabaseConnections() {
        userService.deleteAllUsers();
    }

    @Test
    public void testSaveUser() {

        User user = new User("ykoer", "Yusuf", "Kör", "ykoer@redhat.com", "Application Engineer");
        userService.saveUser(user);

        Optional<User> optionalUser = userService.getUser("ykoer");
        if (optionalUser.isPresent()) {
            User got = optionalUser.get();
            assertThat(got.getUserName()).isEqualTo("ykoer");
            assertThat(got.getFirstName()).isEqualTo("Yusuf");
            assertThat(got.getLastName()).isEqualTo("Kör");
            assertThat(got.getEmail()).isEqualTo("ykoer@redhat.com");
            assertThat(got.getPosition()).isEqualTo("Application Engineer");
        }
    }

    @Test
    public void testUpdateUser() {

        User user = new User("ykoer", "Yusuf", "Kör", "ykoer@redhat.com", "Application Engineer");
        userService.saveUser(user);

        user.setLastName("Doe");
        userService.updateUser(user);

        Optional<User> optionalUser = userService.getUser("ykoer");
        if (optionalUser.isPresent()) {
            User got = optionalUser.get();
            assertThat(got.getUserName()).isEqualTo("ykoer");
            assertThat(got.getFirstName()).isEqualTo("Yusuf");
            assertThat(got.getLastName()).isEqualTo("Doe");
            assertThat(got.getEmail()).isEqualTo("ykoer@redhat.com");
            assertThat(got.getPosition()).isEqualTo("Application Engineer");
        }
    }


    @Test
    public void testDeleteUser() {

        User user = new User("ykoer", "Yusuf", "Kör", "ykoer@redhat.com", "Application Engineer");
        userService.saveUser(user);

        Optional<User> optionalUser = userService.getUser("ykoer");
        assertThat(optionalUser.isPresent()).isTrue();

        userService.deleteUser("ykoer");
        optionalUser = userService.getUser("ykoer");
        assertThat(optionalUser.isPresent()).isFalse();
    }

    @Test
    public void testDeleteAllUsers() {

        User user1 = new User("ykoer", "Yusuf", "Kör", "ykoer@redhat.com", "Application Engineer");
        userService.saveUser(user1);

        User user2 = new User("jdoe", "John", "Doe", "jdoe@redhat.com", "Designer");
        userService.saveUser(user2);

        Optional<User> optionalUser = userService.getUser("ykoer");
        assertThat(optionalUser.isPresent()).isTrue();

        optionalUser = userService.getUser("jdoe");
        assertThat(optionalUser.isPresent()).isTrue();

        userService.deleteAllUsers();

        optionalUser = userService.getUser("ykoer");
        assertThat(optionalUser.isPresent()).isFalse();

        optionalUser = userService.getUser("jdoe");
        assertThat(optionalUser.isPresent()).isFalse();
    }

    @Test
    public void testGetAllUsers() {

        User user1 = new User("ykoer", "Yusuf", "Kör", "ykoer@redhat.com", "Application Engineer");
        userService.saveUser(user1);

        User user2 = new User("jdoe", "John", "Doe", "jdoe@redhat.com", "Designer");
        userService.saveUser(user2);

        Set<User> users = userService.getAllUsers();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void testCountUsers() {

        User user1 = new User("ykoer", "Yusuf", "Kör", "ykoer@redhat.com", "Application Engineer");
        userService.saveUser(user1);

        User user2 = new User("jdoe", "John", "Doe", "jdoe@redhat.com", "Designer");
        userService.saveUser(user2);

        long userCount = userService.countUsers();
        assertThat(userCount).isEqualTo(2);
    }
}
