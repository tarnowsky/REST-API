package com.hapaglloyd.mike.restapi.web;

import com.hapaglloyd.mike.restapi.service.UserService;
import com.hapaglloyd.mike.restapi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        return userService.get(id);
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> get() {
        return userService.get();
    }

    @PostMapping("/users")
    public ResponseEntity<User> save(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/users")
    public ResponseEntity<User> update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

}
