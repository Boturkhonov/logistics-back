package edu.istu.logistics.repo;

import edu.istu.logistics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
