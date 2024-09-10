package edu.example.gccoffee.service;


import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.entity.Product;
import edu.example.gccoffee.exception.ProductException;
import edu.example.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ProductService {
    private final ProductRepository productRepository;


    //상품 등록
    public ProductDTO register(ProductDTO productDTO) {
        try{
            Product product =productDTO.toEntity(); //입력받은 데이터를 DB에 넣기 위해서 dto를 entity로 변환
            productRepository.save(product);//DB에 entity집어 넣기
            return new ProductDTO(product); //DB에 저장한 값을 다시 호출
        }catch(Exception e){ //상품 등록 시 예외가 발생한 경우
            log.error("-----"+e.getMessage()); //에러 로그로 발생 예외의 메시지를 기록하고
            throw ProductException.NOT_REGISTERED.get();   //예외 메시지를 Product NOT Registered로 저장하여
        }
        //ProductTackException 발생시키기
    }

    //상품 조회
    public ProductDTO read(Long productId){// mno라는 고유 ID를 사용하여 데이터베이스에서 특정 데이터를 조회
        Optional<Product> foundProduct= productRepository.findById(productId);
        //조회 결과가 없는 경우
        //예외 메시지를 Product NOT FOUND로 발생시키기
        //ProductTaskException 발생시키기
        Product product=foundProduct.orElseThrow(ProductException.BAD_CREDENTIALS::get);
        return new ProductDTO(product); //넘겨받은 엔티티를 DTO 객체로 반환->DTO는 뷰로 반환
    }

    //상품 수정
    //예외가 발생한 경우 Product NOT Modified 메시지로 예외 발생시키기
    public ProductDTO modify(ProductDTO productDTO) {
        Optional<Product> foundProduct=productRepository.findById(productDTO.getProductId()); //수정하려는 상품을 데이터베이스에서 조회해서
        Product product=foundProduct.orElseThrow(ProductException.NOT_FOUND::get); //해당 상품이 없는 경우 Product NOT FOUND 예외 발생 시키기

        try {
            //상품 이름, 가격, 설명 수정
            product.changeProductName(productDTO.getProductName());
            product.changePrice(productDTO.getPrice());
            product.changeDescription(productDTO.getDescription());

            return new ProductDTO(product);//변경된 상품을 반환
        }catch (Exception e){
            log.error("--- "+e.getMessage());
            throw ProductException.NOT_MODIFIED.get();
        }
    }

    //상품 삭제
    public void remove(Long productId) {
        Optional<Product> foundProduct=productRepository.findById(productId); //삭제하려는 상품의 번호를 데이터베이스에서 조회해서
        Product product=foundProduct.orElseThrow(ProductException.NOT_FOUND::get); //해당 상품이 없는 경우 Product NOT FOUND 예외 발생 시키기

        try {
            productRepository.delete(product);
        }catch (Exception e){
            log.error("--- "+e.getMessage());
            throw ProductException.NOT_REMOVED.get(); // 상품 삭제 실패 예외 발생
        }
    }


    //상품  리스트
    public List<Product> findAll() {
        return productRepository.findAll();
    }






}
