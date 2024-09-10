package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//이거 모르겠어
@Data
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;
    private Email email;
    private String address;
    private String  postcode;
    //private List<OrderItemDTO> orderItems;
    private List<OrderItemDTO> orderItems=new ArrayList<>();
   // private Long productId;//상품 id

    private OrderStatus orderStatus;


    public OrderDTO(Order order) {
        this.orderId = order.getOrderId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postcode = order.getPostcode();
        this.orderStatus = order.getOrderStatus();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDTO::new)  // OrderItemDTO로 변환
                .collect(Collectors.toList());
    }

    public Order toEntity(){
        Order order=Order.builder()
                .orderId(orderId)
                .email(email)
                .address(address)
                .postcode(postcode)
                .orderStatus(orderStatus)
                .build();

        // orderItems가 null이 아니고, 비어 있지 않을 경우만 처리
        if (orderItems != null && !orderItems.isEmpty()) {
            orderItems.forEach(dto -> {
                // OrderItemDTO에서 OrderItem 엔티티로 변환
                OrderItem orderItem = OrderItem.builder()
                        .product(Product.builder().productId(dto.getProductId()).build()) // Product 객체를 생성
                        .category(dto.getCategory())
                        .price(dto.getPrice())
                        .quantity(dto.getQuantity())
                        .order(order) // 현재 생성한 Order 엔티티와 연결
                        .build();

                order.getOrderItems().add(orderItem); // Order에 OrderItem 추가
            });
        }

        return order;
    }

}
