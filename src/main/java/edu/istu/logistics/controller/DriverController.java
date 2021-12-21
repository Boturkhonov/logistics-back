package edu.istu.logistics.controller;

import edu.istu.logistics.entity.Role;
import edu.istu.logistics.entity.User;
import edu.istu.logistics.repo.RoleRepository;
import edu.istu.logistics.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("api/drivers")
public class DriverController {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public DriverController(UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getDriver(@PathVariable Long id) {
        final Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/selected")
    public ResponseEntity<?> getDrivers(@RequestParam List<Long> ids) {
        final List<User> users = userRepository.findAllById(ids);
        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<?> getDrivers() {
        final List<User> users = userRepository.findAll();
        final Role adminRole = roleRepository.findByName("ADMIN");
        users.removeIf(user -> user.getRoles().contains(adminRole));
        return ResponseEntity.ok(users);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateDriver(@RequestBody User user) {

        final Optional<User> optional = userRepository.findById(user.getId());
        if (optional.isPresent()) {
            user.setPassword(optional.get().getPassword());
            return ResponseEntity.ok(userRepository.save(user));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveDriver(@RequestBody User user) {
        user.setLogin(user.getLogin().toLowerCase(Locale.ROOT));
        final Optional<User> userOptional = userRepository.findByLogin(user.getLogin());
        if (!userOptional.isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            final Role role = roleRepository.findByName("DRIVER");
            user.setRoles(Collections.singleton(role));
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDriver(@RequestBody User user) {
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/routes")
    public ResponseEntity<?> getDriverRoutes(@PathVariable Long id) {
        final Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get().getRoutes());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
