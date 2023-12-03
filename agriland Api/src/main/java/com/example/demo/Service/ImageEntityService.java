package com.example.demo.Service;

import com.example.demo.Entity.ImageEntity;
import com.example.demo.Repository.ImageEntityRepo;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.ContextedRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import  java.util.List;
import java.util.zip.DataFormatException;

@Service
public class ImageEntityService {

    @Autowired
    private ImageEntityRepo imageEntityRepo;

    public List<ImageEntity> getAllImages() {
        return imageEntityRepo.findAll();
    }

    public String uploadImage(MultipartFile imageFile) throws IOException {
        var imageToSave = ImageEntity.builder()
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                .build();

        imageEntityRepo.save(imageToSave);
        return "file uploaded successfully : " + imageFile.getOriginalFilename();
    }

    public byte[] downloadImage(String imageName) {
        Optional<ImageEntity> dbImage = imageEntityRepo.findByName(imageName);
        return dbImage.map(image -> {
            try {
                return ImageUtils.decompressImage(image.getImageData());
            } catch (DataFormatException | IOException exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception)
                        .addContextValue("Image ID",  image.getId())
                        .addContextValue("Image name", imageName);
            }
        }).orElse(null);
    }

    public byte[] downloadImageById(Long id) {
        Optional<ImageEntity> dbImage = imageEntityRepo.findById(id);
        return dbImage.map(image -> image.getImageData()).orElse(null);
    }
}
