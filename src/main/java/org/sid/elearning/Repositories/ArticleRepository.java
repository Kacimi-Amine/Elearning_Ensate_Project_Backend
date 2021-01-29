package org.sid.elearning.Repositories;

import org.sid.elearning.Models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Repository
@CrossOrigin("*")
public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findByStatus(Boolean t);
    //Optional<Article> findByIdAndCat_Id(Long id, Long catId);

}

