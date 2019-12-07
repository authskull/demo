package com.authskull.demo.controller;

import com.authskull.demo.dto.NewProduct;
import com.authskull.demo.dto.ProductDto;
import com.authskull.demo.entity.Product;
import com.authskull.demo.manager.ProductManager;
import com.authskull.demo.utils.HttpHelper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static com.authskull.demo.controller.ProductController.PRODUCTS;

@RestController
@RequestMapping(PRODUCTS)
@Validated
public class ProductController {

    public static final String PRODUCTS = "/products";
    private ProductManager manager;

    public ProductController(ProductManager manager) {
        this.manager = manager;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody NewProduct newProduct,
                                 UriComponentsBuilder locationBuilder) {
        Product product = manager.create(newProduct);
        UriComponents uriComponents =
                locationBuilder.path(PRODUCTS).path("/" + product.getId()).build();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable("id") Integer id) {
        return manager.getById(id);
    }

    @GetMapping()
    public ResponseEntity getAll(
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit) {
        Page<ProductDto> productDtos = manager.getAll(offset, limit);
        HttpHeaders headers = HttpHelper.getResultHeaders(productDtos);
        return ResponseEntity.ok().headers(headers).body(productDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable("id") Integer id, @Valid @RequestBody ProductDto productDto) {
        manager.updateById(id, productDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id) {
        manager.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
