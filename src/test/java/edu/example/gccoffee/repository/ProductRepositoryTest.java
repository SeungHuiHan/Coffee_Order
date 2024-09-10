package edu.example.gccoffee.repository;

import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Product;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    Category category = Category.COFFEE_BEAN_PACKAGE;

    @Test //insert테스트
    public void testInsert(){
        Product product = Product.builder()
                .productId(1l)
                .productName("Columbia Coffee")
                .category(category)
                .price(10000L)
                .description("콜롬비아의 맛있는 커피")
                .build();

        Product savedProduct=productRepository.save(product);

        assertNotNull(savedProduct);
        //assertEquals(6, savedProduct.getProductId());

    }

    @Test   //select 테스트
    @Transactional(readOnly = true) //읽기 전용 트랜잭션 모드 데이터 바꾸는것이 목적이 아님
    public void testRead() {
        Long productId = 5L;

        //when
        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent(), "Product should be present");

        System.out.println("--------------------------");
        Product product = foundProduct.get();
        assertNotNull(product);
        assertEquals(5, product.getProductId());

    }

    @Test   //UPDATE 테스트 - 트랜잭션 O
    @Transactional
    @Commit
    public void testUpdateTransactional() {
        Long productId = 6L;
        String productName = "Brazil Serra Do Coffee";
        int price = 15000;
        String description="브라질산 풍미가득한 커피";

        Optional<Product> foundProduct = productRepository.findById(productId); //1. pno에 해당하는 데이터 가져오기
        assertNotNull(foundProduct); //2. 1의 데이터가 존재하는지 검증
        Product product = foundProduct.get(); //3. 1의 데이터에서 Product객체 가져오기
        //4. 3의 객체를 이용하여 productName,price,description는 변경
        product.changeProductName(productName);
        product.changePrice(price);
        product.changeDescription(description);
        foundProduct = productRepository.findById(productId); //5. pno에 해당하는 데이터 다시 가져오기
        assertEquals("Brazil Serra Do Coffee", foundProduct.get().getProductName()); //6. pname과 5의 상품이름이 일치하는지 검증 assert
        assertEquals(15000, foundProduct.get().getPrice()); //7. price와 5의 상품가격이 일치하는지 검증

    }

    @Test   //DELETE 테스트 - 트랜잭션 O
    @Transactional
    @Commit
    public void testDelete() {
        Long productId = 5L;

        assertTrue(productRepository.findById(productId).isPresent()
                , "Product should be present");//1. productId에 해당하는 Product 객체가 존재하는지 검증
        productRepository.deleteById(productId); //2. productId에 해당하는 Product 객체 삭제

        assertFalse(productRepository.findById(productId).isPresent()
                , "Product should be present");//3. productId에 해당하는 Product 객체가 존재하지 않는 것을 검증
    }

}