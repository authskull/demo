package com.authskull.demo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class NewProduct {

    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @JsonBackReference
    private Set<Integer> categories;

    @JsonBackReference
    private Set<Integer> images;

    public NewProduct() {
        this(null, null, null, null, null);
    }
}
