package com.skovdev.springlearn.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

public interface FilesRepository {
    /**
     * Saves original image to file storage + create and save thumbnail.
     * @param file image to save.
     * @return image and its thumbnail URI in storage
     */
    SaveImageResponse saveImageWithThumbnail(MultipartFile file);
    
    @Data
    @AllArgsConstructor
    class SaveImageResponse{
        private URI fullsizeImage;
        private URI thumbnailImage;
    }
}
