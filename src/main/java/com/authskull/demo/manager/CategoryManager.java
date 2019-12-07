package com.authskull.demo.manager;

import com.authskull.demo.dto.CategoryDto;
import com.authskull.demo.dto.NewCategory;
import com.authskull.demo.entity.Category;
import com.authskull.demo.repository.CategoryRepository;
import com.authskull.demo.utils.Helper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.http.HttpHeaders.EMPTY;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class CategoryManager {

    private CategoryRepository repository;
    private Helper helper;

    public CategoryManager(CategoryRepository repository, Helper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    public Category create(NewCategory newCategory) {
        Category category = helper.transformToCategory(newCategory);
        return repository.save(category);
    }

    public CategoryDto getById(Integer id) throws NotFound {
        Category category = this.getCategoryById(id);
        return helper.transformToCategoryDto(category);
    }

    public CategoryDto updateById(Integer id, CategoryDto categoryDto) throws NotFound {
        Category category = this.getCategoryById(id);
        category = category.toBuilder()
                .enable(categoryDto.getEnable())
                .name(categoryDto.getName())
                .build();
        category = repository.save(category);
        return helper.transformToCategoryDto(category);
    }

    public void deleteById(Integer id) {
        Category category = getCategoryById(id);
        category = category.toBuilder().enable(false).build();
        repository.save(category);
    }

    public Category getCategoryById(Integer id) throws NotFound {
        Optional<Category> category = repository.findById(id);
        if (!category.isPresent())
            throw NotFound.create(NOT_FOUND, "", EMPTY, String.format("Category %d Not Found", id).getBytes(), defaultCharset());

        return category.get();
    }

    public List<CategoryDto> getAll() {
        return repository.findAll().stream().map(helper::transformToCategoryDto).collect(Collectors.toList());
    }
}
