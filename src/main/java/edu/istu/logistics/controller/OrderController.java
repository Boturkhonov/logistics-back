package edu.istu.logistics.controller;

import edu.istu.logistics.entity.Order;
import edu.istu.logistics.entity.Route;
import edu.istu.logistics.repo.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        final Optional<Order> optional = orderRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getOrders() {
        final List<Order> orders = orderRepository.findAll();
        orders.forEach(order -> {
            final List<Route> unFinishedRoutes =
                    order.getRoutes().stream().filter(route -> !route.getFinished()).collect(Collectors.toList());
            if (order.getRoutes().size() > 0) {
                if (unFinishedRoutes.size() == 0)
                    order.setFinished(true);
                order.setAssigned(true);
            } else {
                order.setAssigned(false);
            }
        });
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteOrder(@RequestBody Order order) {
        orderRepository.delete(order);
        return ResponseEntity.ok().build();
    }
}
