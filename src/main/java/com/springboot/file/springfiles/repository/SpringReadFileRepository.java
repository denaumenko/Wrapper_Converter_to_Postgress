package com.springboot.file.springfiles.repository;


import com.springboot.file.springfiles.model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringReadFileRepository extends JpaRepository<Entity,Long> {

    List<Entity> findByField1(String name);

}
