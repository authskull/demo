package com.authskull.demo.controller;

import com.authskull.demo.dto.ImageDto;
import com.authskull.demo.dto.NewImage;
import com.authskull.demo.entity.Image;
import com.authskull.demo.manager.ImageManager;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static com.authskull.demo.controller.ImageController.IMAGES;


@RestController
@RequestMapping(IMAGES)
@Validated
public class ImageController {

    public static final String IMAGES = "/images";
    private ImageManager manager;

    public ImageController(ImageManager manager) {
        this.manager = manager;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody NewImage newImage,
                                 UriComponentsBuilder locationBuilder) {
        Image image = manager.create(newImage);
        UriComponents uriComponents =
                locationBuilder.path(IMAGES).path("/" + image.getId()).build();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping("/{id}")
    public ImageDto getById(@PathVariable("id") Integer id) {
        return manager.getById(id);
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<ImageDto> categories = manager.getAll();
        return ResponseEntity.ok().body(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable("id") Integer id, @Valid @RequestBody ImageDto imageDto) {
        manager.updateById(id, imageDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id) {
        manager.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
