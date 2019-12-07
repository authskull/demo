package com.authskull.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@Entity(name = "categories")
@AllArgsConstructor
public class Category implements Serializable {
    @ManyToMany(mappedBy = "productCategories")
    Set<Product> products;
    @Id
    @SequenceGenerator(name = "seq_category_id",
            sequenceName = "seq_category_id",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_category_id")
    private Integer id;
    private String name;
    private Boolean enable;

    public Category() {
        this(null, null, null, null);
    }
}
