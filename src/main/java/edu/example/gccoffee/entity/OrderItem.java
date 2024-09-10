package edu.example.gccoffee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="order_items",
        indexes = @Index(columnList = "order_order_id"))
@Getter
@ToString(exclude = {"product","order"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;


    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY) //fetch = FetchType.LAZY: 지연 로딩,Product 엔티티는 실제로 필요할 때까지 로딩되지 않으며, 이 관계가 실제로 접근될 때에만 쿼리가 실행된다.
    private Order order;

    @Enumerated(EnumType.STRING)
    private Category category;
    private long price;
    private int quantity;

    @CreatedDate //등록 일시 자동 저장
    private LocalDateTime createAt;
    @LastModifiedDate // 수정 시점에 자동 업데이트
    private LocalDateTime updateAt;

    private void chaneCategory(Category category) {this.category = category;}
    private void changePrice(int price) {this.price = price;}
    public void changeQuantity(int quantity) {this.quantity = quantity;}
}
