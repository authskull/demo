package com.authskull.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class NewImage {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String file;

    public NewImage() {
        this(null, null);
    }
}
