package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity와 무조건 같을 필요는 없다!!! 화면에 반환되는 필드만 적어도 됨!!!
@Data
@NoArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private Category category;
    private long price;
    private Integer quantity;

    // OrderItem 엔티티에서 OrderItemDTO로 변환하는 생성자
    public OrderItemDTO(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getProductId();
        this.category = orderItem.getCategory();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
    }

    public OrderItem toEntity(){
        Product product=Product.builder().productId(productId).build();

        OrderItem orderItem=OrderItem.builder()
                .product(product)
                .category(category)
                .price(price)
                .quantity(quantity)
                .build();

        return orderItem;
    }
//    private Email email; //Order
//    private Long productId; //Product
//
//    private Long orderItemId;
//    private Category category;
//    private long price;
//    private int quantity;
//
//    public OrderItemDTO(OrderItem orderItem) {
//      this.productId=orderItem.getProduct().getProductId();
//      this.orderItemId=orderItem.getOrder().getOrderId();
//      this.category=orderItem.getCategory();
//      this.price=orderItem.getPrice();
//      this.quantity=orderItem.getQuantity();
//
//    }

//    public OrderItem toEntity(){
//      Product product=Product.builder().productId(productId).build();
//      Order order=Order.builder().orderId(orderItemId).build();
//      OrderItem orderItem=OrderItem.builder()
//              .product(product)
//              .order(order)
//              .category(category)
//              .price(price)
//              .quantity(quantity)
//              .build();
//
//      return orderItem;
//    }


}
