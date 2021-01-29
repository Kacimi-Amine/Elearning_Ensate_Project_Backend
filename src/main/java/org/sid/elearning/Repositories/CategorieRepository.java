package org.sid.elearning.Repositories;


import org.sid.elearning.Models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Optional;

@Repository
@CrossOrigin("*")
public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    Categorie findCategorieByNom(String name);

    Boolean existsCategorieByNom(String name);
}
