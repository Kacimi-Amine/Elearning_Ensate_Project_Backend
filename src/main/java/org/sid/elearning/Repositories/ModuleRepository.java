package org.sid.elearning.Repositories;

import org.sid.elearning.Models.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("*")
public interface ModuleRepository extends JpaRepository<Module,Long> {
    public  Module findByIntituler(String name) ;
}
