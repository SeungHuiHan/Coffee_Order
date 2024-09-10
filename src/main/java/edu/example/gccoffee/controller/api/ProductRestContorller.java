package edu.example.gccoffee.controller.api;

import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController //RESTful API를 서비스하는 컨트롤러
@RequiredArgsConstructor //필수 또는 final 필드 초기화 생성자 인젝션하는 어노테이션
@RequestMapping("/api/v1/products") //를 대표 요청 매핑으로 설정
@Log4j2
public class ProductRestContorller {
    private final ProductService productService;

    //http://localhost:8080/api/v1/products
    @PostMapping() //예약 상세 조회 --------

    public ResponseEntity<ProductDTO> register(@RequestBody ProductDTO productDTO){ // 클라이언트에서 전송한 JSON 데이터를 ProductDTO 객체로 변환하여 받아온다.
        //상태 코드를 200 OK로 하여, 상품 등록 서비스가 반환하는 데이터를 뷰로 전달
        return ResponseEntity.ok(productService.register(productDTO)); // 유효성 검사를 통과하면 상품을 등록하고, HTTP 상태 코드 200(OK)를 포함한 ResponseEntity 객체를 반환
    }

    //http://localhost:8080/api/v1/products/{productId}
    @GetMapping("/{productId}")//상품 조회
    @ResponseBody
    public ResponseEntity<ProductDTO> read(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.read(productId));
    }

    //http://localhost:8080/api/v1/products/{productId}
    @PutMapping("/{productId}") //상품수정
    @ResponseBody
    public ResponseEntity<ProductDTO> modify( @RequestBody ProductDTO productDTO, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.modify(productDTO));
    }

    //http://localhost:8080/api/v1/products/{productId}
    @DeleteMapping("/{productId}")//상품 삭제
    @ResponseBody
    public ResponseEntity<Map<String,Long>> remove(@PathVariable("productId") Long productId) {
        Long mid = productService.read(productId).getProductId(); //데이터베이스에 이미 저장된 상품의 등록자 ID
        productService.remove(productId); //삭제 처리 후
        return ResponseEntity.ok(Map.of("result", mid));

    }
}
