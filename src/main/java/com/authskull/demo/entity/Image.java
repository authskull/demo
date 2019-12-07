package com.authskull.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@Entity(name = "images")
@AllArgsConstructor
public class Image implements Serializable {
    @ManyToMany(mappedBy = "productImages")
    Set<Product> products;
    @Id
    @SequenceGenerator(name = "seq_image_id",
            sequenceName = "seq_image_id",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_image_id")
    private Integer id;
    private String name;
    private byte[] file;
    private Boolean enable;

    public Image() {
        this(null, null, null, null, null);
    }
}
