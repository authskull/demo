package com.authskull.demo.manager;

import com.authskull.demo.dto.ImageDto;
import com.authskull.demo.dto.NewImage;
import com.authskull.demo.entity.Image;
import com.authskull.demo.repository.ImageRepository;
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
public class ImageManager {

    private ImageRepository repository;
    private Helper helper;

    public ImageManager(ImageRepository repository, Helper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    public Image create(NewImage newImage) {
        Image image = helper.transformToImage(newImage);
        return repository.save(image);
    }

    public ImageDto getById(Integer id) throws NotFound {
        Image image = this.getImageById(id);
        return helper.transformToImageDto(image);
    }

    public ImageDto updateById(Integer id, ImageDto imageDto) throws NotFound {
        Image image = this.getImageById(id);
        image = image.toBuilder()
                .enable(imageDto.getEnable())
                .name(imageDto.getName())
                .build();
        image = repository.save(image);
        return helper.transformToImageDto(image);
    }

    public void deleteById(Integer id) {
        Image image = getImageById(id);
        image = image.toBuilder().enable(false).build();
        repository.save(image);
    }

    public Image getImageById(Integer id) throws NotFound {
        Optional<Image> image = repository.findById(id);
        if (!image.isPresent())
            throw NotFound.create(NOT_FOUND, "", EMPTY, String.format("Image %d Not Found", id).getBytes(), defaultCharset());

        return image.get();
    }

    public List<ImageDto> getAll() {
        return repository.findAll().stream().map(helper::transformToImageDto).collect(Collectors.toList());
    }
}
