package com.authskull.demo.utils;

import com.authskull.demo.dto.*;
import com.authskull.demo.entity.Category;
import com.authskull.demo.entity.Image;
import com.authskull.demo.entity.Product;

import static java.util.stream.Collectors.toSet;

public class Helper {

    public Helper() {
    }

    public Product transformToProduct(NewProduct newProduct) {
        return Product.builder()
                .name(newProduct.getName())
                .enable(true)
                .description(newProduct.getDescription())
                .build();
    }

    public ProductDto transformToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .enable(product.getEnable())
                .description(product.getDescription())
                .categories(product.getProductCategories().stream().map(this::transformToCategoryDto).collect(toSet()))
                .images(product.getProductImages().stream().map(this::transformToImageDto).collect(toSet()))
                .build();
    }

    public Category transformToCategory(NewCategory newCategory) {
        return Category.builder()
                .name(newCategory.getName())
                .enable(true)
                .build();
    }

    public CategoryDto transformToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .enable(category.getEnable())
                .build();
    }

    public Image transformToImage(NewImage newImage) {
        return Image.builder()
                .name(newImage.getName())
                .file(newImage.getFile().getBytes())
                .enable(true)
                .build();
    }

    public ImageDto transformToImageDto(Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .file(new String(image.getFile()))
                .name(image.getName())
                .enable(image.getEnable())
                .build();
    }
}
