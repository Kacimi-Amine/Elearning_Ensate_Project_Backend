package org.sid.elearning.Repositories;

import org.sid.elearning.Models.Article;
import org.sid.elearning.Models.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Repository
@CrossOrigin("*")
public interface ClasseRepository extends JpaRepository<Classe,Long> {
    Optional<Classe> findByIntitulerclass(String catId);
}
