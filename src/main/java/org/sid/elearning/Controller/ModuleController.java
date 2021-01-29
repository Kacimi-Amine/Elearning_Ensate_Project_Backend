package org.sid.elearning.Controller;

import org.sid.elearning.Exeption.ResourceNotFoundException;
import org.sid.elearning.Models.Classe;
import org.sid.elearning.Models.Module;
import org.sid.elearning.Repositories.ClasseRepository;
import org.sid.elearning.Repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ModuleController {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ClasseRepository classeRepository;



    @GetMapping(value = "/modules")
    public List<Module> getAllModule() {
        return moduleRepository.findAll();

    }
    @GetMapping(value = "/modules/{id}")
    public Optional<Module> getModule(@PathVariable long id) {
        return moduleRepository.findById(id);
    }

    //Add module(JAva) ( cocher un ou plusieur classe ) - BI GL ---->
    @PostMapping("/modules")
    public Module createModule(@Valid @RequestBody Module module)
    {
        return moduleRepository.save(module);
    }


    @PutMapping("/modules/{moduleId}")
    public Module updatePost(@PathVariable Long moduleId, @Valid @RequestBody Module artRequest) {
        return moduleRepository.findById(moduleId).map(Module -> {
            Module.setIntituler(artRequest.getIntituler());
            return moduleRepository.save(Module);
        }).orElseThrow(() -> new ResourceNotFoundException("Module  not found"));
    }
//add cour
    @DeleteMapping("/modules/{moduleId}")
    public ResponseEntity<?> deletePost(@PathVariable Long moduleId) {
        return moduleRepository.findById(moduleId).map(module -> {
            moduleRepository.delete(module);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Module not found"));
    }
}
