package edu.istu.logistics.controller;

import edu.istu.logistics.entity.Role;
import edu.istu.logistics.entity.User;
import edu.istu.logistics.repo.RoleRepository;
import edu.istu.logistics.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/drivers")
public class DriverController {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public DriverController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping
    public ResponseEntity<?> saveDriver(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
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
