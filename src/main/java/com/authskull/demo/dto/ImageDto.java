package com.authskull.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class ImageDto {

    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String file;

    @NotNull
    @NotBlank
    private Boolean enable;

    public ImageDto() {
        this(null, null, null, null);
    }
}
