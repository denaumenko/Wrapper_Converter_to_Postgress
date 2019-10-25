package com.springboot.file.springfiles.service;

import com.springboot.file.springfiles.model.Entity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SpringReadFileService  {
    List<Entity> findAll();
    void saveEntity(Entity entity);
    boolean saveDataFromUploadfile(MultipartFile file);


}
