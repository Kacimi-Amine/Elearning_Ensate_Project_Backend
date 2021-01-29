package org.sid.elearning.Controller;


import org.sid.elearning.Exeption.ResourceNotFoundException;
import org.sid.elearning.Models.Article;
import org.sid.elearning.Models.Categorie;
import org.sid.elearning.Models.Classe;
import org.sid.elearning.Repositories.ArticleRepository;
import org.sid.elearning.Repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class CategorieController {

        @Autowired
        private CategorieRepository categorieRepository;
        @Autowired
        private ArticleRepository articleRepository;


        @GetMapping(value = "/categories")
    public List<Categorie> listeCat(){
            return categorieRepository.findAll();
        }

    @GetMapping(value = "/categories/cat")
    public List<Categorie> listeCatplus(){
            List<Categorie> rst = new ArrayList<>();
        List<Categorie> categories =categorieRepository.findAll();
        for (Categorie cat:categories) {
             if(cat.getArticles().size() > 1 && rst.size()<4){
            rst.add(cat);
             }
        }
            return rst;
    }


     @GetMapping(value = "/articles/nombre")
    public long getnbrarticle() {
        return articleRepository.count();
    }
        @GetMapping(value = "/categories/{name}")
    public ResponseEntity<Categorie> getCourseById(@PathVariable String name)
    {
        //return categorieRepository.findCategorieByNom(name);
        Optional<Categorie> Data = Optional.ofNullable(categorieRepository.findCategorieByNom(name));

        if (Data.isPresent()) {
            return new ResponseEntity<>(Data.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

      /*  @GetMapping("/categories/{id}")
    public ResponseEntity<Categorie> getTutorialById(@PathVariable("id") long id) {
        Optional<Categorie> Data = categorieRepository.findById(id);

        if (Data.isPresent()) {
            return new ResponseEntity<>(Data.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } */
    

            @PostMapping(value = "/categories")
        public ResponseEntity<?> createcat(@RequestBody Categorie categorie) {
            try {
                String nom_cat=categorie.getNom();
                for(Categorie categories : categorieRepository.findAll()){
                    if(categories.getNom().equals(nom_cat)){
                        //return ;
                        return new ResponseEntity<> ( "categorie existe deja", HttpStatus.OK);

                    }

                }
                return new ResponseEntity<> ( categorieRepository.save(categorie), HttpStatus.OK);

                //return nom_cat + " added Successfully";
            }
            catch (Exception e){
                return new ResponseEntity<> ( "error categorie not added", HttpStatus.OK);

            }

        }
        //article
        @PostMapping("/categories/{categoryname}")
   public ResponseEntity<?> createArticle(@PathVariable (value = "categoryname") String categoryname,
                                     @Valid @RequestBody Article artc) {
            categorieRepository.findCategorieByNom(categoryname).getArticles().add(artc);
            return new ResponseEntity<>(articleRepository.save(artc), HttpStatus.OK);
        }
        @PutMapping("/categories/{categorieId}")
   public ResponseEntity<?> updatecate(@PathVariable Long categorieId, @Valid @RequestBody Categorie artRequest) {
        return categorieRepository.findById(categorieId).map(cat -> {
            cat.setNom(artRequest.getNom());
            cat.setUrl(artRequest.getUrl());
            return new ResponseEntity<> (  categorieRepository.save(cat), HttpStatus.OK);

        }).orElseThrow(() -> new ResourceNotFoundException("Classe  not found"));
    }
        @DeleteMapping("/categories/{categorieId}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "categorieId") Long categorieId ) {
        return categorieRepository.findById(categorieId).map(cat -> {
            categorieRepository.delete(cat);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("categorie not found")); }

    }
