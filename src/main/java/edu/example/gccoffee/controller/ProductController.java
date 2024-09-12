package edu.example.gccoffee.controller;


import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.entity.Product;
import edu.example.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Log4j2
public class ProductController {
    private final ProductService productService;
    //GET http://localhost:8080/products
    @GetMapping("/products")
    public String listProducts(Model model) {
        System.out.println("/products/product-list ");

        List<Product> products=productService.findAll();
        model.addAttribute("products", products);

        return "product-list";
    }

    //http://localhost:8080/products 의 Add Product 버튼 누르면 나옴
    @GetMapping("new-product")
    public String newProductPage() {
        return "new-product";
    }

    //submit버튼 누르면 product-list로 돌아감
    @PostMapping("/products")
    public String newProduct(ProductDTO productDTO) {
        productService.register(
                productDTO.getProductName(),
                productDTO.getCategory(),
                productDTO.getPrice(),
                productDTO.getDescription());
        return "redirect:/products";
    }
}
