package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.Category;

import edu.example.gccoffee.entity.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long productId;

    @NotEmpty
    private String productName;
    @NotEmpty
    private Category category;
    @NotEmpty
    private long price;
    private String description;

    //Product를 매개변수로 받아서 현재 객체의 필드들을 초기화하는 생성자
    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.description = product.getDescription();

    }

    //DTO(Data Transfer Object)에서 엔티티(Entity)로 변환하는 메서드
    public Product toEntity(){
        Product product = Product.builder().productId(productId)
                .productName(productName)
                .category(category)
                .price(price)
                .description(description)
                .build();

        return product;
    }

}
