package com.authskull.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {

    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private Boolean enable;

    private Set<CategoryDto> categories;

    private Set<ImageDto> images;

    public ProductDto() {
        this(null, null, null, null, null, null);
    }
}
