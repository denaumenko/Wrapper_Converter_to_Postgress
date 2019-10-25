package com.springboot.file.springfiles.controller;


import com.springboot.file.springfiles.model.Entity;
import com.springboot.file.springfiles.repository.SpringReadFileRepository;
import com.springboot.file.springfiles.service.SpringReadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SpringReadController {

    @Autowired private SpringReadFileService springReadFileService;


    @Autowired  private SpringReadFileRepository springReadFileRepository;
    @GetMapping(value = "/")
    public String home(Model model){
        model.addAttribute("entity",new Entity());
        List<Entity> entities = springReadFileService.findAll();
        model.addAttribute("entities", entities);

        return "view/index";
    }
    @GetMapping("/new")
    public String newUser() {
        return "operations/new";
    }
    @PostMapping("/save")
    public String updateUser(@RequestParam String field1,
                             @RequestParam String field2,
                             @RequestParam String field3,
                             @RequestParam String field4
                             ) {
        springReadFileService.saveEntity(new Entity(field1,
                field2,
                field3,
                field4
                ));
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        Entity entity = springReadFileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid entity Id:" + id));
        model.addAttribute("entity", entity);
        return "operations/edit";
    }
    @PostMapping("/update")
    public String saveNote(@RequestParam Long id,
                           @RequestParam String field1,
                           @RequestParam String field2,
                           @RequestParam String field3,
                           @RequestParam String field4

                           ) {
        Entity entity = springReadFileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid entity Id:" + id));
        entity.setField1(field1);
        entity.setField2(field2);
        entity.setField3(field3);
        entity.setField4(field4);


        springReadFileRepository.save(entity);

        return "redirect:/";
    }




    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {

        try {
            Entity entity = springReadFileRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid entity Id:" + id));
            springReadFileRepository.delete(entity);
            List<Entity> entities = springReadFileService.findAll();
            model.addAttribute("entities", entities);
        }catch (Exception e) {
            return "redirect:/";
        }
        return "redirect:/";
    }




    @PostMapping(value = "/fileupload")
    public String uploadFile(@ModelAttribute Entity entity, RedirectAttributes redirectAttributes){
        boolean isFlag = springReadFileService.saveDataFromUploadfile(entity.getFile());
        if(isFlag){
            redirectAttributes.addFlashAttribute("sucessmeassage","File Upload Succesfully");
        }else{
            redirectAttributes.addFlashAttribute("errormeassage","Pls try again");
        }

        return "redirect:/";
    }



}
