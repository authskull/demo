package com.authskull.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@Entity(name = "products")
@AllArgsConstructor
public class Product implements Serializable {
    @ManyToMany
    @JoinTable(
            name = "product_images",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    Set<Image> productImages;
    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Category> productCategories;
    @Id
    @SequenceGenerator(name = "seq_product_id",
            sequenceName = "seq_product_id",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product_id")
    private Integer id;
    private String name;
    private String description;
    private Boolean enable;

    public Product() {
        this(null, null, null, null, null, null);
    }

}
