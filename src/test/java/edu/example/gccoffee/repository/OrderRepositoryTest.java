package edu.example.gccoffee.repository;

import edu.example.gccoffee.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)

class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;

    OrderStatus orderStatus=OrderStatus.ACCPTED;

    @Test //insert테스트
    public void testInsert(){
        Long productId=6L;
        int quantity=4;
        //email 필드가 String이 아니라 Email 타입
        // 따라서 Email 객체를 직접 생성하여 email 필드에 할당해야 함
        Email email = new Email("qaws1@gmail.com");

        // Product 조회
        //먼저 productId에 해당하는 Product이 있는지 조회
        Optional<Product> foundProduct = productRepository.findById(productId);
        Product product = foundProduct.orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Order 생성 및 저장
        Order order = Order.builder()
                .email(email)
                .address("서울시 영등포구 ")
                .postcode("145666")
                .orderStatus(orderStatus)
                .build();
        Order savedOrder = orderRepository.save(order);

        // OrderItem 생성 및 저장
        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .order(savedOrder)
                .category(product.getCategory())
                .price(product.getPrice())
                .quantity(quantity)
                .build();

        OrderItem savedOrderItem=orderItemRepository.save(orderItem);

        assertNotNull(savedOrderItem);
        //assertEquals(orderId,savedOrder.getOrderId());
        assertNotNull(savedOrder);
    }

    @Test //이메일로 고객의 주문 목록 확인
    @Transactional
    public void testGetOrderItems() {
        Email email = new Email("qaws1@gmail.com");

        // 이메일로 주문을 찾기
        List<Order> orders = orderRepository.findByEmailWithOrderItems(email);
        assertNotNull(orders, "Orders should be not null");
        assertFalse(orders.isEmpty(), "Orders should not be empty");

        // 주문 항목을 출력
        orders.forEach(order -> {
            System.out.println("Order ID: " + order.getOrderId());
            order.getOrderItems().forEach(orderItem -> {
                System.out.println("-------------------------");
                System.out.println("Product ID: " + orderItem.getProduct().getProductId());
                System.out.println("Product Name: " + orderItem.getProduct().getProductName());
                System.out.println("Price: " + orderItem.getPrice());
                System.out.println("Quantity: " + orderItem.getQuantity());
            });
        });
    }

    @Test //이메일로 고객의 상품 개수 수정
    @Transactional
    public void testUpdateQuantity() {

    }

    @Test //이메일로 고객의 ORDER_STATUS상태 변환
    @Transactional
    @Commit
    public void testUpdtaeOrderStatus() {
        Email email = new Email("qaws1@gmail.com");

        // 이메일로 주문을 찾기
        List<Order> foundOrders = orderRepository.findByEmailWithOrderItems(email);
        assertNotNull(foundOrders, "Orders should be not null");
        assertFalse(foundOrders.isEmpty(), "Orders should not be empty");

        foundOrders.forEach(orders->{
            orders.changeOrderStatus(OrderStatus.PAYMENT_CONFIRMED);
            System.out.println("Order ID: " + orders.getOrderId());
            System.out.println(orders.getOrderStatus());
        });

    }

    @Test //이메일로 고객 주문 삭제
    @Transactional
    @Commit
    public void testDeleteOrder() {
        Email email = new Email("aba@naver.com");

        // 이메일로 주문을 찾기
        List<Order> foundOrders = orderRepository.findByEmailWithOrderItems(email);
        assertNotNull(foundOrders, "Orders should be not null");
        assertFalse(foundOrders.isEmpty(), "Orders should not be empty");

        foundOrders.forEach(orders->{

        });

    }


}