package edu.example.gccoffee.repository;

import edu.example.gccoffee.entity.Email;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
  //  List<OrderItem> findByEmail(Email email);
}
