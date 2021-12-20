package edu.istu.logistics.repo;

import edu.istu.logistics.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
