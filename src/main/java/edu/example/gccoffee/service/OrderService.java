package edu.example.gccoffee.service;

import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.dto.OrderItemDTO;
import edu.example.gccoffee.dto.PageRequestDTO;
import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.entity.Email;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderStatus;
import edu.example.gccoffee.entity.Product;
import edu.example.gccoffee.exception.OrderException;
import edu.example.gccoffee.exception.ProductException;
import edu.example.gccoffee.repository.OrderItemRepository;
import edu.example.gccoffee.repository.OrderRepository;
import edu.example.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    //주문 등록
    public OrderDTO register(OrderDTO orderDTO) {
        try{
            Order order =orderDTO.toEntity(); //입력받은 데이터를 DB에 넣기 위해서 dto를 entity로 변환
            orderRepository.save(order);//DB에 entity집어 넣기
            return new OrderDTO(order); //DB에 저장한 값을 다시 호출
        }catch(Exception e){ //상품 등록 시 예외가 발생한 경우
            log.error("-----"+e.getMessage()); //에러 로그로 발생 예외의 메시지를 기록하고
            throw OrderException.NOT_REGISTERED.get();   //예외 메시지를 Product NOT Registered로 저장하여
        }
        //ProductTackException 발생시키기
    }

    //주문 조회
    public OrderDTO read(Long orderId){// orderId라는 고유 ID를 사용하여 데이터베이스에서 특정 데이터를 조회
        Optional<Order> foundOrder= orderRepository.findById(orderId);
        //조회 결과가 없는 경우
        //예외 메시지를 Order NOT FOUND로 발생시키기
        //OrderTaskException 발생시키기
        Order order=foundOrder.orElseThrow(OrderException.NOT_FOUND_ORDER::get);
        return new OrderDTO(order); //넘겨받은 엔티티를 DTO 객체로 반환->DTO는 뷰로 반환
    }

    // 주문 상태 수정 (이메일 기준)
    public List<OrderDTO> modifyOrderStatusByEmail(Email email, OrderStatus newStatus) {
        // 이메일로 주문을 조회
        List<Order> orderList = orderRepository.findByEmailWithOrderItems(email);

        if (orderList.isEmpty()) {
            throw OrderException.NOT_FOUND_ORDER.get();
        }

        // 모든 주문의 상태를 변경
        orderList.forEach(order -> {
            order.changeOrderStatus(newStatus);
        });

        // 상태가 변경된 주문 목록을 DTO로 변환하여 반환
        return orderList.stream()
                .map(OrderDTO::new) // Order -> OrderDTO 변환
                .collect(Collectors.toList());
    }

    // 이메일로 주문한 OrderItems 조회
    public List<OrderItemDTO> getOrderItemsByEmail(Email email) {
        // 이메일로 주문을 조회
        List<Order> orders = orderRepository.findByEmailWithOrderItems(email);

        // 모든 주문의 OrderItems를 OrderItemDTO 리스트로 변환하여 반환
        return orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());
    }




//    //주문상태 수정
//    public void modifyOrderStatus(OrderDTO orderDTO){
//        List<Order> orderList=orderRepository.findByEmailWithOrderItems(orderDTO.getEmail());
//
//        // 주문이 존재하지 않으면 예외 발생
//        if (orderList.isEmpty()) {
//            throw OrderException.NOT_FOUND_ORDER.get();
//        }
//
//        // 주문 상태를 변경하고 저장
//        orderList.forEach(order -> {
//            order.changeOrderStatus(orderDTO.getOrderStatus());
//            orderRepository.save(order);
//        });
//
//    }

    //같은 이메일의 주문 목록을 조회

}
