package edu.example.gccoffee.controller.api;

import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.dto.OrderItemDTO;
import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.entity.Email;
import edu.example.gccoffee.entity.OrderStatus;
import edu.example.gccoffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //RESTful API를 서비스하는 컨트롤러
@CrossOrigin(origins = "http://localhost:3000") // 요청을 허용할 도메인 설정
@RequiredArgsConstructor //필수 또는 final 필드 초기화 생성자 인젝션하는 어노테이션
@RequestMapping("/api/v1/orders") //를 대표 요청 매핑으로 설정
@Log4j2
public class OrderRestController {
    private final OrderService orderService;

    //http://localhost:8080/api/v1/orders
    @PostMapping() //상품 주문  --------
    public ResponseEntity<OrderDTO> register(@RequestBody OrderDTO orderDTO){ // 클라이언트에서 전송한 JSON 데이터를 ProductDTO 객체로 변환하여 받아온다.
        //상태 코드를 200 OK로 하여, 상품 등록 서비스가 반환하는 데이터를 뷰로 전달
        return ResponseEntity.ok(orderService.register(orderDTO)); // 유효성 검사를 통과하면 상품을 등록하고, HTTP 상태 코드 200(OK)를 포함한 ResponseEntity 객체를 반환
    }

    //http://localhost:8080/api/v1/orders/{orderId}
    @GetMapping("/{orderId}")
    @ResponseBody
    public ResponseEntity<OrderDTO> read(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.read(orderId));
    }

    //OrderStatus를 @RequestBody로 단일값을 받을 때 발생할 수 있는 타입 변환 이슈로 발생
    //OrderStatus는 enum이고, @RequestBody로 받으려면 JSON 객체로 전달되기 때문에 변환 문제를 일으킬 수 있다.
    // @RequestParam을 사용해서 OrderStatus를 받는 방식을 시도할 수 있다.
    // 이렇게 하면 OrderStatus를 String으로 받고, Spring이 자동으로 enum 타입으로 변환한다.
    //http://localhost:8080/api/v1/orders/hong@gmail.com/status?orderStatus=SHIPPED
    // 주문 상태 변경 API (이메일로 주문 상태 일괄 변경)
    @PutMapping("/{email}/status")
    public ResponseEntity<List<OrderDTO>> modifyOrderStatusByEmail(
            @PathVariable("email") String emailStr,
            @RequestParam("orderStatus") OrderStatus newStatus) {

        // 이메일 객체 생성
        Email email = new Email(emailStr);

        // 주문 상태 변경 후, 변경된 주문 목록 반환
        List<OrderDTO> updatedOrders = orderService.modifyOrderStatusByEmail(email, newStatus);

        return ResponseEntity.ok(updatedOrders);
    }


    // http://localhost:8080/api/v1/orders/qaws1@gmail.com/items
    // 이메일로 주문한 OrderItems 조회 API
    @GetMapping("/{email}/items")
    public ResponseEntity<List<OrderItemDTO>> getOrderItemsByEmail(
            @PathVariable("email") String emailStr) {

        // 이메일 객체 생성
        Email email = new Email(emailStr);

        // 이메일로 주문한 OrderItems 반환
        List<OrderItemDTO> orderItems = orderService.getOrderItemsByEmail(email);

        return ResponseEntity.ok(orderItems);
    }

// http://localhost:8080/api/v1/orders
//    @GetMapping()
//    public ResponseEntity<List<OrderDTO>> getList(OrderDTO orderDTO) {
//        // 이메일 객체 생성
//        Email email = new Email(emailStr);
//
//    }


}
