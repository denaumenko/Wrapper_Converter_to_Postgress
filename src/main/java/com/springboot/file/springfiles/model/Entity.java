package com.springboot.file.springfiles.model;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "newTable")
public class Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "field1")
    private String field1;
    @Column(name = "field2")
    private String field2;
    @Column(name = "field3")
    private String field3;
    @Column(name = "field4")
    private String field4;




    @Transient
    private MultipartFile file;


    public Entity(){}

    public Entity(String field1, String field2, String field3, String field4 ) {
        super();
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }


    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
