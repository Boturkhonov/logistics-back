package edu.istu.logistics.controller;

import edu.istu.logistics.entity.Vehicle;
import edu.istu.logistics.repo.VehicleRepository;
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
@RequestMapping("api/vehicles")
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getVehicle(@PathVariable Long id) {
        final Optional<Vehicle> optional = vehicleRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getVehicles(@RequestParam(required = false) Boolean free) {
        final List<Vehicle> vehicles = vehicleRepository.findAll();
        if (free != null && free) {
            vehicles.removeIf(vehicle -> vehicle.getDriver() != null);
        }
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleRepository.save(vehicle));
    }

    @PostMapping
    public ResponseEntity<?> saveVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleRepository.save(vehicle));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteVehicle(@RequestBody Vehicle vehicle) {
        vehicleRepository.delete(vehicle);
        return ResponseEntity.ok().build();
    }
}
