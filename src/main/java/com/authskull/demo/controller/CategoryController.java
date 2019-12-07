package com.authskull.demo.controller;

import com.authskull.demo.dto.CategoryDto;
import com.authskull.demo.dto.NewCategory;
import com.authskull.demo.entity.Category;
import com.authskull.demo.manager.CategoryManager;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static com.authskull.demo.controller.CategoryController.CATEGORIES;

@RestController
@RequestMapping(CATEGORIES)
@Validated
public class CategoryController {

    public static final String CATEGORIES = "/categories";
    private CategoryManager manager;

    public CategoryController(CategoryManager manager) {
        this.manager = manager;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody NewCategory newCategory,
                                 UriComponentsBuilder locationBuilder) {
        Category category = manager.create(newCategory);
        UriComponents uriComponents =
                locationBuilder.path(CATEGORIES).path("/" + category.getId()).build();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable("id") Integer id) {
        return manager.getById(id);
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<CategoryDto> categories = manager.getAll();
        return ResponseEntity.ok().body(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable("id") Integer id, @Valid @RequestBody CategoryDto categoryDto) {
        manager.updateById(id, categoryDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id) {
        manager.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
