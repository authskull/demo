package com.authskull.demo.manager;

import com.authskull.demo.dto.NewProduct;
import com.authskull.demo.dto.ProductDto;
import com.authskull.demo.entity.Category;
import com.authskull.demo.entity.Image;
import com.authskull.demo.entity.Product;
import com.authskull.demo.repository.ProductRepository;
import com.authskull.demo.utils.Helper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.http.HttpHeaders.EMPTY;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class ProductManager {

    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_LIMIT = 10;
    private ProductRepository repository;
    private ImageManager imageManager;
    private CategoryManager categoryManager;
    private Helper helper;

    public ProductManager(ProductRepository repository, ImageManager imageManager, CategoryManager categoryManager, Helper helper) {
        this.repository = repository;
        this.categoryManager = categoryManager;
        this.imageManager = imageManager;
        this.helper = helper;
    }

    public Product create(NewProduct newProduct) {
        Product product = helper.transformToProduct(newProduct);
        Set<Image> images = newProduct.getImages().stream().map(id -> imageManager.getImageById(id)).collect(Collectors.toSet());
        Set<Category> categories = newProduct.getCategories().stream().map(id -> categoryManager.getCategoryById(id)).collect(Collectors.toSet());
        product = product.toBuilder().productImages(images).productCategories(categories).build();
        return repository.save(product);
    }

    public ProductDto getById(Integer id) throws NotFound {
        Product product = this.getProductById(id);
        return helper.transformToProductDto(product);
    }

    public ProductDto updateById(Integer id, ProductDto productDto) throws NotFound {
        Product product = this.getProductById(id);
        product = product.toBuilder()
                .enable(productDto.getEnable())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .build();
        product = repository.save(product);
        return helper.transformToProductDto(product);
    }

    public void deleteById(Integer id) {
        Product product = getProductById(id);
        product = product.toBuilder().enable(false).build();
        repository.save(product);
    }

    private Product getProductById(Integer id) throws NotFound {
        Optional<Product> product = repository.findById(id);
        if (!product.isPresent())
            throw NotFound.create(NOT_FOUND, NOT_FOUND.getReasonPhrase(), EMPTY, String.format("Product %d Not Found", id).getBytes(), defaultCharset());

        return product.get();
    }

    public Page<ProductDto> getAll(Integer offset, Integer limit) {
        offset = getValue(offset, DEFAULT_OFFSET);
        limit = getValue(offset, DEFAULT_LIMIT);
        Pageable pageable = PageRequest.of(offset, limit, Sort.Direction.ASC);
        Page<Product> products = repository.findAll(pageable);
        List<ProductDto> productDtos = products.getContent().stream().map(helper::transformToProductDto).collect(Collectors.toList());
        return new PageImpl<>(productDtos);
    }

    private Integer getValue(Integer number, Integer defaultValue) {
        return number != null ? number : defaultValue;
    }
}
