package edu.example.gccoffee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name="products")
@Getter
//@ToString(exclude = "images")//images 필드는 toString() 출력에서 제외됨 - 무한 재귀 호출을 방지하기 위함
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(value={AuditingEntityListener.class})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    // JPA는 Enum 값을 데이터베이스에 저장할 때 @Enumerated 어노테이션을 통해 어떻게 저장할지를 결정한다.
    @Enumerated(EnumType.STRING)
    private Category category;
    private long price;
    private String description;

    @CreatedDate //등록 일시 자동 저장
    private LocalDateTime createAt;
    @LastModifiedDate // 수정 시점에 자동 업데이트
    private LocalDateTime updateAt;

    // 생성자
    public Product(String productName, Category category, long price, String description) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }


//    public Product(Long productId) {
//        this.productId = productId;
//    }

    //JPA에서는 setter메서드형태가 change...()로 만들기를 권장
    //그래서 @Data어노테이션(@Setter를 포함하는)을 쓰지않고 @Getter,@Tostring이렇게 따로따로 어노테이션 명시함!!!
    public void changeProductName(String productName) {this.productName = productName;}
    public void changeCategory(Category category) {this.category = category;}
    public void changePrice(long price) {this.price = price;}
    public void changeDescription(String description) {this.description = description;}
}
