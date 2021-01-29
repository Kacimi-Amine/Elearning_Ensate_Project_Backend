package org.sid.elearning.Controller;

import org.sid.elearning.Exeption.ResourceNotFoundException;
import org.sid.elearning.Models.Article;
import org.sid.elearning.Repositories.ArticleRepository;
import org.sid.elearning.Repositories.CategorieRepository;
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
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    //get par nom de categories


    @GetMapping(value = "/articles")
    public List<Article> getAllArticle() {
        return articleRepository.findAll();
    }

    @GetMapping(value = "/articles/active")
    public List<Article> getAllActiveArticle() {
        return articleRepository.findByStatus(true);
    }



    //


    @PutMapping("/articles/{ArticleId}")
    public ResponseEntity<?> updateArticle(@PathVariable (value = "ArticleId") Long ArticleId,
                                 @Valid @RequestBody Article ArticleRequest) {
        return articleRepository.findById(ArticleId).map(Article -> {
            Article.setNom_article(ArticleRequest.getNom_article());
            Article.setStatus(ArticleRequest.getStatus());
            Article.setDescription(ArticleRequest.getDescription());
            return new ResponseEntity<> (  articleRepository.save(Article), HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("ArticleId " + ArticleId + "not found"));
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "articleId") Long articleId) {
        return articleRepository.findById(articleId).map(article -> {
            articleRepository.delete(article);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("article not found with id  and catId "));
    }
}
