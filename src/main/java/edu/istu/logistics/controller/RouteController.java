package edu.istu.logistics.controller;

import edu.istu.logistics.entity.Route;
import edu.istu.logistics.repo.RouteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/routes")
public class RouteController {

    private final RouteRepository routeRepository;

    public RouteController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getRoute(@PathVariable Long id) {
        final Optional<Route> optional = routeRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> saveRoute(@RequestBody Route route) {
        return ResponseEntity.ok(routeRepository.save(route));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateRoute(@RequestBody Route route) {
        final Optional<Route> optional = routeRepository.findById(route.getId());
        if (optional.isPresent()) {
            optional.get().setFinished(route.getFinished());
            return ResponseEntity.ok(routeRepository.save(optional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRoute(@RequestBody Route route) {
        routeRepository.delete(route);
        return ResponseEntity.ok().build();
    }
}
