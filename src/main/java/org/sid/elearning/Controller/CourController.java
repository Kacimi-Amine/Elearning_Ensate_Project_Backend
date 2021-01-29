package org.sid.elearning.Controller;


import org.sid.elearning.Exeption.ResourceNotFoundException;
import org.sid.elearning.Models.Cour;
import org.sid.elearning.Models.Module;
import org.sid.elearning.Repositories.CourRepository;
import org.sid.elearning.Repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class CourController {

    @Autowired
    private CourRepository courRepository;
    @Autowired
    private ModuleRepository moduleRepository;


    //get par nom de modules
    @GetMapping(value = "/cour/{modulename}/")
    //public List<Cour> getAllCour() {        return courRepository.findAll();}
    public List<Cour> getAllCourBymodulename(@PathVariable(value = "modulename") String modulename) {
        Module module = moduleRepository.findByIntituler(modulename);
        return module.getCours();
    }

    @GetMapping(value = "/cours")
    //public List<Cour> getAllCour() {        return courRepository.findAll();}
    public List<Cour> getAllCour() {
       // Module module = moduleRepository.findByIntituler(modulename);
        return courRepository.findAll();
    }
    @GetMapping(value = "/cours/nombre")
    public long getnbrcour() {
        return courRepository.count();
    }

    @PostMapping("/cours/{modulename}/")
        public ResponseEntity<?> createCour(@PathVariable (value = "modulename") String  modulename, @Valid @RequestBody Cour cour) {
         moduleRepository.findByIntituler(modulename).getCours().add(cour);
        return new ResponseEntity<>(  courRepository.save(cour), HttpStatus.OK);

    }

    //


    @PutMapping("/cours/{courId}")
    public Cour updateCour(@PathVariable (value = "courId") Long courId,@Valid @RequestBody Cour CourRequest) {
        return courRepository.findById(courId).map(Cour -> {
            Cour.setIntituler_cour(CourRequest.getIntituler_cour());
            return courRepository.save(Cour);
        }).orElseThrow(() -> new ResourceNotFoundException("CourId not found"));
    }



    @DeleteMapping("/cours/{courId}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "courId") Long courId ) {

        return courRepository.findById(courId).map(cour -> {
            courRepository.delete(cour);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("cour not found"));

    }
}
