package org.sid.elearning.Repositories;

import org.sid.elearning.Models.Article;
import org.sid.elearning.Models.Cour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Repository
@CrossOrigin("*")
public interface CourRepository extends JpaRepository<Cour,Long> {
     //Optional<Cour> findByModu_Intituler(String catId);
    //Optional<Cour> findByIdAndModu_Id(Long id, Long catId);
}
