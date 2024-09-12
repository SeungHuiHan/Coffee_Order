package edu.example.gccoffee.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(value={AuditingEntityListener.class})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    //Email 클래스를 JPA 속성 변환기(@Converter)를 사용하여 처리
    @Convert(converter = EmailConverter.class)
    private Email email;
    private String address;
    private String  postcode;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("orderItemId ASC") // Optional: ordering the list
    @Builder.Default
    //private SortedSet<OrderItem> orderItems = new TreeSet<>();  // 빈 리스트로 초기화
    private List<OrderItem> orderItems = new ArrayList<>();
    // JPA는 Enum 값을 데이터베이스에 저장할 때 @Enumerated 어노테이션을 통해 어떻게 저장할지를 결정한다.
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; ;

    @CreatedDate
    private LocalDateTime createAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    //어렵다어려워
    // orderItems 추가 메서드 수정
    public void addOrderItem(Product product, int quantity) {
        OrderItem orderItem = OrderItem.builder()
                .product(product)    // product 정보를 넣어줌
                .category(product.getCategory())  // product의 category를 가져옴
                .price(product.getPrice())        // product의 price를 가져옴
                .quantity(quantity)               // quantity는 매개변수로 받아옴
                .order(this)                      // 현재 Order 객체를 참조
                .build();

        orderItems.add(orderItem);
    }

    //orderItems삭제
    public void clearOrderItems() {orderItems.clear();}

    public void changeEmail(Email email) {this.email = email;}
    public void changeAddress(String address) {this.address = address;}
    public void changePostcode(String postcode) {this.postcode = postcode;}
    public void changeOrderStatus(OrderStatus orderStatus) {this.orderStatus = orderStatus;}



}
