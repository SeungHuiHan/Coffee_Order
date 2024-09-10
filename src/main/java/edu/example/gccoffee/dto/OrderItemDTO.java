package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDTO {

    private Email email; //Order
    private Long productId; //Product

    private Long orderItemId;
    private Category category;
    private long price;
    private int quantity;

    public OrderItemDTO(OrderItem orderItem) {
      this.productId=orderItem.getProduct().getProductId();
      this.orderItemId=orderItem.getOrder().getOrderId();
      this.category=orderItem.getCategory();
      this.price=orderItem.getPrice();
      this.quantity=orderItem.getQuantity();

    }

    public OrderItem toEntity(){
      Product product=Product.builder().productId(productId).build();
      Order order=Order.builder().orderId(orderItemId).build();
      OrderItem orderItem=OrderItem.builder()
              .product(product)
              .order(order)
              .category(category)
              .price(price)
              .quantity(quantity)
              .build();

      return orderItem;
    }


}
