package edu.example.gccoffee.repository;

import edu.example.gccoffee.entity.Email;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.email = :email")
    List<Order> findByEmailWithOrderItems(@Param("email") Email email);

}
