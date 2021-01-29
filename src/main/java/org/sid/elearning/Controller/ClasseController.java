package org.sid.elearning.Controller;

import org.sid.elearning.Exeption.ResourceNotFoundException;
import org.sid.elearning.Models.Classe;
import org.sid.elearning.Models.Module;
import org.sid.elearning.Repositories.ClasseRepository;
import org.sid.elearning.Repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
//@PreAuthorize("isAuthenticated()")
@RequestMapping("/api")
public class ClasseController {
    @Autowired
    private ClasseRepository  classeRepository;

    @Autowired
    private ModuleRepository moduleRepository ;


    @PostMapping("/AddModulesToClass/{id}")
    public Classe  AddModulesToClass(@PathVariable long id , @RequestBody List<Module> modules){

         Classe classe = classeRepository.findById(id).get();
            classe.getModule().addAll(modules) ;
            return classeRepository.save(classe) ;

    }

    @PostMapping("/classes/{id}")
    public ResponseEntity<?> AddModuleToClass(@PathVariable long id , @RequestBody Module module){
        Classe classe = classeRepository.findById(id).get();
        Module existedModule=moduleRepository.findByIntituler(module.getIntituler());
            if(existedModule!=null){
                classe.getModule().add(existedModule);
                return new ResponseEntity<> (classeRepository.save(classe), HttpStatus.OK);
            }
        classe.getModule().add(module);
        return new ResponseEntity<> (  classeRepository.save(classe), HttpStatus.OK);
    }



    @GetMapping(value = "/classes/{id}")
    public Optional<Classe> getClasse(@PathVariable long id) {
        return classeRepository.findById(id);
    }

    @GetMapping(value = "/classes")
    public List<Classe> getAllClasse() {
        return classeRepository.findAll();
    }

    @GetMapping(value = "/classes/nombre")
    public long getnbr() {
        return classeRepository.count();
    }



    @PostMapping("/classes")
    public Classe createClasse(@Valid @RequestBody Classe classe) {
        return classeRepository.save(classe);
    }



    @PostMapping("/clamod/{id}")
    public Classe createClassewithModule( @PathVariable(value = "id") long id , @RequestBody Module[] module){
        Classe classe = classeRepository.findById(id).get();
        classe.setModule(Arrays.asList(module));
        //getModule().addAll();
        return classeRepository.save(classe);
    }



    @PutMapping("/classes/{classeId}")
    public ResponseEntity<?> updatePost(@PathVariable Long classeId, @Valid @RequestBody Classe artRequest) {
        return classeRepository.findById(classeId).map(Classe -> {
            Classe.setIntitulerclass(artRequest.getIntitulerclass());
             return new ResponseEntity<> (  classeRepository.save(Classe), HttpStatus.OK);

        }).orElseThrow(() -> new ResourceNotFoundException("Classe  not found"));
    }

    @DeleteMapping("/classes/{ClasseId}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "ClasseId") Long ClasseId ) {

            return classeRepository.findById(ClasseId).map(classe -> {
                classeRepository.delete(classe);
                return ResponseEntity.ok().build();
            }).orElseThrow(() -> new ResourceNotFoundException("classe not found"));

}
}
