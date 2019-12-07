package com.authskull.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class NewCategory {

    @NotNull
    private String name;

    public NewCategory() {
        this(null);
    }
}
